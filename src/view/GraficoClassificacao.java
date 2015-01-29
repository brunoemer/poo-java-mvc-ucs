package view;

import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;

import org.jfree.ui.RefineryUtilities;

import persistence.DAOFactory;
import persistence.JogoDAO;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe GraficoClassificacao 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class GraficoClassificacao extends JInternalFrame {
	private JogoDAO jogoDB;
	
	private LineChart lineChart;
	
	private CadastroCampeonato cc;

	/**
	 * Metodo que inicia o Factory
	 */
	public void initObj(){
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.HSQL);
		this.jogoDB = factory.getJogoDAO();
	}
	
	/**
	 * Metodo que inicializa os componentes
	 */
	public void initComponents(){
		this.setTitle("Grafico de classificacao");
		this.setMaximizable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		//this.setSize(400, 350);
		this.pack();
	}
	
	/**
	 * Metodo para setar o cadastro do campeonato
	 * 
	 * @param cc
	 */
	public void setCadastroCampeonato(CadastroCampeonato cc){
		this.cc = cc;
	}
	
	/**
	 * Metodo que cria e exibe os graficos
	 */
	public void exibe(){
        List<Object[]> lista = null;
		try {
			lista = jogoDB.listaGrafico(this.cc.getCampeonato());
		} catch (RegistroNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		
//		lista = new ArrayList<Object[]>();
//		Object[] obj = new Object[]{
//			"Time 1", new String[]{"2012-05-01", "2012-05-03", "2012-05-05"}, new Integer[]{3, 5, 9}
//		};
//		lista.add(obj);
//		obj = new Object[]{
//			"Time 2", new String[]{"2012-05-01", "2012-05-03", "2012-05-05"}, new Integer[]{3, 3, 9}
//		};
//		lista.add(obj);
//		obj = new Object[]{
//			"Time 3", new String[]{"2012-05-01", "2012-05-03", "2012-05-05"}, new Integer[]{0, 1, 3}
//		};
//		lista.add(obj);
//		obj = new Object[]{
//			"Time 4", new String[]{"2012-05-01", "2012-05-03", "2012-05-05"}, new Integer[]{0, 1, 1}
//		};
//		lista.add(obj);
//		obj = new Object[]{
//			"Time 5", new String[]{"2012-05-01", "2012-05-03", "2012-05-05"}, new Integer[]{4, 4, 7}
//		};
//		lista.add(obj);
		
		lineChart = new LineChart("Gráfico classificação", lista);
		lineChart.pack();
        RefineryUtilities.centerFrameOnScreen(lineChart);
        
		lineChart.setVisible(true);
	}
	
}
