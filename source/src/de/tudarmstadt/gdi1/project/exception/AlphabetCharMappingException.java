package de.tudarmstadt.gdi1.project.exception;

/**
 * 
 * @author vamp
 * @version FINAL
 *
 */
public class AlphabetCharMappingException extends RuntimeException {

	private static final long serialVersionUID = 8925678053786926584L;

	/**
	 * Constructs a new InvalidCiphertextException without message
	 */
	public AlphabetCharMappingException() {
		super();
	}

	/**
	 * Constructs a new InvalidCiphertextException with message
	 * @param msg a message which gets displayed
	 */
	public AlphabetCharMappingException(String msg) {
		super(msg);
	}
}
