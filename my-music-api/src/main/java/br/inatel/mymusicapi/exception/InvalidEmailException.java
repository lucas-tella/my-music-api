package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidEmailException extends RuntimeException{
	private static final long serialVersionUID = -6144977413957313564L;

	private String email;
}
