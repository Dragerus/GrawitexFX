/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author szymon
 */
public class Universe extends Observable {
    private ArrayList<Planet> PlanetsTable = new ArrayList<>();
    private ArrayList< Double > PlanetsEnergyData = new ArrayList<>();
    
    void setPlanets(ArrayList<Planet> Planets){
        this.PlanetsTable = Planets;
    }
    
    Object getPlanets(){
        return this.PlanetsTable;
    }
    void packEnergyData(){
        double potential = 0;
        double kinetic = 0;
        for(Planet planet: PlanetsTable){
            potential += planet.getPotentialEnergy();
            kinetic += planet.getKineticEnergy(); /* just in case we would need it later */
            PlanetsEnergyData.add(potential + kinetic);
            //System.out.println("Energia: "+ (potential+kinetic));
        }
        
    }
    
    void updatePlanets() {
        for(Planet planet : PlanetsTable) {
            for(Planet otherPlanet : PlanetsTable) {
                planet.calculateGravity(otherPlanet);
                planet.calculatePotentialEnergy(otherPlanet);
                planet.calculateKineticEnergy();
            }
        }
        for(Planet planet : PlanetsTable) {
            planet.updateState(SimulationConfig.getSimulationTimeStep());
        }
        setChanged();
    }
}
