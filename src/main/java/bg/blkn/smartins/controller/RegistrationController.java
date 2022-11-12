package bg.blkn.smartins.controller;

import bg.blkn.smartins.domain.authorization.Role;
import bg.blkn.smartins.domain.authorization.User;
import bg.blkn.smartins.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo; // -> Аннотация генерит констуктор, который принимает аркумент объект класса UserRepo?

    @GetMapping(path = "/registration")
    public String registration(Authentication authentication, Map<String, Object> model) {

        Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
        model.put("roles", roles);
        Iterable<User> userList = userRepo.findAll();
        model.put("userList", userList);
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String addUser(@RequestParam Role role, User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
            model.put("roles", roles);
            Iterable<User> userList = userRepo.findAll();
            model.put("userList", userList);

            return "registration";
        }

        user.setIsActive(true);
//        user.setRoles(Collections.singleton(USER));

        Set<Role> setRole = new HashSet<>();
        setRole.add(role);

        user.setRoles(setRole);
        userRepo.save(user);

        Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
        model.put("roles", roles);

        Iterable<User> userList = userRepo.findAll();
        model.put("userList", userList);

        return "registration";

    }

    @PostMapping(path = "/deleteUsetById")
    public String deleteUsetByName(@RequestParam String id, Map<String, Object> model) {
        userRepo.deleteById(UUID.fromString(id));

        Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
        model.put("roles", roles);
        Iterable<User> userList = userRepo.findAll();
        model.put("userList", userList);
        return "registration";
    }
}
