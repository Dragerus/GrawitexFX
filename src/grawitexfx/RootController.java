/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author szymon
 */
public class RootController implements Initializable {

    @FXML
    private Button importDataButton;
    
    private boolean simSpeedActualiseEnabled = false;
    
    @FXML
    public ChoiceBox<String> SimTimeChoice;
    
    @FXML
    public ChoiceBox<String> SimStepChoice;
    @FXML
    public TextField SimTimeText;
    @FXML
    public TextField SimStepText;
    @FXML
    private TableView<?> PlanetDataTable;
    @FXML
    private Canvas SimulationCanvas;
    @FXML
    private LineChart<?, ?> EnergyChart;
    
    @FXML
    private Slider SimulationSpeedSlider;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        SimTimeChoice.setItems( FXCollections.observableArrayList( "Sekunda", "Dzień", "Rok") );
        SimTimeChoice.getSelectionModel().selectFirst();

        SimStepChoice.setItems( FXCollections.observableArrayList( "Sekunda", "Dzień", "Rok") );
        SimStepChoice.getSelectionModel().selectFirst();
        
        
        SimulationSpeedSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2){
                System.out.println( arg0.getValue() );
            }
        
        
        });
    }    

    @FXML
    private void importDataFromFile(MouseEvent event) {
        System.out.println("Import data from file");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));  
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            System.out.println("Wybrany plik: "+file.getAbsolutePath());
            
          }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void exportDataToFile(MouseEvent event) {
        System.out.println("Export data to file");
    }

    @FXML
    private void simulationStart(MouseEvent event) {
        System.out.println("Simulation start");
        System.out.println(SimulationSpeedSlider.valueProperty().doubleValue());
    }
    

    @FXML
    private void simulationStop(MouseEvent event) {
        System.out.println("Simulation stop");
    }

    @FXML
    private void simulationReset(MouseEvent event) {
        System.out.println("Simulation reset");
    }

}
