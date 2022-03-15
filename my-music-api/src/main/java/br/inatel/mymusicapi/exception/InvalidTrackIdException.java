package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidTrackIdException extends RuntimeException{
	private static final long serialVersionUID = 1216602469073836952L;

	private String trackId;
}
