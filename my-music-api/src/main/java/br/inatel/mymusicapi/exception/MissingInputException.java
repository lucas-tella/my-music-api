package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MissingInputException extends RuntimeException{
	private static final long serialVersionUID = -2717415047025558480L;

	private String field;
}
