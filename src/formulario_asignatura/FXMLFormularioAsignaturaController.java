/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario_asignatura;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutorias;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioAsignaturaController implements Initializable {

    @FXML
    private TextField nombreAsignatura;
    @FXML
    private TextField codigoAsignatura;
    private Tutorias misTutorias;
    @FXML
    private Button boton_confirmar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        boton_confirmar.disableProperty().bind(Bindings.or(codigoAsignatura.textProperty().isEmpty(), nombreAsignatura.textProperty().isEmpty()));
    }

    @FXML
    private void cerrarVentana(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }

    //Cuando se pulsa el boton confirmar se guarda la asignatura y se vacia el formulario
    @FXML
    private void confirmar(ActionEvent event) {
        Asignatura nuevaAsignatura = new Asignatura(codigoAsignatura.getText(), nombreAsignatura.getText());
        ObservableList<Asignatura> listaAsignaturas = misTutorias.getAsignaturas();
        listaAsignaturas.add(nuevaAsignatura);

        nombreAsignatura.clear();
        codigoAsignatura.clear();

    }

}
