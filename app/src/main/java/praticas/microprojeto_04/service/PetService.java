package praticas.microprojeto_04.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import praticas.microprojeto_04.dto.PetResponseDTO;
import praticas.microprojeto_04.entity.Pet;
import praticas.microprojeto_04.repository.PetRepository;

@RequiredArgsConstructor
@Service
public class PetService {

	private final PetRepository repository;
	
	public List<PetResponseDTO> findAll() {
		List<Pet> pets = repository.findByDeletedFalse();
		return pets.stream().map(this::toResponse).toList();
	}
	
	private PetResponseDTO toResponse(Pet pet) {
		return PetResponseDTO.builder()
				.id(pet.getId())
				.name(pet.getName())
				.species(pet.getSpecies())
				.breed(pet.getBreed())
				.build();
	}
}
