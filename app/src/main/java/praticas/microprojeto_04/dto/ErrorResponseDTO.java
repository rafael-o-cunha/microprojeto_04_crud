package praticas.microprojeto_04.dto;

import java.time.LocalDateTime;

import lombok.Builder;

/**
 *  Este DTO é utilizado na saída padrão de
 *  requisições para tratar retornos quando
 *  há erros.
 * */

@Builder
public record ErrorResponseDTO
(
	LocalDateTime timestamp,
	Integer status,
	String error,
	String message,
	String path
) {}
