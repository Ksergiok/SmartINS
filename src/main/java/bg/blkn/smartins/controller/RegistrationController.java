package bg.blkn.smartins.controller;

import bg.blkn.smartins.domain.authorization.Role;
import static bg.blkn.smartins.domain.authorization.Role.USER;
import bg.blkn.smartins.domain.authorization.User;
import bg.blkn.smartins.repos.UserRepo;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo; // -> Аннотация генерит констуктор, который принимает аркумент объект класса UserRepo?

    @GetMapping(path = "/registration")
    public String registration(Map<String, Object> model) {

        Set<Role> roles = EnumSet.allOf(Role.class); // У костыля с переменным в ENUM ноги растут отсюда
        model.put("roles", roles);
        return "registration";
    }

    @PostMapping(path = "/registration")
    public String addUser(@RequestParam Role role, User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("message", "User exists!");
            return "registration";
        }

        user.setIsActive(true);
//        user.setRoles(Collections.singleton(USER));

        Set<Role> setRole= new HashSet<>();
        setRole.add(role);
        
        user.setRoles(setRole);
        userRepo.save(user);

        return "redirect:/login";
    }
}
