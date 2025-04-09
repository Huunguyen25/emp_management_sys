package employee_manager.controller;

import employee_manager.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AdminController {
    //TODO: Implement admin interactions and send data to model
        @FXML
    public void handleLogout(ActionEvent event){
        try{
            ViewManager.switchScene("/employee_manager/view/Login.fxml", event);
        } catch (Exception e){
            System.out.println("Error login out? " + e.getMessage());
        }
        
    }
}
