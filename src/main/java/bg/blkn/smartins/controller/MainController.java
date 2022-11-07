package bg.blkn.smartins.controller;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.repos.PolicyRepo;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    private PolicyRepo policyRepo;

    @GetMapping(path ="/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }
    
    @GetMapping(path ="/main")
    public String main(Map<String, Object> model) {
        return "main";
    }

    @PostMapping(path ="/")
    public String add(
            @RequestParam String type,
            @RequestParam String createdAt,
            Map<String, Object> model) {
        Policy policy = new Policy(type, createdAt);
        policyRepo.save(policy);
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "main";
    }

    @PostMapping("/delete")
    public String delete(Map<String, Object> model) {
        policyRepo.deleteAll();
        Iterable<Policy> policies = policyRepo.findAll();
        model.put("policies", policies);
        return "main";
    }

    @PostMapping("/deletePolicy")
    public String deletePolicy(String id, Map<String, Object> model) {
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
