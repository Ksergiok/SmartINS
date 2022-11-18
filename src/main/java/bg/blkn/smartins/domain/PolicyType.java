package bg.blkn.smartins.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Ksergi0k
 */
@Entity
public class PolicyType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    int id;
           
//    @OneToOne(mappedBy = "type")
    private String type;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
