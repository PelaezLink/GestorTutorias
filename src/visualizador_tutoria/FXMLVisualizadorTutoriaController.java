/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizador_tutoria;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import modelo.Tutoria;
import modelo.Tutoria.EstadoTutoria;
import modelo.Tutorias;
import ventana_principal.FXMLGestorTutoriasController;

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
    private TextField alumnos;
    @FXML
    private ComboBox<EstadoTutoria> estado;
    @FXML
    private TextArea comentarios;
    @FXML
    private TextField duracion;
    private Tutoria tutoria;
    private Tutorias misTutorias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        //Inicializamos todos los campos.
        asignatura.setText(tutoria.getAsignatura().getDescripcion());
        hora_inicio.setText(tutoria.getInicio().toString());
        estado.setValue(tutoria.getEstado());
        comentarios.setText(tutoria.getAnotaciones());
        duracion.setText(tutoria.getDuracion().toString());
        alumnos.setText(tutoria.getAlumnos().toString());
        
    }    

    //Al pulsar el boton cerrar guardamos la nueva tutoria con los cambios que
    //se le puedan haber hecho en estado y comentarios.
    @FXML
    private void cerrar(ActionEvent event) {
      
      ObservableList<Tutoria> lista_tutorias = misTutorias.getTutoriasConcertadas();
      lista_tutorias.remove(tutoria);
      
      tutoria.setEstado(estado.getValue());
      tutoria.setAnotaciones(comentarios.getText());
      lista_tutorias.add(tutoria);
    }
    
    public void setTutoria(Tutoria t) {
        tutoria = t;
    }
    
}
