package employee_manager.model;
import java.time.YearMonth;

public class PayrollSummary {
    private YearMonth month;
    private String jobTitle;
    private double totalPay;


    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

    public String getFormattedMonth() {
        return month.getMonth().toString() + " " + month.getYear();
    }

    public PayrollSummary(YearMonth month, String jobTitle, double totalPay) {
        this.month = month;
        this.jobTitle = jobTitle;
        this.totalPay = totalPay;
    }
}
