/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizador_tutoria;

import accesoBD.AccesoBD;
import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import modelo.Alumno;
import modelo.Asignatura;
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
    private TextArea alumnos;
    @FXML
    private ComboBox<EstadoTutoria> estado;
    @FXML
    private TextArea comentarios;
    @FXML
    private TextField duracion;
    private Tutoria tutoria;
    private Tutorias misTutorias;
    private FXMLGestorTutoriasController controlador_principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        
        ObservableList<EstadoTutoria> estados_posibles = FXCollections.observableArrayList();
        estados_posibles.addAll(Tutoria.EstadoTutoria.PEDIDA, Tutoria.EstadoTutoria.REALIZADA, Tutoria.EstadoTutoria.ANULADA, Tutoria.EstadoTutoria.NO_ASISTIDA);
        estado.setItems(estados_posibles);
       
    }    

    //Al pulsar el boton cerrar guardamos la nueva tutoria con los cambios que
    //se le puedan haber hecho en estado y comentarios.
    @FXML
    private void cerrar(ActionEvent event) {
      
      controlador_principal.inicializarCalendario();
      controlador_principal.activarBotonAlumnos(true);
      controlador_principal.activarBotonAsignaturas(true);
      controlador_principal.mostarTablaTutorias(LocalDate.now());
      
    }
    
        @FXML
    private void cerrarConfirmacion(ActionEvent event) {
              
      ObservableList<Tutoria> lista_tutorias = misTutorias.getTutoriasConcertadas();
      lista_tutorias.remove(tutoria);
      
      tutoria.setEstado(estado.getValue());
      tutoria.setAnotaciones(comentarios.getText());
      lista_tutorias.add(tutoria);
      controlador_principal.mostarTablaTutorias(tutoria.getFecha());
    }
    
    public void setTutoria(Tutoria t) {
        tutoria = t;
        //Inicializamos todos los campos aqui y no en el intializa porque debe 
        //usar la varuable tutoria que en el intizalize todavia no la tiene.
        asignatura.setText(tutoria.getAsignatura().getDescripcion());
        hora_inicio.setText(tutoria.getInicio().toString());
        estado.setValue(tutoria.getEstado());
        comentarios.setText(tutoria.getAnotaciones());
        
        long dur = tutoria.getDuracion().toMinutes();
        duracion.setText("" + dur);
        
        //Para visualizar la lista de alumnos correctamente
        ObservableList<Alumno> a = tutoria.getAlumnos();
        String alum = "";
        for (Iterator<Alumno> iterator = a.iterator(); iterator.hasNext();) {
            Alumno next = iterator.next();
            alum += next.getNombre() + " " + next.getApellidos() + "\n";           
        }        
        alumnos.setText(alum);
    }
    
    public void setControladorPrincipal(FXMLGestorTutoriasController c) {
        controlador_principal = c;
    }


    
}
