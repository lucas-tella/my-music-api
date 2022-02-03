package br.inatel.mymusicapi.controller;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(404, "User id " + dto.getUserId() + " not found."));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
				new ErrorDto(403, "This title is already being used in another Playlist."));
	}
	
	@GetMapping
	public ResponseEntity<?> listAllPlaylistsByUserId(@RequestParam Long userId) {
		if (!(playlistService.getUserPlaylists(userId)==null)) {
			List<PlaylistDto> playlists = playlistService.getUserPlaylists(userId);
			return ResponseEntity.status(HttpStatus.OK).body(playlists);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User Id " + userId + " not found."));
	}
	
	@PutMapping
	public ResponseEntity<?> updatePlaylist(@RequestParam Long playlistId,
			@RequestBody @Valid NewPlaylistDto dto) {
		PlaylistDto updatedPlaylist = playlistService.update(playlistId, dto);
		if(!(updatedPlaylist==null)) {
			return ResponseEntity.status(HttpStatus.OK).body(updatedPlaylist);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist Id " + playlistId + " not found."));	
		}
	
	@DeleteMapping
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> deletePlaylist(@RequestParam Long playlistId) {
		Long deletedPlaylistId = playlistService.deletePlaylist(playlistId);
		if (!(deletedPlaylistId==null)) {
			return ResponseEntity.status(HttpStatus.OK).body(
					"Playlist " + playlistId + " deleted.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist " + playlistId + " not found."));
	}	
	
	@PostMapping("/tracks")
	@Transactional
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> postTrack(@RequestParam Long playlistId, @RequestBody TrackDto dto) {
		Playlist playlist = playlistService.getPlaylist(playlistId);
		if (playlist==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(404, "Playlist " + playlistId + " not found."));
		}
		TrackExtendedDto track = playlistService.addTrackToPlaylist(playlistId, dto);
		if (!(track==null)) {
			playlistService.savePlaylist(playlist);
			return ResponseEntity.status(HttpStatus.OK).body(track);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Track " + dto.getId() + " not found or already added to playlist " + playlistId + "."));
	}

	@GetMapping("/tracks")
	@Cacheable(value="listPlaylistTracksById")
	public ResponseEntity<?> listPlaylistTracksById(@RequestParam Long playlistId) {
		if (playlistService.getPlaylistTracks(playlistId)!=null){
			return ResponseEntity.status(HttpStatus.OK).body(playlistService.getPlaylistTracks(playlistId));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist " + playlistId + " not found."));
	}
}