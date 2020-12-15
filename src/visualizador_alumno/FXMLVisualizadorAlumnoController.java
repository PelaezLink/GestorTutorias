/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package visualizador_alumno;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import modelo.Alumno;
import modelo.Tutorias;
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
    @FXML
    private Button boton_eliminar;

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
        //Ventana Confirmación
        Alert dialogoAlerta = new Alert(AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Ventana Confirmación");
        dialogoAlerta.setHeaderText(null);
        dialogoAlerta.initStyle(StageStyle.UTILITY);
        dialogoAlerta.setContentText("¿Realmente quieres eliminar el alumno seleccionado?");
        dialogoAlerta.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/recursos/alertaIcono.png"))));
        //Respuesta
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if(result.get()==ButtonType.OK){
            lista_alumnos.remove(alumno);
            nombre.setText(null);
            apellidos.setText(null);
            correo.setText(null);
        }  
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
