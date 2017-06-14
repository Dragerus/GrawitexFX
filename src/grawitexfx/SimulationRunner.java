
package grawitexfx;

import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

/**
 *
 * @author szymon
 */
public class SimulationRunner {
    EnergyAnalyzer energyAnalyzer;
    protected boolean running;
    
    private class Simulation {
        private int iteration;
        private Timeline timeline;
        
        public Simulation() {
            timeline = new Timeline(
                    new KeyFrame(Duration.millis((100.0
                            / (SimulationConfig.getSimulationRealSpeedModifier() + 1))),
                            new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(SimulationConfig.getSimulationTimeStep() * iteration >= SimulationConfig.getSimulationDuration()) {
                        stop();
                        iteration = 0;
                        System.out.println("Done");                   
                        energyAnalyzer.publishDataOnChart();                        
                    }
                    
                    System.out.println(SimulationConfig.getSimulationTimeStep() * iteration);
                    System.out.println(SimulationConfig.getSimulationDuration());
                    System.out.println(iteration);
                    
                    universe.updatePlanets();
                    universe.packEnergyData();
                    iteration++;
                }
            }));
        }
        
        public int getIteration() {
            return iteration;
        }
        
        public void reset() {
            iteration = 0;
        }
        
        public void start() {
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
        
        public void stop() {
            timeline.stop();
        }
        
    }

    private Universe universe;
    private Simulation simulation;
    private Thread simulationThread;

    public SimulationRunner(Universe universe, EnergyAnalyzer energyAnalyzer) {
        this.universe = universe;
        this.simulation = new Simulation();
        this.energyAnalyzer = energyAnalyzer;
    }

    public void resetTime() {
        simulation.reset();
    }

    public void simulate() {
        running = true;
        simulation.start();
    }

    public void stop() {
        running = false;
    }
}
