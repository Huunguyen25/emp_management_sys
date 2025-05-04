package employee_manager.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import employee_manager.model.*;
import employee_manager.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.YearMonth;
import java.sql.SQLException;

public class PayrollDAO {
    public PayrollDAO(){

    }
    public ObservableList<Payroll> getPayrollsByUserId(int empid){
        ObservableList<Payroll> payrollData = FXCollections.observableArrayList();
        String query = "Select * FROM payroll p where p.empid = ?";
        try{
            Connection conn = Database.getConnection();
            PreparedStatement myStmt = conn.prepareStatement(query);
            myStmt.setInt(1, empid);
            ResultSet myRS = myStmt.executeQuery();
            while (myRS.next()){
                Payroll payroll = new Payroll(
                    myRS.getInt("payID"),
                    myRS.getDate("pay_date").toLocalDate(),
                    myRS.getDouble("earnings"),
                    myRS.getDouble("fed_tax"),
                    myRS.getDouble("fed_med"),
                    myRS.getDouble("fed_SS"),
                    myRS.getDouble("state_tax"),
                    myRS.getDouble("retire_401k"),
                    myRS.getDouble("health_care")
                );
                payrollData.add(payroll);
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        return payrollData;
    } 


    public ObservableList<PayrollSummary> getPayrollSummaryByJobTitle() {
        ObservableList<PayrollSummary> summaryData = FXCollections.observableArrayList();
        
        String query = "SELECT DATE_FORMAT(p.pay_date, '%Y-%m') as formatted_date, " +
                      "j.job_title_id as job_title, " +
                      "SUM(p.earnings) as total_pay " +
                      "FROM payroll p " +
                      "JOIN employee_job_titles j ON p.empid = j.empid " +
                      "GROUP BY formatted_date, j.job_title_id " +
                      "ORDER BY formatted_date DESC, j.job_title_id";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String dateStr = rs.getString("formatted_date");
                YearMonth month = YearMonth.parse(dateStr);
                String jobTitle = rs.getString("job_title");
                double totalPay = rs.getDouble("total_pay");
                
                PayrollSummary summary = new PayrollSummary(month, jobTitle, totalPay);
                summaryData.add(summary);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payroll summary: " + e.getMessage());
            e.printStackTrace();
        }
        
        return summaryData;
    }

    public ObservableList<PayrollSummary> getPayrollSummaryByDivision() {
        ObservableList<PayrollSummary> summaryData = FXCollections.observableArrayList();
        
        String query = "SELECT DATE_FORMAT(p.pay_date, '%Y-%m') as formatted_date, " +
                      "d.name as division_name, " +
                      "SUM(p.earnings) as total_pay " +
                      "FROM payroll p " +
                      "JOIN employee_division ed ON p.empid = ed.empid " +
                      "JOIN division d ON ed.div_id = d.id " +
                      "GROUP BY formatted_date, d.name " +
                      "ORDER BY formatted_date DESC, d.name";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                String dateStr = rs.getString("formatted_date");
                YearMonth month = YearMonth.parse(dateStr);
                String divisionName = rs.getString("division_name");
                double totalPay = rs.getDouble("total_pay");
                
                PayrollSummary summary = new PayrollSummary(month, divisionName, totalPay);
                summaryData.add(summary);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching division payroll summary: " + e.getMessage());
            e.printStackTrace();
        }
        
        return summaryData;
    }

    public ObservableList<PayrollDetail> getAllPayrollRecords() {
        ObservableList<PayrollDetail> payrollData = FXCollections.observableArrayList();
        
        String query = "SELECT * FROM payroll ORDER BY empid ASC, pay_date ASC";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                PayrollDetail payroll = new PayrollDetail(
                    rs.getInt("empid"),
                    rs.getInt("payID"),
                    rs.getDate("pay_date").toLocalDate(),
                    rs.getDouble("earnings"),
                    rs.getDouble("fed_tax"),
                    rs.getDouble("fed_med"),
                    rs.getDouble("fed_SS"),
                    rs.getDouble("state_tax"),
                    rs.getDouble("retire_401k"),
                    rs.getDouble("health_care")
                );
                payrollData.add(payroll);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching payroll records: " + e.getMessage());
            e.printStackTrace();
        }
        
        return payrollData;
    }
}


