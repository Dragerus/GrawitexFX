/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import grawitexfx.SimulationConfig.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
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
import javafx.scene.control.Alert;
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
    private TableView<Planet> PlanetDataTable;
    @FXML
    private Canvas SimulationCanvas;
    @FXML
    private LineChart<?, ?> EnergyChart;

    @FXML
    private Slider SimulationSpeedSlider;

    private static final Map<String, TimeUnit> timeUnitMap;

    static {
        timeUnitMap = new HashMap<>();
        timeUnitMap.put("sekund", TimeUnit.Seconds);
        timeUnitMap.put("dni", TimeUnit.Days);
        timeUnitMap.put("lat", TimeUnit.Years);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        SimTimeChoice.setItems(FXCollections.observableArrayList(timeUnitMap.keySet()));
        SimTimeChoice.getSelectionModel().selectFirst();

        SimStepChoice.setItems(FXCollections.observableArrayList(timeUnitMap.keySet()));
        SimStepChoice.getSelectionModel().selectFirst();
        
        SimulationSpeedSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observedValue, Object arg1, Object arg2) {
                System.out.println(observedValue.getValue());
                updateConfig(null);
            }
        });
        
        updateConfig(null);
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
          }
        catch(IOException e){
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
        catch(IOException e){
            e.printStackTrace();
        }
    
    }

    @FXML
    private void simulationStart(MouseEvent event) {
        System.out.println("Simulation start");
        System.out.println(SimulationSpeedSlider.valueProperty().doubleValue());
        System.out.println(SimulationConfig.print());
        
        if(SimulationConfig.getSimulationDuration() <= 0.0 ||
                SimulationConfig.getSimulationTimeStep() <= 0.0 ||
                SimulationConfig.getSimulationTimeStep() >= SimulationConfig.getSimulationDuration()) {
            Alert simulationConfigErrorAlert = new Alert(Alert.AlertType.ERROR);
            simulationConfigErrorAlert.setTitle("Błąd");
            simulationConfigErrorAlert.setHeaderText(
                    "Niepoprawne parametry symulacji"
            );
            simulationConfigErrorAlert.setContentText(
                    "Sprawdź poprawność parametrów i spróbuj ponownie"
            );
            simulationConfigErrorAlert.showAndWait();
        }else{
            SimulationRunner simRunner = new SimulationRunner(universe);
            simRunner.simulate();
        }
        
    }

    @FXML
    private void simulationStop(MouseEvent event) {
        System.out.println("Simulation stop");
    }

    @FXML
    private void simulationReset(MouseEvent event) {
        System.out.println("Simulation reset");
    }
    
    @FXML
    private void updateConfig(MouseEvent event) {
        try {
            SimulationConfig.setSimulationTimeStep(
                    Double.parseDouble(SimStepText.getText()),
                    timeUnitMap.get(SimStepChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {}
        try {
            SimulationConfig.setSimulationDuration(
                    Double.parseDouble(SimTimeText.getText()),
                    timeUnitMap.get(SimTimeChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {}
        SimulationConfig.setSimulationRealSpeedModifier(
                    SimulationSpeedSlider.getValue()
        );
    }
}
