package employee_manager.util;
import employee_manager.model.*;

public class Session {
    //TODO: Implement a private User currentUser object
    private static User _currentUser;

    public static void setUser(User user){
        _currentUser = user;
    }

    public static User getUser(){
        return _currentUser;
    }

    public static void removeCurrentUser(){
        if (_currentUser != null) Session._currentUser = null;
    }
}
