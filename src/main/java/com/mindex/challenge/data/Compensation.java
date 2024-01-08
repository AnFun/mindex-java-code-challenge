package com.mindex.challenge.data;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Compensation {
    /**
     * I chose to use an EmployeeId instead of an Employee because storing a copy of the Employee object in
     * compensation when you could just query the Employee db with the EmployeeId is much simpler on both
     * the client and server sides. For one, every time you updated an Employee in the db, you would have to
     * check the Compensation db for the Employee that you just updated, and if it existed you would have to
     * update that Employee as well.
      */
    private String employeeId;

    private Double salary;

    // Honestly this could just be a long for epoch time, but I felt having a wrapper around it like Date would help
    // for readability’s sake
    private Date date;

    public Compensation() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
