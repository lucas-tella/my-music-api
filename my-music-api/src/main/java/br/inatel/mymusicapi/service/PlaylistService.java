package br.inatel.mymusicapi.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inatel.mymusicapi.dto.PlaylistDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.PlaylistRepository;
import br.inatel.mymusicapi.repository.UserRepository;

@Service
public class PlaylistService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PlaylistRepository playlistRepository;
	
	public void createNewPlaylist(User user) {
		
		userRepository.getById(user.getId());
		PlaylistDto dto = new PlaylistDto();
		dto.setOwner(user);
		
		Playlist newPlaylist = dto.convertToPlaylist();
		playlistRepository.save(newPlaylist);
	}
	
	
}
