/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.Objects;

/**
 *
 * @author adam
 */
public class Planet {
    public static final double GRAVITATIONAL_CONSTANT = 6.6740831e-11;
    
    private String name;
    private Vector velocity;
    private Vector position;
    private Vector acceleration;
    private double mass;
    
    public Planet(String name, double mass, Vector position,  Vector velocity) {
        this.name = new String(name);
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
        this.acceleration = new Vector();
    }
    
    public void calculateGravity(Planet otherPlanet) {
        if(otherPlanet == this)
            return;
        
        Vector direction = otherPlanet.position.subtract(this.position);
        Vector directionNormalized = direction.normalize();
        double distance = direction.length();
        double gravity = (otherPlanet.mass * GRAVITATIONAL_CONSTANT)
                / (distance * distance);
        Vector gravityVector = directionNormalized.scale(gravity);
        
        this.acceleration = this.acceleration.add(gravityVector);
    }
    
    public void updateState(double dt) {
        velocity = velocity.add(acceleration.scale(dt));
        position = position.add(velocity.scale(dt).add(acceleration.scale(dt * dt / 2.0)));
    }
    
    public Vector getVelocity() {return velocity;}
    
    public Vector getPosition() {return position;}
    
    public double getMass() {return mass;}
    
    public Vector getAcceleration() {return acceleration;}

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.name);
        hash = 83 * hash + Objects.hashCode(this.velocity);
        hash = 83 * hash + Objects.hashCode(this.position);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.mass) ^ (Double.doubleToLongBits(this.mass) >>> 32));
        return hash;
    }

    @Override
    public String toString() {
        return name + "\n" + mass + "\n" + position.x + "\n" + position.y +
                "\n" + position.z + "\n"  + velocity.x + "\n" + velocity.y +
                "\n" + velocity.z + "\n" ;
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
        final Planet other = (Planet) obj;
        if (Double.doubleToLongBits(this.mass) != Double.doubleToLongBits(other.mass)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    public String getName(){ return this.name; }
    public double getX(){ return this.position.x; }
    public double getY(){ return this.position.y; }
    public double getZ(){ return this.position.z; }
    public double getVx(){ return this.velocity.x; }
    public double getVy(){ return this.velocity.y; }
    public double getVz(){ return this.velocity.z; }
}
