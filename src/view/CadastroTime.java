package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Time;
import persistence.DAOFactory;
import persistence.TimeDAO;
import exceptions.ErroAlteracaoException;
import exceptions.ErroInclusaoException;
import exceptions.ErroRemocaoException;
import exceptions.RegistroNaoEncontradoException;

/**
 * Classe CadastroTime 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class CadastroTime extends JInternalFrame implements ActionListener {
	private Time time;
	private TimeDAO timeDB;
	
	private JPanel fundo, campos, botoes;
	private JTextField tNome;
	private JComboBox tEstado;
	private JButton bBuscar, bIncluir, bAlterar, bExcluir;
	
	/**
	 * Metodo Construtor
	 * 
	 * @throws HeadlessException
	 */
	public CadastroTime() throws HeadlessException {
		super();
	}

	public void initObj(){
		DAOFactory factory = DAOFactory.getDAOFactory(DAOFactory.HSQL);
		this.timeDB = factory.getTimeDAO();
		this.time = new Time();
	}
	
	public void initComponents(){
		this.setTitle("Cadastro de times");
		this.setClosable(true);
		this.setMaximizable(true);
		
		this.fundo = new JPanel(new BorderLayout());
		this.campos = new JPanel(new GridLayout(2, 2));
		this.botoes = new JPanel(new FlowLayout());
		this.bBuscar = new JButton("Busca");
		this.bIncluir = new JButton("Inclui");
		this.bAlterar = new JButton("Altera");
		this.bExcluir = new JButton("Exclui");
		this.tNome = new JTextField();
		String[] listaEstados = { "AC", "AL", "AM", "AP", "BA", "CE", "DF",
		        "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI",
		        "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO" };
		this.tEstado = new JComboBox(listaEstados);
		
		this.botoes.add(this.bBuscar);
		this.botoes.add(this.bIncluir);
		this.botoes.add(this.bAlterar);
		this.botoes.add(this.bExcluir);
		this.fundo.add(this.botoes, BorderLayout.SOUTH);

		this.campos.add(new JLabel("Nome:"));
		this.campos.add(this.tNome);
		this.campos.add(new JLabel("Estado:"));
		this.campos.add(this.tEstado);
		this.fundo.add(this.campos, BorderLayout.CENTER);

		this.bBuscar.addActionListener(this);
		this.bIncluir.addActionListener(this);
		this.bAlterar.addActionListener(this);
		this.bExcluir.addActionListener(this);
		
		this.getContentPane().add(this.fundo);
		//this.setSize(400, 350);
		this.pack();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.bBuscar){
			this.acaoBuscar();
		} else if(e.getSource() == this.bIncluir){
			this.acaoIncluir();
		} else if(e.getSource() == this.bAlterar){
			this.acaoAlterar();
		} else if(e.getSource() == this.bExcluir){
			this.acaoExcluir();
		}
	}
	
	private void acaoBuscar() {
		String nome = this.tNome.getText();
		try {
			this.time = timeDB.busca(nome);
		} catch (RegistroNaoEncontradoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		if(this.time==null) {
			this.time = new Time();
		}
		this.paraTela();
	}

	private void acaoIncluir() {
		this.telaParaObj();
		try {
			this.timeDB.insere(this.time);
		} catch (ErroInclusaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		this.acaoLimpar();
	}

	private void acaoAlterar() {
		this.telaParaObj();
		try {
			this.timeDB.altera(this.time);
		} catch (ErroAlteracaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		this.acaoLimpar();
	}

	private void acaoExcluir() {
		try {
			this.timeDB.remove(this.time.getId());
		} catch (ErroRemocaoException e) {
			JOptionPane.showMessageDialog(this, e.getMessage(), "Ocorreu um erro", JOptionPane.ERROR_MESSAGE);
		}
		this.acaoLimpar();
	}
	
	private void acaoLimpar(){
		this.tNome.setText("");
		this.tEstado.setSelectedIndex(0);
	}
	
	private void paraTela(){
		this.tNome.setText(this.time.getNome());
		this.tEstado.setSelectedItem(this.time.getEstado());
	}
	
	private void telaParaObj(){
		this.time.setNome(this.tNome.getText());
		this.time.setEstado(String.valueOf(this.tEstado.getSelectedItem()));
	}
	
}
