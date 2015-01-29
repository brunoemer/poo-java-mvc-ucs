package persistence;

import java.util.List;

import model.Campeonato;
import model.Jogo;
import model.Rodada;
import exceptions.ErroAlteracaoException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe JogoDAO 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public interface JogoDAO {
	public void insere(Jogo obj) throws ErroInclusaoException;
	public void altera(Jogo obj) throws ErroAlteracaoException;
	public void remove(Jogo obj) throws ErroRemocaoException;
	public List<Jogo> lista(Rodada rodada) throws RegistroNaoEncontradoException;
	public List<Object[]> listaClassificacao(Campeonato campeonato) throws RegistroNaoEncontradoException;
	public List<Object[]> listaGrafico(Campeonato campeonato) throws RegistroNaoEncontradoException;
}
