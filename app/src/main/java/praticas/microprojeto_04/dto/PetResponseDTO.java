package praticas.microprojeto_04.dto;

import lombok.Builder;

/**
 *  Este DTO é utilizado na saída padrão de
 *  solicitações do verbo GET
 *  - FindAll
 *  - FIndByID
 * */
@Builder
public record PetResponseDTO
(
		Long id,
		String name, 
		String species,
		String breed
) {}
