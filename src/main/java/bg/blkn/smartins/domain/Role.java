package bg.blkn.smartins.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author Ksergi0k
 */
public enum Role implements GrantedAuthority{
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN"); //Выклядит костыльно

    private final String name;

    private Role(java.lang.String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name;
    }
}
