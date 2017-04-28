package controller;

import bean.History;
import bean.Userr;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.HistoryFacade;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
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

@Named("historyController")
@SessionScoped
public class HistoryController implements Serializable {
    
    @EJB
    private services.HistoryFacade ejbFacade;
    private List<History> items = null;
    private History selected;
    
    private String userName;
    private Date dateMin;
    private Date dateMax;
    private int action;
    
    public HistoryController() {
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public Date getDateMin() {
        return dateMin;
    }
    
    public void setDateMin(Date dateMin) {
        this.dateMin = dateMin;
    }
    
    public Date getDateMax() {
        return dateMax;
    }
    
    public void setDateMax(Date dateMax) {
        this.dateMax = dateMax;
    }
    
    public int getAction() {
        return action;
    }
    
    public void setAction(int action) {
        this.action = action;
    }
    
    public History getSelected() {
        if (selected == null) {
            selected = new History();
        }
        return selected;
    }
    
    public void setSelected(History selected) {
        this.selected = selected;
    }
    
    protected void setEmbeddableKeys() {
    }
    
    protected void initializeEmbeddableKey() {
    }
    
    private HistoryFacade getFacade() {
        return ejbFacade;
    }

    /*
    *
    *
    *
     */
    public void search() {
        items = getFacade().findByConditions(userName, dateMin, dateMax, action);
        if (items == null) {
            JsfUtil.addSuccessMessage("No Data Found");
        }else{
            JsfUtil.addSuccessMessage("successe");
        }
    }

    /*
    *
    *
    *
     */
    public History prepareCreate() {
        selected = new History();
        initializeEmbeddableKey();
        return selected;
    }
    
    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("HistoryCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("HistoryUpdated"));
    }
    
    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("HistoryDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public List<History> getItems() {
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
    
    public History getHistory(java.lang.Long id) {
        return getFacade().find(id);
    }
    
    public List<History> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }
    
    public List<History> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }
    
    @FacesConverter(forClass = History.class)
    public static class HistoryControllerConverter implements Converter {
        
        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            HistoryController controller = (HistoryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "historyController");
            return controller.getHistory(getKey(value));
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
            if (object instanceof History) {
                History o = (History) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), History.class.getName()});
                return null;
            }
        }
        
    }
    
}
