package praticas.microprojeto_04.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PetController {

	@GetMapping("/pet")
	public String listarPets() {
		return "requisição recebida";
	}
}
