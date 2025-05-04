package employee_manager.controller;

import java.io.IOException;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.Node;

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


    @FXML private TextField SalaryUpdatePercentage;
    @FXML private TextField maxSalaryUpdate;
    @FXML private TextField minSalaryUpdate;
    
    @FXML private TableView<User> employeeTable;
    @FXML private TableColumn<User, Integer> empIDColumn;
    @FXML private TableColumn<User, String> firstNameColumn;
    @FXML private TableColumn<User, String> lastNameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, LocalDate> hireDateColumn;
    @FXML private TableColumn<User, Double> salaryColumn;
    @FXML private TableColumn<User, String> ssnColumn;
    @FXML private TableColumn<User, String> roleColumn;

    @FXML private ChoiceBox<String> reportChoiceBox;

    private ObservableList<User> masterData;
    private FilteredList<User> filteredData;

    private UserService user = new UserService();
    private boolean changes = false;

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
        //TODO: use minSalaryUpdate, minSalaryUpdateInclusive (which is a checkbox whether or not to include the minSalaryUpdate number ie. < vs <=), maxSalaryUpdate, maxSalaryUpdateInclusive, and salaryUpdatePercentage
        try{
            if(maxSalaryUpdate.getText().trim().isEmpty() || 
               minSalaryUpdate.getText().trim().isEmpty() || 
               SalaryUpdatePercentage.getText().trim().isEmpty()) {
                showAlert(AlertType.ERROR, "Input error", "Enter value parameter and percentage");
                return;
            }
            
            double maxSalary = Double.parseDouble(maxSalaryUpdate.getText().trim());
            double minSalary = Double.parseDouble(minSalaryUpdate.getText().trim());
            double percentageInput = Double.parseDouble(SalaryUpdatePercentage.getText().trim());
            double salaryUpdatePercentage = 1.0 + (percentageInput / 100);

            if (maxSalary <= 0 || minSalary <= 0 || salaryUpdatePercentage <= 0) {
                throw new NumberFormatException();
            }

            boolean confirmed = showConfirmation("Confirm new salary", 
            "Are you sure you want to apple new salaray to employee within: " + 
            minSalary + " " + maxSalary + "?");

            if(confirmed){
                boolean success = user.applySalaryUpdate(salaryUpdatePercentage, minSalary, maxSalary);
                
                if (success) {
                    showAlert(AlertType.INFORMATION, "Success", "New salary applied successfully");
                    changes = true;
                } else {
                    showAlert(AlertType.ERROR, "Error", "Failed to apply new salary");
                }
            }


        } catch(NumberFormatException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void addRemoveEmployee(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee_manager/view/AddRemoveEmpDialog.fxml"));
            Parent dialogContent = loader.load();

            AddRemoveDialogEmpController dialogController = loader.getController();
            dialogController.setUserService(user);
            
            Dialog<ButtonType> dialog = new Dialog<>();
            dialog.setTitle("Add/Remove Employee");
            dialog.setHeaderText("Manage Employees");
            dialog.getDialogPane().setContent(dialogContent);

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            dialog.showAndWait().ifPresent(buttonType -> {
                if (buttonType == ButtonType.OK) {
                    dialogController.processResult();
                    loadEmployeeList();
                }
            });
        } catch (IOException e){
            System.err.println(e);
        }
    }
    @FXML
    void updateTable(ActionEvent event){
        loadEmployeeList();
    }

    @FXML
    void handleGenerateReport(ActionEvent event){
        String reportChoice = reportChoiceBox.getValue();
        
        if(reportChoice.equals("Payroll Summary")) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee_manager/view/PayrollReport.fxml"));
                Parent dialogContent = loader.load();
                
                PayrollReportController reportController = loader.getController();
                
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Complete Payroll Summary");
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(((Node)event.getSource()).getScene().getWindow());
                Scene dialogScene = new Scene(dialogContent);
                dialogStage.setScene(dialogScene);
                
                reportController.loadPayrollData();
                
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error", "Could not load payroll report: " + e.getMessage());
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/employee_manager/view/AdminSummary.fxml"));
                Parent dialogContent = loader.load();
                
                AdminSummaryController summaryController = loader.getController();
                
                Stage dialogStage = new Stage();
                if(reportChoice.equals("Total pay for month by job title")) {
                    dialogStage.setTitle("Payroll Summary by Job Title");
                } else {
                    dialogStage.setTitle("Payroll Summary by Division");
                    summaryController.setReportType("division");
                }
                dialogStage.initModality(Modality.WINDOW_MODAL);
                dialogStage.initOwner(((Node)event.getSource()).getScene().getWindow());
                Scene dialogScene = new Scene(dialogContent);
                dialogStage.setScene(dialogScene);
                
                summaryController.loadSummary();
                
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(AlertType.ERROR, "Error", "Could not load report: " + e.getMessage());
            }
        }
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

        reportChoiceBox.getItems().clear();
        reportChoiceBox.getItems().addAll("Total pay for month by job title", "Total pay for month by Division", "Payroll Summary");
        reportChoiceBox.setValue("Total pay for month by job title");
    }

    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == javafx.scene.control.ButtonType.OK).isPresent();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
    
    private void loadEmployeeList() {
        masterData = user.getAllEmployees();
        filteredData = user.getFilteredEmployees(masterData);
    
        SortedList<User> sortedData = new SortedList<>(filteredData);
    
        sortedData.comparatorProperty().bind(employeeTable.comparatorProperty());
    
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
                if(employee.getSalary() < Double.parseDouble(filterByMinSalary.getText())){
                    return false;
                }
            }
            if(isDouble(filterbyMaxSalary.getText()) && filterbyMaxSalary.getText() != null && !filterbyMaxSalary.getText().isEmpty()){
                if(employee.getSalary() > Double.parseDouble(filterbyMaxSalary.getText())){
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
