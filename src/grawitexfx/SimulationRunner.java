/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

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

            universe.updatePlanets();
            universe.packEnergyData();
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
