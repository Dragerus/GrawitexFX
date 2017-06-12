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
    
    public SimulationRunner(Universe universe){
        this.universe = universe;
    }
    public void simulate(){
        System.out.println("Simulate");
    }
    
}
