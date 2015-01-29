package model;

public class Time extends Model {
	private int id;
	private String nome;
	private String estado;
	
	public Time(){
		
	}

	/**
	  * E o metodo criador da classe Time
	  * 
	  * @param nome          Seta o nomme  
	  * @param estado        Seta o estado UF
	  * @param id            Seta o id
	  */
	public Time(String nome, String estado, int id) {
		super();
		this.nome = nome;
		this.estado = estado;
		this.id = id;
	}

	/**
	  * Busca o ID
	  * 
	  * @return            Retorna um int contendo o id
	  */
	public int getId() {
		return id;
	}

	/**
	  * Seta o ID
	  * 
	  * @param id          Seta o ID
	  */
	public void setId(int id) {
		this.id = id;
	}

	/**
	  * Busca o Nome
	  * 
	  * @return            Retorna o nome
	  */
	public String getNome() {
		return nome;
	}

	/**
	  * Seta a Nome
	  * 
	  * @param nome          Seta o Nome
	  */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	  * Busca o estado UF
	  * 
	  * @return            Retorna um estado UF
	  */
	
	public String getEstado() {
		return estado;
	}

	/**
	  * Seta o Estado UF
	  * 
	  * @param estado          Seta o estado
	  */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * Metodo que retorna o hash dos times
	 * 
	 * @return result         nome e estado do time
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((estado == null) ? 0 : estado.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	/**
	 * Metodo equals dos times
	 * 
	 * @param obj            compara o estado e nome dos times
	 * @return               verdadeiro se for igual
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (estado == null) {
			if (other.estado != null)
				return false;
		} else if (!estado.equals(other.estado))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	/**
	 * Metodo toString
	 * 
	 * @return         nome e estado
	 */
	@Override
	public String toString() {
		return nome + " - " + estado;
	}
	
}