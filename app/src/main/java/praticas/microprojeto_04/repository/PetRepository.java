package praticas.microprojeto_04.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import praticas.microprojeto_04.entity.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{
	
	List<Pet> findByDeletedFalse();
	
	Optional<Pet> findByIdAndDeletedFalse(Long id);
}
