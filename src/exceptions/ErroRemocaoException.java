package exceptions;

/**
 * Classe ErroRemocaoExecption
 * classe que trata os erros de execções na remoção 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class ErroRemocaoException extends Exception {
	private static final String MESSAGE = "Ocorreu um erro removendo o registro";

	/**
	  * É o método criador da classe
	  * 
	  */
	public ErroRemocaoException() {
		super(MESSAGE);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método
	  * 
	  * @param message        mensagem
	  * @param cause          causa
	  */
	public ErroRemocaoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param message         mensagem
	  */
	public ErroRemocaoException(String message) {
		super(MESSAGE + " " + message);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param cause         causa
	  */
	public ErroRemocaoException(Throwable cause) {
		super(MESSAGE, cause);
		// TODO Auto-generated constructor stub
	}

}