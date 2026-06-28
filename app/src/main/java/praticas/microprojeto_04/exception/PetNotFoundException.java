package praticas.microprojeto_04.exception;

public class PetNotFoundException extends RuntimeException{

	public PetNotFoundException(Long id) {
		super("Pet com o id " + id + " não foi encontrado.");
	}
}
