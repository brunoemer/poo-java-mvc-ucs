package view;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Campeonato;
import model.Jogo;
import model.Rodada;
import model.Time;
import persistence.DAOFactory;
import persistence.JogoDAO;
import persistence.RodadaDAO;
import exceptions.ErroInclusaoException;

/**
 * Classe GerarRodada 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class GerarRodada extends JInternalFrame implements ActionListener {
	private RodadaDAO rodadaDB;
	private JogoDAO jogoDB;

	private CadastroCampeonato cc;
	
	private JPanel fundo;
	private JTextField tDataInicial;
	private JButton bGerar;
	
	/**
	 * Metodo construtor
	 * 
	 * @throws HeadlessException
	 */
	public GerarRodada() throws HeadlessException {
		super();
	}

	/**
	 * Metodo que inicia o Factory
	 */
	public void initObj(){
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.HSQL);
		this.rodadaDB = factory.getRodadaDAO();
		this.jogoDB = factory.getJogoDAO();
	}
	
	/**
	 * Metodo que inicializa os componentes
	 */
	public void initComponents(){
		this.setTitle("Gerar rodadas");
		this.setMaximizable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		this.fundo = new JPanel(new BorderLayout());
		this.fundo.setLayout(new BoxLayout(this.fundo, BoxLayout.Y_AXIS));
		
		this.tDataInicial = new JTextField();
		Date data = new Date();
		this.tDataInicial.setText(data.getDate()+"/"+(data.getMonth()+1)+"/"+(data.getYear()+1900));
		this.bGerar = new JButton("Gerar rodadas");
		
		this.fundo.add(new JLabel("Data inicial"));
		this.fundo.add(this.tDataInicial);
		this.fundo.add(this.bGerar);

		this.bGerar.addActionListener(this);
		
		this.getContentPane().add(this.fundo);
		//this.setSize(400, 350);
		this.pack();
	}
	
	/**
	 * Metodo Action Performed que controla todos eventos da classe
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.bGerar){
			this.gerar();
		}
	}
	
	/**
	 * Metodo que gera as rodadas
	 */
	public void gerar(){
		// VERIFICAR NUMERO DE TIMES PAR
		if(cc.getTimesInscritos().size() % 2 == 0){
			List<Time> times = new ArrayList<Time>();
			int n = cc.getTimesInscritos().getSize();
			for(int i = 0; i < n; i++){
				times.add((Time)cc.getTimesInscritos().getElementAt(i));
			}
			
			String[] data = tDataInicial.getText().split("/");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(Integer.parseInt(data[2]), Integer.parseInt(data[1]), Integer.parseInt(data[0])));
			
			Campeonato campeonato = this.cc.getCampeonato();
			
			int num_rounds = times.size() - 1,
				num_matches = (times.size() / 2);
			//System.out.println("rounds: "+num_rounds+" matches: "+num_matches);
			for (int i = 0; i < num_rounds; i++) {
				String datas = cal.getTime().getDate()+"/"+cal.getTime().getMonth()+"/"+cal.getTime().getYear();
				cal.add(Calendar.DAY_OF_WEEK, 7);
				
				// rodada
				Rodada rodada = new Rodada(campeonato, datas, (i + 1));
				int id = 0;
				try {
					id = rodadaDB.insere(rodada);
				} catch (ErroInclusaoException e) {
					JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
				}
				rodada.setId(id);
				
				for (int j = 0; j < num_matches; j++) {
					//System.out.println("A: "+times.get(j).getNome()+" B: "+times.get(num_rounds - j).getNome());
					// jogo
					Jogo jogo = new Jogo();
					jogo.setTimeA(times.get(j));
					jogo.setTimeB(times.get(num_rounds - j));
					jogo.setRodada(rodada);
					jogo.setRealizado(0);
					try {
						jogoDB.insere(jogo);
					} catch (ErroInclusaoException e) {
						JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
					}
				}
				//System.out.println(times);
				Time timeTmp = times.get(num_rounds);
				times.remove(num_rounds);
				times.add(1, timeTmp);
			}
			
			JOptionPane.showMessageDialog(null, "Rodadas geradas com sucesso");
		}else{
			JOptionPane.showMessageDialog(null, "Escolha um numero par de times para gerar rodadas");
		}
		this.setVisible(false);
	}
	
	public void setCadastroCampeonato(CadastroCampeonato cc){
		this.cc = cc;
	}
	
}
