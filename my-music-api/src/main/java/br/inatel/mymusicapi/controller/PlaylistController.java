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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
			if(dto.getUserId()==null) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
						new ErrorDto(403, "Please insert a valid userId."));
				}
			if(newPlaylist != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(newPlaylist);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(404, "User id " + dto.getUserId() + " not found."));
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
				new ErrorDto(403, "This title is already being used in another Playlist."));
	}
	@GetMapping(path="/user/{id}")
	//comentar sobre query parameter
	public ResponseEntity<?> listAllPlaylistsByUserId(@PathVariable Long id) {
		if (!(playlistService.getUserPlaylists(id)==null)) {
			List<PlaylistDto> playlists = playlistService.getUserPlaylists(id);
			return ResponseEntity.status(HttpStatus.OK).body(playlists);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "User Id " + id + " not found."));
	}
	@PutMapping(path="/{id}")
	public ResponseEntity<?> updatePlaylist(@PathVariable Long id,
			@RequestBody @Valid NewPlaylistDto dto) {
		if(dto.getUserId()!=null) {
			PlaylistDto updatedPlaylist = playlistService.update(id, dto);
			if(updatedPlaylist != null) {
				return ResponseEntity.status(HttpStatus.OK).body(updatedPlaylist);
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(404, "Playlist Id " + id + " not found."));	
			}
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(403, "Invalid User Id."));	
		}
	@DeleteMapping(path="/{id}")
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> deletePlaylist(@PathVariable Long id) {
		Long deletedPlaylistId = playlistService.deletePlaylist(id);
		if (!(deletedPlaylistId==null)) {
			return ResponseEntity.status(HttpStatus.OK).body(
					"Playlist " + id + " deleted.");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist " + id + " not found."));
	}	
	@PostMapping(path="/{id}/tracks")
	@Transactional
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> postTrack(@PathVariable Long id, @RequestBody TrackDto dto) {
		Playlist playlist = playlistService.getPlaylist(id);
		if (playlist==null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
					new ErrorDto(404, "Playlist " + id + " not found."));
		}
		TrackExtendedDto track = playlistService.addTrackToPlaylist(id, dto);
		if (track!=null) {
			return ResponseEntity.status(HttpStatus.OK).body(track);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto(
				400, "Track " + dto.getId() + " not found or already added to playlist " + id + "."));
	}
	@GetMapping(path="/{id}/tracks")
	@Cacheable(value="listPlaylistTracksById")
	public ResponseEntity<?> listPlaylistTracksById(@PathVariable Long id) {
		if (playlistService.getPlaylistTracks(id)!=null){
			return ResponseEntity.status(HttpStatus.OK)
					.body(playlistService.getPlaylistTracks(id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
				new ErrorDto(404, "Playlist " + id + " not found."));
	}
	@DeleteMapping(path="/{id}/tracks/{trackId}")
	@CacheEvict(value="listPlaylistTracksById", allEntries = true)
	public ResponseEntity<?> deleteTracks(@PathVariable Long id, @PathVariable String trackId){
		if (playlistService.deleteTracksFromPlaylist(id, trackId)!=null){
			playlistService.deleteTracksFromPlaylist(id, trackId);
			return ResponseEntity.status(HttpStatus.OK).body(
					"Track "+trackId+" deleted from playlist "+id+".");
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new ErrorDto(
						404, "Playlist "+id+" or track "+trackId+" not found."));
	}
}