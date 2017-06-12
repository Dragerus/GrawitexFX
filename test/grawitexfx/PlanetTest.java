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
    public void shouldGravitatePlanetsTowardsEachOther() {
        Planet a = new Planet(
                "A",
                1000000000,
                new Vector(0, 1, 2),
                new Vector()
        );
        Planet b = new Planet(
                "B",
                2000000000,
                new Vector(-2, 2, 0),
                new Vector()
        );
        Vector expectedAccelerationA = new Vector(
                -2.0/3.0,
                1.0/3.0,
                -2.0/3.0
        ).scale(b.getMass_inDouble() * Planet.GRAVITATIONAL_CONSTANT / 9.0);
        Vector expectedAccelerationB = new Vector(
                2.0/3.0,
                -1.0/3.0,
                2.0/3.0
        ).scale(a.getMass_inDouble() * Planet.GRAVITATIONAL_CONSTANT / 9.0);
        a.calculateGravity(b);
        b.calculateGravity(a);
        assertEquals(expectedAccelerationA, a.getAcceleration());
        assertEquals(expectedAccelerationB, b.getAcceleration());
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
