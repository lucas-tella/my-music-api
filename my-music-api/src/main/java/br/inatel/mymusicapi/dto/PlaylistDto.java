package br.inatel.mymusicapi.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.inatel.mymusicapi.model.Playlist;
import br.inatel.mymusicapi.model.User;

public class PlaylistDto {
	
	@NotNull
	@NotEmpty
	private String title;
	private String description;
	private User owner;
	
	public Playlist convertToPlaylist() {
		return new Playlist(title, description, owner);
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
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	
}
