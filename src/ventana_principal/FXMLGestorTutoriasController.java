/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana_principal;

import accesoBD.AccesoBD;
import com.sun.javafx.scene.control.skin.DatePickerSkin;
import formulario_alumno.FXMLFormularioAlumnoController;
import formulario_tutoria.FXMLFormularioTutoriaController;
import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;
import modelo.Tutorias;
import tabla_alumnos.FXMLTablaAlumnosController;
import tabla_asignaturas.FXMLTablaAsignaturasController;
import visualizador_alumno.FXMLVisualizadorAlumnoController;
import visualizador_tutoria.FXMLVisualizadorTutoriaController;

/**
 *
 * @author Luis
 */
public class FXMLGestorTutoriasController implements Initializable {


    private DatePicker calendario;
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
    private VBox centro;
    @FXML
    private BorderPane borderPane;
    @FXML
    private VBox hueco_tabla;
    private LocalDate fecha_seleccionada;
    private Button boton_borrar_asignatura;
    @FXML
    private HBox botones_tabla;
    private Tutoria tutoria_seleccionada;
    private FXMLTablaAsignaturasController controladorAsignaturas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        misTutorias = AccesoBD.getInstance().getTutorias();
        inicializarCalendario();
        inicializarBotonBorrar();
        
    }

