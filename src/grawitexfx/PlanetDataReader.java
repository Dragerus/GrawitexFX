
package grawitexfx;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author szymon
 */
public class PlanetDataReader {

    
    ArrayList<Planet> Planets;
    
    public ArrayList<Planet> readPlanets(String FileName){
        this.Planets = new ArrayList<>();
        
        try( BufferedReader br = new BufferedReader(new FileReader(FileName)) ){
            String line, name = "";
            double mass, x, y ,z, vx, vy, vz;
            mass = 0; x = 0; y = 0; z = 0; vx = 0; vy = 0; vz = 0; 
            int i = 0;
            while((line = br.readLine()) != null ){
                
                switch(i%9){
                    case 0:
                            name = new String(line);
                            //System.out.println("Name: "+name);
                            break;
                    case 1:
                            mass = Double.parseDouble(line);
                            break; 
                    case 2:
                            x = Double.parseDouble(line);
                            break;  
                    case 3:
                            y = Double.parseDouble(line);
                            break;        
                    case 4:
                            z = Double.parseDouble(line);
                            break; 
                    case 5:
                            vx = Double.parseDouble(line);
                            break;
                    case 6:
                            vy = Double.parseDouble(line);
                            break;
                    case 7:
                            vz = Double.parseDouble(line);
                            break;
                    case 8:     /* the blank line */
                            break;
                }
                i++;
                if(i == 9){
                    i = 0;
                    /* System.out.println("Nowa planeta: "+new Planet( name, mass, new Vector(x,y,z), new Vector(vx,vy,vz))); */
                    this.Planets.add( new Planet(
                            name, mass, new Vector(x,y,z), new Vector(vx,vy,vz)));
                    }
            }
            this.Planets.add( new Planet(name, mass, new Vector(x,y,z), new Vector(vx,vy,vz))); /* the last one */
            
        }
        catch(IOException e){
            System.err.println(e.getMessage());
        }

        return Planets;
    }
  
}
