package employee_manager.service;

import employee_manager.DAO.UserDAO;
import employee_manager.model.User;
import employee_manager.util.Database;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class UserService {
    //Implementation for user business logic (rules, validation, processing)

    private UserDAO userDAO = new UserDAO();
    
    public UserService(){}

    public User authenticateRole(int empId, String email){
        return userDAO.authenticatRole(empId, email);
    }

    public ObservableList<User> getAllEmployees() {
        return userDAO.getAllUsers();
    }
    public FilteredList<User> getFilteredEmployees(ObservableList<User> employees) {
        return new FilteredList<>(employees, p -> true);
    }



    public boolean addEmployee(String firstName, String lastName, String email, LocalDate hireDate, double salary, String ssn) {
        return userDAO.addEmployee(
            firstName,
            lastName,
            email,
            hireDate,
            salary,
            ssn
        );
    }
    public boolean removeEmployee(int empid){
        if(empid != 1){
            return userDAO.removeEmployee(empid);
        } else {
            return false;
        }
    }

    public boolean applySalaryUpdate(double percentage, double minSalary, double maxSalary){
        if(percentage < 0 || minSalary < 0 || maxSalary < 0){
            return false;
        } else{     
            return userDAO.applySalaryUpdate(percentage, minSalary, maxSalary);
        }
    }
}
