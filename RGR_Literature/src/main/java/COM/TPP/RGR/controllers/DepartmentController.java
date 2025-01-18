package COM.TPP.RGR.controllers;

import COM.TPP.RGR.models.Department;
import COM.TPP.RGR.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/department")  //
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String listDepartments(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("department", departmentService.getAllDepartments());
        model.addAttribute("isAdmin", isAdmin(userDetails));
        return "department";
    }

    @GetMapping("/add")
    public String addDepartmentForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        model.addAttribute("department", new Department());
        return "add-department";
    }

    @PostMapping("/add")
    public String addDepartment(@AuthenticationPrincipal UserDetails userDetails,
                                @Valid @ModelAttribute("department") Department department,
                                BindingResult result) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            return "add-department";
        }
        departmentService.saveDepartment(department);
        return "redirect:/department";
    }


    @GetMapping("/edit/{id}")
    public String editDepartmentForm(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable("id") Integer id,
                                     Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        Department department = departmentService.findDepartmentById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));
        model.addAttribute("department", department);
        return "edit-department";
    }

    @PostMapping("/update/{id}")
    public String updateDepartment(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable("id") Integer id,
                                   @Valid @ModelAttribute("department") Department department,
                                   BindingResult result) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            return "edit-department";
        }
        department.setDepartmentId(id);
        departmentService.updateDepartment(department);
        return "redirect:/department";
    }

    @GetMapping("/delete/{id}")
    public String deleteDepartment(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable("id") Integer id) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        departmentService.deleteDepartment(id);
        return "redirect:/department";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model) {
        model.addAttribute("error", ex.getReason());
        return "error";
    }
}
