package bg.blkn.smartins.controllers;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.domain.PolicyType;
import bg.blkn.smartins.domain.Role;
import bg.blkn.smartins.services.SmartInsUserDetailsService;
import bg.blkn.smartins.repos.PolicyRepo;
import bg.blkn.smartins.repos.PolicyTypeRepo;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PolicyRepo policyRepo;

    @Autowired
    private PolicyTypeRepo policyTypeRepo;

    @Autowired
    private SmartInsUserDetailsService smartInsUserDetailsService;

    @ModelAttribute("username")
    public String putUserNameToModel(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping(path = "")
    public String greeting(Authentication authentication, Map<String, Object> model) {
        return "main/about";
    }

    @GetMapping(path = "/about")
    public String about(Authentication authentication, Map<String, Object> model) {

        UserDetails details = smartInsUserDetailsService.loadUserByUsername(authentication.getName());

        if (authentication.getAuthorities().stream() //Надо заменить на UserDetailsService
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            model.put("adminPageNavLink", "/registration");
            model.put("adminPageNavName", "Administration");
        }
        return "main/about";
    }

    @GetMapping(path = "/policy/{id}")
    public String getPolicyById(Authentication authentication, @PathVariable("id") String id, Map<String, Object> model) {
        Policy policy = policyRepo.findById(UUID.fromString(id)).get();
        model.put("policy", policy);
        return "main/policy";

    }

    @GetMapping(path = "/policies")
    public String main(Authentication authentication, Map<String, Object> model) {
        Iterable<Policy> policies = policyRepo.findAll();
        Iterable<PolicyType> policyTypes = policyTypeRepo.findAll();
        model.put("policies", policies);
        model.put("policyTypes", policyTypes);

        return "main/policies";
    }

    //Action add policy
    @PostMapping(path = "/addPolicy")
    public String addPolicy(Authentication authentication,
            @RequestParam String type,
            @RequestParam String createdAt,
            @RequestParam String editedAt,
            Map<String, Object> model) {
        try {
            Date editedAtDate;
            if (editedAt.equals("")) {
                editedAtDate = null;
            } else {
                DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                editedAtDate = format.parse(editedAt);
            }
            Policy policy = new Policy(type, createdAt, editedAtDate);
            policyRepo.save(policy);
            Iterable<Policy> policies = policyRepo.findAll();
            model.put("policies", policies);
            return "redirect:/policies";
        } catch (ParseException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "redirect:policies";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(Authentication authentication, Map<String, Object> model) {
        policyRepo.deleteAll();
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "redirect:/policies";
    }

    @DeleteMapping("/deletePolicyById")
    public String deletePolicyById(Authentication authentication, @RequestParam String id, Map<String, Object> model) {
        policyRepo.deleteById(UUID.fromString(id));
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "redirect:/policies";
    }

    @PostMapping("/filter")
    public String filter(Authentication authentication, @RequestParam String type, Map<String, Object> model) {
        Iterable<Policy> policies;
        Iterable<PolicyType> policyTypes = policyTypeRepo.findAll();
        
        if (type == null || type.isBlank()) {
            policies = policyRepo.findAll();
        } else {
            policies = policyRepo.findByType(type);
        }
        
        model.put("policies", policies);
        model.put("policyTypes", policyTypes);
        
        return "main/policies";
    }

}
