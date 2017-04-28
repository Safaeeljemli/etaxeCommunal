/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author safa
 */
@Entity
public class Rue implements Serializable {

    

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    private Quartier quartier;
    @OneToMany(mappedBy = "rue")
    private List<Locale> locales;

    public Rue() {
    }

    public Rue(String name, Quartier quartier, List<Locale> locals) {
        this.name = name;
        this.quartier = quartier;
        this.locales = locals;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public void setLocales(List<Locale> locals) {
        this.locales = locals;
    }
    
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(object instanceof Rue)) {
            return false;
        }
        Rue other = (Rue) object;
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
        String quartierString = "{}";
        if (quartier != null) {
            quartierString = quartier.toString();
        }
        
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"name\":\"" + name + "\",\"quartier\":" + quartierString + ",\"locales\":" + null + "}";
    }

    
    
}
