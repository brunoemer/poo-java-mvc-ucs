package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import persistence.hsql.HSqlConnectionSingleton;

/**
 * Classe Desktop 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class Desktop extends JFrame implements ActionListener {
	private JDesktopPane desktopPane;

	private JMenuBar menuBar;
	private JMenu mArquivo;
	private JMenu mCadastros;
	private JMenuItem miSair;
	private JMenuItem miTime;
	private JMenuItem miCampeonato;

	private CadastroTime ct;
	private CadastroCampeonato cc;
	private GerarRodada gr;
	private ListagemRodadas lr;
	private ResultadoRodada rr;
	private TabelaClassificacao tc;
	private GraficoClassificacao gc;
	
	/**
	 * Metodo que inicia os componentes
	 */
	public void initComponents(){
		this.setTitle("Calculadora campeonatos");
		
		this.desktopPane = new JDesktopPane();
		this.desktopPane.setSize(600, 400);
		
		mArquivo = new JMenu("Arquivo");
		miSair = new JMenuItem("Sair");
		miSair.addActionListener(this);
		mArquivo.add(miSair);
		
		mCadastros = new JMenu("Cadastros");
		miTime = new JMenuItem("Time");
		miTime.addActionListener(this);
		mCadastros.add(miTime);
		miCampeonato = new JMenuItem("Campeonato");
		miCampeonato.addActionListener(this);
		mCadastros.add(miCampeonato);
		
		menuBar = new JMenuBar();
		menuBar.add(mArquivo);
		menuBar.add(mCadastros);
		this.setJMenuBar(menuBar);
		
		this.getContentPane().add(this.desktopPane);
		this.setSize(800, 600);

		cc = new CadastroCampeonato();
		ct = new CadastroTime();
		
		gr = new GerarRodada();
		gr.initObj();
		gr.initComponents();
		gr.setCadastroCampeonato(cc);
		desktopPane.add(gr);
		
		rr = new ResultadoRodada();
		rr.initObj();
		rr.initComponents();
		desktopPane.add(rr);
		
		lr = new ListagemRodadas();
		lr.initObj();
		lr.initComponents();
		lr.setCadastroCampeonato(cc);
		lr.setResultadoRodada(rr);
		desktopPane.add(lr);

		tc = new TabelaClassificacao();
		tc.initObj();
		tc.initComponents();
		tc.setCadastroCampeonato(cc);
		desktopPane.add(tc);

		gc = new GraficoClassificacao();
		gc.initObj();
		gc.initComponents();
		gc.setCadastroCampeonato(cc);
		desktopPane.add(gc);
		
	}
	
	/**
	 * Metodo Action Performed que controla todos eventos da classe
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == miSair) { 
			this.acaoSair();
		} else if(e.getSource() == miTime) {
			ct.initObj();
			ct.initComponents();
			ct.setVisible(true);
			desktopPane.add(ct);
		} else if(e.getSource() == miCampeonato) {
			cc.initObj();
			cc.initComponents();
			cc.setGerarRodada(gr);
			cc.setExibirRodada(lr);
			cc.setTabelaClassificacao(tc);
			cc.setGraficoClassificacao(gc);
			cc.setVisible(true);
			desktopPane.add(cc);
		}
	}
	
	/**
	 * Metodo SingleTin para encerrar a conecção com o BD
	 */
	public void acaoSair(){
		HSqlConnectionSingleton conn = HSqlConnectionSingleton.getInstance();
		try {
			if(conn != null){
				conn.getConnnection().close();
			}
		} catch (SQLException e) {
			
		}
		System.exit(0);
	}
	
}
