package employee_manager.service;

import employee_manager.DAO.UserDAO;
import employee_manager.model.User;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

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
}
