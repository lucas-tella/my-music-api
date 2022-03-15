package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlaylistNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 5196037395235624573L;

	private Long playlistId;
}
