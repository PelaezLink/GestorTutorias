/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizador_tutoria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLVisualizadorTutoriaController implements Initializable {

    @FXML
    private TextField asignatura;
    @FXML
    private TextField hora_inicio;
    @FXML
    private TextField hora_fin;
    @FXML
    private TextField alumnos;
    @FXML
    private ComboBox<?> estado;
    @FXML
    private Button boton_cerrar;
    @FXML
    private TextArea comentarios;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
