package view;

import java.awt.HeadlessException;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import model.Jogo;
import model.Rodada;
import persistence.DAOFactory;
import persistence.JogoDAO;
import exceptions.ErroAlteracaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe ResultadoRodada 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class ResultadoRodada extends JInternalFrame implements ContainerListener {
	private Jogo jogo;
	private JogoDAO jogoDB;
	private Rodada rodada;
	
	private JTable tJogos;
	private AdapterJogoTableModel tJogosModel;
	private String[] colunas;
	
	/**
	 * Metodo construtor
	 * 
	 * @throws HeadlessException
	 */
	public ResultadoRodada() throws HeadlessException {
		super();
	}
	
	/**
	 * Metodo que inicia o Factory
	 */
	public void initObj(){
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.HSQL);
		this.jogoDB = factory.getJogoDAO();
		this.jogo = new Jogo();
		this.rodada = new Rodada();
	}
	
	/**
	 * Metodo que inicializa os componentes
	 */
	public void initComponents(){
		this.setTitle("Resultado da rodada");
		this.setMaximizable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		colunas = new String []{"Time A", "Gols A", "Gols B", "Time B"};  
		this.tJogosModel = new AdapterJogoTableModel();
		this.tJogosModel.setColumnIdentifiers(colunas);
		this.tJogos = new JTable();
		this.tJogos.setModel(this.tJogosModel);
		this.tJogos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(this.tJogos);
		
		tJogos.addContainerListener(this);
		
		this.getContentPane().add(scrollPane);
		//this.setSize(400, 350);
		this.pack();
	}

	/**
	 * Metodo que atualiza a lista de jogos
	 */
	public void atualizaLista(){
		this.tJogosModel = new AdapterJogoTableModel();
		this.tJogosModel.setColumnIdentifiers(colunas);
		this.tJogos.setModel(this.tJogosModel);
		
		List<Jogo> jogos = null;
		try {
			jogos = jogoDB.lista(this.rodada);
		} catch (RegistroNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		for(Jogo jogo : jogos){
			this.tJogosModel.addRow(jogo);
		}
	}
	
	/**
	 * Metodo que seta as rodadas
	 * 
	 * @param rodada
	 */
	public void setRodada(Rodada rodada){
		this.rodada = rodada;
	}

	/**
	 * Metodo que adiciona os componentes
	 * 
	 * @param e
	 */
	@Override
	public void componentAdded(ContainerEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodo que remove os componentes
	 * 
	 * @param ev
	 */
	@Override
	public void componentRemoved(ContainerEvent ev) {
		// TODO Auto-generated method stub
		// verificar qual foi alterado
		int index = tJogos.getSelectedRow();
		jogo = tJogosModel.getJogo(index);
		jogo.setRealizado(1);
		try {
			jogoDB.altera(jogo);
		} catch (ErroAlteracaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
}
