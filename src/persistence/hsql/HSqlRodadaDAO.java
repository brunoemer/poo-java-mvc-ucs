package persistence.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import persistence.RodadaDAO;


import model.Campeonato;
import model.Rodada;
import exceptions.ErroAlteracaoException;
import exceptions.ErroBancoDeDadosException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

public class HSqlRodadaDAO implements RodadaDAO {
	private Connection conn;

	public HSqlRodadaDAO() {
		try {
			conn = HSqlDAOFactory.createConnection();
		} catch (ErroBancoDeDadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int insere(Rodada obj) throws ErroInclusaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("INSERT INTO rodada (id_campeonato, numero, data) VALUES (?, ?, ?)");
			pstmt.setInt(1, obj.getIdCampeonato());
			pstmt.setInt(2, obj.getNumero());
			pstmt.setString(3, obj.getData());
			pstmt.executeUpdate();
			
			pstmt = this.conn.prepareStatement("SELECT id FROM rodada ORDER BY id DESC LIMIT 1");
			ResultSet res = pstmt.executeQuery();
			res.next();
			return res.getInt("id");
		} catch (SQLException e) {
			throw new ErroInclusaoException("em Rodada", e);
		}
	}
	
	public void altera(Rodada obj) throws ErroAlteracaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("UPDATE rodada SET id_campeonato = ?, numero = ?, data = ? WHERE id = ?");
			pstmt.setInt(1, obj.getIdCampeonato());
			pstmt.setInt(2, obj.getNumero());
			pstmt.setString(3, obj.getData());
			pstmt.setInt(4, obj.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroAlteracaoException("em Rodada", e);
		}
	}

	public void remove(Rodada obj) throws ErroRemocaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("DELETE FROM rodada WHERE id = ?");
			pstmt.setInt(1, obj.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroRemocaoException("em Rodada", e);
		}
	}
	
	public List<Rodada> lista(Campeonato campeonato) throws RegistroNaoEncontradoException{
		List<Rodada> lista = new ArrayList<Rodada>();
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("SELECT * FROM rodada WHERE id_campeonato = ?");
			pstmt.setInt(1, campeonato.getId());
			ResultSet res = pstmt.executeQuery();
			boolean achou = false;
			while(res.next()){
				achou = true;
				Rodada rodada = new Rodada(campeonato, res.getString("data"), res.getInt("numero"));
				rodada.setId(res.getInt("id"));
				
				pstmt = this.conn.prepareStatement("SELECT realizado FROM jogo WHERE id_rodada = ?");
				pstmt.setInt(1, res.getInt("id"));
				ResultSet resJ = pstmt.executeQuery();
				int nao_realizado = 0;
				while(resJ.next()){
					if(resJ.getInt("realizado") == 0){
						nao_realizado = 1;
					}
				}
				if(nao_realizado == 1){
					rodada.setRealizado(0);
				}else{
					rodada.setRealizado(1);
				}
				lista.add(rodada);
			}
			if(!achou){
				throw new RegistroNaoEncontradoException("em Rodada");
			}
		} catch (SQLException e) {

		}
		return lista;
	}
}
