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
public class TauxRetardBoisonTrim implements Serializable {

   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double tauxRetardPremierMois;
    private Double tauxRetardAutreMois;
    @OneToOne
    private TauxTaxeBoisson tauxBoissonTaxe;

    public Double getTauxRetardPremierMois() {
        return tauxRetardPremierMois;
    }

    public void setTauxRetardPremierMois(Double tauxRetardPremierMois) {
        this.tauxRetardPremierMois = tauxRetardPremierMois;
    }

    public Double getTauxRetardAutreMois() {
        return tauxRetardAutreMois;
    }

    public void setTauxRetardAutreMois(Double tauxRetardAutreMois) {
        this.tauxRetardAutreMois = tauxRetardAutreMois;
    }

    public TauxTaxeBoisson getTauxBoissonTaxe() {
        return tauxBoissonTaxe;
    }

    public void setTauxBoissonTaxe(TauxTaxeBoisson tauxBoissonTaxe) {
        this.tauxBoissonTaxe = tauxBoissonTaxe;
    }

    public TauxRetardBoisonTrim() {
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
        if (!(object instanceof TauxRetardBoisonTrim)) {
            return false;
        }
        TauxRetardBoisonTrim other = (TauxRetardBoisonTrim) object;
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
        String tauxBoissonTaxeString = "{}";
        if (tauxBoissonTaxe != null) {
            tauxBoissonTaxeString = tauxBoissonTaxe.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"tauxRetardPremierMois\":" + tauxRetardPremierMois + ",\"tauxRetardAutreMois\":" + tauxRetardAutreMois + ",\"tauxBoissonTaxe\":" + tauxBoissonTaxeString + "}";
    }

    
    
}
