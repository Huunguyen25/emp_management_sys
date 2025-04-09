package employee_manager.model;

import java.sql.*;
import employee_manager.util.*;

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
                return new User(role, myRS.getString("Fname"), myRS.getString("Lname"));
            }
        } catch (Exception e) {
            System.out.println("Database error: " + e.getMessage());
        }
        System.out.println("Invalid Credentials");
        return null;
    }

    //TODO: Fetch payroll data here with param int empid
    public static void setFetchedPayrollData(int empid){
        throw new UnsupportedOperationException("Not implemented yet");
    } 
}
