package br.inatel.mymusicapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InvalidPlaylistTitleException extends RuntimeException{
	private static final long serialVersionUID = 5976156529025971771L;
	
	private String title;
}
