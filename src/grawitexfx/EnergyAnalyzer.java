/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import javafx.scene.chart.XYChart;

/**
 *
 * @author szymon
 */
public class EnergyAnalyzer {
    
    
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
    
}
