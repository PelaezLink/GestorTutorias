/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario_alumno;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import modelo.Alumno;
import modelo.Tutorias;
import ventana_principal.FXMLGestorTutoriasController;

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
    @FXML
    private Button boton_confirmar;
    private FXMLGestorTutoriasController controlador_principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        boton_confirmar.disableProperty().bind(Bindings.or(correo.textProperty().isEmpty(),(Bindings.or(nombre.textProperty().isEmpty(), apellidos.textProperty().isEmpty()))));
        
    }    
    
    //Cuando se pulsa el boton confirmar se guarda el alumno y se vacia el formulario
    @FXML
    private void confirmar(ActionEvent event) {
        Alumno nuevoAlumno = new Alumno(nombre.getText(), apellidos.getText(), correo.getText());
        ObservableList<Alumno> listaAlumnos = misTutorias.getAlumnosTutorizados();
        listaAlumnos.add(nuevoAlumno);
        
        nombre.clear();
        apellidos.clear();
        correo.clear();
    }

    //Cerramos la ventana
    @FXML
    private void cancelar(ActionEvent event) {
        controlador_principal.inicializarCalendario();
        controlador_principal.activarBotonAlumnos(true);
        controlador_principal.activarBotonAsignaturas(true);
    }
    
    
    public void setControladorPrincipal(FXMLGestorTutoriasController c) {
        controlador_principal = c;
    }
}
