package bg.blkn.smartins.controllers;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.repos.PolicyRepo;
import bg.blkn.smartins.services.TokenService;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Ksergi0k
 */
@RestController
@RequestMapping("/api/v1")
public class RestApiController {
    
    private static final Logger LOG = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    private PolicyRepo policyRepo;

    @Autowired
    private TokenService tokenServise;
    
    @GetMapping("/searchByKey")
    public ModelAndView getResultBySearchKey() {
        Iterable<Policy> areaList = policyRepo.findAll();
        ModelAndView mv = new ModelAndView("search::search_list");
        mv.addObject("searchList", areaList);

        return mv;
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> getHealth() {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        HashMap<String, String> map = new HashMap<>();
        map.put("healt", "OK");
        map.put("time", df.format(Calendar.getInstance().getTime()));
        map.put("key", "value");
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/token")
    public String token(Authentication authentication) {
        
        LOG.debug("Token request from: '{}'", authentication.getName());
        System.out.printf("Token Request from: %s", authentication.getName());
        
        String token = tokenServise.generateToken(authentication);
        return token;

    }
    
    //For explicitly obtaining of token
    @GetMapping("/token")
    public String tokenShow(Authentication authentication) {

        LOG.debug("Token getRequest from: '{}'", authentication.getName());
        
        System.out.printf("Token getRequest from: %s", authentication.getName());
        
        String token = tokenServise.generateToken(authentication);
        return token;

    }

}
