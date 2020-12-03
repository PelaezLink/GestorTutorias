/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventana_principal;

import com.sun.javafx.scene.control.skin.DatePickerSkin;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;

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
    private ListView<?> viewer;
    @FXML
    private Button boton_crear;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        inicializarCalendario();
        
    }

    public void inicializarCalendario() {
        DatePicker calendario = new DatePicker(LocalDate.now());
        calendario.setShowWeekNumbers(false);
        calendario.setDayCellFactory(cel -> new DiaCelda());

        DatePickerSkin datePickerSkin = new DatePickerSkin(calendario);
        Node popupContent = datePickerSkin.getPopupContent();

        borderPane.setCenter(popupContent);

    }

}

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
