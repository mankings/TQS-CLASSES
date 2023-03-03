package mankings.tqs.service;

import java.util.List;

import mankings.tqs.data.Employee;

public interface EmployeeService {
    public Employee getEmployeeById(Long id);
    public Employee getEmployeeByName(String name);
    public List<Employee> getAllEmployees();
    public boolean exists(String email);
    public Employee save(Employee employee);
}
