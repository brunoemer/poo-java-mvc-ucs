package model;

/**
 * Classe Jogo 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class Jogo extends Model {
	private Rodada rodada;
	private Time timeA;
	private Time timeB;
	private int nro_gols_a;
	private int nro_gols_b;
	private int realizado;
	
	public Jogo(){
		
	}	
	
	/**
	  * É o método criador da classe
	  * 
	  * @param rodada         numero da rodada
	  * @param timeA          time A
	  * @param timeB          time B
	  * @param nro_gols_a     numero de gols A
	  * @param nro_gols_b     numero de gols B
	  */	
	public Jogo(Rodada rodada, Time timeA, int nro_gols_a, Time timeB, int nro_gols_b) {
		this.rodada = rodada;
		this.timeA = timeA;
		this.nro_gols_a = nro_gols_a;
		this.timeB = timeB;
		this.nro_gols_b = nro_gols_b;
	}

	/**
	  * Busca o ID da Rodada
	  * 
	  * @return            Retorna um int contendo o id da rodada
	  */
	public int getIdRodada() {
		return rodada.getId();
	}

	/**
	  * Busca a Rodada
	  * 
	  * @return            Retorna a rodada
	  */
	public Rodada getRodada() {
		return rodada;
	}

	/**
	  * Seta a Rodada
	  * 
	  * @param rodada          Seta a rodada
	  */
	public void setRodada(Rodada rodada) {
		this.rodada = rodada;
	}

	/**
	  * Busca o Time A
	  * 
	  * @return            Retorna um Time
	  */
	public Time getTimeA() {
		return timeA;
	}

	/**
	  * Seta o Time A
	  * 
	  * @param timeA          Seta o Time
	  */
	public void setTimeA(Time timeA) {
		this.timeA = timeA;
	}

	/**
	  * Busca o Time B
	  * 
	  * @return            Retorna um Time
	  */
	public Time getTimeB() {
		return timeB;
	}

	/**
	  * Seta o Time B
	  * 
	  * @param timeB          Seta o Time
	  */
	public void setTimeB(Time timeB) {
		this.timeB = timeB;
	}

	/**
	  * Busca o id do Time A
	  * 
	  * @return            Retorna um int contendo o id do Time A
	  */
	public int getIdTimeA() {
		return this.timeA.getId();
	}

	/**
	  * Busca o id do Time B
	  * 
	  * @return            Retorna um int contendo o id do Time B
	  */
	public int getIdTimeB() {
		return this.timeB.getId();
	}

	/**
	  * Busca o numero de gols do time A
	  * 
	  * @return            Retorna um int contendo o numero de gols
	  */
	public int getNroGolsA() {
		return nro_gols_a;
	}

	/**
	  * Seta o numero de gols do time A
	  * 
	  * @param nro_gols_a          Int seta o numero de gols
	  */
	public void setNroGolsA(int nro_gols_a) {
		this.nro_gols_a = nro_gols_a;
	}

	/**
	  * Busca o numero de gols do time B
	  * 
	  * @return            Retorna um int contendo o numero de gols
	  */
	public int getNroGolsB() {
		return nro_gols_b;
	}

	/**
	  * Seta o numero de gols do time B
	  * 
	  * @param nro_gols_b          Int seta o numero de gols
	  */
	public void setNroGolsB(int nro_gols_b) {
		this.nro_gols_b = nro_gols_b;
	}

	/**
	  * Busca se já foi realizado
	  * 
	  * @return            Retorna um int se já foi realizado
	  */
	public int getRealizado() {
		return realizado;
	}

	/**
	  * Seta se já foi realizado
	  * 
	  * @param realizado          seta se foi realizado
	  */
	public void setRealizado(int realizado) {
		this.realizado = realizado;
	}
}