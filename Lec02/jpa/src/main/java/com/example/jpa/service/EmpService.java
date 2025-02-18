package com.example.jpa.service;
import java.util.ArrayList;
import com.example.jpa.modal.Employee;

public interface EmpService {
    ArrayList<Employee> findAllEmployee();
    Employee findAllEmployeeByID(long id);
    void addEmployee();
    void deleteAllData();
}
