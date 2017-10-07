/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Driver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Paint;
import java.awt.Shape;
import java.util.ArrayList;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.DefaultDrawingSupplier;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Someone
 */
public class Graph extends JFrame {  
    private double[][] xy = null;
        
    public Graph(ArrayList<Data> data, String title){
        super(title);
        ArrayList<Data> points1 = new ArrayList<>();
        ArrayList<Data> points2 = new ArrayList<>();        
        
        for(Data i : data){
            if(i.getHoax()<1){ //hoax=0 -> tidak
                points1.add(i);
            }else{
                points2.add(i);
            }
        }
                        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("Tidak", createSeries(points1));
        dataset.addSeries("Ya", createSeries(points2));
        JFreeChart chart = createChart(dataset, title);
        ChartPanel chartPanel = new ChartPanel(chart, false);
        chartPanel.setPreferredSize(new Dimension(500, 400));
        this.add(chartPanel, BorderLayout.CENTER);       
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true); 
    }
        
    private double[][] createSeries(ArrayList<Data> samples) {
        double[][] series = new double[2][samples.size()];
        for (int i = 0; i < samples.size(); i++) {
            series[0][i] = samples.get(i).getEmosi();
            series[1][i] = samples.get(i).getProvokasi();
        }
        return series;
    }

    private JFreeChart createChart(XYDataset dataset, String title) {
        JFreeChart chart = ChartFactory.createScatterPlot(
            ("Scatter Plot "+title), "Emosi", "Provokasi", dataset,
            PlotOrientation.VERTICAL, true, true, false);
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        Shape[] cross = DefaultDrawingSupplier.createStandardSeriesShapes();
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        MyRenderer renderer = new MyRenderer(false, true);
        plot.setRenderer(renderer);
        renderer.setSeriesShape(0, cross[0]);
        plot.setRenderer(renderer);
        return chart;
    }
    
    private class MyRenderer extends XYLineAndShapeRenderer {

        public MyRenderer(boolean lines, boolean shapes) {
            super(lines, shapes);
        }

        @Override
        public Paint getItemPaint(int row, int col) {
            
                return super.getItemPaint(row, col);
            
        }
    }
}
