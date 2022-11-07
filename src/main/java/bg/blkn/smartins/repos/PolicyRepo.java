package bg.blkn.smartins.repos;

import bg.blkn.smartins.domain.Policy;
import java.util.List;
import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ksergi0k
 */
public interface PolicyRepo extends CrudRepository<Policy, UUID> {
    
    List<Policy> findByType(String type);
}
