package praticas.microprojeto_04.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import praticas.microprojeto_04.dto.PetResponseDTO;
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
}
