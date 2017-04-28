/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author safa
 */
@Entity
public class Redevable implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
     private String cin;
     private String rc;
    private String nom;
    private String prenom;
    private int nature;//1-gerant 2-proprietaire
    private String mail;
    private LocalDate dateDeCommencement;
    @OneToMany(mappedBy = "redevable")
    private List<TaxeAnnuelBoisson> taxeAnnuelBoissons;


    public Redevable() {
    }

    
    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getRc() {
        return rc;
    }

    public void setRc(String rc) {
        this.rc = rc;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getNature() {
        return nature;
    }

    public void setNature(int nature) {
        this.nature = nature;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public LocalDate getDateDeCommencement() {
        return dateDeCommencement;
    }

    public void setDateDeCommencement(LocalDate dateDeCommencement) {
        this.dateDeCommencement = dateDeCommencement;
    }

   

    public List<TaxeAnnuelBoisson> getTaxeAnnuelBoissons() {
        return taxeAnnuelBoissons;
    }

    public void setTaxeAnnuelBoissons(List<TaxeAnnuelBoisson> taxeAnnuelBoissons) {
        this.taxeAnnuelBoissons = taxeAnnuelBoissons;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Redevable other = (Redevable) obj;
        if (!Objects.equals(this.id, other.id)) {
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
        String dateDeCommencementString = "{}";
        if (dateDeCommencement != null) {
            dateDeCommencementString = dateDeCommencement.toString();
        }
        
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"cin\":\"" + cin + "\",\"rc\":\"" + rc + "\",\"nom\":\"" + nom + "\",\"prenom\":\"" + prenom + "\",\"nature\":" + nature + ",\"mail\":\"" + mail + "\",\"dateDeCommencement\":" + dateDeCommencementString + ",\"taxeAnnuelBoissons\":" + null + "}";
    }
    

    
}
