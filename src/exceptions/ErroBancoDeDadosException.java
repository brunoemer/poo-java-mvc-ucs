package exceptions;

/**
 * Classe ErroBancoDeDados
 * classe que trata dos erros do banco de dados
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class ErroBancoDeDadosException extends Exception {
	private static final String MESSAGE = "Ocorreu um erro com o banco de dados";

	/**
	  * É o método criador da classe
	  * 
	  */
	public ErroBancoDeDadosException() {
		super(MESSAGE);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método
	  * 
	  * @param message        mensagem
	  * @param cause          causa
	  */
	public ErroBancoDeDadosException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param message         mensagem
	  */
	public ErroBancoDeDadosException(String message) {
		super(MESSAGE + " " + message);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param cause         causa
	  */
	public ErroBancoDeDadosException(Throwable cause) {
		super(MESSAGE, cause);
		// TODO Auto-generated constructor stub
	}

}