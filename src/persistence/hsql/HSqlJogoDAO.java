package persistence.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Campeonato;
import model.Jogo;
import model.Rodada;
import model.Time;
import persistence.JogoDAO;
import exceptions.ErroAlteracaoException;
import exceptions.ErroBancoDeDadosException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

public class HSqlJogoDAO implements JogoDAO {
	private Connection conn;

	public HSqlJogoDAO() {
		try {
			conn = HSqlDAOFactory.createConnection();
		} catch (ErroBancoDeDadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void insere(Jogo obj) throws ErroInclusaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("INSERT INTO jogo (id_rodada, id_time_a, id_time_b, nro_gols_a, nro_gols_b, realizado) VALUES (?, ?, ?, ?, ?, ?)");
			pstmt.setInt(1, obj.getIdRodada());
			pstmt.setInt(2, obj.getIdTimeA());
			pstmt.setInt(3, obj.getIdTimeB());
			pstmt.setInt(4, obj.getNroGolsA());
			pstmt.setInt(5, obj.getNroGolsB());
			pstmt.setInt(6, obj.getRealizado());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroInclusaoException("em Jogo", e);
		}
	}
	
	public void altera(Jogo obj) throws ErroAlteracaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("UPDATE jogo SET nro_gols_a = ?, nro_gols_b = ?, realizado = ? WHERE id_rodada = ? AND id_time_a = ? AND id_time_b = ?");
			pstmt.setInt(1, obj.getNroGolsA());
			pstmt.setInt(2, obj.getNroGolsB());
			pstmt.setInt(3, obj.getRealizado());
			pstmt.setInt(4, obj.getIdRodada());
			pstmt.setInt(5, obj.getIdTimeA());
			pstmt.setInt(6, obj.getIdTimeB());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroAlteracaoException("em Jogo", e);
		}
	}

	public void remove(Jogo obj) throws ErroRemocaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("DELETE FROM jogo WHERE id_rodada = ?, id_time_a = ?, id_time_b = ?");
			pstmt.setInt(1, obj.getIdRodada());
			pstmt.setInt(2, obj.getIdTimeA());
			pstmt.setInt(3, obj.getIdTimeB());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroRemocaoException("em Jogo", e);
		}
	}

	public List<Jogo> lista(Rodada rodada) throws RegistroNaoEncontradoException{
		List<Jogo> lista = new ArrayList<Jogo>();
		Time timea, timeb;
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("" +
					"SELECT j.nro_gols_a, j.nro_gols_b, j.id_time_a, ta.nome AS nome_a, ta.estado AS estado_a, j.id_time_b, tb.nome AS nome_b, tb.estado AS estado_b " +
					"FROM jogo j, time ta, time tb " +
					"WHERE j.id_time_a = ta.id AND j.id_time_b = tb.id AND j.id_rodada = ?");
			pstmt.setInt(1, rodada.getId());
			ResultSet res = pstmt.executeQuery();
			boolean achou = false;
			while(res.next()){
				achou = true;
				timea = new Time(res.getString("nome_a"), res.getString("estado_a"), res.getInt("id_time_a"));
				timeb = new Time(res.getString("nome_b"), res.getString("estado_b"), res.getInt("id_time_b"));
				lista.add(new Jogo(rodada, timea, res.getInt("nro_gols_a"), timeb, res.getInt("nro_gols_b")));
			}
			if(!achou){
				throw new RegistroNaoEncontradoException("em Jogo");
			}
		} catch (SQLException e) {
			
		}
		return lista;
	}
	
	public List<Object[]> listaClassificacao(Campeonato campeonato) throws RegistroNaoEncontradoException{
		List<Object[]> lista = new ArrayList<Object[]>();
		int gols_favor, gols_sofrido, jogos, vitorias, pontuacao;
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("SELECT t.nome, t.estado, t.id FROM time t, campeonato_time ct WHERE t.id = ct.id_time AND ct.id_campeonato = ?");
			pstmt.setInt(1, campeonato.getId());
			ResultSet res = pstmt.executeQuery();
			boolean achou = false;
			while(res.next()){
				achou = true;
				gols_favor = 0;
				gols_sofrido = 0;
				jogos = 0;
				vitorias = 0;
				pontuacao = 0;
				
				pstmt = this.conn.prepareStatement(
					"SELECT j.nro_gols_a, j.nro_gols_b, " +
					"(CASE j.id_time_a WHEN ? THEN j.nro_gols_a ELSE j.nro_gols_b END) gols_favor, " +
					"(CASE j.id_time_a WHEN ? THEN j.nro_gols_b ELSE j.nro_gols_a END) gols_sofrido " +
					"FROM jogo j, rodada r " +
					"WHERE j.id_rodada = r.id AND realizado = 1 AND (j.id_time_a = ? OR j.id_time_b = ?) AND r.id_campeonato = ?"
				);
				pstmt.setInt(1, res.getInt("id"));
				pstmt.setInt(2, res.getInt("id"));
				pstmt.setInt(3, res.getInt("id"));
				pstmt.setInt(4, res.getInt("id"));
				pstmt.setInt(5, campeonato.getId());
				ResultSet resJ = pstmt.executeQuery();
				while(resJ.next()){
					int nga = resJ.getInt("gols_favor");
					int ngb = resJ.getInt("gols_sofrido");
					gols_favor += nga;
					gols_sofrido += ngb;
					jogos++;
					if(nga > ngb){
						vitorias++;
						pontuacao += 3;
					}else if(nga == ngb){
						pontuacao += 1;
					}
				}
				
				Object[] obj = new Object[]{
					res.getString("nome"), gols_favor, gols_sofrido, (gols_favor-gols_sofrido), jogos, vitorias, pontuacao
				};
				lista.add(obj);
			}
			if(!achou){
				throw new RegistroNaoEncontradoException("em Jogo");
			}
		} catch (SQLException e) {
			
		}
		return lista;
	}
	
	public List<Object[]> listaGrafico(Campeonato campeonato) throws RegistroNaoEncontradoException{
		List<Object[]> lista = new ArrayList<Object[]>();
		List<String> datas;
		List<Integer> pontuacao;
		boolean achou = false;
		int pontuacaoNum;
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("SELECT t.nome, t.estado, t.id FROM time t, campeonato_time ct WHERE t.id = ct.id_time AND ct.id_campeonato = ?");
			pstmt.setInt(1, campeonato.getId());
			ResultSet res = pstmt.executeQuery();
			while(res.next()){
				//System.out.println(res.getString("nome"));
				datas = new ArrayList<String>();
				pontuacao = new ArrayList<Integer>();
				achou = true;
				pontuacaoNum = 0;
				pstmt = this.conn.prepareStatement(
					"SELECT j.nro_gols_a, j.nro_gols_b, r.data, " +
					"(CASE j.id_time_a WHEN ? THEN j.nro_gols_a ELSE j.nro_gols_b END) gols_favor, " +
					"(CASE j.id_time_a WHEN ? THEN j.nro_gols_b ELSE j.nro_gols_a END) gols_sofrido " +
					"FROM jogo j, rodada r " +
					"WHERE j.id_rodada = r.id AND realizado = 1 AND (j.id_time_a = ? OR j.id_time_b = ?) AND r.id_campeonato = ?"
				);
				pstmt.setInt(1, res.getInt("id"));
				pstmt.setInt(2, res.getInt("id"));
				pstmt.setInt(3, res.getInt("id"));
				pstmt.setInt(4, res.getInt("id"));
				pstmt.setInt(5, campeonato.getId());
				ResultSet resJ = pstmt.executeQuery();
				while(resJ.next()){
					datas.add(resJ.getString("data"));
					int gols_favor = resJ.getInt("gols_favor");
					int gols_sofrido = resJ.getInt("gols_sofrido");
					if(gols_favor > gols_sofrido){
						pontuacaoNum += 3;
					}else if(gols_favor == gols_sofrido){
						pontuacaoNum += 1;
					}
					pontuacao.add(pontuacaoNum);
					//System.out.println(resJ.getString("data")+" - "+pontuacaoNum);
				}
				
				Object[] obj = new Object[]{
					res.getString("nome"), datas, pontuacao
				};
				lista.add(obj);
			}
			if(!achou){
				throw new RegistroNaoEncontradoException("em Jogo");
			}
		} catch (SQLException e) {
			
		}
		return lista;
	}
}
