//package br.inatel.mymusicapi.exception;
//
//import static org.springframework.http.HttpStatus.CONFLICT;
//import static org.springframework.http.HttpStatus.NOT_FOUND;
//import java.sql.SQLSyntaxErrorException;
//import java.time.LocalDate;
//import java.util.List;
//import java.util.stream.Collectors;
//import org.springframework.dao.DataAccessResourceFailureException;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//
//@ControllerAdvice
//public class SystemExceptionHandler {
//	@ExceptionHandler({ SQLSyntaxErrorException.class, DataAccessResourceFailureException.class })
//	public ResponseEntity<ErrorResponse> handleSQLSyntaxErrorException(SQLSyntaxErrorException ex) {
//		ErrorResponse responseBody = ErrorResponse.builder().type(SYSTEM_ERROR)
//				.error(new Error(DATABASE_FAILURE, "Failed to communicate with system database.")).build();
//		return ResponseEntity.internalServerError().body(responseBody);
//	}
//
//}
