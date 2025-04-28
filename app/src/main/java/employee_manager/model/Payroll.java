package employee_manager.model;

import java.time.LocalDate;

public class Payroll {
    private int payID;
    private LocalDate pay_date;
    private double earnings;
    private double fed_tax;
    private double fed_med;
    private double fed_SS;
    private double state_tax;
    private double retire_401k;
    private double health_care;

    public Payroll(int payID, LocalDate pay_date, double earnings, double fed_tax, double fed_med, double fed_SS, double state_tax, double retire_401k, double health_care) {
        this.payID = payID;
        this.pay_date = pay_date;
        this.earnings = earnings;
        this.fed_tax = fed_tax;
        this.fed_med = fed_med;
        this.fed_SS = fed_SS;
        this.state_tax = state_tax;
        this.retire_401k = retire_401k;
        this.health_care = health_care;
    }

    public int getPayID() {
        return payID;
    }

    public void setPayID(int payID) {
        this.payID = payID;
    }

    public LocalDate getPay_date() {
        return pay_date;
    }

    public void setPay_date(LocalDate pay_date) {
        this.pay_date = pay_date;
    }

    public double getEarnings() {
        return earnings;
    }

    public void setEarnings(double earnings) {
        this.earnings = earnings;
    }

    public double getFed_tax() {
        return fed_tax;
    }

    public void setFed_tax(double fed_tax) {
        this.fed_tax = fed_tax;
    }

    public double getFed_med() {
        return fed_med;
    }

    public void setFed_med(double fed_med) {
        this.fed_med = fed_med;
    }

    public double getFed_SS() {
        return fed_SS;
    }

    public void setFed_SS(double fed_SS) {
        this.fed_SS = fed_SS;
    }

    public double getState_tax() {
        return state_tax;
    }

    public void setState_tax(double state_tax) {
        this.state_tax = state_tax;
    }

    public double getRetire_401k() {
        return retire_401k;
    }

    public void setRetire_401k(double retire_401k) {
        this.retire_401k = retire_401k;
    }

    public double getHealth_care() {
        return health_care;
    }

    public void setHealth_care(double health_care) {
        this.health_care = health_care;
    }
}
