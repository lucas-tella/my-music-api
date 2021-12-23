package br.inatel.mymusicapi.dao;

import java.util.stream.Collectors;

import java.util.List;


import br.inatel.mymusicapi.model.Playlist;

public class PlaylistDao {

	private String id;
	private String title;
	private String description;
	private UserDao owner;
	private List<TracksDao> tracks;
	private int totalTracks;

	public PlaylistDao(Playlist playlist) {
		this.id = playlist.getPlaylistID();
		this.title = playlist.getTitle();
		this.description = playlist.getDescription();
		this.owner = new UserDao(playlist.getOwner());
//		this.tracks = new ArrayList<>(TracksDao.requestTracks(playlist.getTracksId()));
		this.totalTracks = playlist.getTracksId().size();
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public UserDao getOwner() {
		return owner;
	}

	public List<TracksDao> getTracks() {
		return tracks;
	}

	public int getTotalTracks() {
		return totalTracks;
	}
	
	public boolean isOwner(String id) {
		return this.owner.getId() == id;
	}
	
	public static List<PlaylistDao> convertToDaoList(List<Playlist> playlists) {
		
		List<PlaylistDao> playlistDao = playlists.stream().map(PlaylistDao::new).collect(Collectors.toList());
		
		return playlistDao;
	}
}
