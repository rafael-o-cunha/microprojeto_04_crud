package praticas.microprojeto_04.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;
import praticas.microprojeto_04.dto.ErrorResponseDTO;

@RestControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<ErrorResponseDTO> handlePetNotFound(PetNotFoundException ex, HttpServletRequest  request) {
		return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), request);
	}
	
	@ExceptionHandler(PetAlreadyDeletedException.class)
	public ResponseEntity<ErrorResponseDTO> handlePetAlreadyDeleted(PetAlreadyDeletedException ex, HttpServletRequest request) {
		return buildErrorResponse(HttpStatus.CONFLICT, ex.getMessage(), request);
	}
	
	private ResponseEntity<ErrorResponseDTO> buildErrorResponse(HttpStatus status, String message, HttpServletRequest request) {
		ErrorResponseDTO error = new ErrorResponseDTO(
				LocalDateTime.now(),
				status.value(),
				status.getReasonPhrase(),
				message,
				request.getRequestURI());
		
		return ResponseEntity.status(status).body(error);
	}
}
