/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author safa
 */
@Entity
public class TaxeTrimBoisson implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double chiffreAffaireHT;
    private Double chiffreAffaireTTC;
    private Double montantTotalTaxe;
    private Double montantTaxe;
    private Double montantTotalRetard;
    private Double montantRetardPremierMois;
    private Double montantRetardAutreMois;
    private LocalDate dateActuel;
    private LocalDate taxeYear;
    private int numeroTrim;
    @ManyToOne
    private TaxeAnnuelBoisson taxeAnnuelBoisson;
    @ManyToOne
    private Redevable redevable;
    @ManyToOne
    private Locale local;
    @ManyToOne
    private Userr user;

    public Double getMontantTaxe() {
        return montantTaxe;
    }

    public void setMontantTaxe(Double montantTaxe) {
        this.montantTaxe = montantTaxe;
    }
    
    public Double getChiffreAffaireTTC() {
        return chiffreAffaireTTC;
    }

    public void setChiffreAffaireTTC(Double chiffreAffaireTTC) {
        this.chiffreAffaireTTC = chiffreAffaireTTC;
    }

    public Double getMontantTotalTaxe() {
        return montantTotalTaxe;
    }

    public void setMontantTotalTaxe(Double montantTotalTaxe) {
        this.montantTotalTaxe = montantTotalTaxe;
    }

    public Double getMontantTotalRetard() {
        return montantTotalRetard;
    }

    public void setMontantTotalRetard(Double montantTotalRetard) {
        this.montantTotalRetard = montantTotalRetard;
    }

    public Double getMontantRetardPremierMois() {
        return montantRetardPremierMois;
    }

    public void setMontantRetardPremierMois(Double montantRetardPremierMois) {
        this.montantRetardPremierMois = montantRetardPremierMois;
    }

    public Double getMontantRetardAutreMois() {
        return montantRetardAutreMois;
    }

    public void setMontantRetardAutreMois(Double montantRetardAutreMois) {
        this.montantRetardAutreMois = montantRetardAutreMois;
    }

    public LocalDate getTaxeYear() {
        return taxeYear;
    }

    public void setTaxeYear(LocalDate taxeYear) {
        this.taxeYear = taxeYear;
    }

    public int getNumeroTrim() {
        return numeroTrim;
    }

    public void setNumeroTrim(int numeroTrim) {
        this.numeroTrim = numeroTrim;
    }

    public TaxeTrimBoisson() {
    }

    public Double getChiffreAffaireHT() {
        return chiffreAffaireHT;
    }

    public void setChiffreAffaireHT(Double chiffreAffaireHT) {
        this.chiffreAffaireHT = chiffreAffaireHT;
    }

    public LocalDate getDateActuel() {
        return dateActuel;
    }

    public void setDateActuel(LocalDate dateActuel) {
        this.dateActuel = dateActuel;
    }

    public Userr getUser() {
        return user;
    }

    public void setUser(Userr user) {
        this.user = user;
    }

    public TaxeAnnuelBoisson getTaxeAnnuelBoisson() {
        return taxeAnnuelBoisson;
    }

    public void setTaxeAnnuelBoisson(TaxeAnnuelBoisson taxeAnnuelBoisson) {
        this.taxeAnnuelBoisson = taxeAnnuelBoisson;
    }

    public Redevable getRedevable() {
        return redevable;
    }

    public void setRedevable(Redevable redevable) {
        this.redevable = redevable;
    }

    public Locale getLocal() {
        return local;
    }

    public void setLocal(Locale local) {
        this.local = local;
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
        if (!(object instanceof TaxeTrimBoisson)) {
            return false;
        }
        TaxeTrimBoisson other = (TaxeTrimBoisson) object;
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
        String dateActuelString = "{}";
        if (dateActuel != null) {
            dateActuelString = dateActuel.toString();
        }
        String taxeYearString = "{}";
        if (taxeYear != null) {
            taxeYearString = taxeYear.toString();
        }
        String taxeAnnuelBoissonString = "{}";
        if (taxeAnnuelBoisson != null) {
            taxeAnnuelBoissonString = taxeAnnuelBoisson.toString();
        }
        String redevableString = "{}";
        if (redevable != null) {
            redevableString = redevable.toString();
        }
        String localString = "{}";
        if (local != null) {
            localString = local.toString();
        }
        String userString = "{}";
        if (user != null) {
            userString = user.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"chiffreAffaireHT\":" + chiffreAffaireHT + ",\"chiffreAffaireTTC\":" + chiffreAffaireTTC + ",\"montantTotalTaxe\":" + montantTotalTaxe + ",\"montantTaxe\":" + montantTaxe + ",\"montantTotalRetard\":" + montantTotalRetard + ",\"montantRetardPremierMois\":" + montantRetardPremierMois + ",\"montantRetardAutreMois\":" + montantRetardAutreMois + ",\"dateActuel\":" + dateActuelString + ",\"taxeYear\":" + taxeYearString + ",\"numeroTrim\":" + numeroTrim + ",\"taxeAnnuelBoisson\":" + taxeAnnuelBoissonString + ",\"redevable\":" + redevableString + ",\"local\":" + localString + ",\"user\":" + userString + "}";
    }

    

}
