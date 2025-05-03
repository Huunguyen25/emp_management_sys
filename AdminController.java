package employee_manager.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

import com.sun.tools.classfile.StackMapTable_attribute;

import employee_manager.model.User;
import employee_manager.model.newEmp;
import employee_manager.service.UserService;
import employee_manager.view.ViewManager;
import employee_manager.util.*;
import javafx.beans.value.ChangeListener;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

    @FXML private Button applyFilterBtn;

    @FXML private TextField filterByEmail;
    @FXML private TextField filterByEmpID;
    @FXML private TextField filterByMinSalary;
    @FXML private TextField filterByName;
    @FXML private TextField filterBySSN;
    @FXML private TextField filterbyMaxSalary;

    @FXML private DatePicker filterByHireDateEnd;

    @FXML private DatePicker filterbyHireDateStart;

    @FXML private TextField addEmployeeFName;
    @FXML private TextField addEmployeeLName;
    @FXML private Button addEmpBtn;

    @FXML private TextField removeEmpID;
    @FXML private Button removeEmpBtn;

    @FXML private TextField SalaryUpdatePercentage;
    @FXML private TextField maxSalaryUpdate;
    @FXML private CheckBox maxSalaryUpdateInclusive;
    @FXML private TextField minSalaryUpdate;
    @FXML private CheckBox minSalaryUpdateInclusive;
    
    @FXML private TableView<User> employeeTable;
    @FXML private TableColumn<User, Integer> empIDColumn;
    @FXML private TableColumn<User, String> firstNameColumn;
    @FXML private TableColumn<User, String> lastNameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, LocalDate> hireDateColumn;
    @FXML private TableColumn<User, Double> salaryColumn;
    @FXML private TableColumn<User, String> ssnColumn;
    @FXML private TableColumn<User, String> roleColumn;

    private ObservableList<User> masterData;
    private FilteredList<User> filteredData;

    private UserService user = new UserService();

    //TODO: Implement admin interactions and send data to model
    @FXML
    public void handleLogout(ActionEvent event){ //handle logout click logout and logs out to login menu
        try{
            Session.removeCurrentUser();
            ViewManager.switchScene("/employee_manager/view/Login.fxml", event);
        } catch (Exception e){
            System.out.println("Error login out? " + e.getMessage());
        }
    }

    @FXML
    void handleApplySearchFilter(ActionEvent event) { //handle applying the search filter to the TableView 
        applyFilters();
    }

    @FXML
    void handleApplySalaryUpdate(ActionEvent event) { //handle updating employee salaries
        applySalaryUpdate();
    }
    @FXML
    void handleAddEmployee(ActionEvent event){ //handle adding employees from DB
        addEmployee();
    }
    @FXML
    void handleRemoveEmployee(ActionEvent event){ //handle removing employees from DB
        removeEmployee();
    }
    @FXML
    void updateTable(ActionEvent event){ 
        loadEmployeeList();
    }

    @FXML
    void handleGenerateReport(ActionEvent event){ //handle generating pay reports
        
    }
    

    @FXML
    private void initialize() {
        empIDColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("Fname"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("Lname"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        hireDateColumn.setCellValueFactory(new PropertyValueFactory<>("hireDate"));
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));
        ssnColumn.setCellValueFactory(new PropertyValueFactory<>("ssn"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        loadEmployeeList();
        bindLiveFilter();
    }

    private void bindLiveFilter() {
        ChangeListener<Object> refreshFilter = (obs, oldVal, newVal) -> applyFilters();
    
        filterByName.textProperty().addListener(refreshFilter);
        filterByEmail.textProperty().addListener(refreshFilter);
        filterByEmpID.textProperty().addListener(refreshFilter);
        filterBySSN.textProperty().addListener(refreshFilter);
        filterByMinSalary.textProperty().addListener(refreshFilter);
        filterbyMaxSalary.textProperty().addListener(refreshFilter);
    
        filterByHireDateEnd.valueProperty().addListener(refreshFilter);
        filterbyHireDateStart.valueProperty().addListener(refreshFilter);
    }

    private void addEmployee() { //add employee to DB with first and last name
        String query = "INSERT INTO employees (FName, LName) VALUES (?, ?)";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement myStmt = conn.prepareStatement(query);
            myStmt.setString(1, addEmployeeFName.getText());
            myStmt.setString(2, addEmployeeLName.getText());
            myStmt.executeQuery();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    private void removeEmployee() { //remove employee from DB using empid
        try{
            Integer.parseInt(removeEmpID.getText());
        } catch (Exception e) {
            System.out.println("Invalid Input");
        }

        String query = "DELETE FROM employees WHERE empid = ?";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement myStmt = conn.prepareStatement(query);
            myStmt.setInt(1, Integer.parseInt(removeEmpID.getText()));
            myStmt.executeQuery();
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    
    private void applySalaryUpdate() {
        
    }

    private void loadEmployeeList() {
        // ObservableList<User> employees = user.getAllEmployees();
        // employeeTable.setItems(employees);
        masterData = user.getAllEmployees();
        filteredData = user.getFilteredEmployees(masterData);
    
    // Wrap the FilteredList in a SortedList to enable sorting
        SortedList<User> sortedData = new SortedList<>(filteredData);
    
    // Bind the SortedList comparator to the TableView comparator
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
    
    // Set the sorted and filtered data to the table
        employeeTable.setItems(sortedData);
    }

    private void applyFilters(){
        filteredData.setPredicate(employee -> {
            try {
                LocalDate hireDate = employee.getHireDate();
                LocalDate hireDateStart = filterbyHireDateStart.getValue();
                LocalDate hireDateEnd = filterByHireDateEnd.getValue();

                if(hireDateStart!= null && hireDateStart.isAfter(hireDate)) return false;
                if(hireDateEnd != null && hireDateEnd.isBefore(hireDate)) return false;
            } catch (Exception e) {
                return false;
            }

            if (filterByEmpID.getText() != null && !filterByEmpID.getText().isEmpty()) {
                try {
                    int filterID = Integer.parseInt(filterByEmpID.getText());
                    if (employee.getEmpId() != filterID) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                }
            }

            if((filterBySSN.getText() != null && !filterBySSN.getText().isEmpty())){
                if(!employee.getSsn().toLowerCase().contains(filterBySSN.getText())){
                    return false;
                }
            }

            if((filterByEmail.getText() != null && !filterByEmail.getText().isEmpty())){
                if(!employee.getUserEmail().toLowerCase().contains(filterByEmail.getText())){
                    return false;
                }
            }

            if((filterByName.getText() != null && !filterByName.getText().isEmpty())){
                if(!(employee.getFname()+" "+employee.getLname()).toLowerCase().contains(filterByName.getText().toLowerCase())){
                    return false;
                }
            }

            if(isDouble(filterByMinSalary.getText()) && filterByMinSalary.getText() != null && !filterByMinSalary.getText().isEmpty()){
                if(employee.getSalary() <= Double.parseDouble(filterByMinSalary.getText())){
                    return false;
                }
            }
            if(isDouble(filterbyMaxSalary.getText()) && filterbyMaxSalary.getText() != null && !filterbyMaxSalary.getText().isEmpty()){
                if(employee.getSalary() >= Double.parseDouble(filterbyMaxSalary.getText())){
                    return false;
                }
            }
            return true;
        });
    }

    public boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
