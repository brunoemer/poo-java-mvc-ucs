package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Campeonato 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class Campeonato extends Model {
	private int id;
	private int ano;
	private List times;

	public Campeonato(){
		
	}
	
	/**
	  * É o método criador da classe
	  * 
	  * @param ano         ano do campeonato
	  * @param id          id do campeonato
	  */
	public Campeonato(int ano, int id) {
		super();
		this.ano = ano;
		this.id = id;
		this.times = new ArrayList<Time>();
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
	  * @param id          Int seta o id
	  */
	public void setId(int id) {
		this.id = id;
	}
	
	/**
	  * Busca o Ano
	  * 
	  * @return            Retorna um int contendo o ano
	  */
	public int getAno() {
		return ano;
	}
	
	/**
	  * Seta o Ano
	  * 
	  * @param ano         AAAA Ano 
	  */
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	/**
	  * Seta o Time
	  * 
	  * @param time        String com o nome do Time
	  */
	public void setTimes(List times){
		this.times = times;
	}
	
	/**
	  * Retorna uma lista de Times
	  * 
	  * @return            id
	  */
	public List<Time> getTimes(){
		return this.times;
	}
}