
package grawitexfx;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author szymon
 */
public class PlanetDataWriter {
    
    
    public void writePlanetsToFile(String FileName, Object Planets){
        
        BufferedWriter output = null;
        try {
            ArrayList<Planet> tmp = (ArrayList<Planet>) Planets;
            File file = new File(FileName);
            output = new BufferedWriter(new FileWriter(file));
            int i = 0;
            for(Planet x: tmp){
                    output.write(x.toString().replace(" ", "\n"));
                    if(i != tmp.size()-1){ output.write("\n"); i++; }
            }

            output.close();
            
            
        } catch ( IOException e ) {
            System.err.println(e.getMessage());
        }
        
    }
}
