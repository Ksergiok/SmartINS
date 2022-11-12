package bg.blkn.smartins.controller;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.repos.PolicyRepo;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PolicyRepo policyRepo;


    @GetMapping(path = "")
    public String greeting(Authentication authentication, Map<String, Object> model) {
        model.put("username", authentication.getName());
        return "about";
    }
    @GetMapping(path = "/about")
    public String about(Authentication authentication, Map<String, Object> model) {
        model.put("username", authentication.getName());
        return "about";
    }
    

    @GetMapping(path = "/main")
    public String main(Authentication authentication, Map<String, Object> model) {
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        model.put("username", authentication.getName());
        return "main";
    }

    //Action add policy
    @PostMapping(path = "/addPolicy")
    public String addPolicy(
            @RequestParam String type,
            @RequestParam String createdAt,
            Map<String, Object> model) {
        Policy policy = new Policy(type, createdAt);
        policyRepo.save(policy);
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "main";
    }

    @PostMapping("/deleteAll")
    public String deleteAll(Map<String, Object> model) {
        policyRepo.deleteAll();
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "main";
    }

    @PostMapping("/deletePolicyById")
    public String deletePolicyById(String id, Map<String, Object> model) {
        policyRepo.deleteById(UUID.fromString(id));
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "main";
    }

    @PostMapping("/filter")
    public String filter(@RequestParam String type, Map<String, Object> model) {
        Iterable<Policy> policies;
        if (type == null || type.isBlank()) {
            policies = policyRepo.findAll();
        } else {
            policies = policyRepo.findByType(type);
        }
        model.put("policies", policies);
        return "main";
    }

}
