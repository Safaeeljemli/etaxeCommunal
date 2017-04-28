package controller;

import bean.Locale;
import bean.Redevable;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.RedevableFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.enterprise.context.SessionScoped;
//import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@Named("redevableController")
@SessionScoped
public class RedevableController implements Serializable {

    //variable pour la tabe du Redevable
    @EJB
    private services.RedevableFacade ejbFacade;
    private List<Redevable> items = null;
    private Redevable selected;
    private Redevable gerant;
    private int natureDuGerant = -1;
    private int natureDuPropritaire = -1;
//to enable save button if redevable makaynch f bd
    private String desabalePropritaireSave = "true";
    private String desabaleGerantSave = "true";
    private String desabalePropRc = "false";
    private String desabalepropCin = "false";
    private String desabaleGerantRc = "false";
    private String desabaleGerantCin = "false";

    private List<Locale> locales = null;

    //getters and seters Redevable
    public int getNatureDuGerant() {
        return natureDuGerant;
    }

    public void setNatureDuGerant(int natureDuGerant) {
        this.natureDuGerant = natureDuGerant;
    }

    public int getNatureDuPropritaire() {
        return natureDuPropritaire;
    }

    public void setNatureDuPropritaire(int natureDuPropritaire) {
        this.natureDuPropritaire = natureDuPropritaire;
    }

    public Redevable getGerant() {
        if (gerant == null) {
            gerant = new Redevable();
            gerant.setNature(1);
        }
        return gerant;
    }

    public void setGerant(Redevable gerant) {
        this.gerant = gerant;
        disableOrEnableInput(1, 1, "false");
        disableOrEnableInput(1, 2, "false");
    }

    public RedevableController() {
    }

    public Redevable getSelected() {
        if (selected == null) {
            selected = new Redevable();
            selected.setNature(2);
        }
        return selected;
    }

    public void setSelected(Redevable selected) {
        this.selected = selected;
        disableOrEnableInput(2, 1, "false");
        disableOrEnableInput(2, 2, "false");
    }

    public List<Locale> getLocales() {
        if (locales == null) {
            locales = new ArrayList<>();
        }
        return locales;
    }

    public void setLocales(List<Locale> locales) {
        this.locales = locales;
    }

    public String getDesabalePropritaireSave() {
        return desabalePropritaireSave;
    }

    public void setDesabalePropritaireSave(String desabalePropritaireSave) {
        this.desabalePropritaireSave = desabalePropritaireSave;
    }

    public String getDesabaleGerantSave() {
        return desabaleGerantSave;
    }

    public void setDesabaleGerantSave(String desabaleGerantSave) {
        this.desabaleGerantSave = desabaleGerantSave;
    }

    public String getDesabalePropRc() {
        return desabalePropRc;
    }

    public void setDesabalePropRc(String desabalePropRc) {
        this.desabalePropRc = desabalePropRc;
    }

    public String getDesabalepropCin() {
        return desabalepropCin;
    }

    public void setDesabalepropCin(String desabalepropCin) {
        this.desabalepropCin = desabalepropCin;
    }

    public String getDesabaleGerantRc() {
        return desabaleGerantRc;
    }

    public void setDesabaleGerantRc(String desabaleGerantRc) {
        this.desabaleGerantRc = desabaleGerantRc;
    }

    public String getDesabaleGerantCin() {
        return desabaleGerantCin;
    }

    public void setDesabaleGerantCin(String desabaleGerantCin) {
        this.desabaleGerantCin = desabaleGerantCin;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private RedevableFacade getFacade() {
        return ejbFacade;
    }

    public void prepareCreate() {
        selected = new Redevable();
        selected.setNature(2);
        gerant = new Redevable();
        gerant.setNature(1);
        setNatureDuPropritaire(-1);
        setNatureDuGerant(-1);
        initializeEmbeddableKey();
//        return selected;
    }

    //EntityType : personnePhysique ou entreprise
    public void create(Redevable redevable, int EntityType) {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("RedevableCreated"), redevable);
        if (!JsfUtil.isValidationFailed()) {
            getItems().add(ejbFacade.clone(redevable));
            prepareCreate();// Invalidate list of items to trigger re-query.
        }
        setDesabalePropritaireSave("true");
        disableOrEnableInput(redevable.getNature(), EntityType, "false");
    }

    public void lookUpRc(Redevable newRedevable) {
        List<Redevable> redevables = ejbFacade.findRedevable(newRedevable.getNature(), newRedevable.getRc(), null, null, null);
        if (redevables.isEmpty()) {
            if (newRedevable.getNature() == 2) {
                setDesabalePropritaireSave("false");
                disableOrEnableInput(newRedevable.getNature(), 2, "true");
            } else {
                setDesabaleGerantSave("false");
                disableOrEnableInput(newRedevable.getNature(), 2, "true");
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("success"));
        }
        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RedevableExist"));
    }

    public void lookUpCin(Redevable newRedevable) {
        List<Redevable> redevables = ejbFacade.findRedevable(newRedevable.getNature(), null, newRedevable.getCin(), null, null);
        if (redevables.isEmpty()) {
            if (newRedevable.getNature() == 2) {
                setDesabalePropritaireSave("false");
                disableOrEnableInput(newRedevable.getNature(), 1, "true");
            } else {
                setDesabaleGerantSave("false");
                disableOrEnableInput(newRedevable.getNature(), 1, "true");
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("success"));
        } else {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RedevableExist"));
        }

    }

    public void cancelCreation(int nature) {
        if (nature == 1) {
            setGerant(null);
            setDesabaleGerantSave("true");
            
        } else if (nature == 2) {
            setSelected(null);
            setDesabalePropritaireSave("true");
        }
        disableOrEnableInput(nature, 1, "false");
        disableOrEnableInput(nature, 2, "false");
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("RedevableUpdated"), selected);
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("RedevableDeleted"), selected);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Redevable> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage, Redevable redevable) {
        if (redevable != null) {
            setEmbeddableKeys();
            try {
                switch (persistAction) {
                    case CREATE:
                        getFacade().create(redevable);
                        break;
                    case UPDATE:
                        getFacade().edit(redevable);
                        break;
                    default:
                        getFacade().remove(redevable);
                        break;
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

    public Redevable getRedevable(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Redevable> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Redevable> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    //nature : 1 gerant 2 propritaire
    //EntityType  : 1 personnePhysic 2 entreprise
    private void disableOrEnableInput(int nature, int EntityType, String trueOrFalse) {
        if (nature == 1) {
            if (EntityType == 1) {
                desabaleGerantCin = trueOrFalse;
            } else {
                desabaleGerantRc = trueOrFalse;
            }
        } else if (EntityType == 1) {
            desabalepropCin = trueOrFalse;
        } else {
            desabalePropRc = trueOrFalse;
        }
    }

    @FacesConverter(forClass = Redevable.class)
    public static class RedevableControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            RedevableController controller = (RedevableController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "redevableController");
            return controller.getRedevable(getKey(value));
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
            if (object instanceof Redevable) {
                Redevable o = (Redevable) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Redevable.class.getName()});
                return null;
            }
        }

    }

}
