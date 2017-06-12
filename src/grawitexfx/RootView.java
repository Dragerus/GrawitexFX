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
public class RootView implements Initializable {

    public Universe universe = new Universe();
    /* TODO: potatoes gonna potatoe */
    SimulationRunner simRunner = new SimulationRunner(universe);

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

        NazwaPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        /* TODO: POMOCY!!! */
        System.out.println(PlanetDataTable.getColumns().size());
        MasaPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("mass"));
        xPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("x"));
        yPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("y"));
        zPlanetyColumn.setCellValueFactory(new PropertyValueFactory<>("z"));
        VxColumn.setCellValueFactory(new PropertyValueFactory<>("Vx"));
        VyColumn.setCellValueFactory(new PropertyValueFactory<>("Vy"));
        VzColumn.setCellValueFactory(new PropertyValueFactory<>("Vz"));

        PlanetDataTable.getColumns().addAll(NazwaPlanetyColumn, MasaPlanetyColumn, xPlanetyColumn, yPlanetyColumn, zPlanetyColumn, VxColumn, VyColumn, VzColumn);

        updateConfig(null);
    }

    @FXML
    private void importDataFromFile(MouseEvent event) {
        //System.out.println("Import data from file");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            /*System.out.println("Wybrany plik: "+file.getAbsolutePath());*/

            PlanetDataReader pdr = new PlanetDataReader();
            universe.setPlanets(pdr.readPlanets(file.getAbsolutePath()));
            /*System.out.println("Wczytane:");
            for( Planet x: (ArrayList<Planet>)universe.getPlanets()){
                System.out.println(x);
            }*/
            System.out.println(universe.getPlanets());
        } catch (IOException e) {
            e.printStackTrace();
        }

        VzColumn.notifyAll();
    }

    @FXML
    private void exportDataToFile(MouseEvent event) {
        System.out.println("Export data to file");

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showSaveDialog(stage);
            System.out.println("Wybrany plik: " + file.getAbsolutePath());
            PlanetDataWriter pdw = new PlanetDataWriter();
            pdw.writePlanetsToFile(file.getAbsolutePath(), universe.getPlanets());
            /*
            PlanetDataReader pdr = new PlanetDataReader();
            universe.setPlanets(pdr.readPlanets( file.getAbsolutePath() ));*/
            /*System.out.println("Wczytane:");
            for(Planet x: universe.PlanetsTable){
                System.out.println(x);
            }*/
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    private boolean simulationConfigIsValid() {
        return SimulationConfig.getSimulationDuration() <= 0.0
                || SimulationConfig.getSimulationTimeStep() <= 0.0
                || SimulationConfig.getSimulationTimeStep() >= SimulationConfig.getSimulationDuration();
    }
    
    private void displayError(String header, String content) {
        Alert simulationConfigErrorAlert = new Alert(Alert.AlertType.ERROR);
        simulationConfigErrorAlert.setTitle("Błąd");
        simulationConfigErrorAlert.setHeaderText(header);
        simulationConfigErrorAlert.setContentText(content);
        simulationConfigErrorAlert.showAndWait();
    }

    @FXML
    private void simulationStart(MouseEvent event) {
        System.out.println("Simulation start");
        System.out.println(SimulationSpeedSlider.valueProperty().doubleValue());
        System.out.println(SimulationConfig.print());

        if (!simulationConfigIsValid()) {
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
    private void simulationStop(MouseEvent event) {
        System.out.println("Simulation stop");
        SimulationConfig.disableSimulation();
    }

    @FXML
    private void simulationReset(MouseEvent event) {
        System.out.println("Simulation reset");
        simRunner.resetTime();
    }

    @FXML
    private void updateConfig(MouseEvent event) {
        try {
            SimulationConfig.setSimulationTimeStep(
                    Double.parseDouble(SimStepText.getText()),
                    timeUnitMap.get(SimStepChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
        try {
            SimulationConfig.setSimulationDuration(
                    Double.parseDouble(SimTimeText.getText()),
                    timeUnitMap.get(SimTimeChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
        SimulationConfig.setSimulationRealSpeedModifier(
                SimulationSpeedSlider.getValue()
        );
    }
}
