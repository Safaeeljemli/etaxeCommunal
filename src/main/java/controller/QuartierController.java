package controller;

import bean.Quartier;
import bean.Rue;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.QuartierFacade;

import java.io.Serializable;
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
import services.RueFacade;

@Named("quartierController")
@SessionScoped
public class QuartierController implements Serializable {

    @EJB
    private services.QuartierFacade ejbFacade;
    private List<Quartier> items = null;
    private Quartier selected;
    private Rue selectedRue;
    @EJB
    private services.RueFacade rueFacade;

    public QuartierController() {
    }

    public Rue getSelectedRue() {
        if (selectedRue == null) {
            selectedRue = new Rue();
        }
        return selectedRue;
    }

    public void setSelectedRue(Rue selectedRue) {
        this.selectedRue = selectedRue;
    }

    public RueFacade getRueFacade() {
        return rueFacade;
    }

    public void setRueFacade(RueFacade rueFacade) {
        this.rueFacade = rueFacade;
    }

    public Quartier getSelected() {
        return selected;
    }

    public void setSelected(Quartier selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private QuartierFacade getFacade() {
        return ejbFacade;
    }

    public Quartier prepareCreate() {
        selected = new Quartier();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("QuartierCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("QuartierUpdated"));
    }

//    public void destroy() {
//        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("QuartierDeleted"));
//        if (!JsfUtil.isValidationFailed()) {
//            selected = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }

    public List<Quartier> getItems() {
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
                } else if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(selected, selected.getId());
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

    public Quartier getQuartier(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Quartier> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Quartier> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Quartier.class)
    public static class QuartierControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            QuartierController controller = (QuartierController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "quartierController");
            return controller.getQuartier(getKey(value));
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
            if (object instanceof Quartier) {
                Quartier o = (Quartier) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Quartier.class.getName()});
                return null;
            }
        }

    }

}
