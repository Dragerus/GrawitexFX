
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
            System.out.println("Kolejna iteracja"+ iteration);
            /*   
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException ex) {
                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
            }
             */
        }
        System.out.println(universe.getEnergyData());
    }

}
