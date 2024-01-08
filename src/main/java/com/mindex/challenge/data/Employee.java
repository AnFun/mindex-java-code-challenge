package com.mindex.challenge.data;

import java.util.List;
import java.util.stream.Collectors;

public class Employee {
    private String employeeId;
    private String firstName;
    private String lastName;
    private String position;
    private String department;

    // Converted this to a list of strings instead of employees using the EmployeeId
    private List<String> directReports;

    public Employee() {
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public List<String> getDirectReports() {
        return directReports;
    }

    // Use EmployeeId object to get EmployeeId value from database instead of an entire Employee
    // Then convert list of EmployeeIds to list of Strings
    public void setDirectReports(List<EmployeeId> directReports) {
        this.directReports = directReports.stream().map(EmployeeId::getEmployeeId).collect(Collectors.toList());
    }
}
