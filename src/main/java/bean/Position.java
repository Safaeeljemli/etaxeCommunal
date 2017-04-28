/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author safa
 */
@Entity
public class Position implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private Long id;
    private Double lat=0D;
    private Double lng=0D;
    @ManyToOne
    private Commune commune;
//    @OneToOne
//    private Locale locale;

//    public Locale getLocale() {
//        return locale;
//    }
//
//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }
    

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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
        if (!(object instanceof Position)) {
            return false;
        }
        Position other = (Position) object;
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
//        String localeString = "{}";
//        if (locale != null) {
//            localeString = locale.toString();
//        }
        return "{" + "\"serialVersionUID\":" + serialVersionUIDString + ",\"id\":" + idString + ",\"lat\":" + lat + ",\"lng\":" + lng +"}";
    }

    

}
