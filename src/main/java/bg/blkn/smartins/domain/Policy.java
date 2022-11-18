package bg.blkn.smartins.domain;

import java.util.Date;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



@Entity
public class Policy {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private UUID id;
    
    @Temporal(TemporalType.DATE)
    private Date editedAt;

//    @OneToOne
//    @JoinColumn(name = "policyType", referencedColumnName = "type", table = "PolicyType")
    private String type;
    private String createdAt;

    public Policy() {
    }

    
    public Policy( String type, String createdAt, Date editedAt) {
        this.type = type;
        this.createdAt = createdAt;
        this.editedAt = editedAt;
    }

    public Date getEditedAt() {
        return editedAt;
    }

    public void setEditedAt(Date editedAt) {
        this.editedAt = editedAt;
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
