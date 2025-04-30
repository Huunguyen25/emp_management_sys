package employee_manager.controller;

import employee_manager.view.ViewManager;

import java.time.LocalDate;

import employee_manager.model.Payroll;
import employee_manager.model.User;
import employee_manager.service.PayrollService;
import employee_manager.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

public class RegularEmployeeController {
    //TODO: Implement regular emp interactions and send data to model
    @FXML private Label greetNameLabel;

    @FXML private TableView<Payroll> payrollTable;
    @FXML private TableColumn<Payroll, Integer> payIdColumn;
    @FXML private TableColumn<Payroll, LocalDate> payDateColumn;
    @FXML private TableColumn<Payroll, Double> earningsColumn;
    @FXML private TableColumn<Payroll, Double> fedTaxColumn;
    @FXML private TableColumn<Payroll, Double> fedMedColumn;
    @FXML private TableColumn<Payroll, Double> fedSSColumn;
    @FXML private TableColumn<Payroll, Double> stateTaxColumn;
    @FXML private TableColumn<Payroll, Double> retire401kColumn;
    @FXML private TableColumn<Payroll, Double> healthCareColumn;

    private PayrollService payrollService = new PayrollService();

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
        User user = Session.getUser();

        if (user == null || user.isAdmin()) {
            try{
                ViewManager.switchScene(Constants.LOGIN_VIEW, null);
            } catch (Exception e) {
                System.out.println("Error switching scene: " + e.getMessage());
            }
            return;
        }

        greetNameLabel.setText("Greetings, " + user.getFname() + " " + user.getLname());

        payIdColumn.setCellValueFactory(new PropertyValueFactory<>("payID"));
        payDateColumn.setCellValueFactory(new PropertyValueFactory<>("pay_date"));
        earningsColumn.setCellValueFactory(new PropertyValueFactory<>("earnings"));
        fedTaxColumn.setCellValueFactory(new PropertyValueFactory<>("fed_tax"));
        fedMedColumn.setCellValueFactory(new PropertyValueFactory<>("fed_med"));
        fedSSColumn.setCellValueFactory(new PropertyValueFactory<>("fed_SS"));
        stateTaxColumn.setCellValueFactory(new PropertyValueFactory<>("state_tax"));
        retire401kColumn.setCellValueFactory(new PropertyValueFactory<>("retire_401k"));
        healthCareColumn.setCellValueFactory(new PropertyValueFactory<>("health_care"));

        ObservableList<Payroll> payrollData = payrollService.getPayrollsForUser(user.getEmpId());
        payrollTable.setItems(payrollData);
    }
}
