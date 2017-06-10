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
    private static final double gravitationalConstant = 6.6740831e-11;
    
    private String name;
    private Vector velocity;
    private Vector position;
    private Vector acceleration;
    private double mass;
    
    public Planet(Vector position, double mass, Vector velocity) {
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
        this.acceleration = new Vector(0.0, 0.0, 0.0);
    }
    
    public void calculateGravity(Planet otherPlanet) {
        double gravity = otherPlanet.mass * gravitationalConstant;
        Vector gravityVector = this.position.subtract(otherPlanet.position).scale(gravity);
        this.acceleration = this.acceleration.add(gravityVector);
    }
    
    public void updateState(double dt) {
        Vector acceleration = this.acceleration.scale(1.0 / this.mass);
        this.velocity = this.velocity.add(acceleration.scale(dt));
        this.position = this.position.add(this.velocity.scale(dt).add(acceleration.scale(dt * dt / 2.0)));
    }
    
    public Vector getVelocity() {return this.velocity;}
    
    public Vector getPosition() {return this.position;}
    
    public double getMass() {return this.mass;}

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
        if (!Objects.equals(this.velocity, other.velocity)) {
            return false;
        }
        if (!Objects.equals(this.position, other.position)) {
            return false;
        }
        return true;
    }
    
    
}
