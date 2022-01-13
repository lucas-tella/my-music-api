package br.inatel.mymusicapi.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.mymusicapi.dto.ErrorDto;
import br.inatel.mymusicapi.dto.NewPlaylistDto;
import br.inatel.mymusicapi.dto.PlaylistDto;
import br.inatel.mymusicapi.dto.TrackDto;
import br.inatel.mymusicapi.dto.TrackExtendedDto;
import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.service.PlaylistService;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

	@Autowired 
	private PlaylistService playlistService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postPlaylist(@RequestBody @Valid NewPlaylistDto dto) {
		if(playlistService.isTitleValid(dto)) {
			PlaylistDto newPlaylist = playlistService.createNewPlaylist(dto);
			if(!(newPlaylist == null)) {
				return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto(
					404, "User id '" + dto.getUserId() + "' not found."));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorDto(
				403, "This title is already being used in another Playlist."));
	}
	
	@GetMapping
	public ResponseEntity<?> listAllPlaylistsByUserId(@RequestParam Long userId) {
		if (!(playlistService.getUserPlaylists(userId)==null)) {
			List<PlaylistDto> playlists = playlistService.getUserPlaylists(userId);
			return ResponseEntity.status(HttpStatus.OK).body(playlists);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User Id '" + userId + "' not found."));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> updatePlaylist(@PathVariable Long id,
			@RequestBody @Valid NewPlaylistDto dto) {
		PlaylistDto updatedPlaylist = playlistService.update(id, dto);
		if(!(updatedPlaylist==null)) {
			return ResponseEntity.status(HttpStatus.OK).body(updatedPlaylist);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist Id '" + id + "' not found."));	
		}
	
	@DeleteMapping ("/{id}")
	public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
		Long deletedPlaylistId = playlistService.deletePlaylist(id);
		if (!(deletedPlaylistId==null)) {
			return ResponseEntity.status(HttpStatus.OK).body("Playlist '" + id + "' deleted.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist '" + id + "' not found."));
	}	
	
	@PostMapping("/{id}/tracks")
	@Transactional
	public ResponseEntity<?> postTrack(@PathVariable Long id, @RequestBody TrackDto dto) {
		Playlist playlist = playlistService.getPlaylist(id);
		if (playlist==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(404, "Playlist '" + id + "' not found."));
		}
		TrackExtendedDto track = playlistService.addTrackToPlaylist(id, dto);
		if (!(track==null)) {
			playlistService.savePlaylist(playlist);
			return ResponseEntity.status(HttpStatus.OK).body(track);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Track '" + dto.getId() + "' not found or already added to playlist '" + id + "'."));
	}

	@GetMapping("/{id}/tracks")
	public ResponseEntity<?> listPlaylistTracksById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistTracks(id));
	}
		
//		Playlist playlist = playlistService.getPlaylist(id);
//		
//		if (playlist==null) {
//			
//			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//					new ErrorDto(404, "Playlist Id '" + id + "' not found."));
//		}
//		
//		if (!(playlistService.getUserPlaylists(id)==null)) {
//
//			List<PlaylistDto> playlists = playlistService.getUserPlaylists(id);
//		
//			return ResponseEntity.status(HttpStatus.OK).body(playlists);
//		}
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//				new ErrorDto(404, "User Id '" + id + "' not found."));
//	}

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