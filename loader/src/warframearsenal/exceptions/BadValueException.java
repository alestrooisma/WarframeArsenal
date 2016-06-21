package warframearsenal.exceptions;

/**
 *
 * @author Ale Strooisma
 */
public class BadValueException extends Exception {

	public BadValueException() {
	}

	public BadValueException(String message) {
		super(message);
	}

	public BadValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public BadValueException(Throwable cause) {
		super(cause);
	}

	public BadValueException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
