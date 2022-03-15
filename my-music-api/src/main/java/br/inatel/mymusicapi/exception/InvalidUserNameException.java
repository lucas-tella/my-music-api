package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidUserNameException extends RuntimeException{
	private static final long serialVersionUID = -8694155242569749785L;
	
	private String userName;
}
