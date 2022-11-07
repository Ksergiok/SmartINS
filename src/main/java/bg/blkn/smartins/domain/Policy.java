package bg.blkn.smartins.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    
    private String type;
    private String createdAt;

    public Policy() {
    }

    
    public Policy( String type, String createdAt) {
        this.type = type;
        this.createdAt = createdAt;
    }


    public UUID getId() {
        return id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
