package view;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import model.Model;
import model.Rodada;

/**
 * Classe AdapterTableModel 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class AdapterRodadaTableModel extends DefaultTableModel implements AdapterTableModel {
	private List<Rodada> rodadas;
	
	/**
	 * metodo construtor
	 */
	public AdapterRodadaTableModel() {
		super();
		// TODO Auto-generated constructor stub
		rodadas = new ArrayList<Rodada>();
	}

	/**
	 * Metodo para adicionar a linha
	 * 
	 * @param obj       rodada
	 */
	public void addRow(Model obj) {
		Rodada data = (Rodada) obj;
		rodadas.add(data);
		// TODO Auto-generated method stub
		super.addRow(new Object[] {data.getNumero(), data.getData(), (data.getRealizado() == 1?"Sim":"Nao")});
	}
	/**
	 * Metodo para buscar as rodadas pelo index
	 * 
	 * @param index
	 * @return          rodadas
	 */
	public Rodada getRodada(int index) {
		return rodadas.get(index);
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
		return false;
	}
}
