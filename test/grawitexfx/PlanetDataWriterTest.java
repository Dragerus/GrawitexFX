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
        
        String correctText = "A\n10.0\n0.0\n1.0\n2.0\n0.0\n0.0\n0.0\n\n";
        File file = new File("writerTestFile.txt");
        FileInputStream fis;
        byte[] data = new byte[(int) 1024];
        try {
            fis = new FileInputStream(file);
            fis.read(data);
        } catch (Exception ex) {
            //Logger.getLogger(PlanetDataWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //file.delete();
        String writtenText = new String(data).trim();
        //System.out.println(writtenText);
        assertEquals(correctText.trim(), writtenText);
    }
    
     @Test
    public void writeThreePlanetToFile(){
        
        Planet a = new Planet(
                "A",
                10,
                new Vector(0, 1, 2),
                new Vector(0,0,0)
        );
        Planet b = new Planet(
                "B",
                11,
                new Vector(0, 1, 12),
                new Vector(0,0,10)
        );
        Planet c = new Planet(
                "C",
                15,
                new Vector(1, 1, 2),
                new Vector(-30,2,0)
        );
        ArrayList<Planet> arrList = new ArrayList<>();
        arrList.add(a);
        arrList.add(b);
        arrList.add(c);
        PlanetDataWriter pdw = new PlanetDataWriter();
        pdw.writePlanetsToFile("writerTestFile.txt", arrList);
        
        String correctText =  "A\n10.0\n0.0\n1.0\n2.0\n0.0\n0.0\n0.0\n\n";
               correctText += "B\n11.0\n0.0\n1.0\n12.0\n0.0\n0.0\n10.0\n\n";
               correctText += "C\n15.0\n1.0\n1.0\n2.0\n-30.0\n2.0\n0.0";
         //System.out.println(">>>");
         //System.out.println(correctText);
         //System.out.println("<<<");
               File file = new File("writerTestFile.txt");
        FileInputStream fis;
        byte[] data = new byte[(int) 1024*48];
        try {
            fis = new FileInputStream(file);
            fis.read(data);
        } catch (Exception ex) {
            //Logger.getLogger(PlanetDataWriterTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        //file.delete();
        String writtenText = new String(data);
        writtenText = writtenText.trim();
        System.out.println("WritenText"+writtenText);
         System.out.println(correctText);
        assertEquals(correctText, writtenText);
    }
    
}
