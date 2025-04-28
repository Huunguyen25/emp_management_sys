package employee_manager.model;

import java.sql.*;
import employee_manager.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Model {
    //Function will return a user object that contains the email, first and last name
    public static User authenticateRole(int empid, String email){
        String query = "SELECT e.email, e.Fname, e.Lname FROM employees e WHERE e.empid = ?";

        try(Connection myConn = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD)){
            PreparedStatement myStmt = myConn.prepareStatement(query);
            myStmt.setInt(1, empid);
            ResultSet myRS = myStmt.executeQuery();
            if (myRS.next() && myRS.getString("email").toLowerCase().equals(email.toLowerCase())){
                String role = (empid==1)? "admin": "regularEmployee";
                String fName = myRS.getString("Fname");
                String lName = myRS.getString("Lname");
                if (role.equalsIgnoreCase("admin")){
                    return new Admin(fName, lName);
                } else {
                    ObservableList<Payroll> payrollData = fetchPayrollData(empid);
                    return new RegularEmployee(fName, lName, payrollData);
                }
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        System.out.println("Invalid Credentials");
        return null;
    }

    //TODO: Fetch payroll data here with param int empid
    public static ObservableList<Payroll> fetchPayrollData(int empid){
        ObservableList<Payroll> payrollData = FXCollections.observableArrayList();
        String query = "Select * FROM payroll p where p.empid = ?";
        try(Connection myConn = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD)){
            PreparedStatement myStmt = myConn.prepareStatement(query);
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

    public static ObservableList<Employee> fetchEmployee(){
        ObservableList<Employee> employees = FXCollections.observableArrayList();
        String query = "SELECT empid, Fname, Lname, email, HireDate, salary, ssn FROM employees";
        try (Connection myConn = DriverManager.getConnection(Config.DB_URL, Config.DB_USERNAME, Config.DB_PASSWORD)){
            PreparedStatement myStmt = myConn.prepareStatement(query);
            ResultSet myRS = myStmt.executeQuery();
            while(myRS.next()){
                Employee employee = new Employee(
                    myRS.getInt("empid"),
                    myRS.getString("Fname"),
                    myRS.getString("Lname"),
                    myRS.getString("email"),
                    myRS.getDate("HireDate").toLocalDate(),
                    myRS.getDouble("salary"),
                    myRS.getString("ssn")
                );  
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching employees: " + e.getMessage());
        }
        return employees;
    }
}
