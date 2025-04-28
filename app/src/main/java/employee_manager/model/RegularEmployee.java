package employee_manager.model;

import employee_manager.model.*;
import javafx.collections.ObservableList;

public class RegularEmployee extends User {

    public ObservableList<Payroll> _payrollData;

    public RegularEmployee(String fName, String lName, ObservableList<Payroll> payrollData){
        super(fName, lName);
        this._payrollData = payrollData;
    }

    @Override
    public String getRole(){
        return "RegularEmployee";
    }

    public ObservableList<Payroll> get_payrollData() {
        return _payrollData;
    }

    public void set_payrollData(ObservableList<Payroll> _payrollData) {
        this._payrollData = _payrollData;
    }
}
