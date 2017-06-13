/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.Objects;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author adam
 */
public class Planet {
    public static final double GRAVITATIONAL_CONSTANT = 6.67408313131313131313131e-11;
    
    private String name;
    private Vector velocity;
    private Vector position;
    private Vector acceleration;
    private double mass;
    private double potential_energy;
    private Vector kinetic_energy; 
    
    public Planet(String name, double mass, Vector position,  Vector velocity) {
        this.name = new String(name);
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
        this.acceleration = new Vector();
        this.kinetic_energy = new Vector();
        
    }
    public void calculateKineticEnergy(){
        this.kinetic_energy = this.velocity.getSquare().scale(this.mass).scale(0.5); /* chain chain chain */        
    }
    public Double getKineticEnergy(){
        return this.kinetic_energy.length();
    }
    
    public void calculatePotentialEnergy(Planet otherPlanet){
        if(otherPlanet == this)             /* nothing happens nonetheless  */
                             return; 
        Vector direction = otherPlanet.position.subtract(this.position);
        double distance = direction.length();
        this.potential_energy -= (GRAVITATIONAL_CONSTANT * this.mass * otherPlanet.mass) / distance;
        
    }
    public double getPotentialEnergy(){
        return this.potential_energy;
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
    
    public double getMass_inDouble() {return mass;}
    
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
    /* for tableView in fx  */
    public String getName(){ return new SimpleStringProperty(this.name).getValue(); }
    public Double getMass(){ return new SimpleDoubleProperty(this.mass).getValue(); }
    public Double getX(){ return new SimpleDoubleProperty(this.position.x).getValue(); }
    public Double getY(){ return new SimpleDoubleProperty(this.position.y).getValue(); }
    public Double getZ(){ return new SimpleDoubleProperty(this.position.z).getValue(); }
    public Double getVx(){ return new SimpleDoubleProperty(this.velocity.x).getValue(); }
    public Double getVy(){ return new SimpleDoubleProperty(this.velocity.y).getValue(); }
    public Double getVz(){ return new SimpleDoubleProperty(this.velocity.z).getValue(); }
    
}
