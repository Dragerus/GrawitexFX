/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;

/**
 *
 * @author szymon
 */
public class Universe {
    private ArrayList<Planet> PlanetsTable = new ArrayList<>();
    
    
    void setPlanets(ArrayList<Planet> Planets){
        this.PlanetsTable = Planets;
    }
    
    Object getPlanets(){
        return this.PlanetsTable.clone();
    }
    
}
