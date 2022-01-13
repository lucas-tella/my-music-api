package br.inatel.mymusicapi.dto;

import java.util.LinkedList;
import java.util.List;

import java.util.stream.Collectors;

import br.inatel.mymusicapi.model.Playlist;

public class PlaylistDto {
	
	private Long id;
	private String title;
	private String description;
	private Long userId;
	private List<TrackExtendedDto> tracks;

	public PlaylistDto(Playlist playlist) {
		this.id = playlist.getId();
		this.title = playlist.getTitle();
		this.description = playlist.getDescription();
		this.userId = playlist.getUser().getId();
		this.tracks = new LinkedList<TrackExtendedDto>();
	}
	
	public PlaylistDto() {}
	
	public List<PlaylistDto> convert(List<Playlist> playlists) {
		
		return playlists.stream().map(PlaylistDto::new).collect(Collectors.toList());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<TrackExtendedDto> getTracks() {
		return tracks;
	}

	public void setTracks(List<TrackExtendedDto> tracks) {
		this.tracks = tracks;
	}
}
