package br.inatel.mymusicapi.ApiResources;

public class Artist {

	private String id;
	private String name;
	private String link;
	private Integer totalAlbums;
	private String genre;

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getLink() {
		return link;
	}

	public Integer getTotalAlbums() {
		return totalAlbums;
	}

	public String getType() {
		return genre;
	}
}
