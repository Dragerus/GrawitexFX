/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Observable;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author szymon
 */
public class Universe extends Observable {
    private ArrayList<Planet> planetsTable = new ArrayList<>();
    private ArrayList< Double > PlanetsEnergyData = new ArrayList<>();

    
    void setPlanets(ArrayList<Planet> Planets){
        System.out.println("SET PLANETS CALLED");
        this.planetsTable = Planets;
        setChanged();
        notifyObservers();
    }
    
    Collection<Planet> getPlanets(){
        return this.planetsTable;
    }
    void packEnergyData(){
        double potential = 0;
        double kinetic = 0;
        for(Planet planet: planetsTable){
            potential += planet.getPotentialEnergy();
            kinetic += planet.getKineticEnergy(); /* just in case we would need it later */
            
            //System.out.println("Energia: "+ (potential+kinetic));
        }
        PlanetsEnergyData.add(potential + kinetic);
    }
    
    public ArrayList<Double> getEnergyData(){ return this.PlanetsEnergyData; }
   
    public void updatePlanets() {
        for(Planet planet : planetsTable) {
            for(Planet otherPlanet : planetsTable) {
                planet.calculateGravity(otherPlanet);
                planet.calculatePotentialEnergy(otherPlanet);
                planet.calculateKineticEnergy();
            }
        }
        for(Planet planet : planetsTable) {
            planet.updateState(SimulationConfig.getSimulationTimeStep());
        }
        setChanged();
        notifyObservers();
    }
}
