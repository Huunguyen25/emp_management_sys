package employee_manager.model;

public class User {
    //Note: add more variables if needed for view
    private String _role;
    private String _fName, _lName;
    private Payroll _payrollData;

    //Note: Add more paramters if more objects are considered
    public User(String role, String fName, String lName){
        this._role = role;
        this._fName = fName;
        this._lName = lName;
    }
    public String getRole(){
        return this._role;
    }
    public String getFName(){
        return this._fName;
    }
    public String getLName(){
        return this._lName;
    }

    //Setters and getters for Payroll data
    public Payroll getPayrollData(){
        return this._payrollData;
    }
    public void setPayrollData(Payroll payrollData){
        this._payrollData = payrollData;
    }
    //TODO: Implement user class storing the classification (admin/regular emp)

    //TODO: Implement a constructor that will set private variables of users like F/Lname salaries, ect... 

    //TODO: Implement getters for those private variables for later use. 
}
