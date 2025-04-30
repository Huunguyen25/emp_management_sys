package employee_manager.service;

import employee_manager.DAO.PayrollDAO;
import employee_manager.model.Payroll;
import javafx.collections.ObservableList;

public class PayrollService {
    private PayrollDAO payrollDAO = new PayrollDAO();
    public PayrollService(){

    }
    public ObservableList<Payroll> getPayrollsForUser(int userId) {
        return payrollDAO.getPayrollsByUserId(userId);
    }
}
