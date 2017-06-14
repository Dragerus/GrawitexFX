/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    
    public XYChart.Series<Number, Number> getDataSeries(ArrayList<Double> data){
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
    
    public void publishDataOnChart(){
        //System.out.println("Iteracji:"+ universe.getEnergyData().size());
        //System.out.println("Energie układu: "+ universe.getEnergyData() );
        ArrayList<Double> data = universe.getEnergyData();
        ArrayList<Double> tmp = (ArrayList<Double>)data.clone();
        Collections.sort(tmp, new Comparator<Double>(){
            public int compare(Double d1, Double d2){
            if(d1 > d2)return 1;
            else if(d1 == d2)return 0;
            return -1;}
        });
        double mean = tmp.get( (int)(tmp.size()/2) );
        //System.out.println("Mean:"+mean);
        for(int i = 0; i< data.size(); i++){
            data.set(i, data.get(i)/mean);
        }
        //System.out.println("Energie układu: "+ data );
        this.chart.getData().add( this.getDataSeries( data ) );
        this.chart.getYAxis().setLabel("Energia \n*"+mean);
        
        /*XYChart.Series<Integer, Double> EnergyData = new XYChart.Series<>();
        EnergyData = this.getDataSeries( universe.getEnergyData() );*/
    }
            /*EnergyAnalyzer EnergyAnalyzer = new EnergyAnalyzer();
            
            ;*/
    
    
}
