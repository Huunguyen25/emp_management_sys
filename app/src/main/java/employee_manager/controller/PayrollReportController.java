package employee_manager.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import employee_manager.model.PayrollDetail;
import employee_manager.service.PayrollService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PayrollReportController {
    @FXML private TableView<PayrollDetail> payrollTable;
    
    @FXML private TableColumn<PayrollDetail, Integer> empIdCol;
    @FXML private TableColumn<PayrollDetail, Integer> payIdColumn;
    @FXML private TableColumn<PayrollDetail, LocalDate> payDateColumn;
    @FXML private TableColumn<PayrollDetail, Double> earningsColumn;
    @FXML private TableColumn<PayrollDetail, Double> fedTaxColumn;
    @FXML private TableColumn<PayrollDetail, Double> fedMedColumn;
    @FXML private TableColumn<PayrollDetail, Double> fedSSColumn;
    @FXML private TableColumn<PayrollDetail, Double> stateTaxColumn;
    @FXML private TableColumn<PayrollDetail, Double> retire401kColumn;
    @FXML private TableColumn<PayrollDetail, Double> healthCareColumn;
    
    private PayrollService payrollService = new PayrollService();
    
    @FXML
    private void initialize() {
        empIdCol.setCellValueFactory(new PropertyValueFactory<>("empId"));
        payIdColumn.setCellValueFactory(new PropertyValueFactory<>("payID"));
        payDateColumn.setCellValueFactory(new PropertyValueFactory<>("pay_date"));
        earningsColumn.setCellValueFactory(new PropertyValueFactory<>("earnings"));
        fedTaxColumn.setCellValueFactory(new PropertyValueFactory<>("fed_tax"));
        fedMedColumn.setCellValueFactory(new PropertyValueFactory<>("fed_med"));
        fedSSColumn.setCellValueFactory(new PropertyValueFactory<>("fed_SS"));
        stateTaxColumn.setCellValueFactory(new PropertyValueFactory<>("state_tax"));
        retire401kColumn.setCellValueFactory(new PropertyValueFactory<>("retire_401k"));
        healthCareColumn.setCellValueFactory(new PropertyValueFactory<>("health_care"));
        
        // Add formatting for date values
        payDateColumn.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<PayrollDetail, LocalDate>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                
                @Override
                protected void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(formatter.format(date));
                    }
                }
            };
        });
        
        // Add formatting for currency values
        earningsColumn.setCellFactory(column -> createCurrencyCell());
        fedTaxColumn.setCellFactory(column -> createCurrencyCell());
        fedMedColumn.setCellFactory(column -> createCurrencyCell());
        fedSSColumn.setCellFactory(column -> createCurrencyCell());
        stateTaxColumn.setCellFactory(column -> createCurrencyCell());
        retire401kColumn.setCellFactory(column -> createCurrencyCell());
        healthCareColumn.setCellFactory(column -> createCurrencyCell());
    }
    
    private javafx.scene.control.TableCell<PayrollDetail, Double> createCurrencyCell() {
        return new javafx.scene.control.TableCell<PayrollDetail, Double>() {
            @Override
            protected void updateItem(Double amount, boolean empty) {
                super.updateItem(amount, empty);
                if (empty || amount == null) {
                    setText(null);
                } else {
                    setText(String.format("$%.2f", amount));
                }
            }
        };
    }
    
    public void loadPayrollData() {
        ObservableList<PayrollDetail> payrollData = payrollService.getAllPayrollRecords();
        payrollTable.setItems(payrollData);
    }
    
    public void closeDialog() {
        Stage stage = (Stage) payrollTable.getScene().getWindow();
        stage.close();
    }
}
