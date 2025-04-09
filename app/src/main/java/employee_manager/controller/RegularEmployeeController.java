package employee_manager.controller;

import employee_manager.view.ViewManager;
import employee_manager.model.User;
import employee_manager.util.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class RegularEmployeeController {
    //TODO: Implement regular emp interactions and send data to model
    private User _regularEmployee;
    @FXML private Label greetNameLabel;

    @FXML
    private void handleLogout(ActionEvent event){
        try{
            Session.removeCurrentUser();
            ViewManager.switchScene(Constants.LOGIN_VIEW, event);
        } catch (Exception e){
            System.out.println("Error login out? " + e.getMessage());
        }
    }
    @FXML
    private void initialize(){
        //TODO: Get the current session user
        this._regularEmployee = Session.getUser();
        greetNameLabel.setText("Greetings, " + _regularEmployee.getFName() + " " + _regularEmployee.getLName());
    }
}
