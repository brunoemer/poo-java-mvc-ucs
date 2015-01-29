package view;

import persistence.hsql.HSqlConnectionSingleton;

/**
 * Classe Inicio 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class Inicio {
	public static void main(String[] args) {
		HSqlConnectionSingleton.getInstance();
		Desktop d = new Desktop();
		d.initComponents();
		d.setVisible(true);
	}
}
