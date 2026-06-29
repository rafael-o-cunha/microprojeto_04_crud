package praticas.microprojeto_04.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;

@Builder
public record CreatePetRequestDTO
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
