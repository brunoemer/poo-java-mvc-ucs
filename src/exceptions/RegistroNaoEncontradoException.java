package exceptions;

/**
 * Classe ErroRegistroNaoEncontradoExcpetion
 * classe que trata os erros de execção dos registros não encontrados 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class RegistroNaoEncontradoException extends Exception {
	private static final String MESSAGE = "Registro nao encontrado";

	/**
	  * É o método criador da classe
	  * 
	  */
	public RegistroNaoEncontradoException() {
		super(MESSAGE);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método
	  * 
	  * @param message        mensagem
	  * @param cause          causa
	  */
	public RegistroNaoEncontradoException(String message, Throwable cause) {
		super(MESSAGE + " " + message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param message         mensagem
	  */
	public RegistroNaoEncontradoException(String message) {
		super(MESSAGE + " " + message);
		// TODO Auto-generated constructor stub
	}

	/**
	  * É o método 
	  * 
	  * @param cause         causa
	  */
	public RegistroNaoEncontradoException(Throwable cause) {
		super(MESSAGE, cause);
		// TODO Auto-generated constructor stub
	}

}