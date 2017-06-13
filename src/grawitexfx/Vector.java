/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.lang.Math;

/**
 *
 * @author adam
 */
public class Vector {
    public double x, y, z;
    
    public Vector() {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }
    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    public Vector getSquare(){
        return new Vector(
        this.x * this.x,
        this.y * this.y,
        this.z * this.z
        );
    }
    
    public Vector add(Vector other) {
        return new Vector(
                this.x + other.x,
                this.y + other.y,
                this.z + other.z
        );
    }
    
    public Vector subtract(Vector other) {
        return new Vector(
                this.x - other.x,
                this.y - other.y,
                this.z - other.z
        );
    }
    
    public Vector scale(double scale) {
        return new Vector(
                this.x * scale,
                this.y * scale,
                this.z * scale
        );
    }
    
    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
    
    public Vector normalize() {
        double length = this.length();
        
        return new Vector(
                x / length,
                y / length,
                z / length
        );
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.x) ^ (Double.doubleToLongBits(this.x) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.y) ^ (Double.doubleToLongBits(this.y) >>> 32));
        hash = 79 * hash + (int) (Double.doubleToLongBits(this.z) ^ (Double.doubleToLongBits(this.z) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vector other = (Vector) obj;
        if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        if (Double.doubleToLongBits(this.z) != Double.doubleToLongBits(other.z)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Vector{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}