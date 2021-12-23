package br.inatel.mymusicapi.dao;

import java.util.List;
import java.util.stream.Collectors;

import br.inatel.mymusicapi.ApiResources.Album;
import br.inatel.mymusicapi.ApiResources.Artist;
import br.inatel.mymusicapi.ApiResources.ApiTrack;

public class TracksDao {

	private String trackId;
	private String title;
	private Artist artist;
	private Album album;
	private int duration;
	private String url;
	
	public TracksDao(ApiTrack track) {
		this.trackId = track.getTrackId();
		this.title = track.getTitle();
		this.artist = track.getArtist();
		this.album = track.getAlbum();
		this.duration = track.getDuration();
		this.url = track.getLink();
	}
	
	public static List<TracksDao> convertToDaoList(List<ApiTrack> tracks) {
		List<TracksDao> tracksDao = tracks.stream().map(TracksDao::new).collect(Collectors.toList());
		return tracksDao;
	}

//	public static List<TracksDao> requestTracks(List<String> tracksId) {
//		ApiTrack track = new ApiTrack();
//		List<ApiTrack> tracks = track.requestTrackById(tracksId);
//
//		return convertToDaoList(tracks);
//	}

	public String getTrackId() {
		return trackId;
	}

	public String getTitle() {
		return title;
	}

	public Artist getArtist() {
		return artist;
	}

	public Album getAlbum() {
		return album;
	}

	public int getDuration() {
		return duration;
	}

	public String getUrl() {
		return url;
	}
	
}
