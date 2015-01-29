package exceptions;

/**
 * Classe ErroAlteracaoException
 * classe que trata os erros de execção na alteração 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class ErroAlteracaoException extends Exception {
	private static final String MESSAGE = "Ocorreu um erro alterando o registro";

	/**
	  * É o método criador da classe
	  * 
	  */
	public ErroAlteracaoException() {
		super(MESSAGE);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método
	  * 
	  * @param message        mensagem
	  * @param cause          causa
	  */
	public ErroAlteracaoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param message         mensagem
	  */
	public ErroAlteracaoException(String message) {
		super(MESSAGE + " " + message);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param cause         causa
	  */
	public ErroAlteracaoException(Throwable cause) {
		super(MESSAGE, cause);
		// TODO Auto-generated constructor stub
	}

}
