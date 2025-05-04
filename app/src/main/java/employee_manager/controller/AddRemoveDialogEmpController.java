package employee_manager.controller;

import employee_manager.model.User;
import employee_manager.service.UserService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;


public class AddRemoveDialogEmpController {
    @FXML private Button addEmplDialogBtn;
    @FXML private Button RemoveEmpDialogBtn;

    @FXML private TextField firstNameField;
    @FXML private TextField lastNameField;
    @FXML private TextField emailField;
    @FXML private DatePicker hireDatePicker;
    @FXML private TextField salaryField;
    @FXML private TextField ssnField;


    @FXML private TableView<User> employeesTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> roleColumn;
    @FXML private Button removeButton;

    private UserService userService;
    private boolean changes = false;

    public void showAddEmpMenu(ActionEvent event){

    }
    public void showRemoveEmpMenu(ActionEvent event){

    }

    @FXML
    void handleAddEmployee() {
        try {
            if (firstNameField.getText().trim().isEmpty() ||
                lastNameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                hireDatePicker.getValue() == null ||
                salaryField.getText().trim().isEmpty() ||
                ssnField.getText().trim().isEmpty()) {
                showAlert(AlertType.ERROR, "Input Error", "Please fill in all fields");
                return;
            }

            double salary;
            try {
                salary = Double.parseDouble(salaryField.getText().trim());
                if (salary <= 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                showAlert(AlertType.ERROR, "Input Error", "Salary must be a positive number");
                return;
            }

            if (!ssnField.getText().matches("\\d{3}-\\d{2}-\\d{4}")) {
                showAlert(AlertType.ERROR, "Input Error", "SSN must be in format XXX-XX-XXXX");
                return;
            }
            if (!emailField.getText().contains("@")) {
                showAlert(AlertType.ERROR, "Input Error", "Please enter a valid email address");
                return;
            }

            boolean success = userService.addEmployee(
                firstNameField.getText().trim(),
                lastNameField.getText().trim(),
                emailField.getText().trim(),
                hireDatePicker.getValue(),
                salary,
                ssnField.getText().trim()
            );

            if (success) {
                showAlert(AlertType.INFORMATION, "Success", "Employee added successfully");
                changes = true;
                clearFields();
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to add employee");
            }
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "An unexpected error occurred");
            e.printStackTrace();
        }
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        hireDatePicker.setValue(null);
        salaryField.clear();
        ssnField.clear();
    }

    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleRemoveEmployee(){
        User selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
        
        if (selectedEmployee == null) {
            showAlert(AlertType.WARNING, "Selection Error", "Please select an employee to remove");
            return;
        }
        
        if (selectedEmployee.getEmpId() == 1 || selectedEmployee.getRole().equals("admin")) {
            showAlert(AlertType.ERROR, "Forbidden", "Cannot remove the main administrator account");
            return;
        }
        
        boolean confirmed = showConfirmation("Confirm Deletion", 
            "Are you sure you want to remove employee: " + 
            selectedEmployee.getFname() + " " + selectedEmployee.getLname() + "?");
        
        if (confirmed) {
            boolean success = userService.removeEmployee(selectedEmployee.getEmpId());
            
            if (success) {
                employeesTable.getItems().remove(selectedEmployee);
                showAlert(AlertType.INFORMATION, "Success", "Employee removed successfully");
                changes = true;
            } else {
                showAlert(AlertType.ERROR, "Error", "Failed to remove employee");
            }
        }
    }
    private boolean showConfirmation(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        return alert.showAndWait().filter(response -> response == javafx.scene.control.ButtonType.OK).isPresent();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        employeesTable.setItems(userService.getAllEmployees());
    }

    public void processResult(){
        if (changes) {
            employeesTable.setItems(userService.getAllEmployees());
            changes = false;
        }
    }

    @FXML
    private void initialize(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("empId"));
        nameColumn.setCellValueFactory(cellData -> {
            User user = cellData.getValue();
            return new javafx.beans.property.SimpleStringProperty(
                user.getFname() + " " + user.getLname());
        });
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("userEmail"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
    }
}
