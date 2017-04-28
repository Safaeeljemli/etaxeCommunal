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
public class TaxeAnnuelBoisson implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    @ManyToOne
    private Locale locale;
    private int finished =0;// 1:yes -1:no  0:notVerifiedyet 
    private Double montantTaxeannuel=0D;
    @ManyToOne
    private Redevable redevable;
    @OneToMany(mappedBy = "taxeAnnuelBoisson")
    private List<TaxeTrimBoisson> taxeTimBoissons;
    private int annee;

    public TaxeAnnuelBoisson() {
    }

    public int getFinished() {
        return finished;
    }

    public void setFinished(int finished) {
        this.finished = finished;
    }

    public Double getMontantTaxeannuel() {
        return montantTaxeannuel;
    }

    public void setMontantTaxeannuel(Double montantTaxeannuel) {
        this.montantTaxeannuel = montantTaxeannuel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public Redevable getRedevable() {
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public List<TaxeTrimBoisson> getTaxeTimBoissons() {
        return taxeTimBoissons;
    }

    public void setTaxeTimBoissons(List<TaxeTrimBoisson> taxeTimBoissons) {
        this.taxeTimBoissons = taxeTimBoissons;
    }

    public int getAnnee() {
        return annee;

    }

    public void setAnnee(int annee) {
        this.annee = annee;
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
        if (!(object instanceof TaxeAnnuelBoisson)) {
            return false;
        }
        TaxeAnnuelBoisson other = (TaxeAnnuelBoisson) object;
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
        String localeString = "{}";
        if (locale != null) {
            localeString = locale.toString();
        }
        String redevableString = "{}";
        if (redevable != null) {
            redevableString = redevable.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"locale\":" + localeString + ",\"finished\":" + finished + ",\"montantTaxeannuel\":" + montantTaxeannuel + ",\"redevable\":" + redevableString + ",\"taxeTimBoissons\":" + null + ",\"annee\":" + annee + "}";
    }

    

}
