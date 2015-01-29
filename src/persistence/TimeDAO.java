package persistence;

import java.util.List;

import model.Time;
import exceptions.ErroAlteracaoException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe TimeDAO 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public interface TimeDAO {
	public int insere(Time obj) throws ErroInclusaoException;
	public int altera(Time obj) throws ErroAlteracaoException;
	public int remove(int id) throws ErroRemocaoException;
	public List<Time> buscaTodos() throws RegistroNaoEncontradoException;
	public Time busca(String nome) throws RegistroNaoEncontradoException;
}
