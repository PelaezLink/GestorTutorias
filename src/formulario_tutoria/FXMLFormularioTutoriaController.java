/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario_tutoria;

import accesoBD.AccesoBD;
import java.net.URL;
import java.time.Duration;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;
import modelo.Tutorias;
import ventana_principal.FXMLGestorTutoriasController;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioTutoriaController implements Initializable {

    @FXML
    private ComboBox<Asignatura> asignatura;
    @FXML
    private ComboBox<?> hora_inicio;
    @FXML
    private ChoiceBox<Alumno> alumnos;
    @FXML
    private TextArea comentarios;
    @FXML
    private Button boton_confirmar;
    @FXML
    private Button boton_cancelar;
    
    private Tutorias misTutorias;
    @FXML
    private ComboBox<String> duracion;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        
        asignatura.setItems(misTutorias.getAsignaturas());
        alumnos.setItems(misTutorias.getAlumnosTutorizados()); 
        ObservableList<Duration> minutos = FXCollections.observableArrayList();
        //duracion.setItems(minutos);
        
    }    

    @FXML
    private void confirmar(ActionEvent event) {
        Tutoria nueva = new Tutoria();
        nueva.setAsignatura(asignatura.getValue());
        //nueva.setDuracion(duracion.getValue());
        nueva.setAnotaciones(comentarios.getText());
        nueva.setEstado(Tutoria.EstadoTutoria.PEDIDA);
    }

    @FXML
    private void cancelar(ActionEvent event) {
           
        
    }
    
}
