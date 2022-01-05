package br.inatel.mymusicapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.mymusicapi.dto.NewUserDto;
import br.inatel.mymusicapi.dto.PlaylistDto;
import br.inatel.mymusicapi.dto.UserDto;
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
	
	public PlaylistDto createNewPlaylist(PlaylistDto dto, Long userId) {
		
		Playlist playlist = Playlist.builder()
				.title(dto.getTitle())
				.description(dto.getDescription())
				.build();
		
		Optional<Playlist> foundPlaylist = playlistRepository.findById(playlist.getId());
		
		Optional<User> foundUser = userRepository.findById(userId);
		
		if(!foundPlaylist.isPresent() && foundUser.isPresent()) {
			
			Playlist newPlaylist = playlistRepository.save(playlist);
			
			User user = foundUser.get();

			return PlaylistDto.builder()
					.id(newPlaylist.getId())
					.title(newPlaylist.getTitle())
					.description(newPlaylist.getDescription())
					.user(user)
					.build();
		}
		
		return null;
	}
}
