package br.inatel.mymusicapi.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
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
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/playlists")
public class PlaylistController {
	
	@Autowired 
	private PlaylistService playlistService;
	
	@PostMapping
	@Transactional
	public ResponseEntity<?> postPlaylist(@RequestBody @Valid NewPlaylistDto dto) {
		PlaylistDto newPlaylist = playlistService.createNewPlaylist(dto);
		log.info("New playlist successfuly created.");
		return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
	}
	
//	@GetMapping(path="/user/{id}")
//	//comentar sobre query parameter
//	public ResponseEntity<Page<?>> listAllPlaylistsByUserId(
//			@PathVariable Long id, @RequestParam("page") int page, @RequestParam("size") int size) {
//		if ((playlistService.getUserPlaylists(id)!=null)) {
//			List<PlaylistDto> playlists = playlistService.getUserPlaylists(id);
//			return ResponseEntity.status(HttpStatus.OK).body(playlists);
//		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
//				new ErrorDto(404, "User Id " + id + " not found."));
//	}
	
	@GetMapping
	public ResponseEntity<?> listAllPlaylistsByUserId(@PathParam(value = "user") Long user) {
		List<PlaylistDto> playlists = playlistService.getUserPlaylists(user);
		return ResponseEntity.status(HttpStatus.OK).body(playlists);
	}
	
	@PutMapping(path="/{id}")
	@Transactional
	public ResponseEntity<?> updatePlaylist(@PathVariable Long id, @RequestBody @Valid NewPlaylistDto dto) {
		PlaylistDto updatedPlaylist = playlistService.update(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(updatedPlaylist);
	}
	
	@DeleteMapping(path="/{id}")
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
		playlistService.deletePlaylist(id);
		log.info("Playlist successfuly deleted.");
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
	
	@PostMapping(path="/{id}/tracks")
	@Transactional
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> postTrack(@PathVariable Long id, @RequestBody TrackDto dto) {
		playlistService.getPlaylist(id);
		TrackExtendedDto track = playlistService.addTrackToPlaylist(id, dto);
		return ResponseEntity.status(HttpStatus.OK).body(track);
	}
	
	@GetMapping(path="/{id}/tracks")
	@Cacheable(value="listPlaylistTracksById")
	public ResponseEntity<?> listPlaylistTracksById(@PathVariable Long id) {
		return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistTracks(id));
	}
	
	@DeleteMapping(path="/{id}/tracks/{trackId}")
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> deleteTracks(@PathVariable Long id, @PathVariable String trackId){
		playlistService.deleteTracksFromPlaylist(id, trackId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
	}
}