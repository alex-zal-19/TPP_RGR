package COM.TPP.RGR.controllers;

import COM.TPP.RGR.models.Univgroup;
import COM.TPP.RGR.service.UnivgroupService;
import COM.TPP.RGR.service.DepartmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/univgroup")
public class UnivgroupController {

    @Autowired
    private UnivgroupService univgroupService;

    @Autowired
    private DepartmentService departmentService;

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String listUnivgroups(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("univgroup", univgroupService.getAllUnivGroups());
        model.addAttribute("isAdmin", isAdmin(userDetails));
        return "univgroup";
    }

    @GetMapping("/add")
    public String addUnivgroupForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        model.addAttribute("univgroup", new Univgroup());
        model.addAttribute("department", departmentService.getAllDepartments());
        return "add-univgroup";
    }

    @PostMapping("/add")
    public String addUnivgroup(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @ModelAttribute("univgroup") Univgroup univgroup,
                               BindingResult result, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            model.addAttribute("department", departmentService.getAllDepartments());
            return "add-univgroup";
        }
        univgroupService.saveUnivGroup(univgroup);
        return "redirect:/univgroup";
    }

    @GetMapping("/edit/{id}")
    public String editUnivgroupForm(@AuthenticationPrincipal UserDetails userDetails,
                                    @PathVariable("id") Integer id,
                                    Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        Univgroup univgroup = univgroupService.findUnivGroupById(id).orElse(null);
        if (univgroup != null) {
            model.addAttribute("univgroup", univgroup);
            model.addAttribute("department", departmentService.getAllDepartments());
            return "edit-univgroup";
        } else {
            return "redirect:/univgroup";
        }
    }

    @PostMapping("/update/{id}")
    public String updateUnivgroup(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable("id") Integer id,
                                  @Valid @ModelAttribute("univgroup") Univgroup univgroup,
                                  BindingResult result, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            model.addAttribute("department", departmentService.getAllDepartments());
            return "edit-univgroup";
        }
        univgroup.setId(id);
        univgroupService.updateUnivGroup(univgroup);
        return "redirect:/univgroup";
    }

    @GetMapping("/delete/{id}")
    public String deleteUnivgroup(@AuthenticationPrincipal UserDetails userDetails,
                                  @PathVariable("id") Integer id) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        univgroupService.deleteUnivGroup(id);
        return "redirect:/univgroup";
    }

}
