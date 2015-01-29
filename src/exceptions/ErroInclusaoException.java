package exceptions;

/**
 * Classe ErroInclusaoExection
 * classe que trata os erros das exceções nas inclusões
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class ErroInclusaoException extends Exception {
	private static final String MESSAGE = "Ocorreu um erro incluindo o registro";

	/**
	  * É o método criador da classe
	  * 
	  */
	public ErroInclusaoException() {
		super(MESSAGE);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método
	  * 
	  * @param message        mensagem
	  * @param cause          causa
	  */
	public ErroInclusaoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param message         mensagem
	  */
	public ErroInclusaoException(String message) {
		super(MESSAGE + " " + message);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param cause         causa
	  */
	public ErroInclusaoException(Throwable cause) {
		super(MESSAGE, cause);
		// TODO Auto-generated constructor stub
	}

}