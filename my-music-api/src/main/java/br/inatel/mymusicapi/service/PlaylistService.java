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
import br.inatel.mymusicapi.exception.InvalidPlaylistTitleException;
import br.inatel.mymusicapi.exception.InvalidTrackIdException;
import br.inatel.mymusicapi.exception.MissingInputException;
import br.inatel.mymusicapi.exception.PlaylistNotFoundException;
import br.inatel.mymusicapi.exception.TrackAlreadyAddedException;
import br.inatel.mymusicapi.exception.TrackNotFoundException;
import br.inatel.mymusicapi.exception.UserNotFoundException;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.PlaylistRepository;
import br.inatel.mymusicapi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PlaylistService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlaylistRepository playlistRepository;
	@Autowired
	private ExternalApiAdapter adapter;
	
	public PlaylistDto createNewPlaylist(NewPlaylistDto dto) {
		Playlist playlist = new Playlist(dto);
		
		if (!isTitleValid(dto)) {
			throw new InvalidPlaylistTitleException(dto.getTitle());
		}
		
		if(dto.getUserId()==null) {
			throw new MissingInputException("userId");
		}
		
		Optional<User> user = userRepository.findById(dto.getUserId());
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(dto.getUserId());
		}
		
		User foundUser = user.get();
		playlist.setUser(foundUser);
		Playlist newPlaylist = playlistRepository.save(playlist);
		foundUser.getPlaylists().add(playlist);
		
		return new PlaylistDto(newPlaylist);
	}
	
	public boolean isTitleValid (NewPlaylistDto dto) {
		Optional <Playlist> optPlaylist = playlistRepository.findByTitle(dto.getTitle());
		
		if(optPlaylist.isPresent()) {
			return false;
		}
		
		return true;
	}
	
//	public List<PlaylistDto> getUserPlaylists(Long userId, Page pageable) {
//		Optional <User> user = userRepository.findByIdContaining(userId, pageable);
//		if(user.isPresent()) {
//			List<Playlist> playlists = user.get().getPlaylists();
//			List<PlaylistDto> dto = new PlaylistDto().convert(playlists);
//			return dto;
//		}
//		return null;
//	}
	
	public List<PlaylistDto> getUserPlaylists(Long userId) {
		Optional <User> user = userRepository.findById(userId);
		
		if(!user.isPresent()) {
			throw new UserNotFoundException(userId);
		}
		
		List<Playlist> playlists = user.get().getPlaylists();
		List<PlaylistDto> dto = new PlaylistDto().convert(playlists);
		
		return dto;
	}
	
	public Long deletePlaylist(Long id) {
			Optional<Playlist> playlist = playlistRepository.findById(id);
		
			if (!playlist.isPresent()) {
				throw new PlaylistNotFoundException(id);
			}
			
			playlistRepository.deleteById(id);
			return null;
	}
	
	public Playlist getPlaylist (Long id) {
		Optional<Playlist> playlist = playlistRepository.findById(id);
		
		if (!playlist.isPresent()) {
			throw new PlaylistNotFoundException(id);
		}
		
		return playlist.get();
	}
	
	public PlaylistDto update(Long id, NewPlaylistDto dto) {
		Playlist foundPlaylist = getPlaylist(id);
		
		if (!isTitleValid(dto)) {
			throw new InvalidPlaylistTitleException(dto.getTitle());
		}
		
		foundPlaylist.setTitle(dto.getTitle());
		foundPlaylist.setDescription(dto.getDescription());
		savePlaylist(foundPlaylist);
		PlaylistDto updatedDto = new PlaylistDto(foundPlaylist);
		
		return updatedDto;
	}
	
	public TrackExtendedDto addTrackToPlaylist (Long playlistId, TrackDto dto) {

		Playlist foundPlaylist = getPlaylist(playlistId);

		if (dto.getId()==null) {
			throw new MissingInputException("trackId");
		}
		
		TrackExtendedDto track = adapter.getTrackById(dto.getId());
		
		if (track.getId()==null) {
			throw new InvalidTrackIdException(dto.getId());
		}
		
		if (foundPlaylist.getTrackIds().contains(dto.getId())) {
			throw new TrackAlreadyAddedException(track.getTitle());
		}
		
		foundPlaylist.getTrackIds().add(dto.getId());
		savePlaylist(foundPlaylist);
		log.info("New track added to playlist.");
		
		return track;
	}
	
	public Playlist savePlaylist (Playlist playlist) {
		log.info("Saving playlist...");
		return playlistRepository.save(playlist);
	}
	
	public PlaylistDto getPlaylistTracks (Long id) {
		Optional<Playlist> playlist = playlistRepository.findById(id);
		if (!playlist.isPresent()) {
			throw new PlaylistNotFoundException(id);
		}
		
		PlaylistDto dto = new PlaylistDto(getPlaylist(id));
		List<String> trackIds = playlist.get().getTrackIds();
		
		for(String trackId : trackIds) {
			TrackExtendedDto track = adapter.getTrackById(trackId);
			dto.getTracks().add(track);
		}
		
		return dto;
	}
	
	public PlaylistDto deleteTracksFromPlaylist(Long playlistId, String trackId) {
		Optional<Playlist> playlist = playlistRepository.findById(playlistId);
		
		if(!playlist.isPresent()) {
			throw new PlaylistNotFoundException(playlistId);
		}
		
		Playlist foundPlaylist = getPlaylist(playlistId);
		TrackDto dto = new TrackDto(trackId);
		
		if (!foundPlaylist.getTrackIds().contains(dto.getId())) {
			throw new TrackNotFoundException(trackId);
		}

		foundPlaylist.getTrackIds().remove(dto.getId());
		playlistRepository.save(foundPlaylist);
		PlaylistDto playlistDto = new PlaylistDto(foundPlaylist);
		log.info("Track deleted.");
		
		return playlistDto;
	}
	
}