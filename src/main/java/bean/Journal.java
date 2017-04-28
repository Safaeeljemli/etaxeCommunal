/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bean;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Entity
public class Journal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Userr user;
    private LocalDateTime dateDeModification;
    private int typeDaction;//1:delete 2:edite
    private String oldeValue;
    private String newValue;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Userr getUser() {
        return user;
    }

    public void setUser(Userr user) {
        this.user = user;
    }

    public LocalDateTime getDateDeModification() {
        return dateDeModification;
    }

    public void setDateDeModification(LocalDateTime dateDeModification) {
        this.dateDeModification = dateDeModification;
    }

    public int getTypeDaction() {
        return typeDaction;
    }

    public void setTypeDaction(int typeDaction) {
        this.typeDaction = typeDaction;
    }

    public String getOldeValue() {
        return oldeValue;
    }

    public void setOldeValue(String oldeValue) {
        this.oldeValue = oldeValue;
    }

    public String getNewValue() {
        return newValue;
    }

    public void setNewValue(String newValue) {
        this.newValue = newValue;
    }
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Journal)) {
            return false;
        }
        Journal other = (Journal) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        String serialVersionUIDString = "{}";
        if (serialVersionUID != 0) {
            serialVersionUIDString = String.valueOf(serialVersionUID);
        }
        String idString = "{}";
        if (id != null) {
            idString = id.toString();
        }
        String userString = "{}";
        if (user != null) {
            userString = user.toString();
        }
        String dateDeModificationString = "{}";
        if (dateDeModification != null) {
            dateDeModificationString = dateDeModification.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"user\":" + userString + ",\"dateDeModification\":" + dateDeModificationString + ",\"typeDaction\":" + typeDaction + ",\"oldeValue\":\"" + oldeValue + "\",\"newValue\":\"" + newValue + "\"}";
    }

    

    
}
