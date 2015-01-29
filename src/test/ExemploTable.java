//import java.util.*;
//import java.awt.*;
//
//import javax.swing.JPanel;
//	
//	public class NotaFiscal extends JPanel{
//	
//	    private Connection con = BancoDeDados.getConnection();
//	    private String[] abas = {
//	        "SCM - Matriz", "SCM - Filial NP", "SCM - Filial VA", "SCM - Filial SFP", "SCM - Filial AP",
//	        "SVA - Ordena"
//	    };
//	    // TIPO SERVICO(1 SCM, 2 SVA) E ID EMPRESA
//	    private int[][] ids = {
//	        {1, 1}, {1, 8}, {1, 9}, {1, 11}, {1, 12},
//	        {2, 2}
//	    };
//	    private final ArrayList[] ids_nota = new ArrayList[this.ids.length];
//	    private String[] columnNames = {"Pessoa", "Data", "Valor total"};
//	    private ListarTableModel[] modelo = new ListarTableModel[this.ids.length];
//	   
//	    public NotaFiscal(){
//	        super();
//	        this.setLayout(new GridLayout(1, 1));
//	        this.setVisible(true);
//	
//	        JTabbedPane jtab = new JTabbedPane();
//	
//	        for(int i = 0; i < this.ids.length; i++){
//	            final int indice = i;
//	            final JTable tabela = new JTable();
//	            JScrollPane scrollPane = new JScrollPane(tabela);
//	            tabela.setFillsViewportHeight(true);
//	            this.modelo[i] = new ListarTableModel(this.columnNames, 0);
//           		tabela.setModel(this.modelo[i]);
//	
//	            jtab.addTab(this.abas[i], scrollPane);
//	
//	            tabela.addMouseListener(new MouseAdapter() {
//	                @Override
//	                public void mouseClicked(MouseEvent e){
//	                    if(e.getClickCount() == 2){
//	                        makePrint(indice, tabela);
//	                    }
//	               }
//	            });
//	
//	            tabela.addKeyListener(new KeyListener() {
//	
//	                public void keyTyped(KeyEvent e) {
//	                    return;
//	                }
//	
//	                public void keyPressed(KeyEvent e) {
//	                    if(e.getKeyCode() == KeyEvent.VK_F5){
//	                        createTable();
//	                    }
//	                    if(e.getKeyCode() == KeyEvent.VK_ENTER){
//	                        makePrint(indice, tabela);
//	                    }
//	                    return;
//	                }
//	
//	                public void keyReleased(KeyEvent e) {
//	                    return;
//	                }
//	            });
//	        }
//	
//	        this.add(jtab);
//	
//	        this.createTable();
//	    }
//	
//	    public void createTable(){
//	        try {
//	            Statement stmt = (Statement) this.con.createStatement();
//	            for(int i = 0; i < this.ids.length; i++){
//	                this.modelo[i].setNumRows(0);
//	                this.ids_nota[i] = new ArrayList<Integer>();
//	                String query =
//	                    "SELECT n.id, n.pessoa_nome, SUM(i.valor) AS valor_total, " +
//	                        "DATE_FORMAT(n.data, \"%d/%m/%Y\") AS data_emissao " +
//	                    "FROM nota_fiscal n " +
//	                        "LEFT JOIN nota_fiscal_itens i ON n.id = i.id_nota_fiscal " +
//	                    "WHERE n.id_empresa = \"" + this.ids[i][1] + "\" " +
//	                        "AND n.tipo_servico = \"" + this.ids[i][0] + "\" AND n.historico = \"0\" AND n.nfe = \"0\" " +
//	                    "GROUP BY n.id ORDER BY n.pessoa_nome";
//	               ResultSet row = stmt.executeQuery(query);
//	               while(row.next()){
//	                   String pessoa = row.getString("pessoa_nome");
//	                   String data = row.getString("data_emissao");
//	                   float valorTotal = row.getFloat("valor_total");
//	
//	                   this.ids_nota[i].add(new Integer(row.getInt("id")));
//	                   this.modelo[i].addRow(new Object[]{ pessoa,
//	                                                    data,
//	                                                    valorTotal
//                                                });
//	               }
//	           }
//	        } catch (SQLException exc){
//	            JOptionPane.showMessageDialog(null, "Erro ao carregar: "+exc);
//	        }
//	    }
//	   
//	    private void makePrint(int indice, JTable tabela){
//	        int tipo_servico = this.ids[indice][0];
//	        int id_empresa = this.ids[indice][1];
//	        int numNota = 0;
//	        final int[] linhas = tabela.getSelectedRows();
//	        if(linhas.length < 1){
//	            JOptionPane.showMessageDialog(null, "Selecione uma nota para imprimir");
//	        }else{
//	            try{
//	            	Statement infNota = (Statement) this.con.createStatement();
//	                String queryNota =
//	                        "SELECT num FROM nota_fiscal_padrao " +
//	                        "WHERE id_empresa = \""+id_empresa+"\" AND tipo_servico = \""+tipo_servico+"\"";
//	                ResultSet rowNota = infNota.executeQuery(queryNota);
//	                rowNota.next();
//	                numNota = rowNota.getInt("num") + 1;
//            } catch (SQLException exc){
//	                //JOptionPane.showMessageDialog(null, exc);
//	            }
//	            String[] confirm = {"Sim", "Não"};
//	            int ok = JOptionPane.showOptionDialog(this, "Tem certeza que deseja imprimir a Nota Fiscal "+numNota+"?", "Imprimir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, confirm, null);
//            if(ok == 0){
//	
//	                ArrayList<Integer> idArr = this.ids_nota[indice];
//	
//	                // Faz a impressao das notas selecionadas
//                final CriaNotaFiscal nota[] = new CriaNotaFiscal[linhas.length];
//	                for(int i = 0; i < linhas.length; i++){
//	                    nota[i] = new CriaNotaFiscal(this.con);
//	                    boolean isSet = nota[i].setNotaFiscal(idArr.get(linhas[i]), numNota, id_empresa, tipo_servico);
//	                    if(isSet){
//	                        if(nota[i].getHistoricoRepetido()){
//	                            String[] confirmExc = {"Sim", "Não"};
//	                            int okExc = JOptionPane.showOptionDialog(this, "Ja existe a nota "+numNota+" no historico deseja excluir a nota do historico?", "Excluir", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, confirmExc, null);
//	                            if(okExc == 0){
//	                                nota[i].excluirHistoricoRepetido();
//	                            }else{
//	                                nota[i].setSalvar(false);
//	                                continue;
//	                            }
//	                        }
//	                        boolean isPrinted = nota[i].print();
//	                        try {
//	                            Thread.sleep(2000);
//	                        } catch (InterruptedException ex) {
//	                            Logger.getLogger(NotaFiscal.class.getName()).log(Level.SEVERE, null, ex);
//	                        }
//	                    }
//	                    numNota++;
//	                }
//	
//	                // Pede para salvar as notas que estao ok
//	                final JDialog dialog = new JDialog();
//	                dialog.setTitle("Salvar Notas");
//	                dialog.setLayout(new BorderLayout());
//	                Container c = JFrame.getFrames()[0];
//	                dialog.setLocation(c.getX()+250, c.getY()+100);
//	                JPanel painel = new JPanel(new GridLayout(0, 1));
//	                final ArrayList<JCheckBox> cboxGroup = new ArrayList<JCheckBox>();
//	
//	                JScrollPane spanel = new JScrollPane(painel);
//	                spanel.setSize(300, 400);
//	
//	                dialog.add(spanel, BorderLayout.CENTER);
//	
//	                String valor = "";
//	                for(int i = 0; i < linhas.length; i++){
//	                    if(nota[i].getSalvar()){
//	                        valor = "";
//	                        valor += tabela.getValueAt(linhas[i], 0);
//	                        valor += " - ";
//	                        valor += tabela.getValueAt(linhas[i], 2);
//	                        JCheckBox cbox = new JCheckBox(valor, true);
//	                        painel.add(cbox);
//	                        cboxGroup.add(cbox);
//	                    }
//	                }
//	
//	                JButton salvar = new JButton("Salvar notas selecionadas");
//	                salvar.addActionListener(new ActionListener(){
//	                    public void actionPerformed(ActionEvent e){
//	                        for(int i = 0; i < cboxGroup.size(); i++){
//	                            if(cboxGroup.get(i).isSelected()){
//	                                nota[i].salvar();
//	                            }
//	                        }
//	                        createTable();
//	                        dialog.setVisible(false);
//	                    }
//	                });
//	                JPanel painel2 = new JPanel();
//	                painel2.add(salvar);
//	                dialog.add(painel2, BorderLayout.AFTER_LAST_LINE);
//	
//	                dialog.setSize(300, 400);
//	                dialog.setVisible(true);
//	            }
//	        }
//	    }
//	}