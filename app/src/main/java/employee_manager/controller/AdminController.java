package employee_manager.controller;

import java.time.LocalDate;

import employee_manager.model.Employee;
import employee_manager.model.Model;
import employee_manager.view.ViewManager;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class AdminController {

    @FXML private TextField SalaryUpdatePercentage;
    @FXML private Button applyFilterBtn;
    @FXML private TextField filterByEmail;
    @FXML private TextField filterByEmpID;
    @FXML private DatePicker filterByHireDateEnd;
    @FXML private CheckBox filterByMaxSalaryInclusive;
    @FXML private TextField filterByMinSalary;
    @FXML private CheckBox filterByMinSalaryInclusive;
    @FXML private TextField filterByName;
    @FXML private TextField filterBySSN;
    @FXML private DatePicker filterbyHireDateStart;
    @FXML private TextField filterbyMaxSalary;
    @FXML private TextField maxSalaryUpdate;
    @FXML private CheckBox maxSalaryUpdateInclusive;
    @FXML private TextField minSalaryUpdate;
    @FXML private CheckBox minSalaryUpdateInclusive;

    @FXML private TableView<Employee> employeeTable;
    @FXML private TableColumn<Employee, Integer> empIDColumn;
    @FXML private TableColumn<Employee, String> firstNameColumn;
    @FXML private TableColumn<Employee, String> lastNameColumn;
    @FXML private TableColumn<Employee, String> emailColumn;
    @FXML private TableColumn<Employee, LocalDate> hireDateColumn;
    @FXML private TableColumn<Employee, Double> salaryColumn;
    @FXML private TableColumn<Employee, String> ssnColumn;

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
    void handleApplySearchFilter(ActionEvent event) { //handle applying the search filter to the TableView 
        //TODO: use filterByEmpID, filterByName, filterByEmail, filterByMinSalary, filterByMinSalaryInclusive (checkbox whether or not to include filterbyMinSalary number ie. < vs <=), filterByMaxSalary, filterByMaxSalaryInclusive, filterByHireDateStart, filterByHireDateEnd, and SSN
    }

    @FXML
    void handleApplySalaryUpdate(ActionEvent event) { //handle updating employee salaries
        //TODO: use minSalaryUpdate, minSalaryUpdateInclusive (which is a checkbox whether or not to include the minSalaryUpdate number ie. < vs <=), maxSalaryUpdate, maxSalaryUpdateInclusive, and salaryUpdatePercentage
    }
    @FXML
    void addRemoveEmployee(ActionEvent event){

    }
    @FXML
    void updateTable(ActionEvent event){
        loadEmployeeList();
    }

    @FXML
    void handleGenerateReport(ActionEvent event){
        
    }
    

    @FXML
    private void initialize() {
        empIDColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        loadEmployeeList();
    }
    
    private void loadEmployeeList() {
        ObservableList<Employee> employees = Model.fetchEmployee();
        employeeTable.setItems(employees);
    }
}
