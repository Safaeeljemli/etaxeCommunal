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
public class History implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Userr user;
    private int type;//1:login  2:logout
    private LocalDateTime inOutTimeStamp;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public LocalDateTime getInOutTimeStamp() {
        return inOutTimeStamp;
    }

    public void setInOutTimeStamp(LocalDateTime inOutTimeStamp) {
        this.inOutTimeStamp = inOutTimeStamp;
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
        if (!(object instanceof History)) {
            return false;
        }
        History other = (History) object;
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
        String inOutTimeStampString = "{}";
        if (inOutTimeStamp != null) {
            inOutTimeStampString = inOutTimeStamp.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"user\":" + userString + ",\"type\":" + type + ",\"inOutTimeStamp\":" + inOutTimeStampString + "}";
    }

    

    

    

}
