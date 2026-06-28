package praticas.microprojeto_04.dto;

import lombok.Builder;

@Builder
public record PetResponseDTO
(
		Long id,
		String name, 
		String species,
		String breed
) {}
