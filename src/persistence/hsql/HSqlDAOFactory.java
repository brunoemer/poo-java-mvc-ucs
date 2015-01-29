package persistence.hsql;

import java.sql.Connection;

import persistence.CampeonatoDAO;
import persistence.DAOFactory;
import persistence.JogoDAO;
import persistence.RodadaDAO;
import persistence.TimeDAO;
import exceptions.ErroBancoDeDadosException;

public class HSqlDAOFactory extends DAOFactory {
	
	public static Connection createConnection() throws ErroBancoDeDadosException{
		return HSqlConnectionSingleton.getInstance().getConnnection();
	}
	
	public TimeDAO getTimeDAO() {
		return new HSqlTimeDAO();
	}

	public CampeonatoDAO getCampeonatoDAO() {
		return new HSqlCampeonatoDAO();
	}

	public RodadaDAO getRodadaDAO() {
		return new HSqlRodadaDAO();
	}

	public JogoDAO getJogoDAO() {
		return new HSqlJogoDAO();
	}
	
}
