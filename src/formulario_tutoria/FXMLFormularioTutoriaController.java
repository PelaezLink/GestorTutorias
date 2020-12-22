/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package formulario_tutoria;

import accesoBD.AccesoBD;
import java.net.URL;
import java.time.Duration;
import static java.time.Duration.ofMinutes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SelectionModel;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import modelo.Alumno;
import modelo.Asignatura;
import modelo.Tutoria;
import modelo.Tutorias;
import ventana_principal.FXMLGestorTutoriasController;

/**
 * FXML Controller class
 *
 * @author Luis
 */
public class FXMLFormularioTutoriaController implements Initializable {

    @FXML
    private ComboBox<Asignatura> asignatura;
    @FXML
    private ComboBox<LocalTime> hora_inicio;
    @FXML
    private TextArea comentarios;

    private Tutorias misTutorias;
    @FXML
    private ComboBox<String> duracion;
    private ObservableList<Tutoria> listaTutoriasDia;
    private LocalDate fecha;
    @FXML
    private Button boton_confirmar;
    private FXMLGestorTutoriasController controlador_principal;
    private ObservableList<LocalTime> inicios;
    @FXML
    private ListView<Alumno> selectorAlumnos;
    private ObservableList<Alumno> alumnosSeleccionados;

    /**
     * Initializes the controller class.
     */
    //Inicializamos los campos
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        misTutorias = AccesoBD.getInstance().getTutorias();

        comboBoxAsignaturaConverter();
        asignatura.setItems(misTutorias.getAsignaturas());
        selectorAlumnos.setItems(misTutorias.getAlumnosTutorizados().sorted());
        selectorAlumnos.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectorAlumnos.setCellFactory(c -> new AlumnoListCell());

        selectorAlumnos.getSelectionModel().getSelectedItems();

        //Manejador de eventos para no tener que tener mantenido el control para
        //hacer seleccion multiple.
        //Fuente: https://living-sun.com/es/java/454578-mimicking-ctrlclick-multiple-selection-in-listview-using-javafx-java-listview-javafx-javafx-8-mouseclick-event.html
        selectorAlumnos.addEventFilter(MouseEvent.MOUSE_PRESSED, evt -> {
            Node node = evt.getPickResult().getIntersectedNode();
            alumnosSeleccionados = selectorAlumnos.getSelectionModel().getSelectedItems();

            // go up from the target node until a list cell is found or it"s clear
            // it was not a cell that was clicked
            while (node != null && node != selectorAlumnos && !(node instanceof ListCell)) {
                node = node.getParent();
            }

            // if is part of a cell or the cell,
            // handle event instead of using standard handling
            if (node instanceof ListCell) {
                // prevent further handling
                evt.consume();

                ListCell cell = (ListCell) node;
                ListView lv = cell.getListView();

                // focus the listview
                lv.requestFocus();

                if (!cell.isEmpty()) {
                    // handle selection for non-empty cells
                    int index = cell.getIndex();
                    if (cell.isSelected()) {
                        lv.getSelectionModel().clearSelection(index);
                    } else if (alumnosSeleccionados.size() < 4) {
                        lv.getSelectionModel().select(index);
                    }
                }
            }

        });

