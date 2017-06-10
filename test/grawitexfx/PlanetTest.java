/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author adam
 */
public class PlanetTest {
    
    public PlanetTest() {
    }

    @Test
    public void testApplyForce() {
        System.out.println("applyForce");
        Vector force = null;
        Planet instance = null;
        instance.applyForce(force);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testUpdateState() {
        System.out.println("updateState");
        double dt = 0.0;
        Planet instance = null;
        instance.updateState(dt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
