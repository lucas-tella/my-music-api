package br.inatel.mymusicapi.dao;


import br.inatel.mymusicapi.model.User;

public class UserDao {
	
	private String id;
	private String name;
	private int totalPlaylists;
	
	public UserDao(User user) {
		this.id = user.getId();
		this.name = user.getName();
		this.totalPlaylists = user.getMyPlaylists().size();
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getTotalPlaylists() {
		return totalPlaylists;
	}
}
