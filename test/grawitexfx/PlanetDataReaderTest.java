/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author szymon
 */
public class PlanetDataReaderTest {
        
    public PlanetDataReaderTest() {}
        
    @Test
    public void standardFormatFileRead(){
        PlanetDataReader pdr = new PlanetDataReader();
        ArrayList<Planet> planet_list;
        planet_list = pdr.readPlanets("./data/small_data.in");
        
        assertEquals(planet_list.get(1).getName(), "Mercury");
        assertEquals(planet_list.get(0).getName(), "Sun");
        assertEquals(planet_list.get(2).getPosition(), new Vector(9.0, 5.0, -4.0));
        //assertEquals();
    }
}
