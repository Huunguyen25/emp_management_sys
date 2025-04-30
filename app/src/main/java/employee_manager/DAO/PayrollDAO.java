package employee_manager.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import employee_manager.model.Payroll;
import employee_manager.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
}
