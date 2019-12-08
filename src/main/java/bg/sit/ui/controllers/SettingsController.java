package bg.sit.ui.controllers;

import bg.sit.session.SessionHelper;
import java.net.URL;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {

    @FXML
    private TextField years;
    @FXML
    private TextField limitMA;
    @FXML
    private DatePicker date;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        getData();
    }

    public void setLimitMA(ActionEvent event) {
        SessionHelper.setMaLimit(Double.parseDouble(limitMA.getText()));
        getData();
    }

    public void setYearBeofreDiscard(ActionEvent event) {
        SessionHelper.setYearsBeforeDiscard(Integer.parseInt(years.getText()));
        getData();
    }

    public void setDate(ActionEvent event) {
        LocalDate DP = date.getValue();
        SessionHelper.setCurrentDate(Date.valueOf(DP));
        getData();
    }

    public void getData() {
        limitMA.setText(String.valueOf(SessionHelper.getMaLimit()));
        LocalDate localCurrentDate = Instant.ofEpochMilli(SessionHelper.getCurrentDate().getTime())
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        date.setValue(localCurrentDate);
        years.setText(String.valueOf(SessionHelper.getYearsBeforeDiscard()));
    }
}
