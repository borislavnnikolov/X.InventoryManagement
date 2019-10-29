package bg.sit.xinventorymanagement;

import bg.sit.business.pojo.User;
import bg.sit.business.services.UsersService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class FXMLController implements Initializable {
    
    @FXML
    private Label label;
    
    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        UsersService userService = new UsersService();
        User user = userService.getUsers().get(0);
        System.out.println("Name: " + user.getName() + " Username: " + user.getUsername() + " password: " + user.getPassword() + " RoleType " + user.getRoleType());
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
}
