package praticas.microprojeto_04.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import praticas.microprojeto_04.dto.CreatePetRequestDTO;
import praticas.microprojeto_04.dto.PetResponseDTO;
import praticas.microprojeto_04.dto.UpdatePetRequestDTO;
import praticas.microprojeto_04.service.PetService;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/pets")
public class PetController {

	private final PetService service;
	
	@GetMapping
	public ResponseEntity<List<PetResponseDTO>> findAll() {
		List<PetResponseDTO> pets = service.findAll();
		return ResponseEntity.ok(pets);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PetResponseDTO> findById(@PathVariable Long id) {
		return ResponseEntity.ok(service.findById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<PetResponseDTO> update(@PathVariable Long id, @RequestBody UpdatePetRequestDTO requestDTO) {
		PetResponseDTO response = service.update(id,  requestDTO);
		return ResponseEntity.ok(response);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PostMapping
	public ResponseEntity<PetResponseDTO> create(@RequestBody CreatePetRequestDTO requestDTO) {
		PetResponseDTO responseDTO = service.create(requestDTO);
		URI location = URI.create("/pets/" + responseDTO.id());
		return ResponseEntity.created(location).body(responseDTO);
	}
}
