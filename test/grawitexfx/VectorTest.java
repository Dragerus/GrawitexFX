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
public class VectorTest {
    
    public VectorTest() {
    }

    @Test
    public void shouldConfirmVectorEquality() {
        Vector a = new Vector(1.0, 4.123, -5.321);
        Vector b = new Vector(1.0, 4.123, -5.321);
        assertEquals(a, b);
    }
    
    @Test
    public void shouldConfirmVectorInequality() {
        Vector a = new Vector(1.0, 4.123, 5.321);
        Vector b = new Vector(1.0, 4.123, -5.321);
        assertNotEquals(a, b);
    }
    
    @Test
    public void shouldReturnVectorSum() {
        Vector a = new Vector(3.0, 1.7, 2.123);
        Vector b = new Vector(-1.5, 412.23, -5.123);
        Vector result = a.add(b);
        Vector expected = new Vector(1.5, 413.93, -3.0);
        
        assertEquals(expected, result);
    }

    @Test
    public void shouldReturnScaledVector() {
        Vector a = new Vector(2.146, -6.21, 4121.1595);
        double b = 3.12;
        Vector result = a.scale(b);
        Vector expected = new Vector(a.x * b, a.y * b, a.z * b);
        
        assertEquals(expected, result);
    }
    
}
