package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 7302172341954918736L;

	private Long userId;
}