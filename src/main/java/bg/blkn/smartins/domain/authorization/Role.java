package bg.blkn.smartins.domain.authorization;

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
