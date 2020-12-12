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
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
    private ChoiceBox<Alumno> alumnos;
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
        
        comboBoxAlumnosConverter();
        alumnos.setItems(misTutorias.getAlumnosTutorizados());
        
        ObservableList<String> minutos = FXCollections.observableArrayList();
        minutos.addAll("10", "20", "30", "40", "50", "60");        
        duracion.setItems(minutos);
                
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
        
        ObservableList<Alumno> lista_alumnos = nueva.getAlumnos();
        lista_alumnos.addAll(alumnos.getValue());
        
        ObservableList<Tutoria> lista_tutorias = misTutorias.getTutoriasConcertadas();
        lista_tutorias.add(nueva);
        controlador_principal.mostarTablaTutorias(fecha);
        
    }

    //Cerramos la ventana
    @FXML
    private void cancelar(ActionEvent event) {
        controlador_principal.inicializarCalendario();

    }

    public void setInicio() {
        hora_inicio.setItems(getHorasDisponibles());
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
    
    //Metodo que nos devuelve la lista con las horas de inicio dispoibles para elegir en la nueva tutoria.
    public ObservableList<LocalTime> getHorasDisponibles() {
        
        //Primero creamos la lista con todos los intervalos de 10 minutos entre
        //las 8:00 y las 20:00
        ArrayList<LocalTime> lista = new ArrayList<LocalTime>();
        ObservableList<LocalTime> horasDisponibles = FXCollections.observableList(lista);
        LocalTime hora = LocalTime.of(8,0);
        horasDisponibles.add(hora);
        for(int j = 0; j < 72; j++) {
            hora = hora.plusMinutes(10);
            horasDisponibles.add(hora);
        }
        
        //Ahora borramos las que estan ocupadas por otras tutorias de ese 
        //mismo dia
        for (Iterator<Tutoria> iterator = listaTutoriasDia.iterator(); iterator.hasNext();) {
            Tutoria next = iterator.next();
            if (fecha == next.getFecha()) {
                LocalTime inicio = next.getInicio();
                Duration duracionTutoria = next.getDuracion();
                long minutosDuracion = duracionTutoria.toMinutes();
                int iteraciones = (int) minutosDuracion / 10;
                for (int i = 1; i <= iteraciones; i++) {
                    horasDisponibles.remove(inicio);
                    inicio = inicio.plusMinutes(10);
                }
            }
        }
        return horasDisponibles;
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
    
        private void comboBoxAlumnosConverter() {
        alumnos.setConverter(new StringConverter<Alumno>() {
            @Override
            public String toString(Alumno a) {
                return a.getNombre() + " " + a.getApellidos();
            }

            @Override
            public Alumno fromString(final String string) {
                return alumnos.getItems().stream().filter(alumnos -> alumnos.getApellidos().equals(string)).findFirst().orElse(null);
            }
        });
    }

}
