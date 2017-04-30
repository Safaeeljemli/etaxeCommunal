package controller;

import bean.Userr;
import controller.util.HashageUtil;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import services.UserFacade;

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

@Named("userController")
@SessionScoped
public class UserController implements Serializable {

    @EJB
    private services.UserFacade ejbFacade;
    @EJB
    private services.HistoryFacade hf;
    private List<Userr> items = null;
    private Userr selected;

    private String admin;

    public UserController() {
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public void connecte() {
        int res = ejbFacade.seConnnecter(getSelected());
        switch (res) {
            case (-5):
                JsfUtil.addErrorMessage("Veuilliez saisir votre login");
                break;
            case (-4):
                JsfUtil.addErrorMessage("Login n'existe pas");
                break;
            case (-1):
                JsfUtil.addErrorMessage("User deja connecter veuiller vous deconnecter des autre device ou notifier votre admin ");
                break;
            case (-2):
                JsfUtil.addErrorMessage("Utilisateur est bloqué");
                break;
            case (-3):
                JsfUtil.addErrorMessage("Mot de passe incorrect");
                break;
            default:
                try {
                    SessionUtil.redirect("/eTaxeCommunal/faces/secured/home/accueil");
                    System.out.println(SessionUtil.getConnectedUser());
                } catch (IOException ex) {
                    Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        setSelected(null);

    }

    public void seDeConnnecter() throws IOException {
        System.out.println("seDeConnnecter");
        ejbFacade.seDeConnnecter();
        SessionUtil.redirect("/eTaxeCommunal/faces/Login.xhtml");
    }

    public String canAccesseAdminBar() {
        if (SessionUtil.getConnectedUser().isAdminn()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String canAccesseRedevable() {
        if (SessionUtil.getConnectedUser().isRedevable()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String canAccesseTaxes() {
        if (SessionUtil.getConnectedUser().isTaxes()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String canAccesseAdressage() {
        if (SessionUtil.getConnectedUser().isAdressage()) {
            return "true";
        } else {
            return "false";
        }
    }

    public String canAccesseLocals() {
        if (SessionUtil.getConnectedUser().isLocals()) {
            return "true";
        } else {
            return "false";
        }
    }

    public Userr getSelected() {
        if (selected == null) {
            selected = new Userr();
        }
        return selected;
    }

    public void setSelected(Userr selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private UserFacade getFacade() {
        return ejbFacade;
    }

    public Userr prepareCreate() {
        selected = new Userr();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        if (selected != null) {
            getSelected().setPasswrd(HashageUtil.sha256(getSelected().getPasswrd()));
            getFacade().create(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserCreated"));
            items = null;
            selected = null;
        }
        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
    }

    public void update() {
        if (selected != null) {
            Userr loadedUser = ejbFacade.find(selected.getLogin());
            selected.setPasswrd(loadedUser.getPasswrd());
            getFacade().edit(selected, selected.getLogin());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            items = null;
            selected = null;
        }
        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
    }

    public void destroy(Userr user) {
        if (user != null) {
            Userr loadedUser = ejbFacade.find(user.getLogin());
            user.setPasswrd(loadedUser.getPasswrd());
            getFacade().remove(user);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("UserUpdated"));
            items = null;
        }
        JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
    }

    public List<Userr> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }


    public Userr getUser(java.lang.String id) {
        return getFacade().find(id);
    }

    public List<Userr> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Userr> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Userr.class)
    public static class UserControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            UserController controller = (UserController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "userController");
            return controller.getUser(getKey(value));
        }

        java.lang.String getKey(String value) {
            java.lang.String key;
            key = value;
            return key;
        }

        String getStringKey(java.lang.String value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Userr) {
                Userr o = (Userr) object;
                return getStringKey(o.getLogin());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Userr.class.getName()});
                return null;
            }
        }

    }

}
