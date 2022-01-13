package br.inatel.mymusicapi.dto;

import lombok.Data;

@Data
public class TrackExtendedDto {
	
	private String id;
	private String title;
	private String duration;
	private ArtistDto artist;
	private AlbumDto album;
	
}
