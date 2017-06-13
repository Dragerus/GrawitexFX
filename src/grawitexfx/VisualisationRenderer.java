/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.Observable;
import java.util.Observer;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SubScene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 *
 * @author adam
 */
public class VisualisationRenderer implements Observer {
    private SubScene scene;
    private Universe universe;
    private PhongMaterial redMaterial;
    private Group group;
    
    public VisualisationRenderer(SubScene visualizationScene, Universe universe) {
        scene = visualizationScene;
        this.universe = universe;
        
        group = new Group();
        scene.setRoot(group);
        Camera camera = new PerspectiveCamera(true);
        camera.setTranslateZ(-500.0);
        camera.setNearClip(0.1);
        camera.setFarClip(10000.0);
        scene.setCamera(camera);
        scene.setFill(null);
        AmbientLight light = new AmbientLight(Color.WHITE);
        group.getChildren().add(light);
        
        Sphere sphere = new Sphere(1.0);
        group.getChildren().add(sphere);
        redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        sphere.setMaterial(redMaterial);
        
//        for(Planet planet : universe.getPlanets()) {
//            Sphere planetMesh = new Sphere();
//            planetMesh.setMaterial(redMaterial);
//            planetMesh.setTranslateX(planet.getX());
//            planetMesh.setTranslateY(planet.getY());
//            planetMesh.setTranslateZ(planet.getZ());
//            group.getChildren().add(planetMesh);
//        }
    }
    
    @Override
    public void update(Observable o, Object arg) {
        scene.setFill(Color.BLACK);
        for(Planet planet : universe.getPlanets()) {
            Sphere planetMesh = new Sphere(1.0);
            planetMesh.setMaterial(redMaterial);
            planetMesh.setTranslateX(planet.getX());
            planetMesh.setTranslateY(planet.getY());
            planetMesh.setTranslateZ(planet.getZ());
            group.getChildren().add(planetMesh);
        }
        System.out.println("Renderer updated");
    }
    
}
