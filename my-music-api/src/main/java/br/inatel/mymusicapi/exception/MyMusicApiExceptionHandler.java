package br.inatel.mymusicapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.inatel.mymusicapi.dto.ErrorDto;

@ControllerAdvice
public class MyMusicApiExceptionHandler {

	@ExceptionHandler(MissingInputException.class)
    public ResponseEntity<ErrorDto> handleMissingInputException(MissingInputException ex) {	        
		String errorMessage = "Missing " + ex.getField() + " input field.";
		ErrorDto responseBody = new ErrorDto(HttpStatus.BAD_REQUEST.ordinal(), errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorDto> handleUserNotFoundException(UserNotFoundException ex){
		String errorMessage = "User " + ex.getUserId() + " not found.";
		ErrorDto responseBody = new ErrorDto(404, errorMessage);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}
	
	@ExceptionHandler(InvalidEmailException.class)
	public ResponseEntity<ErrorDto> handleInvalidEmailException(InvalidEmailException ex){
		String errorMessage = "Insert a valid email.";
		ErrorDto responseBody = new ErrorDto(400, errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

	@ExceptionHandler(InvalidUserNameException.class)
	public ResponseEntity<ErrorDto> handleIvalidUserNameExeption(InvalidUserNameException ex){
		String errorMessage = "Username must have under 15 characters.";
		ErrorDto responseBody = new ErrorDto(400, errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	@ExceptionHandler(InvalidPasswordException.class)
	public ResponseEntity<ErrorDto> handleIvalidPasswordExeption(InvalidPasswordException ex){
		String errorMessage = "Password must have at least 8 characters.";
		ErrorDto responseBody = new ErrorDto(400, errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	@ExceptionHandler(InvalidPlaylistTitleException.class)
	public ResponseEntity<ErrorDto> handleIvalidPlaylistTitleExeption(InvalidPlaylistTitleException ex){
		String errorMessage = "Title '" + ex.getTitle() + "' is already in use.";
		ErrorDto responseBody = new ErrorDto(400, errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	@ExceptionHandler(PlaylistNotFoundException.class)
	public ResponseEntity<ErrorDto> handlePlaylistNotFoundException(PlaylistNotFoundException ex){
		String errorMessage = "Playlist " + ex.getPlaylistId() + " not found.";
		ErrorDto responseBody = new ErrorDto(404, errorMessage);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}
	
	@ExceptionHandler(TrackAlreadyAddedException.class)
	public ResponseEntity<ErrorDto> handleMusicAlreadyAddedException(TrackAlreadyAddedException ex){
		String errorMessage = "Track '" + ex.getTitle() + "' already added to this playlist.";
		ErrorDto responseBody = new ErrorDto(400, errorMessage);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	@ExceptionHandler(TrackNotFoundException.class)
	public ResponseEntity<ErrorDto> handleTrackNotFoundException(TrackNotFoundException ex){
		String errorMessage = "Track " + ex.getTrackId() + " not found.";
		ErrorDto responseBody = new ErrorDto(404, errorMessage);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}
	
	@ExceptionHandler(InvalidTrackIdException.class)
	public ResponseEntity<ErrorDto> handleInvalidTrackIdException(InvalidTrackIdException ex){
		String errorMessage = "Track id '" + ex.getTrackId() + "' not found.";
		ErrorDto responseBody = new ErrorDto(400, errorMessage);
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(responseBody);
	}
}