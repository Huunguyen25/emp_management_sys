package employee_manager.service;

import employee_manager.DAO.PayrollDAO;
import employee_manager.model.Payroll;
import employee_manager.model.PayrollDetail;
import employee_manager.model.PayrollSummary;
import javafx.collections.ObservableList;

public class PayrollService {
    private PayrollDAO payrollDAO = new PayrollDAO();
    public PayrollService(){

    }
    public ObservableList<Payroll> getPayrollsForUser(int userId) {
        return payrollDAO.getPayrollsByUserId(userId);
    }

    public ObservableList<PayrollSummary> getPayrollSummaryByJobTitle(){
        return payrollDAO.getPayrollSummaryByJobTitle();
    }

    public ObservableList<PayrollSummary> getPayrollSummaryByDivision(){
        return payrollDAO.getPayrollSummaryByDivision();
    }

    public ObservableList<PayrollDetail> getAllPayrollRecords() {
        return payrollDAO.getAllPayrollRecords();
    }
}
