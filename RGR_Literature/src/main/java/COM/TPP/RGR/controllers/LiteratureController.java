package COM.TPP.RGR.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import COM.TPP.RGR.models.Literature;
import COM.TPP.RGR.service.LiteratureService;
import COM.TPP.RGR.service.UnivgroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

@Controller
@RequestMapping("/literature")
public class LiteratureController {

    @Autowired
    private LiteratureService literatureService;

    @Autowired
    private UnivgroupService univgroupService;

    private boolean isAdmin(UserDetails userDetails) {
        return userDetails != null && userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
    }

    @GetMapping
    public String listLiteratures(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        model.addAttribute("literature", literatureService.getAllLiteratures());
        model.addAttribute("isAdmin", isAdmin(userDetails));
        return "literature";
    }

    @GetMapping("/add")
    public String addLiteratureForm(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        model.addAttribute("literature", new Literature());
        model.addAttribute("univgroup", univgroupService.getAllUnivGroups());
        return "add-literature";
    }

    @PostMapping("/add")
    public String addLiterature(@AuthenticationPrincipal UserDetails userDetails,
                                @Valid @ModelAttribute("literature") Literature literature,
                                BindingResult result, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            model.addAttribute("univgroup", univgroupService.getAllUnivGroups());
            return "add-literature";
        }
        literatureService.saveLiterature(literature);
        return "redirect:/literature";
    }

    @GetMapping("/edit/{id}")
    public String editLiteratureForm(@AuthenticationPrincipal UserDetails userDetails,
                                     @PathVariable("id") Integer id, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        Literature literature = literatureService.findLiteratureById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Literature not found"));

        model.addAttribute("literature", literature);
        model.addAttribute("univgroup", univgroupService.getAllUnivGroups());
        return "edit-literature";
    }

    @PostMapping("/update/{id}")
    public String updateLiterature(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable("id") Integer id,
                                   @Valid @ModelAttribute("literature") Literature literature,
                                   BindingResult result, Model model) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        if (result.hasErrors()) {
            model.addAttribute("univgroup", univgroupService.getAllUnivGroups());
            return "edit-literature";
        }
        literature.setId(id);
        literatureService.updateLiterature(literature);
        return "redirect:/literature";
    }

    @GetMapping("/delete/{id}")
    public String deleteLiterature(@AuthenticationPrincipal UserDetails userDetails,
                                   @PathVariable("id") Integer id) {
        if (!isAdmin(userDetails)) {
            return "access-denied";
        }
        literatureService.deleteLiterature(id);
        return "redirect:/literature";
    }

    @ExceptionHandler(ResponseStatusException.class)
    public String handleNotFound(ResponseStatusException ex, Model model) {
        model.addAttribute("error", ex.getReason());
        return "error";
    }
}