//USAMOS EL CÓDIGO DEL EJEMPLO DATEPIC PARA MOSTRAR EL CALENDARIO
    public void inicializarCalendario() {
        centro.getChildren().clear();
        calendario = new DatePicker(LocalDate.now());
        calendario.setShowWeekNumbers(false);
        calendario.setDayCellFactory(cel -> new DiaCelda());

        calendario.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fecha_seleccionada = calendario.getValue();
                mostarTablaTutorias(calendario.getValue());
            }
        });

        DatePickerSkin datePickerSkin = new DatePickerSkin(calendario);
        Node popupContent = datePickerSkin.getPopupContent();

        centro.getChildren().add(popupContent);
        boton_crear.setDisable(false);
        //borderPane.setCenter(popupContent);
    }

    //Creamos un boton que solo se usara para asignaturas
    public void inicializarBotonBorrar() {
        boton_borrar_asignatura = new Button("Eliminar");
        boton_borrar_asignatura.setPrefSize(100, 30);
        boton_borrar_asignatura.setFont(boton_crear.getFont());
        boton_borrar_asignatura.setDisable(true);
        
        boton_borrar_asignatura.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
               controladorAsignaturas.eliminarAsignatura();
            }
        });
    }

    //Metodo que apartir de la fecha busca en la lista de tutorias las que
    //haya ese dia y las muestra en el tableView
    public void mostarTablaTutorias(LocalDate fecha) { 
        botones_tabla.getChildren().remove(boton_borrar_asignatura);
        boton_borrar_asignatura.setDisable(true);
        boton_crear.setText("Nueva Tutoria");
        hueco_tabla.getChildren().clear();
        hueco_tabla.getChildren().add(tabla_tutorias); 
        ObservableList<Tutoria> listaTutoriasDia = getTutoriasDia(fecha);
        tabla_tutorias.setItems(listaTutoriasDia); 
        columna_inicio.setCellValueFactory(cellData -> cellData.getValue().inicioProperty());
        columna_asignatura.setCellValueFactory(cellData -> cellData.getValue().asignaturaProperty());
        columna_duracion.setCellValueFactory(cellData -> cellData.getValue().duracionProperty());
    }    

    
    //Metodo para devolver una lista con las Tutorias de una fecha dada.
    public ObservableList<Tutoria> getTutoriasDia(LocalDate fecha) {
        //ArrayList<Tutoria> lista = new ArrayList<Tutoria>();
        ObservableList<Tutoria> listaTutoriasDia = FXCollections.observableArrayList();

        ObservableList<Tutoria> listaTutorias = misTutorias.getTutoriasConcertadas();
        for (Iterator<Tutoria> iterator = listaTutorias.iterator(); iterator.hasNext();) {
            Tutoria next = iterator.next();
            if (next.getFecha().equals(fecha)) { //importante aqui que se equals y no ==
                listaTutoriasDia.add(next);
            }

        }
        return listaTutoriasDia;

    }

    //Código que quita el calendario y en su lugar muestra el formulario para
    //nueva tutoria, alumno o asignatura. En el caso de asignatura es una
    //ventana modal
    
    @FXML
    private void crearNuevo(ActionEvent event) throws IOException {
        if ("Nueva Tutoria".equals(boton_crear.getText())) {
            centro.getChildren().clear();
            FXMLLoader formulario_tutoria = new FXMLLoader(getClass().getResource("/formulario_tutoria/FXMLFormularioTutoria.fxml"));
            centro.getChildren().add(formulario_tutoria.load());
            FXMLFormularioTutoriaController controlador_formulario_tutoria = formulario_tutoria.getController();
            controlador_formulario_tutoria.setControladorPrincipal(this);
            controlador_formulario_tutoria.setFecha(fecha_seleccionada);
            controlador_formulario_tutoria.setTutoriasDia(getTutoriasDia(fecha_seleccionada));
            controlador_formulario_tutoria.setInicio();
            boton_crear.setDisable(true);
        }
        
        if ("Nuevo Alumno".equals(boton_crear.getText())) {
            centro.getChildren().clear();
            FXMLLoader formulario_alumno = new FXMLLoader(getClass().getResource("/formulario_alumno/FXMLFormularioAlumno.fxml"));
            centro.getChildren().add(formulario_alumno.load());
            FXMLFormularioAlumnoController controlador_formulario_alumno = formulario_alumno.getController();
            controlador_formulario_alumno.setControladorPrincipal(this);
            boton_crear.setDisable(true);
        }
        
        if ("Nueva Asignatura".equals(boton_crear.getText())) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/formulario_asignatura/FXMLFormularioAsignatura.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage ventana2 = new Stage();
            ventana2.initModality(Modality.APPLICATION_MODAL);
            ventana2.setScene(scene);
            ventana2.showAndWait();
        
        }
    }

    //Metodo que muestra la lista de asignaturas en el tableView llamando
    //al fichero FXML que tiene la tabla de ese tipo cuando se pulsa el 
    //boton asignaturas.
    @FXML
    private void mostrarTablaAsignaturas(ActionEvent event) throws IOException {
        boton_crear.setText("Nueva Asignatura");
        hueco_tabla.getChildren().clear();
        FXMLLoader tabla_asignaturas = new FXMLLoader(getClass().getResource("/tabla_asignaturas/FXMLTablaAsignaturas.fxml"));
        hueco_tabla.getChildren().add(tabla_asignaturas.load());
        FXMLTablaAsignaturasController controlador_tabla_asiganturas = tabla_asignaturas.getController();
        controladorAsignaturas = controlador_tabla_asiganturas;
        controlador_tabla_asiganturas.setControladorPrincipal(this);
        botones_tabla.getChildren().add(boton_borrar_asignatura);
        boton_borrar_asignatura.setDisable(true);
    }

    //Metodo que muestra la lista de alumnos en el tableView
    //al fichero FXML que tiene la tabla de ese tipo cuando se pulsa el
    //boton alumnos
    @FXML
    private void mostrarTablaAlumnos(ActionEvent event) throws IOException {
        botones_tabla.getChildren().remove(boton_borrar_asignatura);
        boton_crear.setText("Nuevo Alumno");
        hueco_tabla.getChildren().clear();
        FXMLLoader tabla_alumnos = new FXMLLoader(getClass().getResource("/tabla_alumnos/FXMLTablaAlumnos.fxml"));
        hueco_tabla.getChildren().add(tabla_alumnos.load());
        FXMLTablaAlumnosController controlador_tabla_alumnos = tabla_alumnos.getController();
        controlador_tabla_alumnos.setControladorPrincipal(this);
        boton_borrar_asignatura.setDisable(true);
    }

    //Metodo que se lanza al clickar en una Tutoria de la tabla y que muestra sus
    //datos detallados en el visualizador.
    @FXML
    public void mostrar_tutoria(MouseEvent event) throws IOException {
        tutoria_seleccionada = tabla_tutorias.getSelectionModel().getSelectedItem();
        if (tutoria_seleccionada != null) {
            centro.getChildren().clear();
            FXMLLoader visualizador_tutoria = new FXMLLoader(getClass().getResource("/visualizador_tutoria/FXMLVisualizadorTutoria.fxml"));
            centro.getChildren().add(visualizador_tutoria.load());
            FXMLVisualizadorTutoriaController controlador_visualizador_tutoria = visualizador_tutoria.getController();
            controlador_visualizador_tutoria.setTutoria(tutoria_seleccionada);
            controlador_visualizador_tutoria.setControladorPrincipal(this);
            boton_crear.setDisable(true);
        }
    }

    //Metodo que se llamara desde e controlador de la tabla de alumnos cuando 
    //se pinche en alguno para mostrar sus datos detallados en el visualizador
    public void mostrar_alumno(Alumno a) throws IOException {
        centro.getChildren().clear();
        FXMLLoader visualizador_alumno = new FXMLLoader(getClass().getResource("/visualizador_alumno/FXMLVisualizadorAlumno.fxml"));
        centro.getChildren().add(visualizador_alumno.load());
        FXMLVisualizadorAlumnoController controlador_visualizador_alumno = visualizador_alumno.getController();
        controlador_visualizador_alumno.setAlumno(a);
        controlador_visualizador_alumno.setControladorPrincipal(this);
        boton_crear.setDisable(true);
    }

    //Metodo para poder activar el boton desde la clase la tabla de asignaturas. 
    public void activarBotonEliminarAsignatura() {
        boton_borrar_asignatura.setDisable(false);
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

            this.setText(this.getText() + "\rLibre");
        }
    }

}
