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
        
    }

    @Test
    public void shouldMovePlanetWithConstantVelocity() {
        Vector position = new Vector(1.0, -3.0, 1.0);
        double mass = 1000000000.0;
        Vector velocity = new Vector(1.0, -1.0, 0.1);
        Planet planet = new Planet("PLANET",mass, position, velocity);
        planet.updateState(1.0);
        Vector expectedPosition = new Vector(2.0, -4.0, 1.1);
        assertEquals(expectedPosition, planet.getPosition());
    }
}
