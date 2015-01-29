package persistence;

import model.Campeonato;
import exceptions.ErroAlteracaoException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe CampeonatoDAO 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public interface CampeonatoDAO {
	public int insere(Campeonato obj) throws ErroInclusaoException;
	public void altera(Campeonato obj) throws ErroAlteracaoException, ErroRemocaoException;
	public void remove(Campeonato obj) throws ErroRemocaoException;
	public Campeonato busca(int ano) throws RegistroNaoEncontradoException;
}
