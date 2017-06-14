/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 *
 * @author szymon
 */
public class PlanetDataWriterTest {
    public PlanetDataWriterTest(){}
    
    @Test
    public void writeOnePlanetToFile(){
        
        Planet a = new Planet(
                "A",
                10,
                new Vector(0, 1, 2),
                new Vector(0,0,0)
        );
        ArrayList<Planet> arrList = new ArrayList<>();
        arrList.add(a);
        PlanetDataWriter pdw = new PlanetDataWriter();
        pdw.writePlanetsToFile("writerTestFile.txt", arrList);
        
        String correctText = "A\n10\n0\n1\n2\n0\n0\n0\n";
        File file = new File("writerTestFile.txt");
        FileInputStream fis;
        byte[] data = new byte[(int) 1024];
        try {
            fis = new FileInputStream(file);
            fis.read(data);
        } catch (Exception ex) {
            //Logger.getLogger(PlanetDataWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        file.delete();
        String writtenText = new String(data);
        //System.out.println(writtenText);
        assertEquals(correctText, writtenText);
        
        
        
    }
    
    
}
