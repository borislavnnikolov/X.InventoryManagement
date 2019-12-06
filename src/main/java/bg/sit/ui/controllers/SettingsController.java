package bg.sit.ui.controllers;

import bg.sit.session.SessionHelper;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {

    @FXML
    private TextField year;
    @FXML
    private TextField limitMA;
    @FXML
    private DatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void setLimitMA(ActionEvent event) {
        SessionHelper.setMALimit(Double.parseDouble(limitMA.getText()));
        limitMA.clear();
    }

    public void setYearBeofreDiscard(ActionEvent event) {
        SessionHelper.setYearsBeforeDiscard(Integer.parseInt(year.getText()));
        year.clear();
    }

    public void setDate(ActionEvent event) {
        LocalDate DP = date.getValue();
        SessionHelper.setCurrentDate(DP);
        date.setValue(null);
    }
}
