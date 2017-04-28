/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author safa
 */
@Entity
public class TauxTaxeBoisson implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double taux;
    @OneToOne
    private Activite activite;
  

    public TauxTaxeBoisson() {
    }

    public TauxTaxeBoisson(Double taux, Activite activite) {
        this.taux = taux;
        this.activite = activite;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTaux() {
        return taux;
    }

    public void setTaux(Double taux) {
        this.taux = taux;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
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
        if (!(object instanceof TauxTaxeBoisson)) {
            return false;
        }
        TauxTaxeBoisson other = (TauxTaxeBoisson) object;
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
        String activiteString = "{}";
        if (activite != null) {
            activiteString = activite.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"taux\":" + taux + ",\"activite\":" + activiteString + "}";
    }

   
    
}
