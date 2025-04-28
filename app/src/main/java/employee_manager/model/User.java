package employee_manager.model;

public abstract class User {
    //Note: add more variables if needed for view
    private String _fName, _lName;

    //Note: Add more paramters if more objects are considered
    public User(String fName, String lName){
        this._fName = fName;
        this._lName = lName;
        
    }
    public abstract String getRole();

    public String getFName(){
        return this._fName;
    }
    public String getLName(){
        return this._lName;
    }

    //Setters and getters for Payroll data
    //TODO: Implement user class storing the classification (admin/regular emp)

    //TODO: Implement a constructor that will set private variables of users like F/Lname salaries, ect... 

    //TODO: Implement getters for those private variables for later use. 
}
