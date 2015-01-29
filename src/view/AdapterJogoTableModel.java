package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Jogo;
import model.Model;

/**
 * Classe AdapterJogoTableModel 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class AdapterJogoTableModel extends DefaultTableModel implements AdapterTableModel {
	private List<Jogo> jogos;
	
	
	/**
	 * Metodo contrutor da classe
	 */
	public AdapterJogoTableModel() {
		super();
		// TODO Auto-generated constructor stub
		jogos = new ArrayList<Jogo>();
	}

	/**
	 * método para adicionar as linhas
	 * 
	 * @param obj      Adicionar objeto jogo
	 */
	public void addRow(Model obj) {
		Jogo data = (Jogo) obj;
		jogos.add(data);
		// TODO Auto-generated method stub
		super.addRow(new Object[] {data.getTimeA().getNome(), data.getNroGolsA(), data.getNroGolsB(), data.getTimeB().getNome()});
	}
	
	/**
	 * Metodo para remover a linha
	 * 
	 * @param row
	 */
	@Override
	public void removeRow(int row) {
		jogos.remove(row);
		// TODO Auto-generated method stub
		super.removeRow(row);
	}
	
	/**
	 * metodo para buscar os jogos pelo index
	 * 
	 * @param index
	 * @return jogos
	 */
	public Jogo getJogo(int index) {
		return jogos.get(index);
	}
	
	/**
	 * metodo para setar os valores das celulas de uma coluna
	 * 
	 * @param aValue
	 * @param row        linha
	 * @param column     coluna
	 */
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		// atualiza gols na lista
		if(column == 1){
			Jogo jogo = jogos.get(row);
			jogo.setNroGolsA(Integer.valueOf((String) aValue));
			jogos.set(row, jogo);
		}else if(column == 2){
			Jogo jogo = jogos.get(row);
			jogo.setNroGolsB(Integer.valueOf((String) aValue));
			jogos.set(row, jogo);
		}
		// TODO Auto-generated method stub
		super.setValueAt(aValue, row, column);
	}

	/**
	 * Metodo para informar se a célula é editavel ou não
	 * 
	 * @param row       linha
	 * @param column    coluna
	 * @return          verdadeiro ou falso
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		// TODO Auto-generated method stub
		if(column == 1 || column == 2){
			return true;
		}
		return false;
		//return super.isCellEditable(row, column);
	}
}
