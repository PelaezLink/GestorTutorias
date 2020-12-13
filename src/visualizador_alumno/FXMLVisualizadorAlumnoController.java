/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizador_alumno;

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
import tabla_alumnos.FXMLTablaAlumnosController;
import ventana_principal.FXMLGestorTutoriasController;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLVisualizadorAlumnoController implements Initializable {

    @FXML
    private ImageView foto;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellidos;
    @FXML
    private TextField correo;
    private Tutorias misTutorias;
    private Alumno alumno;
    private FXMLGestorTutoriasController controlador_principal; 

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();               
    }    

    @FXML
    private void cerrar(ActionEvent event) {
        controlador_principal.inicializarCalendario();
        controlador_principal.activarBotonAlumnos(true);
        controlador_principal.activarBotonAsignaturas(true);
    }

    @FXML
    private void eliminar(ActionEvent event) {
        ObservableList<Alumno> lista_alumnos = misTutorias.getAlumnosTutorizados();
        lista_alumnos.remove(alumno);
    }
    
    public void setAlumno(Alumno a) {
        alumno = a;
        //Inicializamos los datos aqui y no en el intialice porque debemos tener
        //el alumno setado primero antes, y el initialize se ejecuta antes de 
        //setear el alumno.
        nombre.setText(alumno.getNombre());
        apellidos.setText(alumno.getApellidos());
        correo.setText(alumno.getEmail());
    }
    
    public void setControladorPrincipal(FXMLGestorTutoriasController c) {
        controlador_principal = c;
    }
    
}
