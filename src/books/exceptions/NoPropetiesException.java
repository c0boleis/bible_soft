package books.exceptions;

public class NoPropetiesException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5142019966622062089L;

	public NoPropetiesException() {
		super("The file \"info.properties\" wasn't found");
	}

	public NoPropetiesException(String message) {
		super(message);
	}

	public NoPropetiesException(Throwable cause) {
		super(cause);
	}

	public NoPropetiesException(String message, Throwable cause) {
		super(message, cause);
	}

	public NoPropetiesException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
