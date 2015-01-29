package persistence;

import java.util.List;

import model.Campeonato;
import model.Rodada;
import exceptions.ErroAlteracaoException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe RodadaDAO 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public interface RodadaDAO {
	public int insere(Rodada obj) throws ErroInclusaoException;
	public void altera(Rodada obj) throws ErroAlteracaoException;
	public void remove(Rodada obj) throws ErroRemocaoException;
	public List<Rodada> lista(Campeonato campeonato) throws RegistroNaoEncontradoException;
}
