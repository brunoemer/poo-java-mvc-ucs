package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.Campeonato;
import model.Time;
import persistence.CampeonatoDAO;
import persistence.DAOFactory;
import persistence.TimeDAO;
import exceptions.ErroAlteracaoException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe CadastroCampeonato 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class CadastroCampeonato extends JInternalFrame implements ActionListener  {
	private Campeonato campeonato;
	private CampeonatoDAO campeonatoDB;
	private TimeDAO timeDB;
	
	private GerarRodada gr;
	private ListagemRodadas lr;
	private TabelaClassificacao tc;
	private GraficoClassificacao gc;
	
	private JPanel fundo, campos, botoes;
	private JTextField tAno;
	private JList timesDisp, timesInsc;
	private DefaultListModel timesDispModel, timesInscModel;
	
	private JButton bGerarRodadas, bExibirRodadas, bClassificacao, bGrafico, bBuscar, bSalvar, bExcluir, bLimpar, bListAdd, bListBack;

	/**
	 * Metodo Construtor
	 * 
	 * @throws HeadlessException
	 */
	public CadastroCampeonato() throws HeadlessException {
		super();
	}

	/**
	 * Metodo que inicia o Factory
	 */
	public void initObj(){
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.HSQL);
		this.campeonatoDB = factory.getCampeonatoDAO();
		this.timeDB = factory.getTimeDAO();
		this.campeonato = new Campeonato();
	}
	
	/**
	 * Metodo que inicializa os componentes
	 */
	public void initComponents(){
		this.setTitle("Cadastro de campeonato");
		this.setClosable(true);
		this.setMaximizable(true);
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(3, 3));
		this.botoes = new JPanel(new FlowLayout());
		
		this.tAno = new JTextField();
		this.bGerarRodadas = new JButton("Gerar rodadas");
		this.bExibirRodadas = new JButton("Exibir rodadas");
		this.bClassificacao = new JButton("Classificação");
		this.bGrafico = new JButton("Gráfico");
		
		this.timesDispModel = new DefaultListModel();
		List<Time> lista = null;
		try {
			lista = this.timeDB.buscaTodos();
		} catch (RegistroNaoEncontradoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Time obj : lista){
			this.timesDispModel.addElement(obj);
		}
		this.timesDisp = new JList(this.timesDispModel);
		this.timesDisp.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.timesInscModel = new DefaultListModel();
		this.timesInsc = new JList(this.timesInscModel);
		this.timesInsc.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JPanel panelAno = new JPanel();
		panelAno.setLayout(new BoxLayout(panelAno, BoxLayout.X_AXIS));
		panelAno.add(new JLabel("Ano:"));
		panelAno.add(this.tAno);
		this.campos.add(panelAno);
		this.campos.add(new JPanel());
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new BoxLayout(panelBotoes, BoxLayout.Y_AXIS));
		panelBotoes.add(this.bGerarRodadas);
		panelBotoes.add(this.bExibirRodadas);
		panelBotoes.add(this.bClassificacao);
		panelBotoes.add(this.bGrafico);
		this.campos.add(panelBotoes);
		this.campos.add(new JLabel("Times disponiveis"));
		this.campos.add(new JPanel());
		this.campos.add(new JLabel("Times inscritos"));
		this.campos.add(this.timesDisp);
		
		JPanel botoesLista = new JPanel();
		botoesLista.setLayout(new BoxLayout(botoesLista, BoxLayout.Y_AXIS));
		this.bListAdd = new JButton(">");
		botoesLista.add(bListAdd);
		this.bListBack = new JButton("<");
		botoesLista.add(bListBack);
		this.campos.add(botoesLista);
		
		this.campos.add(this.timesInsc);
		this.fundo.add(this.campos, BorderLayout.CENTER);
		
		this.bBuscar = new JButton("Busca");
		this.bSalvar = new JButton("Salva");
		this.bExcluir = new JButton("Exclui");
		this.bLimpar = new JButton("Limpa");
		
		this.botoes.add(this.bBuscar);
		this.botoes.add(this.bSalvar);
		this.botoes.add(this.bExcluir);
		this.botoes.add(this.bLimpar);
		this.fundo.add(this.botoes, BorderLayout.SOUTH);

		this.bGerarRodadas.addActionListener(this);
		this.bExibirRodadas.addActionListener(this);
		this.bClassificacao.addActionListener(this);
		this.bGrafico.addActionListener(this);
		this.bBuscar.addActionListener(this);
		this.bSalvar.addActionListener(this);
		this.bExcluir.addActionListener(this);
		this.bLimpar.addActionListener(this);
		this.bListAdd.addActionListener(this);
		this.bListBack.addActionListener(this);
		
		this.getContentPane().add(this.fundo);
		//this.setSize(400, 250);
		this.pack();
	}
	
	/**
	 * Metodo Action Performed que controla todos eventos da classe
	 * 
	 * @param e
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.bGerarRodadas){
			this.acaoGerarRodadas();
		} else if(e.getSource() == this.bExibirRodadas){
			this.acaoExibirRodadas();
		} else if(e.getSource() == this.bClassificacao){
			this.acaoClassificacao();
		} else if(e.getSource() == this.bGrafico){
			this.acaoGraficoClassificacao();
		} else if(e.getSource() == this.bBuscar){
			this.acaoBuscar();
		} else if(e.getSource() == this.bSalvar){
			this.acaoSalvar();
		} else if(e.getSource() == this.bExcluir){
			this.acaoExcluir();
		} else if(e.getSource() == this.bLimpar){
			this.acaoLimpar();
		} else if(e.getSource() == this.bListAdd){
			this.acaoListAdd();
		} else if(e.getSource() == this.bListBack){
			this.acaoListBack();
		}
		
	}
	
	/**
	 * Metodo para setar as Rodadas
	 * 
	 * @param gr
	 */
	public void setGerarRodada(GerarRodada gr){
		this.gr = gr;
	}
	
	/**
	 * Metodo para tornas as rodadas Visiveis
	 */
	private void acaoGerarRodadas(){
		gr.setVisible(true);
	}
	
	/**
	 * Metodo para setar as Rodadas
	 * 
	 * @param lr
	 */
	public void setExibirRodada(ListagemRodadas lr){
		this.lr = lr;
	}

	/**
	 * Metodo para exibir as rodadas
	 */
	private void acaoExibirRodadas(){
		lr.atualizaLista();
		lr.setVisible(true);
	}
	
	/**
	 * Metodo para setar a tabela classificação
	 * 
	 * @param tc
	 */
	public void setTabelaClassificacao(TabelaClassificacao tc){
		this.tc = tc;
	}
	
	/**
	 * Metodo para atualizar a lista de Classificação
	 */
	private void acaoClassificacao(){
		tc.atualizaLista();
		tc.setVisible(true);
	}

	/**
	 * Metodo para setar o grafico de Classificação
	 * 
	 * @param gc
	 */
	public void setGraficoClassificacao(GraficoClassificacao gc){
		this.gc = gc;
	}
	
	/**
	 * Metodo que exibe a classificação
	 */
	private void acaoGraficoClassificacao(){
		gc.exibe();
	}
	
	/**
	 * Metodo que pega o campeonato
	 * 
	 * @return        campeonato
	 */
	public Campeonato getCampeonato(){
		return this.campeonato;
	}
	
	/**
	 * Metodo que pega os times inscritos
	 * 
	 * @return       times inscritos
	 */
	public DefaultListModel getTimesInscritos(){
		return this.timesInscModel;
	}
	
	/**
	 * Metodo que Lista os adicionados
	 */
	private void acaoListAdd(){
		int[] listSelected = this.timesDisp.getSelectedIndices();
		for(int index : listSelected){
			this.timesInscModel.addElement(this.timesDispModel.getElementAt(index));
			this.timesDispModel.remove(index);
		}
	}
	
	/**
	 * Metodo que adiciona o elemento Time
	 */
	private void acaoListBack(){
		int[] listSelected = this.timesInsc.getSelectedIndices();
		for(int index : listSelected){
			this.timesDispModel.addElement(this.timesInscModel.getElementAt(index));
			this.timesInscModel.remove(index);
		}
	}
	
	/**
	 * Metodo que busca o campeonato
	 */
	private void acaoBuscar() {
		Integer ano = Integer.valueOf(this.tAno.getText());
		try {
			this.campeonato = campeonatoDB.busca(ano);
			if(this.campeonato == null) {
				this.campeonato = new Campeonato();
			}
			this.acaoLimpar();
			this.paraTela();
		} catch (RegistroNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Metodo que salva o campeonato
	 */
	private void acaoSalvar() {
		this.telaParaObj();
		if(this.campeonato.getId() > 0){
			try {
				this.campeonatoDB.altera(this.campeonato);
			} catch (ErroAlteracaoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			} catch (ErroRemocaoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			}
		}else{
			int id = 0;
			try {
				id = this.campeonatoDB.insere(this.campeonato);
			} catch (ErroInclusaoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
			}
			this.campeonato.setId(id);
		}
		//this.acaoLimpar();
	}

	/**
	 * Metodo que exclui o campeonato
	 */
	private void acaoExcluir() {
		try {
			this.campeonatoDB.remove(this.campeonato);
		} catch (ErroRemocaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		this.acaoLimpar();
	}
	
	/**
	 * metodo que limpa ao incluir o ano
	 */
	private void acaoLimpar(){
		this.tAno.setText("");
		this.timesInscModel.clear();
	}
	
	/**
	 * Metodo que coloca o campeonato na tela
	 */
	private void paraTela(){
		this.tAno.setText(String.valueOf(this.campeonato.getAno()));
		List<Time> lista = this.campeonato.getTimes();
		for(Time t : lista){
			this.timesInscModel.addElement(t);
			// remove da outra lista
//			int n = this.timesDispModel.size();
//			for(int i = 0; i < n; i++){
//				Time tt = (Time)this.timesDispModel.getElementAt(i);
//				if(tt.getId() == t.getId()){
//					this.timesInscModel.remove(i);
//					break;
//				}
//			}
		}
	}
	
	/**
	 * Metodo que coloca os time nas tela
	 */
	private void telaParaObj(){
		this.campeonato.setAno(Integer.valueOf(this.tAno.getText()));
		List<Time> lista = new ArrayList<Time>();
		int n = this.timesInscModel.getSize();
		for(int i = 0; i < n; i++){
			lista.add((Time)this.timesInscModel.getElementAt(i));
		}
		this.campeonato.setTimes(lista);
	}

}
