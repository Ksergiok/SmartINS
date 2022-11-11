package bg.blkn.smartins.controller;

import bg.blkn.smartins.domain.authorization.Role;
import bg.blkn.smartins.domain.authorization.User;
import bg.blkn.smartins.repos.PolicyRepo;
import java.util.Collection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import org.springframework.security.core.GrantedAuthority;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.util.Assert;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MainController.class)
public class MainControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private PolicyRepo policyRepo;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }

    @Test
    @WithMockUser

    void testGreetingPage() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("about"))
                .andExpect(content().string(containsString("Hello")));
    }
    
    @Test
    @WithMockUser
    public void testGetRole(){
        User user = new User();
        
        Collection authorities = user.getAuthorities();
        GrantedAuthority authoritiesArray =(GrantedAuthority) authorities.toArray()[0];
        
        System.out.println("TESTED ROLE: "+authoritiesArray.getAuthority());
        Assert.isTrue("ROLE_ADMIN".equals(authoritiesArray.getAuthority()),"False");
    };
}
