package view;

import java.util.List;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import persistence.DAOFactory;
import persistence.JogoDAO;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe TabelaClassificacao 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class TabelaClassificacao extends JInternalFrame {
	private JogoDAO jogoDB;
	
	private CadastroCampeonato cc;

	private JTable tClassificacao;
	private DefaultTableModel tClassificacaoModel;
	private String[] colunas;
	
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
		this.setTitle("Tabela de classificacao");
		this.setMaximizable(true);
		this.setClosable(true);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		
		colunas = new String []{"Time", "Gols favor", "Gols sofridos", "Saldo", "Jogos", "Vitórias", "Pontuação"};  
		this.tClassificacaoModel = new DefaultTableModel();
		this.tClassificacaoModel.setColumnIdentifiers(colunas);
		this.tClassificacao = new JTable();
		this.tClassificacao.setModel(this.tClassificacaoModel);
		this.tClassificacao.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane scrollPane = new JScrollPane(this.tClassificacao);
		
		this.getContentPane().add(scrollPane);
		//this.setSize(400, 350);
		this.pack();
	}

	/**
	 * Metodo que atualiza a lista
	 */
	public void atualizaLista(){
		this.tClassificacaoModel = new DefaultTableModel();
		this.tClassificacaoModel.setColumnIdentifiers(colunas);
		this.tClassificacao.setModel(this.tClassificacaoModel);
		
		List<Object[]> jogos = null;
		try {
			jogos = jogoDB.listaClassificacao(this.cc.getCampeonato());
		} catch (RegistroNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		for(Object[] jogo : jogos){
			this.tClassificacaoModel.addRow(jogo);
		}
	}
	
	/**
	 * metodo que cadastra o campeonato
	 * @param cc
	 */
	public void setCadastroCampeonato(CadastroCampeonato cc){
		this.cc = cc;
	}
	
}
