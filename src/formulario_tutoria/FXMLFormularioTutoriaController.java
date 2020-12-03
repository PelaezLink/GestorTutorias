/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario_tutoria;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioTutoriaController implements Initializable {

    @FXML
    private ComboBox<?> asignatura;
    @FXML
    private ComboBox<?> hora_inicio;
    @FXML
    private ComboBox<?> hora_fin;
    @FXML
    private ChoiceBox<?> alumnos;
    @FXML
    private TextArea comentarios;
    @FXML
    private Button boton_confirmar;
    @FXML
    private Button boton_cancelar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
