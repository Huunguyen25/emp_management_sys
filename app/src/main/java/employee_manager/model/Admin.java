package employee_manager.model;

import employee_manager.model.*;

public class Admin extends User {
    public Admin(String fName, String lName){
        super(fName, lName);
    }

    @Override
    public String getRole(){
        return "Admin";
    }
}
