/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabla_asignaturas;

import accesoBD.AccesoBD;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
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
    private FXMLGestorTutoriasController controlador_principal;
    private Asignatura seleccionada;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        ObservableList<Asignatura> listaAsignaturas = misTutorias.getAsignaturas();
        //Activamos el boton para poder borrar asignaturas.
        
        
        tabla_asignaturas.setItems(listaAsignaturas);
        columna_nombre_asignatura.setCellValueFactory(cellData -> cellData.getValue().descripcionProperty());
        columna_codigo.setCellValueFactory(cellData -> cellData.getValue().codigoProperty());
    }
    
    //Elimina la asignatura seleccionada de la lista.
    public void eliminarAsignatura() {
        ObservableList<Asignatura> lista_asignaturas = misTutorias.getAsignaturas();
        //Ventana Confirmación
        Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
        dialogoAlerta.setTitle("Eliminar Asignatura");
        dialogoAlerta.setHeaderText("ATENCIÓN");
        dialogoAlerta.initStyle(StageStyle.DECORATED);
        dialogoAlerta.setContentText("¿Seguro que quieres eliminar la asignatura seleccionada?" + "\r");
        //Respuesta
        Optional<ButtonType> result = dialogoAlerta.showAndWait();
        if(result.get()==ButtonType.OK){
            lista_asignaturas.remove(seleccionada);
        }
        if(tabla_asignaturas.getItems().size() == 0) {
            controlador_principal.desactivarBotonEliminarAsignatura();
        }
    
    }
     
    @FXML
    private void asignaturaSeleccionada(MouseEvent event) {
        seleccionada = tabla_asignaturas.getSelectionModel().getSelectedItem();
        if (seleccionada != null) {
            controlador_principal.activarBotonEliminarAsignatura();
        }
    }

    public void setControladorPrincipal(FXMLGestorTutoriasController p) {
        controlador_principal = p;
    }

    
}
