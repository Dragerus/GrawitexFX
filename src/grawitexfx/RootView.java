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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author szymon
 */
public class RootView implements Initializable {

    public Universe universe = new Universe();
    private SimulationRunner simRunner = new SimulationRunner(universe);
    private VisualisationRenderer renderer;
    private boolean simSpeedActualiseEnabled = false;

    private static final Map<String, TimeUnit> timeUnitMap;
    static {
        timeUnitMap = new HashMap<>();
        timeUnitMap.put("sekund", TimeUnit.Seconds);
        timeUnitMap.put("dni", TimeUnit.Days);
        timeUnitMap.put("lat", TimeUnit.Years);
    }
    
    @FXML
    private ChoiceBox<String> SimTimeChoice;
    @FXML
    private ChoiceBox<String> SimStepChoice;
    @FXML
    private TextField SimTimeText;
    @FXML
    private TextField SimStepText;
    @FXML
    private TableView<Planet> PlanetDataTable;
    @FXML
    private TableColumn<Planet, String> NazwaPlanetyColumn;
    @FXML
    private TableColumn<Planet, Double> MasaPlanetyColumn;
    @FXML
    private TableColumn<Planet, Double> xPlanetyColumn;
    @FXML
    private TableColumn<Planet, Double> yPlanetyColumn;
    @FXML
    private TableColumn<Planet, Double> zPlanetyColumn;
    @FXML
    private TableColumn<Planet, Double> VxColumn;
    @FXML
    private TableColumn<Planet, Double> VyColumn;
    @FXML
    private TableColumn<Planet, Double> VzColumn;
    @FXML
    private Slider SimulationSpeedSlider;
    @FXML
    private LineChart<Number, Number> EnergyChart;
    @FXML
    private SubScene visualizationScene;
    @FXML
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        EnergyChart = new LineChart<>(xAxis, yAxis);
        EnergyChart.setTitle("Energia układu planet");
    
    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.getData().add(new XYChart.Data<>( (Number)1, (Number)23));
    series.getData().add(new XYChart.Data<>( (Number)2, (Number)114));
    *///System.out.println(EnergyChart.getXAxis());
    //EnergyChart.getData().add(series);
    /*

    xAxis.setLabel("Iteracja");
    this.EnergyChart 

    
    series.setName("Energia planet");
    // populating the series with data
    this.EnergyChart.setTitle("Energia Planet");
    

    series.getData().add(new XYChart.Data<>(3, 15));
    series.getData().add(new XYChart.Data<>(4, 124));
    
    System.out.println(series);
    this.EnergyChart.getData().add(series);
    System.out.println(this.EnergyChart.getData().toArray().length);
        */

        
        
        //System.out.println(rb);
        SimTimeChoice.setItems(FXCollections.observableArrayList(timeUnitMap.keySet()));
        SimTimeChoice.getSelectionModel().selectFirst();

        SimStepChoice.setItems(FXCollections.observableArrayList(timeUnitMap.keySet()));
        SimStepChoice.getSelectionModel().selectFirst();

        SimulationSpeedSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                SimulationConfig.setSimulationRealSpeedModifier((double)observable.getValue());
            }
        });
        
        setSimulationDuration((MouseEvent)null);
        setSimulationTimeStep((MouseEvent)null);
        SimulationConfig.setSimulationRealSpeedModifier(50.0);


        /* TODO: POMOCY!!! */
        //System.out.println("Ilosc kolumn w tabeli:"+PlanetDataTable.getColumns().size());
        //PlanetDataTable.s
        NazwaPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        MasaPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("mass"));
        xPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        zPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("z"));
        VxColumn.setCellValueFactory(new PropertyValueFactory<>("Vx"));
        VyColumn.setCellValueFactory(new PropertyValueFactory<>("Vy"));
        VzColumn.setCellValueFactory(new PropertyValueFactory<>("Vz"));
        //System.out.println(PlanetDataTable.getColumns().get(0).getText());
        //PlanetDataTable.getColumns().addAll(NazwaPlanetyColumn/*, MasaPlanetyColumn, xPlanetyColumn, yPlanetyColumn, zPlanetyColumn, VxColumn, VyColumn, VzColumn*/ );

        this.renderer = new VisualisationRenderer(visualizationScene, universe);
        universe.addObserver(renderer);
    }
    
    private void displayError(String header, String content) {
        Alert simulationConfigErrorAlert = new Alert(Alert.AlertType.ERROR);
        simulationConfigErrorAlert.setTitle("Błąd");
        simulationConfigErrorAlert.setHeaderText(header);
        simulationConfigErrorAlert.setContentText(content);
        simulationConfigErrorAlert.showAndWait();
    }
    
    private void displayFileChooser() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if(file == null){return;}                          /*  "cancel clicked"  */
        } catch (IOException ex) {
            Logger.getLogger(RootView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void importDataFromFile() {
        //System.out.println("Import data from file");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if(file == null){return;}                          /*  "cancel clicked"  */
            PlanetDataReader pdr = new PlanetDataReader();
            ArrayList<Planet> data = pdr.readPlanets(file.getAbsolutePath());
            universe.setPlanets(data);
            PlanetDataTable.setItems(FXCollections.observableArrayList(data));
            
        } catch (IOException e) {
            this.displayError("Błąd podczas importu danych", e.getMessage());
            //e.printStackTrace();
        }
        //VzColumn.notifyAll();
    }
    
    @FXML
    private void exportDataToFile() {
        System.out.println("Export data to file");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);
            if(file == null){return ;}                          /*  "cancel clicked"  */
            System.out.println("Wybrany plik: " + file.getAbsolutePath());
     
            PlanetDataWriter pdw = new PlanetDataWriter();
            pdw.writePlanetsToFile(file.getAbsolutePath(), universe.getPlanets());

            this.displayError("Eksport danych do pliku", "Pomyślnie wyeksportowano dane do pliku o nazwie: "+file.getName());
        } catch (IOException e) {
            this.displayError("Błąd podczas eksportu danych", e.getMessage());
            //e.printStackTrace();
        }

    }
   
    @FXML
    public void simulationStart() {
        System.out.println("Simulation start");
        //System.out.println(SimulationSpeedSlider.valueProperty().doubleValue());
        System.out.println(SimulationConfig.print());

        if (!SimulationConfig.isValid()) {
            displayError(
                    "Niepoprawne parametry symulacji",
                    "Sprawdź poprawność parametrów i spróbuj ponownie"
            );
        } else {
            SimulationConfig.enableSimulation();
            simRunner.simulate();
        }
    }

    @FXML
    private void simulationStop() {
        System.out.println("Simulation stop");
        SimulationConfig.disableSimulation();
    }

    @FXML
    public void simulationReset() {
        System.out.println("Simulation reset");
        simRunner.resetTime();
    }

    @FXML
    private void setSimulationTimeStep(InputMethodEvent event) {
        try {
            SimulationConfig.setSimulationTimeStep(
                    Double.parseDouble(SimStepText.getText()),
                    timeUnitMap.get(SimStepChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
    }
    
    @FXML
    private void setSimulationDuration(InputMethodEvent event) {
        try {
            SimulationConfig.setSimulationDuration(
                    Double.parseDouble(SimTimeText.getText()),
                    timeUnitMap.get(SimTimeChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
    }
    
    @FXML
    private void setSimulationDuration(MouseEvent event) {
        try {
            SimulationConfig.setSimulationDuration(
                    Double.parseDouble(SimTimeText.getText()),
                    timeUnitMap.get(SimTimeChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
    }

    @FXML
    private void setSimulationTimeStep(MouseEvent event) {
        try {
            SimulationConfig.setSimulationTimeStep(
                    Double.parseDouble(SimStepText.getText()),
                    timeUnitMap.get(SimStepChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
    }

    @FXML
    private void updateConfig(MouseEvent event) {
    }

    @FXML
    private void updateConfig(InputMethodEvent event) {
    }
}
