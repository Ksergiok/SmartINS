package bg.blkn.smartins.repos;

import bg.blkn.smartins.domain.authorization.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, UUID> {
    User findByUsername(String username);
    
}
