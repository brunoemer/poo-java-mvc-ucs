package model;

public class Rodada extends Model {
	private int id;
	private Campeonato campeonato;
	private String data;
	private int numero;
	private int realizado;

	public Rodada(){
		
	}

	/**
	  * E o metodo criador da classe a Rodada
	  * 
	  * @param camepeonato          Seta o campeonato
	  * @param data                 Seta a data
	  * @param numero               Seta o numero
	  */
	public Rodada(Campeonato campeonato, String data, int numero){
		super();
		this.campeonato = campeonato;
		this.data = data;
		this.numero = numero;
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
	  * Busca o ID do Campeonato
	  * 
	  * @return            Retorna um int contendo o id do campeonato
	  */
	public int getIdCampeonato() {
		return campeonato.getId();
	}
	
	/**
	  * Busca o Campeonato
	  * 
	  * @return            Retorna o campeonato
	  */
	public Campeonato getCampeonato() {
		return campeonato;
	}

	/**
	  * Seta o campeonato
	  * 
	  * @param campeonato          Seta o campeonato
	  */
	public void setCampeonato(Campeonato campeonato) {
		this.campeonato = campeonato;
	}

	/**
	  * Busca a Data
	  * 
	  * @return            Retorna a data
	  */
	public String getData() {
		return data;
	}
	
	/**
	  * Seta a Rodada
	  * 
	  * @param rodada          Seta a rodada
	  */
	public void setData(String data) {
		this.data = data;
	}
	
	/**
	  * Busca o numero da rodada
	  * 
	  * @return            Retorna o numero da rodada
	  */
	public int getNumero() {
		return numero;
	}
	
	/**
	  * Seta a o numero da Rodada
	  * 
	  * @param numero          Seta a o numero da rodada
	  */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	  * Busca se a rodada ja foi realizada
	  * 
	  * @return            Retorna um int contendo o se a rodada ja foi realizada
	  */
	public int getRealizado() {
		return realizado;
	}

	/**
	  * Seta se a rodada ja foi realizada 
	  * 
	  * @param realizado          Seta se a rodada ja foi realizada
	  */
	public void setRealizado(int realizado) {
		this.realizado = realizado;
	}	
}