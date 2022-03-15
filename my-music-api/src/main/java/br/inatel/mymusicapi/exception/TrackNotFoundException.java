package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackNotFoundException extends RuntimeException{
	private static final long serialVersionUID = -3247058845005257148L;

	private String trackId;
}
