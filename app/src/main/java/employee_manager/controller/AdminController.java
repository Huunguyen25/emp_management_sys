package employee_manager.controller;

import employee_manager.view.ViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class AdminController {
    
    //TODO: Implement admin interactions and send data to model
        @FXML
    public void handleLogout(ActionEvent event){ //handle logout click logout and logs out to login menu
        try{
            ViewManager.switchScene("/employee_manager/view/Login.fxml", event);
        } catch (Exception e){
            System.out.println("Error login out? " + e.getMessage());
        }
        
    }

    @FXML
    void applySearchFilter(ActionEvent event) { //handle applying the search filter to the TableView 
        //TODO: use filterByEmpID, filterByName, filterByEmail, filterByMinSalary, filterByMinSalaryInclusive (checkbox whether or not to include filterbyMinSalary number ie. < vs <=), filterByMaxSalary, filterByMaxSalaryInclusive, filterByHireDateStart, filterByHireDateEnd, and SSN
    }

    @FXML
    void applySalaryUpdate(ActionEvent event) { //handle updating employee salaries
        //TODO: use minSalaryUpdate, minSalaryUpdateInclusive (which is a checkbox whether or not to include the minSalaryUpdate number ie. < vs <=), maxSalaryUpdate, maxSalaryUpdateInclusive, and salaryUpdatePercentage
    }

    @FXML
    private Button Logout;

    @FXML
    private TextField SalaryUpdatePercentage;

    @FXML
    private Button applyFilterBtn;

    @FXML
    private TextField filterByEmail;

    @FXML
    private TextField filterByEmpID;

    @FXML
    private DatePicker filterByHireDateEnd;

    @FXML
    private CheckBox filterByMaxSalaryInclusive;

    @FXML
    private TextField filterByMinSalary;

    @FXML
    private CheckBox filterByMinSalaryInclusive;

    @FXML
    private TextField filterByName;

    @FXML
    private TextField filterBySSN;

    @FXML
    private DatePicker filterbyHireDateStart;

    @FXML
    private TextField filterbyMaxSalary;

    @FXML
    private TextField maxSalaryUpdate;

    @FXML
    private CheckBox maxSalaryUpdateInclusive;

    @FXML
    private TextField minSalaryUpdate;

    @FXML
    private CheckBox minSalaryUpdateInclusive;
}
