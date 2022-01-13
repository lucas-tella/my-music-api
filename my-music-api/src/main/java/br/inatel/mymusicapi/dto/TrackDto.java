package br.inatel.mymusicapi.dto;

public class TrackDto {
	
	private String id;
	
	public TrackDto(String id) {
		this.id = id;
	}

	public TrackDto() {}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
}
