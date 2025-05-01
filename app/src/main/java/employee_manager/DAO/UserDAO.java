package employee_manager.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import employee_manager.model.User;
import employee_manager.util.Database;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserDAO {

    public UserDAO(){
    }

    /**
     * call UserDAO that will return a user object
     * @param empid
     * @param email
     * @return a user object 
     */
    public User authenticatRole(int empid, String email){
        User user = null;
        String query = "SELECT * FROM employees WHERE empid = ? AND email = ?";
        try {
            Connection conn = Database.getConnection();
            PreparedStatement myStmt = conn.prepareStatement(query);
            myStmt.setInt(1, empid);
            myStmt.setString(2, email);
            ResultSet rs = myStmt.executeQuery();

            if (rs.next() && rs.getString("email").toLowerCase().equals(email.toLowerCase())){
                String role = (empid==1)? "admin": "regularEmployee";
                int empId = rs.getInt("empid");
                String fName = rs.getString("Fname");
                String lName = rs.getString("Lname");
                String userEmail = rs.getString("email");
                LocalDate hireDate = rs.getDate("HireDate").toLocalDate();
                double salary = rs.getDouble("salary");
                String ssn = rs.getString("ssn");

                user = new User(empId, role, fName, lName, userEmail, hireDate, salary, ssn);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public ObservableList<User> getAllUsers() {
        ObservableList<User> users = FXCollections.observableArrayList();
        String query = "SELECT empid, Fname, Lname, email, HireDate, salary, ssn FROM employees";
        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int empId = rs.getInt("empid");
                String role = (empId == 1) ? "admin" : "employee";
                String Fname = rs.getString("Fname");
                String Lname = rs.getString("Lname");
                String email = rs.getString("email");
                java.time.LocalDate HireData = rs.getDate("HireDate").toLocalDate();
                double salary = rs.getDouble("salary");
                String ssn = rs.getString("ssn");

                User user = new User(empId, role, Fname, Lname, email, HireData, salary, ssn);
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }
}
