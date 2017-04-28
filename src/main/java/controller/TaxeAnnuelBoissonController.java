package controller;

import bean.TaxeAnnuelBoisson;
import bean.TaxeTrimBoisson;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.TaxeAnnuelBoissonFacade;

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

@Named("taxeAnnuelBoissonController")
@SessionScoped
public class TaxeAnnuelBoissonController implements Serializable {

    @EJB
    private services.TaxeAnnuelBoissonFacade ejbFacade;
    private List<TaxeAnnuelBoisson> items = null;
    private TaxeAnnuelBoisson selected;
    
    
     @EJB
    private services.TaxeTrimBoissonFacade taxeTrimBoissonFacade;
    private  List<TaxeTrimBoisson> yearlyTaxeTrims;

    public TaxeAnnuelBoissonController() {
    }

    public List<TaxeTrimBoisson> getYearlyTaxeTrims() {
        if(yearlyTaxeTrims==null)
            yearlyTaxeTrims=new ArrayList();
        return yearlyTaxeTrims;
    }

    public void setYearlyTaxeTrims(List<TaxeTrimBoisson> yearlyTaxeTrims) {
        this.yearlyTaxeTrims = yearlyTaxeTrims;
    }
    
    
    public TaxeAnnuelBoisson getSelected() {
        if(selected==null)
            selected=new TaxeAnnuelBoisson();
        return selected;
    }

    public void setSelected(TaxeAnnuelBoisson selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxeAnnuelBoissonFacade getFacade() {
        return ejbFacade;
    }

    public TaxeAnnuelBoisson prepareCreate() {
        selected = new TaxeAnnuelBoisson();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelBoissonCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelBoissonUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TaxeAnnuelBoissonDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void findTaxeTrim(TaxeAnnuelBoisson taxeAnnuelBoisson){
        yearlyTaxeTrims=taxeTrimBoissonFacade.findByTaxeAnnuel(taxeAnnuelBoisson);
        if(yearlyTaxeTrims.isEmpty() || yearlyTaxeTrims==null)
            JsfUtil.addErrorMessage("failed");
        else
            JsfUtil.addSuccessMessage("Success");
    }

    public List<TaxeAnnuelBoisson> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
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

    public TaxeAnnuelBoisson getTaxeAnnuelBoisson(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<TaxeAnnuelBoisson> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<TaxeAnnuelBoisson> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = TaxeAnnuelBoisson.class)
    public static class TaxeAnnuelBoissonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxeAnnuelBoissonController controller = (TaxeAnnuelBoissonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxeAnnuelBoissonController");
            return controller.getTaxeAnnuelBoisson(getKey(value));
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
            if (object instanceof TaxeAnnuelBoisson) {
                TaxeAnnuelBoisson o = (TaxeAnnuelBoisson) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxeAnnuelBoisson.class.getName()});
                return null;
            }
        }

    }

}
