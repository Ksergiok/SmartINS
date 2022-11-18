package bg.blkn.smartins.controllers;

import bg.blkn.smartins.domain.RandomFact;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.repository.init.ResourceReader.Type.JSON;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author Ksergi0k
 */
@Controller
public class ApiClientController {

    @Autowired
    private RestTemplate restTemplate;

    @ModelAttribute("username")
    public String putUserNameToModel(Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping(path = "/apiexample")
    public String getPokemon(Authentication authentication, Map<String, Object> model) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        RandomFact[] randomFact = objectMapper.readValue(restTemplate.getForObject("http://jservice.io/api/random?count=1", String.class), RandomFact[].class);
        model.put("randomFact", randomFact[0]);

        return "api/apiexample";
    }
}
