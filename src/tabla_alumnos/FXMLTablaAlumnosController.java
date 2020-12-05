/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabla_alumnos;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutorias;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLTablaAlumnosController implements Initializable {

    @FXML
    private TableView<Alumno> tabla_alumnos;
    @FXML
    private TableColumn<Alumno, String> columna_nombre;
    @FXML
    private TableColumn<Alumno, String> columna_apellidos;
    private Tutorias misTutorias;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        ObservableList<Alumno> listaAlumnos = misTutorias.getAlumnosTutorizados();
        tabla_alumnos.setItems(listaAlumnos);
        columna_nombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        columna_apellidos.setCellValueFactory(cellData -> cellData.getValue().apellidosProperty());
    }    
    
}
