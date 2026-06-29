package praticas.microprojeto_04.exception;

public class PetAlreadyDeletedException extends BusinessException {

	public PetAlreadyDeletedException(Long id) {
		super("O pet com id " + id + " já está excluído.");
	}
}
