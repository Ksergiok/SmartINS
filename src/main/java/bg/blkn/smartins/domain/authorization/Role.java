package bg.blkn.smartins.domain.authorization;

/**
 *
 * @author Ksergi0k
 */
public enum Role {
    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN"); //Выклядит костыльно

    private final String name;

    private Role(java.lang.String name) {
        this.name = name;
    }
}
