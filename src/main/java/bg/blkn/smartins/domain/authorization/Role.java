package bg.blkn.smartins.domain.authorization;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Ksergi0k
 */
public enum Role {
    USER("USER"), ADMIN("ADMIN"); //Выклядит костыльно

    private final String name;
    
    private Role(java.lang.String name) {
        this.name = name;
    }            
}
