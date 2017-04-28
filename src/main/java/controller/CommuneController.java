package controller;

import bean.Commune;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.CommuneFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("communeController")
@SessionScoped
public class CommuneController implements Serializable {

    @EJB
    private services.CommuneFacade ejbFacade;
    @EJB
    private services.SecteurFacade secteurFacade;
    @EJB
    private services.QuartierFacade quartierFacade;
    @EJB
    private services.RueFacade rueFacade;
    
    private List<Commune> items = null;
    private List<Secteur> secteurs;
    private Commune selected;
    private Commune thisCommun = null;
    private Secteur thisSecteur = null;
    private Quartier thisQuartier = null;
    private String thisNameSec;
       private Rue thisRue = null;

    public Rue getThisRue() {
        return thisRue;
    }

    public void setThisRue(Rue thisRue) {
        this.thisRue = thisRue;
    }

    public Commune getSelected() {
        return selected;
    }

    public void setSelected(Commune selected) {
        this.selected = selected;
    }

    public Commune getThisCommun() {
       if (thisCommun == null) {
            thisCommun = new Commune();
        }
        return thisCommun;
    }
    public void setThisCommun(Commune thisCommun) {
        this.thisCommun = thisCommun;
    }

    public Secteur getThisSecteur() {
        if(thisSecteur==null){
            thisSecteur=new Secteur();
        }
        return thisSecteur;
    }

    public void setThisSecteur(Secteur thisSecteur) {
        this.thisSecteur = thisSecteur;
    }

    public Quartier getThisQuartier() {
        if(thisQuartier==null){
            thisQuartier=new Quartier();
        }
        return thisQuartier;
    }

    public void setThisQuartier(Quartier thisQuartier) {
        this.thisQuartier = thisQuartier;
    }

    public List<Secteur> getSecteurs() {
        return secteurs;
    }

    public void setSecteurs(List<Secteur> secteurs) {
        this.secteurs = secteurs;
    }
    
      public List<Commune> getCommunesAvailableSelectOne() {
        return ejbFacade.findAll();
    }

      public void secteureByCommun(Commune commun) {
           getThisCommun().setSecteurs(secteurFacade.findSecteureByCommun(commun));
           getThisSecteur().setQuartiers(new ArrayList<Quartier>());//pour enisialiser si la commune et changer
           getThisQuartier().setRues(new ArrayList<Rue>());//appel avec get pour eviter le cas ou ThisSecteur et ThisQyartie son null
        
      }
   public void secteureByName(Secteur secteur) {
           thisCommun.setSecteurs(secteurFacade.secteureByName(thisNameSec));
           getThisSecteur().setQuartiers(new ArrayList<Quartier>());//pour enisialiser si la commune et changer
           getThisQuartier().setRues(new ArrayList<Rue>());//appel avec get pour eviter le cas ou ThisSecteur et ThisQyartie son null
        
      }
      public void quartierBySecteure(Secteur secteur) {
     
            getThisSecteur().setQuartiers(quartierFacade.findBySecteur(secteur));
             getThisQuartier().setRues(new ArrayList<Rue>());
            
    }

    public void rueByQuartier(Quartier quartier) {
            getThisQuartier().setRues(rueFacade.findByQuartier(quartier));
    }
//    public List<Secteur> searchSecteur(String name){
//         Secteur secteur=new Secteur();
//        
//    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private CommuneFacade getFacade() {
        return ejbFacade;
    }

    public Commune prepareCreate() {
        selected = new Commune();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("CommuneCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("CommuneUpdated"));
    }
//
//    public void destroy() {
//        
//        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("CommuneDeleted"));
//        if (!JsfUtil.isValidationFailed()) {
//            selected = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }

    public List<Commune> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

        private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                    getFacade().create(selected);
                } else if (persistAction == PersistAction.UPDATE){
                    getFacade().edit(selected,selected.getId());
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public Commune getCommune(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Commune> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Commune> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Commune.class)
    public static class CommuneControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            CommuneController controller = (CommuneController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "communeController");
            return controller.getCommune(getKey(value));
        }

        java.lang.Long getKey(String value) {
            java.lang.Long key;
            key = Long.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Long value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Commune) {
                Commune o = (Commune) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Commune.class.getName()});
                return null;
            }
        }

    }

}
