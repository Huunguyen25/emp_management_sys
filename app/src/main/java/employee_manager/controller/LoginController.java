package employee_manager.controller;
import employee_manager.model.*;
import employee_manager.view.ViewManager;
import employee_manager.util.*;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class LoginController {

    @FXML private TextField empidField;
    @FXML private TextField emailField;

    @FXML
    private void handleLoginClick(ActionEvent event){
        try{
            Integer employeeId = empidField.getText().matches("[0-9]+") ? Integer.parseInt(empidField.getText()) : null;
            String email = emailField.getText();

            if (employeeId == null || employeeId == 0 || !email.contains("@example.com")) {
                System.out.println("Invalid credentials format");
                return;
            }
            //Instead of role, make a new user object
            User user = Model.authenticateRole(employeeId, email);

            
            if (user == null) return;
            switch (user.getRole()) {
                case "Admin":
                    Session.setUser(user);
                    ViewManager.switchScene(Constants.ADMIN_VIEW, event);
                    break;
                case "RegularEmployee":
                    Session.setUser(user);
                    ViewManager.switchScene(Constants.REGULAR_EMP_VIEW, event);
                    break;
                default:
                    break;
            }
        } catch(Exception e){
            System.out.println("Error login in. Try again. " + e);
            e.printStackTrace();
        }
    }
    @FXML
    private void initialize() {
        System.out.println("Login view initialized");
    }
}
