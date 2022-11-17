package bg.blkn.smartins.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

//    private final RsaKeyProperties rsaKeys;
//    public WebSecurityConfig(bg.blkn.smartins.config.RsaKeyProperties rsaKeys) {
//        this.rsaKeys = rsaKeys;
//    }
    
    
    

    @Autowired
    UserDetailsService smartInsUserDetailsService;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService(smartInsUserDetailsService).passwordEncoder(
                NoOpPasswordEncoder.getInstance());
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests((requests) -> requests
                    .antMatchers("/css/**").permitAll()
                    .antMatchers("/api/v1/login").permitAll()
   //                 .antMatchers("/registration").hasRole("ADMIN") //Replaced with @PreAuthorize annotation on methods
                    .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                    .loginPage("/login").permitAll()
                )
//                .httpBasic(Customizer.withDefaults())
//                .userDetailsService(smartInsUserDetailsService)
//                  .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .logout(LogoutConfigurer::permitAll);

        return http.build();
    }
    
//    @Bean
//    JwtDecoder jwtDecoder(){
//        return NimbusJwtDecoder.withPublicKey(rsaKeys.publicKey()).build();
//    }
}
