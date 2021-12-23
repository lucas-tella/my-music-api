package br.inatel.mymusicapi.model;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Playlist {
	
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String playlistID;
	
	private String title;
	private String description="Standard description";
	
	@ManyToOne()
	@JoinColumn(name="USER_ID", nullable=false, updatable=false)
	private User owner;
	
	private ArrayList<String> trackId = new ArrayList<>();

	public Playlist(String title, String description, User owner) {
		this.title = title;
		this.description = description;
		this.owner = owner;
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

	public String getPlaylistID() {
		return playlistID;
	}

	public User getOwner() {
		return owner;
	}

	public ArrayList<String> getTracksId() {
		return trackId;
	}
}