package bg.blkn.smartins.controller;

import static bg.blkn.smartins.domain.authorization.Role.USER;
import bg.blkn.smartins.domain.authorization.User;
import bg.blkn.smartins.repos.UserRepo;
import java.util.Collections;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
   
    @Autowired
    private UserRepo userRepo;
    
    @GetMapping(path = "/registration")
    public String registration(){
        return "registration";
    }
    
    @PostMapping(path = "/registration")
    public String addUser(User user, Map<String, Object> model){
        User userFromDb = userRepo.findByUsername(user.getUsername());
        
        if(userFromDb != null){
            model.put("message", "User exists!");
            return "registration";
        }
        
        user.setIsActive(true);
        user.setRoles(Collections.singleton(USER));
        userRepo.save(user);
        
        return "redirect:/login";
    }
}
