package persistence.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Campeonato;
import model.Time;
import persistence.CampeonatoDAO;
import exceptions.ErroAlteracaoException;
import exceptions.ErroBancoDeDadosException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

public class HSqlCampeonatoDAO implements CampeonatoDAO {
	private Connection conn;

	public HSqlCampeonatoDAO() {
		try {
			conn = HSqlDAOFactory.createConnection();
		} catch (ErroBancoDeDadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int insere(Campeonato obj) throws ErroInclusaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("INSERT INTO campeonato (ano) VALUES (?)");
			pstmt.setInt(1, obj.getAno());
			pstmt.executeUpdate();

			pstmt = this.conn.prepareStatement("SELECT id FROM campeonato ORDER BY id DESC LIMIT 1");
			ResultSet res = pstmt.executeQuery();
			res.next();
			int id_campeonato = res.getInt("id");
			
			List<Time> times = obj.getTimes();
			for(Time time : times){
				pstmt = this.conn.prepareStatement("INSERT INTO campeonato_time (id_campeonato, id_time) VALUES (?, ?)");
				pstmt.setInt(1, id_campeonato);
				pstmt.setInt(2, time.getId());
				pstmt.executeUpdate();
			}
			
			return id_campeonato;
		} catch (SQLException e) {
			throw new ErroInclusaoException("em Campeonato", e);
		}
	}
	
	public void altera(Campeonato obj) throws ErroAlteracaoException, ErroRemocaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("UPDATE campeonato SET ano = ? WHERE id = ?");
			pstmt.setInt(1, obj.getAno());
			pstmt.setInt(2, obj.getId());
			pstmt.executeUpdate();
			
			this.removeTimes(obj);
			List<Time> times = obj.getTimes();
			for(Time time : times){
				pstmt = this.conn.prepareStatement("INSERT INTO campeonato_time (id_campeonato, id_time) VALUES (?, ?)");
				pstmt.setInt(1, obj.getId());
				pstmt.setInt(2, time.getId());
				pstmt.executeUpdate();
			}
		} catch (SQLException e) {
			throw new ErroAlteracaoException("em Campeonato", e);
		}
	}

	public void remove(Campeonato obj) throws ErroRemocaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("DELETE FROM campeonato WHERE id = ?");
			pstmt.setInt(1, obj.getId());
			pstmt.executeUpdate();
			this.removeTimes(obj);
		} catch (SQLException e) {
			throw new ErroRemocaoException("em Campeonato", e);
		}
	}
	
	private void removeTimes(Campeonato obj) throws ErroRemocaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("DELETE FROM campeonato_time WHERE id_campeonato = ?");
			pstmt.setInt(1, obj.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroRemocaoException("em Campeonato", e);
		}
	}
	
	public Campeonato busca(int ano) throws RegistroNaoEncontradoException{
		List<Time> lista = new ArrayList<Time>();
		Campeonato campeonato = null;
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("SELECT * FROM campeonato WHERE ano = ?");
			pstmt.setInt(1, ano);
			ResultSet res = pstmt.executeQuery();
			boolean achou = res.next();
			if(achou == false){
				throw new RegistroNaoEncontradoException("em Campeonato");
			}
			campeonato = new Campeonato(res.getInt("ano"), res.getInt("id"));
			
			pstmt = this.conn.prepareStatement("SELECT t.id, t.nome, t.estado FROM campeonato_time ct, time t WHERE ct.id_time = t.id AND ct.id_campeonato = ?");
			pstmt.setInt(1, campeonato.getId());
			res = pstmt.executeQuery();
			achou = false;
			while(res.next()){
				achou = true;
				lista.add(new Time(res.getString("nome"), res.getString("estado"), res.getInt("id")));
			}
			campeonato.setTimes(lista);
			if(!achou){
				throw new RegistroNaoEncontradoException("em Rodada");
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return campeonato;
	}
	
}
