package praticas.microprojeto_04.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

/**
 * Este DTO é utilizado em solicitações PUT para
 * atualização de dados de registros. 
 * 
 */

@Builder
public record UpdatePetRequestDTO
(
		String name,
		String species,
		String breed,
		String color,
		BigDecimal weight,
		Boolean vaccinated,
		LocalDate birthDate,
		String notes
		
) { }
