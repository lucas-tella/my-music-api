package br.inatel.mymusicapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.mymusicapi.adapter.ExternalApiAdapter;
import br.inatel.mymusicapi.dto.NewPlaylistDto;
import br.inatel.mymusicapi.dto.PlaylistDto;
import br.inatel.mymusicapi.dto.TrackDto;
import br.inatel.mymusicapi.dto.TrackExtendedDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.PlaylistRepository;
import br.inatel.mymusicapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlaylistRepository playlistRepository;
	@Autowired
	private ExternalApiAdapter adapter;
	
	public PlaylistDto createNewPlaylist(NewPlaylistDto dto) {
		Playlist playlist = new Playlist(dto);
		if(dto.getUserId()==null) {
			return null;
		}
		Optional<User> user = userRepository.findById(dto.getUserId());
		if(user.isPresent()) {
			User foundUser = user.get();
			playlist.setUser(foundUser);
			Playlist newPlaylist = playlistRepository.save(playlist);
			foundUser.getPlaylists().add(playlist);
			return new PlaylistDto(newPlaylist);
		}
		return null;
	}
	public boolean isTitleValid (NewPlaylistDto dto) {
		Optional <Playlist> optPlaylist = playlistRepository.findByTitle(dto.getTitle());
		if(optPlaylist.isPresent()) {
			return false;
		}
		return true;
	}
	public List<PlaylistDto> getUserPlaylists(Long userId) {
		Optional <User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			List<Playlist> playlists = user.get().getPlaylists();
			List<PlaylistDto> dto = new PlaylistDto().convert(playlists);
			return dto;
		}
		return null;
	}
	public Long deletePlaylist(Long id) {
			Optional<Playlist> playlist = playlistRepository.findById(id);
			if (playlist.isPresent()) {
				playlistRepository.deleteById(id);
				return playlist.get().getId();
			}
			return null;
	}
	public Playlist getPlaylist (Long id) {
		Optional<Playlist> playlist = playlistRepository.findById(id);
		if (playlist.isPresent()) {
			return playlist.get();
		}
		return null;
	}
	public PlaylistDto update(Long id, NewPlaylistDto dto) {
		Optional<Playlist> playlist = playlistRepository.findById(id);
		if (playlist.isPresent()) {
			Playlist foundPlaylist = playlist.get();
			Optional<Playlist> existingOpPlaylist = playlistRepository.findByTitle(dto.getTitle());
			Playlist existingPlaylist = existingOpPlaylist.get();
			if(existingPlaylist.getTitle() != dto.getTitle()) {
				foundPlaylist.setTitle(dto.getTitle());
				foundPlaylist.setDescription(dto.getDescription());
				savePlaylist(foundPlaylist);
				PlaylistDto updatedDto = new PlaylistDto(foundPlaylist);
				return updatedDto;
			}
			return null;
		}
		return null;
	}
	public TrackExtendedDto addTrackToPlaylist (Long playlistId, TrackDto dto) {
		if (dto.getId()!=null) {
			Optional<Playlist> playlist = playlistRepository.findById(playlistId);
			Playlist foundPlaylist = playlist.get();
			if (playlist.isPresent() && !foundPlaylist.getTrackIds().contains(dto.getId())) {
					TrackExtendedDto track = adapter.getTrackById(dto.getId());
					if (track.getId()!=null) {
						foundPlaylist.getTrackIds().add(dto.getId());
						savePlaylist(foundPlaylist);
						return track;
				}
			}
			return null;
		}
		return null;
	}
	public PlaylistDto deleteTracksFromPlaylist(Long playlistId, String trackId) {
		Optional<Playlist> playlist = playlistRepository.findById(playlistId);
		if(!playlist.isPresent()) {
			return null;
		}
		TrackDto dto = new TrackDto(trackId);
		Playlist foundPlaylist = (playlist.get());
		if (playlist.get().getTrackIds().contains(dto.getId())) {
			foundPlaylist.getTrackIds().remove(dto.getId());
			playlistRepository.save(foundPlaylist);
			PlaylistDto playlistDto = new PlaylistDto(foundPlaylist);
			return playlistDto;
		}
		return null;
	}
	public Playlist savePlaylist (Playlist playlist) {
		return playlistRepository.save(playlist);
	}
	public PlaylistDto getPlaylistTracks (Long id) {
		Optional<Playlist> playlist = playlistRepository.findById(id);
		if (playlist.isPresent()) {
			PlaylistDto dto = new PlaylistDto(playlist.get());
			List<String> trackIds = playlist.get().getTrackIds();
			for(String trackId : trackIds) {
				TrackExtendedDto track = adapter.getTrackById(trackId);
				dto.getTracks().add(track);
			}
			return dto;
		}
		return null;
	}
}
