
package grawitexfx;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author szymon
 */
public class SimulationRunner {
    protected boolean running;
    
    private class Simulation implements Runnable {
        private int iteration;
        
        public int getIteration() {
            return iteration;
        }
        
        public void reset() {
            iteration = 0;
        }

        @Override
        public synchronized void run() {
            iteration = 0;
            
            while (SimulationConfig.getSimulationTimeStep() * iteration < SimulationConfig.getSimulationDuration()) {
                if(!running) {
                    try {
                        wait();
                    } catch(InterruptedException e) {
                    }
                }
                universe.updatePlanets();
                universe.packEnergyData();
                iteration++;
                System.out.println("Kolejna iteracja"+ iteration);
                /*   
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
                }
                 */
            }
            
            iteration = 0;
        }
    }

    private Universe universe;
    private Simulation simulation;
    private Thread simulationThread;

    public SimulationRunner(Universe universe) {
        this.universe = universe;
        this.simulation = new Simulation();
        this.simulationThread = new Thread(this.simulation);
    }

    public void resetTime() {
        simulation.reset();
    }

    public void simulate() {
        running = true;
        switch(simulationThread.getState()) {
            case NEW:
                simulationThread.run();
                //simulationThread.start(); - concurrent version
                break;
            case WAITING:
                simulationThread.interrupt();
                break;
            case TERMINATED:
                simulationThread = new Thread(new Simulation());
                break;
            default:
                break;
        }
//        while (SimulationConfig.getSimulationTimeStep() * iteration < SimulationConfig.getSimulationDuration() && SimulationConfig.canRun()) {
//
//            universe.updatePlanets();
//            universe.packEnergyData();
//            iteration++;
//            //System.out.println("Kolejna iteracja" + iteration);
//            /*   
//            try {
//                TimeUnit.SECONDS.sleep(3);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(SimulationRunner.class.getName()).log(Level.SEVERE, null, ex);
//            }
//             */
//        }
//        System.out.println(universe.getEnergyData());
    }

    public void stop() {
        running = false;
    }
}
