/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author szymon
 */
public class SimulationRunner {

    Universe universe;
    private int iteration;

    public SimulationRunner(Universe universe) {
        this.universe = universe;
        this.iteration = 0;
    }

    public void resetTime() {
        this.iteration = 0;
    }

    public void simulate() {

        while (SimulationConfig.getSimulationTimeStep() * iteration < SimulationConfig.getSimulationDuration() && SimulationConfig.canRun()) {

            /* update logic moved to universe
            for (Planet x : (ArrayList<Planet>) universe.getPlanets()) {

                for (Planet y : (ArrayList<Planet>) universe.getPlanets()) {
                    if (x != y) {
                        // it`s pointless to calculate gravitation to self
                        x.calculateGravity(y);
                    }
                    System.out.println("t(" + iteration + ")" + x.getName() + " " + y.getName());
                }
            }
            for (Planet x : (ArrayList<Planet>) universe.getPlanets()) {
                x.updateState(SimulationConfig.getSimulationTimeStep());
            }*/
            universe.updatePlanets();
            iteration += 1;
            /*   
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
             */
        }

    }

}
