/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author szymon
 */
public class Universe extends Observable {
    private ArrayList<Planet> PlanetsTable = new ArrayList<>();
    
    void setPlanets(ArrayList<Planet> Planets){
        this.PlanetsTable = Planets;
    }
    
    Object getPlanets(){
        return this.PlanetsTable;
    }
    
    void updatePlanets() {
        for(Planet planet : PlanetsTable) {
            for(Planet otherPlanet : PlanetsTable) {
                planet.calculateGravity(otherPlanet);
            }
        }
        for(Planet planet : PlanetsTable) {
            planet.updateState(SimulationConfig.getSimulationTimeStep());
        }
        setChanged();
    }
}
