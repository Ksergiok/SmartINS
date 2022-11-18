package bg.blkn.smartins.repos;

import bg.blkn.smartins.domain.Policy;
import bg.blkn.smartins.domain.PolicyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author Ksergi0k
 */
public interface PolicyTypeRepo extends CrudRepository<PolicyType, Integer>{
    
}
