package controller;

import bean.TauxTaxeBoisson;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.TauxTaxeBoissonFacade;

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
import org.primefaces.event.CellEditEvent;

@Named("tauxBoissonTaxeController")
@SessionScoped
public class TauxBoissonTaxeController implements Serializable {

    @EJB
    private services.TauxTaxeBoissonFacade ejbFacade;
    private List<TauxTaxeBoisson> items = null;
    private TauxTaxeBoisson selected;

    public TauxBoissonTaxeController() {
    }

    public TauxTaxeBoisson getSelected() {
        return selected;
    }

    public void setSelected(TauxTaxeBoisson selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TauxTaxeBoissonFacade getFacade() {
        return ejbFacade;
    }

    public TauxTaxeBoisson prepareCreate() {
        selected = new TauxTaxeBoisson();
        initializeEmbeddableKey();
        return selected;
    }

    public void create(TauxTaxeBoisson taxeBoisson) {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TauxBoissonTaxeCreated"),taxeBoisson);
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update(TauxTaxeBoisson taxeBoisson) {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TauxBoissonTaxeUpdated"),taxeBoisson);
    }

    public void destroy(TauxTaxeBoisson taxeBoisson) {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TauxBoissonTaxeDeleted"),taxeBoisson);
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<TauxTaxeBoisson> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage,TauxTaxeBoisson taxeBoisson) {
        if (taxeBoisson != null) {
            setEmbeddableKeys();
            try {
                if (persistAction == PersistAction.CREATE) {
                     getFacade().create(taxeBoisson);
                } else if(persistAction == PersistAction.UPDATE){
                    getFacade().edit(taxeBoisson,taxeBoisson.getId());
                }else{
                    getFacade().remove(taxeBoisson);
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

    public TauxTaxeBoisson getTauxBoissonTaxe(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TauxTaxeBoisson> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TauxTaxeBoisson> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TauxTaxeBoisson.class)
    public static class TauxBoissonTaxeControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TauxBoissonTaxeController controller = (TauxBoissonTaxeController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "tauxBoissonTaxeController");
            return controller.getTauxBoissonTaxe(getKey(value));
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
            if (object instanceof TauxTaxeBoisson) {
                TauxTaxeBoisson o = (TauxTaxeBoisson) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TauxTaxeBoisson.class.getName()});
                return null;
            }
        }

    }

}
