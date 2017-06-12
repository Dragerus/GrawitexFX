/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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

    public Universe universe = new Universe(); /* TODO: potatoes gonna potatoe */
    
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
    private TableView<Planet> PlanetDataTable;
    @FXML
    private Canvas SimulationCanvas;
    @FXML
    private LineChart<?, ?> EnergyChart;
    
    @FXML
    private Slider SimulationSpeedSlider;
    @FXML
    private TableColumn<?, ?> NazwaPlanetyColumn;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        PlanetDataTable = new TableView<Planet>();
        System.out.println(PlanetDataTable.getColumns().size());
        System.out.println(PlanetDataTable.getColumns());
        /*PlanetDataTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
        PlanetDataTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("mass"));
        PlanetDataTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("x"));
        PlanetDataTable.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("y"));
        PlanetDataTable.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("z"));
        PlanetDataTable.getColumns().get(5).setCellValueFactory(new PropertyValueFactory<>("Vx"));
        PlanetDataTable.getColumns().get(6).setCellValueFactory(new PropertyValueFactory<>("Vy"));
        PlanetDataTable.getColumns().get(7).setCellValueFactory(new PropertyValueFactory<>("Vz")); */
        
        
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
        //System.out.println("Import data from file");
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));  
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            /*System.out.println("Wybrany plik: "+file.getAbsolutePath());*/
            
            PlanetDataReader pdr = new PlanetDataReader();
            universe.setPlanets(pdr.readPlanets( file.getAbsolutePath() ));
            /*System.out.println("Wczytane:");
            for( Planet x: (ArrayList<Planet>)universe.getPlanets()){
                System.out.println(x);
            }*/
                        
            ObservableList<Planet> obsList = FXCollections.observableArrayList( (ArrayList<Planet>)universe.getPlanets() );
            System.out.println(obsList.toString());
           
            PlanetDataTable.setEditable(true);
            PlanetDataTable.setItems(obsList);
            
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }

    @FXML
    private void exportDataToFile(MouseEvent event) {
        System.out.println("Export data to file");
        
        try{
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));  
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);
            System.out.println("Wybrany plik: "+file.getAbsolutePath());
            PlanetDataWriter pdw = new PlanetDataWriter();
            pdw.writePlanetsToFile(file.getAbsolutePath() , universe.getPlanets() );
            /*
            PlanetDataReader pdr = new PlanetDataReader();
            universe.setPlanets(pdr.readPlanets( file.getAbsolutePath() ));*/
            /*System.out.println("Wczytane:");
            for(Planet x: universe.PlanetsTable){
                System.out.println(x);
            }*/
          }
        catch(Exception e){
            e.printStackTrace();
        }
    
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