        boton_confirmar.disableProperty().bind(Bindings.or(asignatura.valueProperty().isNull(), (Bindings.or(hora_inicio.valueProperty().isNull(), (Bindings.or(duracion.valueProperty().isNull(), Bindings.not(Bindings.size(selectorAlumnos.getSelectionModel().getSelectedItems()).greaterThan(0))))))));
    }

    //Cuando se pulsa el boton confirmar se guarda la tutoria y se vacia el formulario
    @FXML
    private void confirmar(ActionEvent event) {
        Tutoria nueva = new Tutoria();
        nueva.setAsignatura(asignatura.getValue());
        nueva.setAnotaciones(comentarios.getText());
        nueva.setEstado(Tutoria.EstadoTutoria.PEDIDA);
        nueva.setInicio(hora_inicio.getValue());
        nueva.setFecha(fecha);

        int dur = Integer.parseInt(duracion.getValue());
        nueva.setDuracion(ofMinutes(dur));

        ObservableList<Alumno> alumnosNueva = nueva.getAlumnos();
        alumnosNueva.addAll(alumnosSeleccionados);

        ObservableList<Tutoria> lista_tutorias = misTutorias.getTutoriasConcertadas();
        lista_tutorias.add(nueva);
        controlador_principal.mostarTablaTutorias(fecha);

        //Limpiamos el formulario.
        asignatura.setValue(null);
        hora_inicio.setValue(null);
        setTutoriasDia(lista_tutorias);
        setInicio();
        comentarios.setText(null);
        duracion.setItems(null);
        selectorAlumnos.getSelectionModel().clearSelection();
    }

    //Cerramos la ventana
    @FXML
    private void cancelar(ActionEvent event) {
        controlador_principal.inicializarCalendario();
        controlador_principal.activarBotonAlumnos(true);
        controlador_principal.activarBotonAsignaturas(true);

    }

    public void setInicio() {
        hora_inicio.setItems(getHorasDisponibles());
        inicios = getHorasDisponibles();

    }

    public void setFecha(LocalDate f) {
        fecha = f;
    }

    public void setTutoriasDia(ObservableList<Tutoria> t) {
        listaTutoriasDia = t;
    }

    public void setControladorPrincipal(FXMLGestorTutoriasController c) {
        controlador_principal = c;
    }

    @FXML
    private void setDuraciones(ActionEvent event) {
        if (hora_inicio.getValue() != null) {
            duracion.setItems(getDuracionesPosibles(hora_inicio.getValue()));
        }
    }

    //Metodo que nos devuelve la lista con las horas de inicio dispoibles para elegir en la nueva tutoria.
    public ObservableList<LocalTime> getHorasDisponibles() {

        //Primero creamos la lista con todos los intervalos de 10 minutos entre
        //las 8:00 y las 20:00
        ArrayList<LocalTime> lista = new ArrayList<LocalTime>();
        ObservableList<LocalTime> horasDisponibles = FXCollections.observableList(lista);
        LocalTime hora = LocalTime.of(8, 0);
        horasDisponibles.add(hora);
        for (int j = 0; j < 72; j++) {
            hora = hora.plusMinutes(10);
            horasDisponibles.add(hora);
        }

        //Ahora borramos las que estan ocupadas por otras tutorias de ese 
        //mismo dia
        for (Iterator<Tutoria> iterator = listaTutoriasDia.iterator(); iterator.hasNext();) {
            Tutoria next = iterator.next();

            LocalTime inicio = next.getInicio();
            Duration duracionTutoria = next.getDuracion();
            long minutosDuracion = duracionTutoria.toMinutes();
            int iteraciones = (int) minutosDuracion / 10;
            for (int i = 1; i <= iteraciones; i++) {
                horasDisponibles.remove(inicio);
                inicio = inicio.plusMinutes(10);
            }

        }
        return horasDisponibles;
    }

    //Metodo que calcula las duraciones posibles apartir de la hora de inicio 
    //que ha elegido el usuario, para que no coincida con otras tutorias de ese
    //mismo dia, asi evitamos que el usuario se equivoque. 
    public ObservableList<String> getDuracionesPosibles(LocalTime ini) {
        ObservableList<String> minutos = FXCollections.observableArrayList();
        minutos.add("10");
        long min = 20;

        for (int i = 0; i < 5; i++) {
            if (inicios.contains(ini.plusMinutes(min - 10))) {
                minutos.add(min + "");
                min += 10;
            } else {
                break;
            }
        }
        return minutos;
    }

    //AQUI TENEmos LOS CONVERTERS PARA LOS COMBO BOX DEL FORMULARIO, CÓDIGO DE:
    //https://medium.com/@idrisbalikisopeyemi/working-with-javafx-combobox-a0c3ce7a440e
    private void comboBoxAsignaturaConverter() {
        asignatura.setConverter(new StringConverter<Asignatura>() {
            @Override
            public String toString(Asignatura a) {
                return a.getDescripcion();
            }

            @Override
            public Asignatura fromString(final String string) {
                return asignatura.getItems().stream().filter(asignatura -> asignatura.getDescripcion().equals(string)).findFirst().orElse(null);
            }
        });
    }

}

//Clase para el formato de la listview selectora de los alumnos
class AlumnoListCell extends ListCell<Alumno> {

    @Override
    protected void updateItem(Alumno item, boolean empty) {
        super.updateItem(item, empty); // Obligatoria esta llamada
        if (item == null || empty) {
            setText(null);
        } else {
            setText(item.getNombre() + " " + item.getApellidos());
        }
    }
}
