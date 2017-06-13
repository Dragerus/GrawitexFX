/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

/**
 *
 * @author adam
 */
public final class SimulationConfig {
    
    public static Universe universe = new Universe();

    private static boolean canRun = false;
    private static double simulationDuration;
    private static double simulationTimeStep;
    private static double simulationRealSpeedModifier;
    private static final double YEAR_SCALE = 365.25;
    private static final double DAY_SCALE = 1.0;
    private static final double SECOND_SCALE = 1.0 / 3600.0 / 24.0;

    public enum TimeUnit {
        Seconds,
        Days,
        Years
    }

    public static boolean canRun(){
        return canRun;
    }
    
    public static void disableSimulation(){
        canRun = false;
    }
    
    public static void enableSimulation(){
        canRun = true;
    }
        
    
    public static double getSimulationDuration() {
        return simulationDuration;
    }

    public static void setSimulationDuration(double simulationDuration, TimeUnit unit) {
        SimulationConfig.simulationDuration = simulationDuration;
        switch (unit) {
            case Seconds:
                SimulationConfig.simulationDuration *= SECOND_SCALE;
                break;
            case Days:
                SimulationConfig.simulationDuration *= DAY_SCALE;
                break;
            case Years:
                SimulationConfig.simulationDuration *= YEAR_SCALE;
                break;
            default:
                break;
        }
    }

    public static double getSimulationTimeStep() {
        return simulationTimeStep;
    }

    public static void setSimulationTimeStep(double simulationTimeStep, TimeUnit unit) {
        SimulationConfig.simulationTimeStep = simulationTimeStep;
        switch (unit) {
            case Seconds:
                SimulationConfig.simulationTimeStep *= SECOND_SCALE;
                break;
            case Days:
                SimulationConfig.simulationTimeStep *= DAY_SCALE;
                break;
            case Years:
                SimulationConfig.simulationTimeStep *= YEAR_SCALE;
                break;
            default:
                break;
        }
    }
    
    public static boolean isValid() {
        return SimulationConfig.getSimulationDuration() > 0.0
                || SimulationConfig.getSimulationTimeStep() > 0.0
                || SimulationConfig.getSimulationTimeStep() < SimulationConfig.getSimulationDuration();
    }

    public static double getSimulationRealSpeedModifier() {
        return simulationRealSpeedModifier;
    }

    public static void setSimulationRealSpeedModifier(double simulationRealSpeedModifier) {
        SimulationConfig.simulationRealSpeedModifier = simulationRealSpeedModifier;
    }

    public static String print() {
        return "simulationDuration = " + simulationDuration
                + "\nsimulationTimeStep = " + simulationTimeStep
                + "\nsimulationRealSpeedModifier = " + simulationRealSpeedModifier;
    }

    private static final SimulationConfig INSTANCE = new SimulationConfig();

    private SimulationConfig() {
    }
    
    public static SimulationConfig getInstance() {
        return INSTANCE;
    }

}
