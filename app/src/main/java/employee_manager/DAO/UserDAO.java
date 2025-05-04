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
            System.out.println("Error fetching users: " + e.getMessage());
        }

        return users;
    }

    public int getNextEmpId() throws SQLException {
        String stmt = "SELECT MAX(empid) FROM employees";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(stmt);
             ResultSet rs = ps.executeQuery()) {
    
            if (rs.next()) {
                int maxId = rs.getInt(1);
                return maxId + 1;
            } else {
                return 1;
            }
        }
    }

    public boolean addEmployee(String firstName, String lastName, String email, LocalDate hireDate, double salary, String ssn){
        String stmt = "INSERT INTO employees (empid, Fname, Lname, email, HireDate, salary, ssn) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(stmt)) {
                int empId = getNextEmpId();
                if (empId != 1) {
                    ps.setInt(1, empId);
                } else {
                    return false;
                }
                ps.setString(2, firstName);
                ps.setString(3, lastName);
                ps.setString(4, email);
                ps.setDate(5, java.sql.Date.valueOf(hireDate));
                ps.setDouble(6, salary);
                ps.setString(7, ssn);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
            return false;
        }
    }
    public boolean removeEmployee(int empid){
        String stmt = "DELETE FROM employees WHERE empid = ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(stmt)) {
            ps.setInt(1, empid);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error removing employee: " + e.getMessage());
            return false;
        }
    }

    public boolean applySalaryUpdate(double percentage, double minSalary, double maxSalary) {
        String stmt = "UPDATE employees SET salary = salary * ? WHERE salary BETWEEN ? AND ?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(stmt)) {
                ps.setDouble(1, percentage);
                ps.setDouble(2, minSalary);
                ps.setDouble(3, maxSalary);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error updating salaries: " + e.getMessage());
            return false;
        }
    }
}
