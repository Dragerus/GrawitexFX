/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

/**
 *
 * @author szymon
 */
public class EnergyAnalyzer {
    
        public Universe universe;
        public LineChart<Number, Number> chart;
    
    public EnergyAnalyzer(Universe universe, LineChart<Number, Number> chart){
        this.universe = universe;
        this.chart = chart;
    }
    
    public XYChart.Series<Integer, Double> getDataSeries(ArrayList<Double> data){
        XYChart.Series series = new XYChart.Series();
        int i = 0;
        for (Double x: data){
            series.getData().add(new XYChart.Data(i, x));
            i += 1;
        }
        /*series.getData().add(new XYChart.Data(1, 23));
        series.getData().add(new XYChart.Data(2, 14));
        series.getData().add(new XYChart.Data(3, 15));
        series.getData().add(new XYChart.Data(4, 24));
        series.getData().add(new XYChart.Data(5, 34));
        series.getData().add(new XYChart.Data(6, 36));
        series.getData().add(new XYChart.Data(7, 22));
        series.getData().add(new XYChart.Data(8, 45));
        series.getData().add(new XYChart.Data(9, 43));
        series.getData().add(new XYChart.Data(10, 17));
        series.getData().add(new XYChart.Data(11, 29));
        series.getData().add(new XYChart.Data(12, 25));*/
        
        return series;
    }
    
    public void publishDataOnChart(XYChart<Integer, Double> series){
  
        
        /*XYChart.Series<Integer, Double> EnergyData = new XYChart.Series<>();
        EnergyData = this.getDataSeries( universe.getEnergyData() );*/
    }
            /*EnergyAnalyzer EnergyAnalyzer = new EnergyAnalyzer();
            
            ;*/
    
    
}
