package view;

import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import model.Rodada;
import persistence.DAOFactory;
import persistence.RodadaDAO;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe ListagemRodadas 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class ListagemRodadas extends JInternalFrame implements ActionListener, MouseListener{
	private RodadaDAO rodadaDB;

	private CadastroCampeonato cc;
	private ResultadoRodada rr;
	
	private JTable tRodadas;
	private AdapterRodadaTableModel tRodadasModel;
	private String[] colunas;
	
	/**
	 * Metodo construtor
	 * 
	 * @throws HeadlessException
	 */
	public ListagemRodadas() throws HeadlessException {
		super();
	}

	/**
	 * Metodo que inicia o Factory
	 */
	public void initObj(){
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.HSQL);
		this.rodadaDB = factory.getRodadaDAO();
	}
	
	/**
	 * Metodo que inicializa os componentes
	 */
	public void initComponents(){
		this.setTitle("Rodadas");
		this.setMaximizable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		colunas = new String []{"Numero", "Data", "Realizada"};  
		this.tRodadasModel = new AdapterRodadaTableModel();
		this.tRodadasModel.setColumnIdentifiers(colunas);
		this.tRodadas = new JTable();
		this.tRodadas.setModel(this.tRodadasModel);
		this.tRodadas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(this.tRodadas);
		
		tRodadas.addMouseListener(this);
		
		this.getContentPane().add(scrollPane);
		//this.setSize(400, 350);
		this.pack();
	}
	
	/**
	 * Metodo que atualiza a lista
	 */
	public void atualizaLista(){
		this.tRodadasModel = new AdapterRodadaTableModel();
		this.tRodadasModel.setColumnIdentifiers(colunas);
		this.tRodadas.setModel(this.tRodadasModel);
		
		List<Rodada> rodadas = null;
		try {
			rodadas = rodadaDB.lista(this.cc.getCampeonato());
			for(Rodada rodada : rodadas){
				this.tRodadasModel.addRow(rodada);
			}
		} catch (RegistroNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Metodo que cadastro o campeonato
	 * @param cc
	 */
	public void setCadastroCampeonato(CadastroCampeonato cc){
		this.cc = cc;
	}
	
	/**
	 * metodo que seta o resultado da rodada
	 * @param rr
	 */
	public void setResultadoRodada(ResultadoRodada rr){
		this.rr = rr;
	}
	
	/**
	 * Metodo Action Performed que controla todos eventos da classe
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	/**
	 *Metodo que captura o click do mouse
	 * 
	 * @param e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
		int index = tRodadas.getSelectedRow();
		Rodada rodada = tRodadasModel.getRodada(index);
		rr.setRodada(rodada);
		rr.atualizaLista();
		rr.setVisible(true);
	}

	/**
	 * Metodo que captura quando o mouse foi pressionado
	 * @param e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodo que captura quando o mouse foi liberado
	 * @param e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Metodo que captura quando o mouse entra num componente
	 * @param e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * metodo que captura quando o mouse sai de um componente
	 * @param e
	 */
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
}
