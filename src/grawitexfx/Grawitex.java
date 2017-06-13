/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grawitexfx;

import javafx.application.Application;
import static javafx.application.Application.launch;
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
//        try {
            Parent root = FXMLLoader.load(getClass().getResource("Root.fxml"));
            
            Scene scene = new Scene(root);
            stage.setTitle("GrawitexFX");
            stage.setScene(scene);
            stage.show();
//        } catch (IOException iOException) {
//            System.err.println("Couldn't open FXML");
//        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }   
}
