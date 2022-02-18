package br.inatel.mymusicapi.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.inatel.mymusicapi.dto.NewPlaylistDto;

@Entity
@Table(name = "playlist")
public class Playlist {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String title;
	private String description;
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	@ElementCollection
	@CollectionTable(name="playlist_tracks")
	private List<String> trackIds;
	
	public Playlist(NewPlaylistDto dto) {
		this.title = dto.getTitle();
		this.description = dto.getDescription();
	}
	public Playlist() {
		
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
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<String> getTrackIds() {
		return trackIds;
	}
	public void setTrackIds(List<String> trackIds) {
		this.trackIds = trackIds;
	}
}