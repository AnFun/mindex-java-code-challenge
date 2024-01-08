package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public ReportingStructure reportCount(String id) {
        Employee employee = read(id);
        Set<String> uniqueIds = new HashSet<>();
        int totalReportCount = generateCount(employee, uniqueIds);

        return new ReportingStructure(employee,totalReportCount);
    }

    private int generateCount(Employee employee, Set<String> uniqueIds) {
        if (employee.getDirectReports() == null) {
            return 0;
        }
        int start = 0;
        for (String id : employee.getDirectReports()) {
            if (!uniqueIds.contains(id)) {
                uniqueIds.add(id);
                start += generateCount(read(id),uniqueIds);
            }
        }
        return start + employee.getDirectReports().size();
    }
}
