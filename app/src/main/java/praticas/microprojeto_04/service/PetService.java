package praticas.microprojeto_04.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import praticas.microprojeto_04.dto.CreatePetRequestDTO;
import praticas.microprojeto_04.dto.PetResponseDTO;
import praticas.microprojeto_04.dto.UpdatePetRequestDTO;
import praticas.microprojeto_04.entity.Pet;
import praticas.microprojeto_04.exception.PetNotFoundException;
import praticas.microprojeto_04.repository.PetRepository;

@RequiredArgsConstructor
@Service
public class PetService {

	private final PetRepository repository;
	
	public List<PetResponseDTO> findAll() {
		List<Pet> pets = repository.findByDeletedFalse();
		return pets.stream().map(this::toResponse).toList();
	}
	
	public PetResponseDTO findById(Long id) {
		Pet pet = repository.findByIdAndDeletedFalse(id).orElseThrow(() -> new PetNotFoundException(id));
		return toResponse(pet);
	}
	
	public PetResponseDTO update(Long id, UpdatePetRequestDTO requestDTO) {
		
		Pet pet = repository.findByIdAndDeletedFalse(id).orElseThrow(() -> new PetNotFoundException(id));
		
		pet.setName(requestDTO.name());
	    pet.setSpecies(requestDTO.species());
	    pet.setBreed(requestDTO.breed());
	    pet.setColor(requestDTO.color());
	    pet.setWeight(requestDTO.weight());
	    pet.setVaccinated(requestDTO.vaccinated());
	    pet.setBirthDate(requestDTO.birthDate());
	    pet.setNotes(requestDTO.notes());
	    pet.setUpdated_at(LocalDateTime.now());
	    
	    Pet updatedPet = repository.save(pet);
	    
	    return toResponse(updatedPet);
	}
	
	public void delete(Long id) {
		Pet pet = repository.findById(id).orElseThrow(() -> new PetNotFoundException(id));
		pet.markAsDeleted();
		repository.save(pet);
	}
	
	public PetResponseDTO create(CreatePetRequestDTO requestDTO) {
		LocalDateTime now = LocalDateTime.now();
		
		Pet pet = Pet.builder() 
				.name(requestDTO.name())
				.species(requestDTO.species())
				.breed(requestDTO.breed())
				.color(requestDTO.color())
				.weight(requestDTO.weight())
				.vaccinated(requestDTO.vaccinated())
				.birthDate(requestDTO.birthDate())
				.notes(requestDTO.notes())
				.deleted(Boolean.FALSE)
				.created_at(now)
				.updated_at(now)
				.build();
		
		Pet createdPet = repository.save(pet);
		
		return toResponse(createdPet);
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
