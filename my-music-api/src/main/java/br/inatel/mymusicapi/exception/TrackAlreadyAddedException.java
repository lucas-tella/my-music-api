package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrackAlreadyAddedException extends RuntimeException{
	private static final long serialVersionUID = -4781668005974376077L;

	private String title;  
}
