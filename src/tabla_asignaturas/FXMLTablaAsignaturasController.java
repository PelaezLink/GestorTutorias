/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabla_asignaturas;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import modelo.Asignatura;
import modelo.Tutoria;
import modelo.Tutorias;
import ventana_principal.FXMLGestorTutoriasController;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLTablaAsignaturasController implements Initializable {

    @FXML
    private TableView<Asignatura> tabla_asignaturas;
    @FXML
    private TableColumn<Asignatura, String> columna_nombre_asignatura;
    @FXML
    private TableColumn<Asignatura, String> columna_codigo;
    private Tutorias misTutorias;
    private FXMLGestorTutoriasController principal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        ObservableList<Asignatura> listaAsignaturas = misTutorias.getAsignaturas();
        //Activamos el boton para poder borrar asignaturas.
        principal = new FXMLGestorTutoriasController();
        principal.activarBotonEliminarAsignatura();
        
        tabla_asignaturas.setItems(listaAsignaturas);
        columna_nombre_asignatura.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        columna_codigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
    }

    
    
}
