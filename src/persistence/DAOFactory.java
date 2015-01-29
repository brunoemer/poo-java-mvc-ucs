package persistence;

import persistence.hsql.HSqlDAOFactory;

/**
 * Classe DAOFactory
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public abstract class DAOFactory {
	public static final int HSQL = 1;
	
	public static DAOFactory getDAOFactory(int constType){
		switch (constType) {
		case HSQL:
			return new HSqlDAOFactory();
		default:
			return null;
		}
	}

	public abstract TimeDAO getTimeDAO();
	public abstract CampeonatoDAO getCampeonatoDAO();
	public abstract RodadaDAO getRodadaDAO();
	public abstract JogoDAO getJogoDAO();
}
