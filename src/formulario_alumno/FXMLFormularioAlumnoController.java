/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario_alumno;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import modelo.Alumno;
import modelo.Tutorias;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioAlumnoController implements Initializable {

    @FXML
    private ImageView foto;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField correo;
    private Tutorias misTutorias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        
    }    

    @FXML
    private void confirmar(ActionEvent event) {
        Alumno nuevoAlumno = new Alumno(nombre.getText(), apellidos.getText(), correo.getText());
        ObservableList<Alumno> listaAlumnos = misTutorias.getAlumnosTutorizados();
        listaAlumnos.add(nuevoAlumno);
    }

    @FXML
    private void cancelar(ActionEvent event) {
    }
    
}
