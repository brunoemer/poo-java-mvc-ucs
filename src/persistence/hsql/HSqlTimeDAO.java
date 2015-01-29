package persistence.hsql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Time;
import persistence.TimeDAO;
import exceptions.ErroAlteracaoException;
import exceptions.ErroBancoDeDadosException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

public class HSqlTimeDAO implements TimeDAO {
	private Connection conn;
	
	public HSqlTimeDAO() {
		try {
			conn = HSqlDAOFactory.createConnection();
		} catch (ErroBancoDeDadosException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public int insere(Time obj) throws ErroInclusaoException {
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("INSERT INTO time (nome, estado) VALUES (?, ?)");
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEstado());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroInclusaoException("em Time", e);
		}
	}
	
	public int altera(Time obj) throws ErroAlteracaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("UPDATE time SET nome = ?, estado = ? WHERE id = ?");
			pstmt.setString(1, obj.getNome());
			pstmt.setString(2, obj.getEstado());
			pstmt.setInt(3, obj.getId());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroAlteracaoException("em Time", e);
		}
	}

	public int remove(Time obj) throws ErroRemocaoException{
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("DELETE FROM time WHERE id = ?");
			pstmt.setInt(1, obj.getId());
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			throw new ErroRemocaoException("em Time", e);
		}
	}

	public int remove(int id) throws ErroRemocaoException{
		return this.remove(new Time(null, null, id));
	}
	
	public List<Time> buscaTodos() throws RegistroNaoEncontradoException{
		List<Time> lista = new ArrayList<Time>();
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("SELECT * FROM time ORDER BY id");
			ResultSet res = pstmt.executeQuery();
			boolean achou = false;
			while(res.next()){
				achou = true;
				lista.add(new Time(res.getString("nome"), res.getString("estado"), res.getInt("id")));
			}
			if(!achou){
				throw new RegistroNaoEncontradoException("em Time");
			}
		} catch (SQLException e) {
			
		}
		return lista;
	}
	
	public Time busca(String nome) throws RegistroNaoEncontradoException{
		Time time = null;
		PreparedStatement pstmt;
		try {
			pstmt = this.conn.prepareStatement("SELECT * FROM time WHERE nome LIKE ?");
			pstmt.setString(1, "%"+nome+"%");
			ResultSet res = pstmt.executeQuery();
			boolean achou = res.next();
			if(achou == false){
				throw new RegistroNaoEncontradoException("em Time");
			}
			time = new Time(res.getString("nome"), res.getString("estado"), res.getInt("id"));
		} catch (SQLException e) {
			
		}
		return time;
	}
	
}
