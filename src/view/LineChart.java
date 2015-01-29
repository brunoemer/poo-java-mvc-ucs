package view;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 * Classe LineChart 
 * @author            Bruno Emer
 * @author            Michel Zanuz
 *
 */
public class LineChart extends ChartFrame {

	/**
	 * Metodo que cria o gráfico
	 *  
	 * @param title           titulo
 	 * @param lista           lista de dados
	 */
	public LineChart(String title, List<Object[]> lista) {
		super(title, null);
		
		CategoryDataset dataset = createDataset(lista);
		JFreeChart chart = createChart(dataset);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new Dimension(700, 500));
		setContentPane(chartPanel);
	}
	
	private CategoryDataset createDataset(List<Object[]> lista) {
		ArrayList<String> datas;
		ArrayList<Integer> dados;
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(Object[] row : lista){
			 String serie = (String) row[0];
			 datas = (ArrayList<String>) row[1];
			 dados = (ArrayList<Integer>) row[2];
			 for(int i = 0; i < datas.size(); i++){
				 dataset.addValue(dados.get(i), serie, datas.get(i));
			 }
		}
		 
		 // row keys...
//        String series1 = "First";
//        String series2 = "Second";
//        String series3 = "Third";
//        
//        // column keys...
//        String type1 = "Type 1";
//        String type2 = "Type 2";
//        String type3 = "Type 3";
//        String type4 = "Type 4";
//        String type5 = "Type 5";
//        String type6 = "Type 6";
//        String type7 = "Type 7";
//        String type8 = "Type 8";
//        
//        // create the dataset...
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//        dataset.addValue(1.0, series1, type1);
//        dataset.addValue(4.0, series1, type2);
//        dataset.addValue(3.0, series1, type3);
//        dataset.addValue(5.0, series1, type4);
//        dataset.addValue(5.0, series1, type5);
//        dataset.addValue(7.0, series1, type6);
//        dataset.addValue(7.0, series1, type7);
//        dataset.addValue(8.0, series1, type8);
//
//        dataset.addValue(5.0, series2, type1);
//        dataset.addValue(7.0, series2, type2);
//        dataset.addValue(6.0, series2, type3);
//        dataset.addValue(8.0, series2, type4);
//        dataset.addValue(4.0, series2, type5);
//        dataset.addValue(4.0, series2, type6);
//        dataset.addValue(2.0, series2, type7);
//        dataset.addValue(1.0, series2, type8);
//
//        dataset.addValue(4.0, series3, type1);
//        dataset.addValue(3.0, series3, type2);
//        dataset.addValue(2.0, series3, type3);
//        dataset.addValue(3.0, series3, type4);
//        dataset.addValue(6.0, series3, type5);
//        dataset.addValue(3.0, series3, type6);
//        dataset.addValue(4.0, series3, type7);
//        dataset.addValue(3.0, series3, type8);

        return dataset;
    }
    
	/**
	 * Metodo que traça os graficos
	 * 
	 * @param dataset
	 * @return       grafico
	 */
    private JFreeChart createChart(final CategoryDataset dataset) {
        // create the chart...
        final JFreeChart chart = ChartFactory.createLineChart(
            "Classificação",	       // chart title
            "Data",                    // domain axis label
            "Pontuação",               // range axis label
            dataset,                   // data
            PlotOrientation.VERTICAL,  // orientation
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );
        
        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = (CategoryPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.white);

        // customise the range axis...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRangeIncludesZero(true);
        
        // customise the renderer...
        //final LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
        
        return chart;
    }
    
}
