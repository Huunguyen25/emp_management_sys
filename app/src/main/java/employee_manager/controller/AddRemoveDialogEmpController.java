package employee_manager.controller;

import employee_manager.model.User;
import employee_manager.service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class AddRemoveDialogEmpController {
    @FXML private Button addEmplDialogBtn;
    @FXML private Button RemoveEmpDialogBtn;


    @FXML private TableView<User> employeesTable;
    @FXML private TableColumn<User, Integer> idColumn;
    @FXML private TableColumn<User, String> nameColumn;
    @FXML private TableColumn<User, String> emailColumn;
    @FXML private TableColumn<User, String> roleColumn;
    @FXML private Button removeButton;

    private UserService userService;

    public void showAddEmpMenu(ActionEvent event){

    }
    public void showRemoveEmpMenu(ActionEvent event){

    }

    @FXML 
    void handleAddEmployee(){

    }

    @FXML
    void handleRemoveEmployee(){

    }

    public void setUserService(UserService userService) {
        this.userService = userService;
        employeesTable.setItems(userService.getAllEmployees());
    }

    public void processResult(){
        //Will be confirmation for adding/removing emp
    }

    @FXML
    private void initialize(){
        
    }




}
