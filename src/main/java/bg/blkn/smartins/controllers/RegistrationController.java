package bg.blkn.smartins.controllers;

import bg.blkn.smartins.domain.Role;
import bg.blkn.smartins.domain.User;
import bg.blkn.smartins.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @ModelAttribute("username")
    public String putUserNameToModel(Authentication authentication){
        return authentication.getName();
    }
    
    @GetMapping(path = "/registration")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String registration(Authentication authentication, Map<String, Object> model) {

        Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
        model.put("roles", roles);
        Iterable<User> userList = userRepo.findAll();
        model.put("userList", userList);
        return "registration/registration";
    }

    @PostMapping(path = "/registration")
    public String addUser(Authentication authentication, @RequestParam Role role, User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
            model.put("roles", roles);
            Iterable<User> userList = userRepo.findAll();
            model.put("userList", userList);

            return "registration/registration";
        }

        user.setIsActive(true);

        Set<Role> setRole = new HashSet<>();
        setRole.add(role);

        user.setRoles(setRole);
        userRepo.save(user);

        Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
        model.put("roles", roles);
        
        model.put("message", "User added!");

        Iterable<User> userList = userRepo.findAll();
        model.put("userList", userList);
        return "registration/registration";

    }

    @DeleteMapping(path = "/deleteUsetById")
    public String deleteUsetByName(Authentication authentication, @RequestParam String id, Map<String, Object> model) {
        userRepo.deleteById(UUID.fromString(id));
        return "redirect:/registration";
    }
}
