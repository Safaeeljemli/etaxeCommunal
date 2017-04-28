/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author safa
 */
@Entity
public class Userr implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String login;
    private String passwrd;
    @ManyToOne
    private Commune commune;
    @OneToMany(mappedBy = "user")
    private List<TaxeTrimBoisson> taxeTrimBoissons;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private boolean blocked=false;
    private int nbrCnx;
//    private boolean connected=false;
    @OneToMany(mappedBy = "user")
    private List<Device> devices;
        
    
    //rights
//    private boolean creationActivite = true;      
//    private boolean creationSecteure = true;      
//    private boolean creationUser = true;    
//    private boolean taxeBoison = true;   
//    private boolean taux = true;
    private boolean adminn;
    private boolean redevable ;      
    private boolean taxes ;      
    private boolean adressage;    
    private boolean locals;   
    
    public Userr() {
    }
    

    public Userr(String login) {
        this.login = login;
    }
//
//    public boolean isConnected() {
//        return connected;
//    }
//
//    public void setConnected(boolean connected) {
//        this.connected = connected;
//    }

    public boolean isRedevable() {
        return redevable;
    }

    public void setRedevable(boolean redevable) {
        this.redevable = redevable;
    }

    public boolean isTaxes() {
        return taxes;
    }

    public void setTaxes(boolean taxes) {
        this.taxes = taxes;
    }

    public boolean isAdressage() {
        return adressage;
    }

    public void setAdressage(boolean Adressage) {
        this.adressage = adressage;
    }

    public boolean isLocals() {
        return locals;
    }

    public void setLocals(boolean locals) {
        this.locals = locals;
    }
    
    public boolean isAdminn() {
        return adminn;
    }

    public void setAdminn(boolean adminn) {
        this.adminn = adminn;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPasswrd() {
        return passwrd;
    }

    public void setPasswrd(String passwrd) {
        this.passwrd = passwrd;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public List<TaxeTrimBoisson> getTaxeTrimBoissons() {
        return taxeTrimBoissons;
    }

    public void setTaxeTrimBoissons(List<TaxeTrimBoisson> taxeTrimBoissons) {
        this.taxeTrimBoissons = taxeTrimBoissons;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getNbrCnx() {
        return nbrCnx;
    }

    public void setNbrCnx(int nbrCnx) {
        this.nbrCnx = nbrCnx;
    }

    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.login);
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
        final Userr other = (Userr) obj;
        if (!Objects.equals(this.login, other.login)) {
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
        String communeString = "{}";
        if (commune != null) {
            communeString = commune.toString();
        }
      
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"login\":\"" + login + "\",\"passwrd\":\"" + passwrd + "\",\"commune\":" + communeString + ",\"taxeTrimBoissons\":" + null + ",\"nom\":\"" + nom + "\",\"prenom\":\"" + prenom + "\",\"email\":\"" + email + "\",\"tel\":\"" + tel + "\",\"blocked\":" + blocked + ",\"nbrCnx\":" + nbrCnx +",\"devices\":" + null + ",\"adminn\":" +String.valueOf(adminn) + ",\"redevable\":" + String.valueOf(redevable) + ",\"taxes\":" + String.valueOf(taxes) + ",\"adressage\":" + String.valueOf(adressage) + ",\"locals\":" + String.valueOf(locals) + "}";
    }

   
    

}
