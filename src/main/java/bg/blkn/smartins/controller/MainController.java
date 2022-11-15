package bg.blkn.smartins.controller;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.repos.PolicyRepo;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PolicyRepo policyRepo;

    @GetMapping(path = "")
    public String greeting(Authentication authentication, Map<String, Object> model) {
        model.put("username", authentication.getName());
        return "main/about";
    }

    @GetMapping(path = "/about")
    public String about(Authentication authentication, Map<String, Object> model) {
        model.put("username", authentication.getName());
        return "main/about";
    }

    @GetMapping(path = "/policy/{id}")
    public String getPolicyById(Authentication authentication, @PathVariable("id") String id, Map<String, Object> model) {
        Policy policy  = policyRepo.findById(UUID.fromString(id)).get();
        model.put("id", policy.getId());
        model.put("type", policy.getType());
        model.put("createdAt", policy.getCreatedAt());
        model.put("username", authentication.getName());
        return "main/policy";

    }

    @GetMapping(path = "/policies")
    public String main(Authentication authentication, Map<String, Object> model) {
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        model.put("username", authentication.getName());
        return "main/policies";
    }

    //Action add policy
    @PostMapping(path = "/addPolicy")
    public String addPolicy(Authentication authentication,
            @RequestParam String type,
            @RequestParam String createdAt,
            Map<String, Object> model) {
        Policy policy = new Policy(type, createdAt);
        policyRepo.save(policy);
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        model.put("username", authentication.getName());
        return "redirect:/policies";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll(Authentication authentication, Map<String, Object> model) {
        policyRepo.deleteAll();
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        model.put("username", authentication.getName());
        return "redirect:/policies";
    }

    @DeleteMapping("/deletePolicyById")
    public String deletePolicyById(Authentication authentication,@RequestParam String id, Map<String, Object> model) {
        policyRepo.deleteById(UUID.fromString(id));
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        model.put("username", authentication.getName());
        return "redirect:/policies";
    }

    @PostMapping("/filter")
    public String filter(Authentication authentication, @RequestParam String type, Map<String, Object> model) {
        Iterable<Policy> policies;
        if (type == null || type.isBlank()) {
            policies = policyRepo.findAll();
        } else {
            policies = policyRepo.findByType(type);
        }
        model.put("policies", policies);
        model.put("username", authentication.getName());
        return "main/policies";
    }

}
