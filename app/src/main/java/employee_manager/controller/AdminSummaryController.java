package employee_manager.controller;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import employee_manager.model.PayrollSummary;
import employee_manager.service.PayrollService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminSummaryController {
    @FXML private TableView<PayrollSummary> summaryTable;

    @FXML private TableColumn<PayrollSummary, YearMonth> monthCol;

    @FXML private TableColumn<PayrollSummary,String> jobTitleCol;

    @FXML private TableColumn<PayrollSummary, Double> totalPayCol;

    private PayrollService payrollService =  new PayrollService();
    
    private String reportType = "jobTitle"; // Default report type

    public void setReportType(String reportType) {
        this.reportType = reportType;
        
        // Update column header based on report type
        if ("division".equals(reportType)) {
            jobTitleCol.setText("Division");
        } else {
            jobTitleCol.setText("Job Title ID");
        }
    }

    @FXML
    private void initialize(){
        monthCol.setCellValueFactory(new PropertyValueFactory<>("month"));
        jobTitleCol.setCellValueFactory(new PropertyValueFactory<>("jobTitle"));
        totalPayCol.setCellValueFactory(new PropertyValueFactory<>("totalPay"));
        
        // Add formatting for date values
        monthCol.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<PayrollSummary, YearMonth>() {
                private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
                
                @Override
                protected void updateItem(YearMonth date, boolean empty) {
                    super.updateItem(date, empty);
                    if (empty || date == null) {
                        setText(null);
                    } else {
                        setText(formatter.format(date));
                    }
                }
            };
        });
        
        totalPayCol.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<PayrollSummary, Double>() {
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
        });
    }

    @FXML
    public void loadSummary() {
        ObservableList<PayrollSummary> summaryData;
        
        if ("division".equals(reportType)) {
            summaryData = payrollService.getPayrollSummaryByDivision();
        } else {
            summaryData = payrollService.getPayrollSummaryByJobTitle();
        }
        
        summaryTable.setItems(summaryData);
    }

    public void closeDialog() {
        Stage stage = (Stage) summaryTable.getScene().getWindow();
        stage.close();
    }
}
