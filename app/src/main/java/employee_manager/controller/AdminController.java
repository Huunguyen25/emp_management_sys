package employee_manager.controller;

import java.time.LocalDate;

import employee_manager.model.User;
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
    @FXML private TextField filterByMaxSalary;
    @FXML private TextField filterByName;
    @FXML private TextField filterBySSN;

    @FXML private DatePicker filterByHireDateEnd;
    @FXML private DatePicker filterByHireDateStart;

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
            System.err.println("Error login out? " + e.getMessage());
        }
    }

    @FXML
    void handleApplySearchFilter(ActionEvent event) { //handle applying the search filter to the TableView 
        //TODO: use filterByEmpID, filterByName, filterByEmail, filterByMinSalary, filterByMinSalaryInclusive (checkbox whether or not to include filterbyMinSalary number ie. < vs <=), filterByMaxSalary, filterByMaxSalaryInclusive, filterByHireDateStart, filterByHireDateEnd, and SSN
        applyFilters();
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
        filterByMaxSalary.textProperty().addListener(refreshFilter);
    
        filterByHireDateEnd.valueProperty().addListener(refreshFilter);
        filterByHireDateStart.valueProperty().addListener(refreshFilter);
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

    private void applyFilters() {
        filteredData.setPredicate(employee -> {
            if (!Filters.applyDateFilter_isAfter(filterByHireDateEnd, employee.getHireDate())) return false;
            if (!Filters.applyDateFilter_isBefore(filterByHireDateStart, employee.getHireDate())) return false;
            if (!Filters.fieldIsBlank(filterByEmpID) &&
                !Filters.applyEmpIDFilter(filterByEmpID, employee.getEmpId())) return false;
            if (!Filters.fieldIsBlank(filterBySSN) &&
                !Filters.applyTextFilter(filterBySSN, employee.getSsn())) return false;
            if (!Filters.fieldIsBlank(filterByEmail) &&
                !Filters.applyTextFilter(filterByEmail, employee.getUserEmail())) return false;
            if (!Filters.fieldIsBlank(filterByName) &&
                !Filters.applyNameFilter(filterByName, employee.getFname(), employee.getLname())) return false;
            if (!Filters.fieldIsBlank(filterByMinSalary) &&
                !Filters.applySalaryFilter(filterByMinSalary, employee.getSalary(), false)) return false;
            if (!Filters.fieldIsBlank(filterByMaxSalary) &&
                !Filters.applySalaryFilter(filterByMaxSalary, employee.getSalary(), true)) return false;
            return true;
        });
    }
}
