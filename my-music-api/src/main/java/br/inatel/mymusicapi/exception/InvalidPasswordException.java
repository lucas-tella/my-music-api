package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidPasswordException extends RuntimeException {
	private static final long serialVersionUID = 6312678371565157681L;

	private String password;
}
