package it.si2001.Employee.controller;

import it.si2001.Employee.model.Employee;
import it.si2001.Employee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.UriComponentsBuilder;


import java.util.List;
import java.util.Optional;

@Controller
public class AppController {


    @Autowired
    private EmployeeService employeeService;

    // == HOME == getAllEmployee()
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseEntity<List<Employee>> homeRest() {
        List<Employee> employees = (List<Employee>) employeeService.getAllEmployee();
        if (employees.isEmpty()) {
            return new ResponseEntity<List<Employee>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Employee>>(employees, HttpStatus.OK);
    }

    // == getEmployeeById(id)
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> getEmployee(@PathVariable("id") int id) {
        System.out.println("Fetching User with id " + id);
        Optional<Employee> employeeOp = employeeService.findById(id);
        Employee employee = employeeOp.get();

        if (employee == null) {
            System.out.println("User with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    // == createEmployee()
    @RequestMapping(value = "/employee/", method = RequestMethod.POST)
    public ResponseEntity<Void> createEmployee(@RequestBody Employee employee, UriComponentsBuilder ucBuilder) {
        System.out.println("Creating User " + employee.getName());

        if (employeeService.isExist(employee)) {
            System.out.println("A User with name " + employee.getName() + " already exist");
            return new ResponseEntity<Void>(HttpStatus.CONFLICT);
        }

        employeeService.saveEmployee(employee);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/employee/{id}").buildAndExpand(employee.getEmployeeId()).toUri());

        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    // == updateEmployee()
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") int id, @RequestBody Employee employee) {
        System.out.println("Updating Employee " + id);

        Optional<Employee> currentEmployeeOp = employeeService.findById(id);
        Employee currentEmployee = currentEmployeeOp.get();

        if (currentEmployee == null) {
            System.out.println("Employee with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        currentEmployee.setName(employee.getName());
        currentEmployee.setSurname(employee.getSurname());
        currentEmployee.setCountry(employee.getCountry());
        currentEmployee.setBirthDate(employee.getBirthDate());
        currentEmployee.setMaritalStatus(employee.getMaritalStatus());
        currentEmployee.setSkills(employee.getSkills());

        employeeService.saveEmployee(currentEmployee);
        return new ResponseEntity<Employee>(currentEmployee, HttpStatus.OK);
    }

    // == deleteEmployee()
    @RequestMapping(value = "/employee/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteEmployee(@PathVariable("id") int id) {
        System.out.println("Fetching & Deleting User with id " + id);

        Optional<Employee> employeeOp = employeeService.findById(id);
        Employee employee = employeeOp.get();

        if (employee == null) {
            System.out.println("Unable to delete. User with id " + id + " not found");
            return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
        }

        employeeService.deleteEmployee(employee);
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }


    // == deleteAllEmployee()
    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Employee> deleteAllUsers() {
        System.out.println("Deleting All Users");

        employeeService.deleteAllEmployee();
        return new ResponseEntity<Employee>(HttpStatus.NO_CONTENT);
    }

    
//    _____________ == SERVICE CON MODELandVIEW == __________________
    
//    @Autowired
//    private MaritalStatusService maritalStatusService;
//
//    @Autowired
//    private SkillService skillService;


//    @RequestMapping(value = {"/", "/home"}, method = RequestMethod.GET)
//    public ModelAndView home(@PathVariable (required = false) String filter) {
//        ModelAndView mav = new ModelAndView();
//        System.out.println(filter);
//
//        mav.setViewName("home");
//
//        if (isValid(filter)) {
//            System.out.println("filtro non vuoto");
//            List<Employee> list = search(filter);
//            System.out.println(list);
//            mav.addObject("employees", list);
//        } else {
//            // TODO filter null
//            System.out.println("filtro vuoto");
//            mav.addObject("employees", employeeService.getAllEmployee());
//        }
//        return mav;
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.GET)
//    public ModelAndView createGet() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("create");
//        mav.addObject("employee", new Employee());
//        mav.addObject("maritalStatus", maritalStatusService.getAllMaritalStatus());
//        mav.addObject("skills", skillService.getAllSkills());
//        return mav;
//    }
//
//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    public ModelAndView createPost(@Valid Employee employee, BindingResult bindingResult) {
//        ModelAndView mav = new ModelAndView();
//
//        //TODO valid field
//
//        employeeService.saveEmployee(employee);
//
//        if (!bindingResult.hasErrors()) {
//            return mav = home(null);
//        } else {
//            return mav = createGet();
//        }
//    }
//
//    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
//    public ModelAndView updateGet(@PathVariable Integer id) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("update");
//        if (employeeService.findById(id) != null) {
//            System.out.println("|= nul");
//            mav.addObject("employee", employeeService.findById(id));
//            mav.addObject("maritalStatus", maritalStatusService.getAllMaritalStatus());
//            mav.addObject("skills", skillService.getAllSkills());
//            return mav;
//        } else {
//            System.out.println(" == null");
//            return mav = home(null);
//        }
//    }
//
//    @RequestMapping(value = "/update", method = RequestMethod.POST)
//    public ModelAndView updatePost(@Valid Employee employee, BindingResult bindingResult) {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("update");
//
//        employeeService.saveEmployee(employee);
//
//        //TODO valid field
//
//        if (!bindingResult.hasErrors()) {
//            System.out.println("no error");
////            return new ModelAndView("redirect:/");
//            return mav = home(null);
//        } else {
//            System.out.println("error");
//            return mav = updateGet(employee.getEmployeeId());
//        }
//    }
//
//    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//    public ModelAndView deleteGet(@PathVariable Integer id) {
//        ModelAndView mav = new ModelAndView();
//        if (employeeService.findById(id) != null) {
//            System.out.println("|= nul");
//            Optional<Employee> e = employeeService.findById(id);
//            employeeService.deleteEmployee(e.get());
//            return mav = home(null);
//        } else {
//            System.out.println(" == null");
//            return mav = home(null);
//        }
//    }
//
//    public List<Employee> search(String search) {
//        List<Employee> filterList = employeeService.search(search);
//        return filterList;
//    }

}
