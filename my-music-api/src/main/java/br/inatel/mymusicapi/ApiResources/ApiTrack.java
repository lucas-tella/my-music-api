package br.inatel.mymusicapi.ApiResources;

public class ApiTrack {

	private String trackId;
	private String link;
	private String title;
	private Artist artist;
	private Album album;
	private Integer duration;
	
	public String getTrackId() {
		return trackId;
	}
	public String getLink() {
		return link;
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
	public Integer getDuration() {
		return duration;
	}
}


//	public List<Track> requestTrackById(List<String> myTracksId) {
//		
//		List<Track> myTracks = new ArrayList<>();
//
//		myTracksId.forEach(id -> {
//			try {
//				myTracks.add(apiService.getTrackById(id));
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		});
//
//		return myTracks;
//	}