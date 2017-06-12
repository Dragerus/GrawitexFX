/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author szymon
 */
public class Grawitex extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("GrawitexFX");
        stage.setScene(scene);
        stage.show();

        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //System.out.println("Hello world");    
        launch(args);
       
    }
    
}
