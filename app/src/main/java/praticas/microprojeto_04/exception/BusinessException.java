package praticas.microprojeto_04.exception;

public abstract class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}
}
