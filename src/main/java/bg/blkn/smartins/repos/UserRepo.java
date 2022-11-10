package bg.blkn.smartins.repos;

import bg.blkn.smartins.domain.authorization.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

//Represents DB table in Java
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    
}
