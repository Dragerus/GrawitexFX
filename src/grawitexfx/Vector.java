/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

/**
 *
 * @author adam
 */
public class Vector {
    public double x, y, z;
    
    public Vector() {}
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector add(Vector other) {
        return new Vector(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z
        );
    }
    
    public Vector scale(double scale) {
        return new Vector(
                this.x * scale,
                this.y * scale,
                this.z * scale
        );
    }
}