package br.inatel.mymusicapi.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.inatel.mymusicapi.dao.PlaylistDao;
import br.inatel.mymusicapi.dto.ErrorDto;
import br.inatel.mymusicapi.dto.PlaylistDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;
import br.inatel.mymusicapi.repository.LikeRepository;
import br.inatel.mymusicapi.repository.PlaylistRepository;
import br.inatel.mymusicapi.repository.UserRepository;
import br.inatel.mymusicapi.service.PlaylistService;
import br.inatel.mymusicapi.service.UserService;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

	@Autowired 
	private PlaylistService playlistService;
	
	
	@PostMapping("/{id}")
	@Transactional
	public ResponseEntity<?> postPlaylist(@RequestBody @Valid PlaylistDto dto,
					UriComponentsBuilder uriBuilder, @PathVariable("id") Long userId){
		
		PlaylistDto newPlaylist = playlistService.createNewPlaylist(dto, userId);
		
		URI uri = uriBuilder.path("/playlists/{id}").buildAndExpand(newPlaylist.getPlaylistID()).toUri();
		
		return ResponseEntity.created(uri).body(new PlaylistDao(newPlaylist));
	}
	
	@GetMapping
	public ResponseEntity<?> listAllPlaylists() {

		List<Playlist> playlists = playlistRepository.findAll();
		
		return ResponseEntity.status(HttpStatus.OK).body(PlaylistDao.convertToDaoList(playlists));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> listById(@PathVariable("id") String id) {

		Optional<Playlist> genericPlaylist = playlistRepository.findById(id);

		if (genericPlaylist.isPresent()) {
			PlaylistDao playlistDao = new PlaylistDao(genericPlaylist.get());
			
			return ResponseEntity.status(HttpStatus.OK).body(playlistDao);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(404, "Playlist not found."));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchByTitle(String title) {

		Optional<Playlist> genericPlaylist = playlistRepository.findByTitle(title);

		if (genericPlaylist.isPresent()) {
			PlaylistDao playlistDao = new PlaylistDao(genericPlaylist.get());
			
			return ResponseEntity.status(HttpStatus.OK).body(playlistDao);
		}

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(404, "Playlist not found."));
	}
	
//	@PutMapping("/{id}")
//	@Transactional
//	public ResponseEntity<PlaylistDao> updatePlaylist(@PathVariable("id") String id,
//			@RequestBody @Valid PlaylistDtoUpdate dto) {
//
//		Optional<Playlist> genericPlaylist = playlistRepository.findById(id);
//
//		if (genericPlaylist.isPresent()) {
//
//			if (User userId != genericPlaylist.get().getOwner().getId()) {
//				return ResponseEntity.status(403).build();
//			}
//
//			Playlist updatedPlaylist = dto.updatePlaylist(genericPlaylist.get());
//			
//			return ResponseEntity.ok(new PlaylistDao(updatedPlaylist));
//		}
//
//		return ResponseEntity.notFound().build();
//	}
}
