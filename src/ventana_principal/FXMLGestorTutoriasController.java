/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana_principal;

import accesoBD.AccesoBD;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import modelo.Tutoria;
import modelo.Tutorias;

/**
 *
 * @author Luis
 */
public class FXMLGestorTutoriasController implements Initializable {

    @FXML
    private BorderPane borderPane;

    private DatePicker calendario;
    @FXML
    private Button boton_asignaturas;
    @FXML
    private Button boton_alumnos;
    @FXML
    private Button boton_salir;
    @FXML
    private Button boton_crear;

    private Tutorias misTutorias;
    @FXML
    private TableColumn<Tutoria, String> columna_inicio;
    @FXML
    private TableColumn<Tutoria, String> columna_asignatura;
    @FXML
    private TableView<Tutoria> tabla_tutorias;
    @FXML
    private TableColumn<Tutoria, String> columna_duracion;
    @FXML
    private VBox centroUno;
    @FXML
    private VBox centroDos;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarCalendario();
        misTutorias = AccesoBD.getInstance().getTutorias();

    }

//USAMOS EL CÓDIGO DEL EJEMPLO DATEPIC PARA MOSTRAR EL CALENDARIO
    public void inicializarCalendario() {
        centroUno.getChildren().clear();
        DatePicker calendario = new DatePicker(LocalDate.now());
        calendario.setShowWeekNumbers(false);
        calendario.setDayCellFactory(cel -> new DiaCelda());

        DatePickerSkin datePickerSkin = new DatePickerSkin(calendario);
        Node popupContent = datePickerSkin.getPopupContent();

        centroUno.getChildren().add(popupContent);

        //borderPane.setCenter(popupContent);
    }

    //Metodo que apartir de la fecha busca en la lista de tutorias las que
    //haya ese dia y las muestra en el tableView (EN PROCESO)
    public void mostarTablaTutorias(LocalDate fecha) {
        ArrayList<Tutoria> lista = new ArrayList<Tutoria>();
        ObservableList<Tutoria> listaTutoriasDia = FXCollections.observableList(lista);

        ObservableList<Tutoria> listaTutorias = misTutorias.getTutoriasConcertadas();
        for (Iterator<Tutoria> iterator = listaTutorias.iterator(); iterator.hasNext();) {
            Tutoria next = iterator.next();
            if (next.getFecha() == fecha) {
                listaTutoriasDia.add(next);
            }

        }
        tabla_tutorias.setItems(listaTutoriasDia);
        columna_inicio.setCellValueFactory(cellData -> cellData.getValue().inicioProperty());
        columna_asignatura.setCellValueFactory(cellData -> cellData.getValue().asignaturaProperty());
        columna_duracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty());
    }


//CODIGO QUE QUITA EL CALENDARIO Y MUESTRA EL FORMULARIO DE NUEVA ASIGNATURA
//AL PULSAR EL BOTON DE NUEVA ASIGNATURA
    @FXML
    private void nuevaTutoria(ActionEvent event) throws IOException {
        centroUno.getChildren().clear();
        centroDos.getChildren().add(FXMLLoader.load(getClass().getResource("/formulario_tutoria/FXMLFormularioTutoria.fxml")));
        boton_crear.setDisable(true);
    }

}

//USAMOS EL CODIGO DE DATEPIC PARA CUSTOMIZAR LA CELDA
class DiaCelda extends DateCell {

    String newline = System.getProperty("line.separator");

    @Override
    public void updateItem(LocalDate item, boolean empty) {
        super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.

        // Show Weekends in blue color
        DayOfWeek day = DayOfWeek.from(item);
        if (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY) {
            this.setTextFill(Color.ROSYBROWN);
            this.setDisable(true);

            this.setText(this.getText() + "\r");
        } else {
            this.setText(this.getText() + "\rlibre");
        }
    }

}
