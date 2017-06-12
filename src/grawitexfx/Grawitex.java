/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author szymon
 */
public class Grawitex extends Application {
    
    public Universe universe = new Universe(); /* TODO: potatoes gonna potatoe */
    SimulationRunner simRunner = new SimulationRunner(universe);

    private static final Map<String, SimulationConfig.TimeUnit> timeUnitMap;
     static {
        timeUnitMap = new HashMap<>();
        timeUnitMap.put("sekund", SimulationConfig.TimeUnit.Seconds);
        timeUnitMap.put("dni", SimulationConfig.TimeUnit.Days);
        timeUnitMap.put("lat", SimulationConfig.TimeUnit.Years);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("GrawitexFX");
        stage.setScene(scene);
        stage.show();

    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println("Hello world");    
        launch(args);
       
    }
    
    public void importDataFromFile() {
        //System.out.println("Import data from file");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Demo.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Importuj dane z pliku");
            stage.setScene(new Scene(root1));
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(stage);
            if(file == null){return ;}                          /*  "cancel clicked"  */
            PlanetDataReader pdr = new PlanetDataReader();
            universe.setPlanets(pdr.readPlanets(file.getAbsolutePath()));

            System.out.println(universe.getPlanets());
        } catch (IOException e) {
            this.displayError("Błąd podczas importu danych", e.getMessage());
            //e.printStackTrace();
        }

        //VzColumn.notifyAll();
    }
    
    public void exportDataToFile() {
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
    
    private void simulationStart() {
        System.out.println("Simulation start");
        //System.out.println(SimulationSpeedSlider.valueProperty().doubleValue());
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

    private void simulationStop() {
        System.out.println("Simulation stop");
        SimulationConfig.disableSimulation();
    }

    private void simulationReset() {
        System.out.println("Simulation reset");
        simRunner.resetTime();
    }
/* // TODO: To powinno chyba być w SimulationConfig
    private void updateConfig() {
        try {
            SimulationConfig.setSimulationTimeStep(
                    Double.parseDouble( RootView.SimStepText.getText()),
                    timeUnitMap.get( RootView.SimStepChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
        try {
            SimulationConfig.setSimulationDuration(
                    Double.parseDouble( RootView.SimTimeText.getText() ),
                    timeUnitMap.get( RootView.SimTimeChoice.getSelectionModel().getSelectedItem())
            );
        } catch (NumberFormatException e) {
        }
        SimulationConfig.setSimulationRealSpeedModifier(
                RootView.SimulationSpeedSlider.getValue()
        );
    }
    */
    
    
    
}
