package employee_manager.model;

public class User {
    public int empId;
    public String role;
    public String fName;
    public String lName;
    public String userEmail;
    public java.time.LocalDate hireDate;
    public double salary;
    public String ssn;

    public boolean isAdmin;

    public User(int empId, String role, String fName, String lName, String userEmail, java.time.LocalDate hireDate, double salary, String ssn) {
        this.empId = empId;
        this.role = role;
        this.fName = fName;
        this.lName = lName;
        this.userEmail = userEmail;
        this.hireDate = hireDate;
        this.salary = salary;
        this.ssn = ssn;
    }
    
    public boolean isAdmin(){
        return role.equalsIgnoreCase("admin") ? true : false;
    }
    
    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getFname() {
        return fName;
    }

    public void setFname(String fName) {
        this.fName = fName;
    }

    public String getLname() {
        return lName;
    }

    public void getLname(String lName) {
        this.lName = lName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public java.time.LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(java.time.LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }
}
