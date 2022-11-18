package bg.blkn.smartins.controllers;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.repos.PolicyRepo;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Ksergi0k
 */
@Controller
public class SearchController {

    @Autowired
    private PolicyRepo policyRepo;

    @GetMapping("/search")
    public String getSearch(Authentication authentication, Map<String, Object> model) {
        model.put("username", authentication.getName());

        return "search/search";
    }

}
