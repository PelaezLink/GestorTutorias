/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana_principal;

import accesoBD.AccesoBD;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author Luis
 */
public class GestorTutorias extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLGestorTutorias.fxml"));
        
        Scene scene = new Scene(root);
        stage.setTitle("GESTOR TUTORIAS");
        stage.setMinHeight(722);
        stage.setMinWidth(1150);
        stage.getIcons().add(new Image("/recursos/en-linea.png"));
        stage.setScene(scene);
        stage.show();
        //Linea de codigo de stack overflow: https://stackoverflow.com/questions/44548460/javafx-stage-close-event-handler
        //Para guardar el estado de las tutorias cuando se cierre la aplicación.
        stage.setOnHiding( event -> {AccesoBD.getInstance().salvar();} );
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
