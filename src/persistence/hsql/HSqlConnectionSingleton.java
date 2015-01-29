package persistence.hsql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import exceptions.ErroBancoDeDadosException;

public class HSqlConnectionSingleton {
	private static HSqlConnectionSingleton instance = null;
	private Connection conn;
	public static final String DRIVER = "org.hsqldb.jdbcDriver";
	public static final String DBURL = "jdbc:hsqldb:hsql://localhost";
	
	private HSqlConnectionSingleton() throws ErroBancoDeDadosException {
		String user = "sa";
		String pwd = "";
		try {
	        Class.forName(DRIVER);
		    conn = DriverManager.getConnection(DBURL, user, pwd);      
		} catch (ClassNotFoundException cnfe) {
			throw new ErroBancoDeDadosException("com o driver JDBC");
		} catch (SQLException se) {
			throw new ErroBancoDeDadosException("ao conectar");
		}
	}
	
	public static HSqlConnectionSingleton getInstance(){
		if(instance == null){
			try {
				instance = new HSqlConnectionSingleton();
			} catch (ErroBancoDeDadosException e) {
				JOptionPane.showMessageDialog(null, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
			}
		}
		return instance;
	}
	
	public Connection getConnnection(){
		return this.conn;
	}
	
}
