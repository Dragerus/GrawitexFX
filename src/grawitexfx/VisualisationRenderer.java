/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import javafx.scene.AmbientLight;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
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
    private Group group, planetGroup;
    private Camera camera;
    private ArrayList<Sphere> meshes;
    
    public VisualisationRenderer(SubScene visualizationScene, Universe universe) {
        scene = visualizationScene;
        this.universe = universe;
        meshes = new ArrayList<>();
        
        group = new Group();
        planetGroup = new Group();
        scene.setRoot(group);
        camera = new PerspectiveCamera(true);
        recalculateCameraProperties();
        scene.setCamera(camera);
        scene.setFill(null);
        AmbientLight light = new AmbientLight(Color.WHITE);
        PointLight pointLight = new PointLight(Color.WHITE);
        group.getChildren().addAll(light, pointLight, planetGroup);
        
        redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);
        
//        for(Planet planet : universe.getPlanets()) {
//            Sphere planetMesh = new Sphere(10.0);
//            planetMesh.setMaterial(redMaterial);
//            planetMesh.setTranslateX(planet.getX());
//            planetMesh.setTranslateY(planet.getY());
//            planetMesh.setTranslateZ(planet.getZ());
//            meshes.add(planetMesh);
//        }
        
//        Sphere planetMesh = new Sphere(10.0);
//        planetMesh.setMaterial(redMaterial);
//        planetMesh.setTranslateX(0.0);
//        planetMesh.setTranslateY(0.0);
//        planetMesh.setTranslateZ(0.0);
//        planetGroup.getChildren().add(planetMesh);
    }
    
    private void recalculateCameraProperties() {
        double minZ = 0.0, maxZ = 0.0;

        if(universe.getPlanets().size() == 1)
            maxZ = minZ = universe.getPlanets().iterator().next().getZ();
        else if(universe.getPlanets().size() >= 2) {
            minZ = universe.getPlanets().stream().min(
                    (first, second) -> Double.compare(first.getZ(), second.getZ())
            ).get().getZ();
            maxZ = universe.getPlanets().stream().max(
                    (first, second) -> Double.compare(first.getZ(), second.getZ())
            ).get().getZ();
        }
        
        camera.setTranslateZ(-Math.abs(10.0 * minZ - 500.0));
        camera.setNearClip(0.1);
        camera.setFarClip(Math.abs(maxZ * 10.0 + 10000.0));
        
//        camera.setTranslateZ(-500.0);
//        camera.setNearClip(0.1);
//        camera.setFarClip(10000.0);
    }
    
    @Override
    public void update(Observable o, Object arg) { 
        scene.setFill(Color.BLACK);
        planetGroup.getChildren().clear();
        meshes.clear();
        for(Planet planet : universe.getPlanets()) {
            Sphere planetMesh = new Sphere(
                    (camera.getTranslateZ() + planet.getZ())
                    / scene.getWidth() + 5.0);
            planetMesh.setMaterial(redMaterial);
            planetMesh.setTranslateX(planet.getX());
            planetMesh.setTranslateY(planet.getY());
            planetMesh.setTranslateZ(planet.getZ());
            meshes.add(planetMesh);
        }
        
        planetGroup.getChildren().addAll(meshes);
       // System.out.println("Renderer updated");
        recalculateCameraProperties();
        scene.setCamera(camera);
        
        /*for(Planet planet : universe.getPlanets()) {
            System.out.println(planet.toString());
        }*/
    }
}
