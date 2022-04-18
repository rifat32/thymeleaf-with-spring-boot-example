package com.example.employeemanagement.controller;

import com.example.employeemanagement.entity.Employee;
import com.example.employeemanagement.repository.EmployeeRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class EmployeeController {

    private final EmployeeRepository employeeRepo;

    public EmployeeController(EmployeeRepository employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    @GetMapping({"employee-list","/"})
    public ModelAndView listEmployee() {
        ModelAndView mav = new ModelAndView("employee-list");
        List<Employee> employees = employeeRepo.findAll();
        mav.addObject("employees",employees);
        return mav;
    }
    @GetMapping({"/employee-create"})
    public ModelAndView createEmployee() {
        ModelAndView mav = new ModelAndView("employee-create");
        Employee newEmployee = new Employee();
        mav.addObject("employee",newEmployee);
        return mav;
    }
    @PostMapping({"/employee-store"})
    public String storeEmployee(@ModelAttribute  Employee employee) {
        employeeRepo.save(employee);
        return "redirect:/";
    }
    @GetMapping({"/employee-edit"})
    public ModelAndView editEmployee(@RequestParam Integer id) {
        ModelAndView mav = new ModelAndView("employee-create");
        Employee employee = employeeRepo.findById(id).get();
        mav.addObject("employee",employee);
        return mav;
    }
    @GetMapping("employee-delete")
    public String deleteEmployee(@RequestParam Integer id) {
        employeeRepo.deleteById(id);
//        scheduleFixedRateTask(id);
        return "redirect:/";
    }
    @Scheduled(fixedRate = 1000)
    public static void scheduleFixedRateTask() {
        System.out.println("Fixed rate task - "  + System.currentTimeMillis() / 1000);
    }

}
