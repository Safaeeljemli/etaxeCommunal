
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author safa
 */
@Entity
public class Locale implements Serializable {


    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private String nom;
    private String complementAdress;
    @ManyToOne
    private Rue rue;
    @OneToOne
    private Activite typeLocal;
    @ManyToOne
    private Redevable gerant;
    @ManyToOne
    private Redevable propriete;
    @OneToMany(mappedBy = "locale")
    private List<TaxeAnnuelBoisson> taxeAnnuelBoissons;
    @OneToOne
    private Position position;
    

    public Locale() {
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }


    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    public String getComplementAdress() {
        return complementAdress;
    }

    public void setComplementAdress(String complementAdress) {
        this.complementAdress = complementAdress;
    }

    public Rue getRue() {
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public Activite getTypeLocal() {
        return typeLocal;
    }

    public void setTypeLocal(Activite typeLocal) {
        this.typeLocal = typeLocal;
    }

    public Redevable getGerant() {
        return gerant;
    }

    public void setGerant(Redevable gerant) {
        this.gerant = gerant;
    }

    public Redevable getPropriete() {
        return propriete;
    }

    public void setPropriete(Redevable propriete) {
        this.propriete = propriete;
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
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Locale)) {
            return false;
        }
        Locale other = (Locale) object;
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
        String rueString = "{}";
        if (rue != null) {
            rueString = rue.toString();
        }
        String typeLocalString = "{}";
        if (typeLocal != null) {
            typeLocalString = typeLocal.toString();
        }
        String gerantString = "{}";
        if (gerant != null) {
            gerantString = gerant.toString();
        }
        String proprieteString = "{}";
        if (propriete != null) {
            proprieteString = propriete.toString();
        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"nom\":\"" + nom + "\",\"complementAdress\":\"" + complementAdress + "\",\"rue\":" + rueString + ",\"typeLocal\":" + typeLocalString + ",\"gerant\":" + gerantString + ",\"propriete\":" + proprieteString + ",\"taxeAnnuelBoissons\":" + null + "}";
    }

    
    
}
