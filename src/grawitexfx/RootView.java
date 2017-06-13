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
    Grawitex grawitex = new Grawitex();             /* TODO: potatoes gonna potatoe */
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
    public ChoiceBox<String> SimTimeChoice;
    @FXML
    public ChoiceBox<String> SimStepChoice;
    @FXML
    public TextField SimTimeText;
    @FXML
    public TextField SimStepText;
    @FXML
    public TableView<Planet> PlanetDataTable;
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
    public TableColumn<Planet, Double> VzColumn;
    @FXML
    public Slider SimulationSpeedSlider;
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
    xAxis.setLabel("Iteracja");
    this.EnergyChart = new LineChart<>(xAxis, yAxis);

    XYChart.Series<Number, Number> series = new XYChart.Series<>();
    series.setName("Energia planet");
    // populating the series with data
    this.EnergyChart.setTitle("Energia Planet");
    
    series.getData().add(new XYChart.Data<>(1, 23));
    series.getData().add(new XYChart.Data<>(2, 114));
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
    private void importDataFromFile(MouseEvent event) {
        System.out.println("Import data from file");
        ArrayList<Planet> data = (ArrayList<Planet>)grawitex.importDataFromFile();
        PlanetDataTable.setItems(FXCollections.observableArrayList(data));
    }

    @FXML
    private void exportDataToFile(MouseEvent event) {
        System.out.println("Export data to file");
        grawitex.exportDataToFile();
    }
   
    @FXML
    private void simulationStart(MouseEvent event) {
        System.out.println("Simulation start");
        grawitex.simulationStart();
       
    }

    @FXML
    private void simulationStop(MouseEvent event) {
        System.out.println("Simulation stop");
        grawitex.simulationStop();
    }

    @FXML
    private void simulationReset(MouseEvent event) {
        System.out.println("Simulation reset");
        grawitex.simulationReset();
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
