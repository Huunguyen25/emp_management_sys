package employee_manager.controller;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import java.time.LocalDate;

public class Filters {
    static boolean fieldIsBlank(TextField field) {
        String text = field.getText();
        return (text == null || text.isBlank());
    }
    
    static boolean applyTextFilter(TextField field, String property) {
        String fieldText = field.getText().trim();
        return property.equalsIgnoreCase(fieldText);
    }

    static boolean applyNameFilter(TextField field, String fName, String lName) {
        String fieldText = (field.getText().trim()).toLowerCase();
        String property = (fName+" "+lName).toLowerCase();
        return property.contains(fieldText);
    }
    
    static boolean applySalaryFilter(TextField field, Double salary, boolean isMax) {
        try {
            Double value = Double.parseDouble(field.getText());
            return isMax? (salary <= value) : (salary >= value);
        } catch (Exception e) {
            return false;
        }
    }

    static boolean applyEmpIDFilter(TextField field, int empID) {
        try {
            int value = Integer.parseInt(field.getText());
            return (value == empID);
        } catch (Exception e) {
            return false;
        }
    }
    
    /* TODO: I think there needs to be a way to turn on and off date filters in the gui, otherwise these filters will always be active */
    static boolean applyDateFilter(DatePicker comboBox, LocalDate hireDate, boolean isAfter) {
        LocalDate date = comboBox.getValue();
        if (date == null) return false; // this might not do anything. test this
        return isAfter? (date.isAfter(hireDate)) : (date.isBefore(hireDate));
    }
    static boolean applyDateFilter_isAfter(DatePicker comboBox, LocalDate hireDate) {
        return applyDateFilter(comboBox, hireDate, true);
    }
    static boolean applyDateFilter_isBefore(DatePicker comboBox, LocalDate hireDate) {
        return applyDateFilter(comboBox, hireDate, false);
    }
}
