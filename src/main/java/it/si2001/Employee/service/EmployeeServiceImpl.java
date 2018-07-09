package it.si2001.Employee.service;

import it.si2001.Employee.model.Employee;
import it.si2001.Employee.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService {

    @Qualifier("employeeRepository")
    @Autowired
    private EmployeeRepository employeeRepository;


    @Override
    public void saveEmployee(Employee employee) {

       this.employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployee() {
        return this.employeeRepository.findAll();
    }

//    @Override
//    public Employee findEmployee(int id) {
//
//        return employeeRepository.findEmployee(id);
//    }

    @Override
    public void deleteEmployee(Employee employee)
    {

        this.employeeRepository.delete(employee);
    }

    @Override
    public Optional<Employee> findById(Integer id) {
        return employeeRepository.findById(id);
    }

    @Override
    public List<Employee> search(String searchParam) {
        List<Employee> list = new ArrayList<Employee>();

        for(Employee e : getAllEmployee()){
            Boolean cond = e.getName().contains(searchParam) || e.getSurname().contains(searchParam) || e.getCountry().contains(searchParam);
            if(cond){
                list.add(e);
            }
        }
        return list;
    }

    @Override
    public boolean isExist(Employee employee) {
        return this.employeeRepository.existsById(employee.getEmployeeId());
    }

//    @Override
//    public void updateEmployee(Employee currentEmployee) {
//        this.employeeRepository.updateEmployee(currentEmployee);
//    }

    @Override
    public void deleteAllEmployee() {
        this.employeeRepository.deleteAll();
    }


}
