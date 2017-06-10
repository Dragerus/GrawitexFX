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
public class Planet {
    private Vector velocity;
    private Vector position;
    private Vector force;
    private double mass;
    
    public Planet(Vector position, double mass, Vector velocity) {
        this.velocity = velocity;
        this.position = position;
        this.mass = mass;
        this.force = new Vector(0.0, 0.0, 0.0);
    }
    
    public void applyForce(Vector force) {
        this.force.add(force);
    }
    
    public void updateState(double dt) {
        Vector acceleration = this.force.scale(1.0 / this.mass);
        this.velocity = this.velocity.add(acceleration.scale(dt));
        this.position = this.position.add(this.velocity.scale(dt).add(acceleration.scale(dt * dt / 2.0)));
    }
}
