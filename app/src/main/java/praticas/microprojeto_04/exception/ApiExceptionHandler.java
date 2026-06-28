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
		
		ErrorResponseDTO error = new ErrorResponseDTO(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				HttpStatus.NOT_FOUND.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}
}
