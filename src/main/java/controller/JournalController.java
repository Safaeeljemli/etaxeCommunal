package controller;

import bean.Journal;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import services.JournalFacade;

import java.io.Serializable;
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

@Named("journalController")
@SessionScoped
public class JournalController implements Serializable {

    @EJB
    private services.JournalFacade ejbFacade;
    private List<Journal> items = null;
    private Journal selected;
    
    
    private String userName;
    private Date dateMin;
    private Date dateMax;
    private int action;

    public JournalController() {
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
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
    
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    

    public Journal getSelected() {
        if(selected==null)
            selected=new Journal();
        return selected;
    }

    public void setSelected(Journal selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private JournalFacade getFacade() {
        return ejbFacade;
    }

    public Journal prepareCreate() {
        selected = new Journal();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("JournalCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("JournalUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("JournalDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }
    
    public void search() {
        items = getFacade().findByConditions(userName, dateMin, dateMax, action);
        if (items == null) {
            JsfUtil.addSuccessMessage("No Data Found");
        }else{
            JsfUtil.addSuccessMessage("successe");
        }
    }

    public List<Journal> getItems() {
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

    public Journal getJournal(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Journal> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Journal> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Journal.class)
    public static class JournalControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            JournalController controller = (JournalController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "journalController");
            return controller.getJournal(getKey(value));
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
            if (object instanceof Journal) {
                Journal o = (Journal) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Journal.class.getName()});
                return null;
            }
        }

    }

}
