package controller;

import bean.Locale;
import bean.Redevable;
import bean.TaxeTrimBoisson;
import controller.util.JsfUtil;
import controller.util.SessionUtil;
import services.TaxeTrimBoissonFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import services.LocaleFacade;
import services.RedevableFacade;

@Named("taxeTrimBoissonController")
@SessionScoped
public class TaxeTrimBoissonController implements Serializable {

    @EJB
    private services.TaxeTrimBoissonFacade ejbFacade;
    private List<TaxeTrimBoisson> items = null;
    private TaxeTrimBoisson selected;

    @EJB
    RedevableFacade redevableFacade;
    @EJB
    LocaleFacade localeFacade;
    private List<Locale> localsAvailable;
    private String propCode;
    private String gerantCode;
    private TaxeTrimBoisson anItem;
    private String renderYear="false";
    
    public TaxeTrimBoissonController() {
    }

    public String getRenderYear() {
        return renderYear;
    }

    public void setRenderYear(String renderYear) {
        this.renderYear = renderYear;
    }

    public TaxeTrimBoisson getAnItem() {
        if(anItem==null)
            anItem=new TaxeTrimBoisson();
        return anItem;
    }

    public void setAnItem(TaxeTrimBoisson anItem) {
        this.anItem = anItem;
    }
    

    public String getGerantCode() {
        return gerantCode;
    }

    public void setGerantCode(String gerantCode) {
        this.gerantCode = gerantCode;
    }

    public String getPropCode() {
        return propCode;
    }

    public void setPropCode(String propCode) {
        this.propCode = propCode;
    }

    public TaxeTrimBoisson getSelected() {
        if(selected==null)
            selected=new TaxeTrimBoisson();
        return selected;
    }

    public void setSelected(TaxeTrimBoisson selected) {
        this.selected = selected;
    }

    public List<TaxeTrimBoisson> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private TaxeTrimBoissonFacade getFacade() {
        return ejbFacade;
    }

    public List<Locale> getLocalsAvailable() {
        if (localsAvailable == null) {
            localsAvailable = new ArrayList();
        }
        return localsAvailable;
    }

    public void setLocalsAvailable(List<Locale> localsAvailable) {
        this.localsAvailable = localsAvailable;
    }

    public void findLocals(int natureredevable,TaxeTrimBoisson ttb) {
        List<Redevable> redevables = findRedevable(natureredevable);
        if (redevables.isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("RedavableSearchIsEmpty"));
        } else {
            ttb.setRedevable(redevables.get(0));
            if (natureredevable == 2) {
                localsAvailable = localeFacade.findLocals(null, null, null, null, null, null, null, null, redevables.get(0));
            }
            if (natureredevable == 1) {
                localsAvailable = localeFacade.findLocals(null, null, null, null, null, null, null, redevables.get(0), null);
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("success"));
            setRenderYear("true");
        }

    }

    private List<Redevable> findRedevable(int natureredevable) {
        List<Redevable> redevables = new ArrayList<>();
        if (natureredevable == 2) {
            if (propCode != null && !"".equals(propCode)) {
                redevables = redevableFacade.findRedevable(2, propCode, null, null, null);
                if (redevables.isEmpty()) {
                    redevables = redevableFacade.findRedevable(2, null, propCode, null, null);
                }
            }
        } else if (gerantCode != null && !"".equals(gerantCode)) {
            redevables = redevableFacade.findRedevable(1, gerantCode, null, null, null);
            if (redevables.isEmpty()) {
                redevables = redevableFacade.findRedevable(1, null, gerantCode, null, null);
            }
        }
        return redevables;
    }

    public void calculateChiffreAffaireHT(TaxeTrimBoisson ttb) {
        ttb.setChiffreAffaireHT(ejbFacade.calculeChiffreAffaireHT(ttb.getLocal(), ttb.getChiffreAffaireTTC()));
    }

    public void calculateChiffreAffaireTTC(TaxeTrimBoisson ttb) {
        ttb.setChiffreAffaireTTC(ejbFacade.calculeChiffreAffaireTTC(ttb.getLocal(), ttb.getChiffreAffaireHT()));
    }

    public TaxeTrimBoisson prepareCreate() {
        selected = new TaxeTrimBoisson();
        anItem=new TaxeTrimBoisson();
        initializeEmbeddableKey();
        return selected;
    }

    public void createTest() {
        int res = getFacade().createTaxeTrimBoisson(selected, SessionUtil.getConnectedUser());
        switch (res) {
            case -1:
                JsfUtil.addErrorMessage("Tous les trimestres de l'annee " + selected.getTaxeYear().getYear() + " son payee");
                break;
            case -2:
                JsfUtil.addErrorMessage("le trimestr " + selected.getNumeroTrim() + " de l'annee " + selected.getTaxeYear().getYear() + " et déja payee");
                break;
            case -3:
                JsfUtil.addErrorMessage("annee saisi erronée");
                break;
            case -4:
                JsfUtil.addErrorMessage("le trimestr " + selected.getNumeroTrim() + " de l'annee " + selected.getTaxeYear().getYear() + " n'est pas toujour terminee");
                break;
            case -5:
                JsfUtil.addErrorMessage("Taux Taxe pour l'activiter " + selected.getLocal().getTypeLocal().getNom() + " et introuvable \n informer l'administrateure");
                break;
            case -6:
                JsfUtil.addErrorMessage("Taux Retard Taxe pour l'activiter " + selected.getLocal().getTypeLocal().getNom() + " et introuvable \n informer l'administrateure");
                break;
            default:
                JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("success"));
                getItems().add(ejbFacade.clone(selected));
                break;
        }
        setRenderYear("false");
    }
    public void simuler() {
        
        System.out.println(getAnItem().getChiffreAffaireHT());
        System.out.println(getAnItem().getChiffreAffaireTTC());
        System.out.println(getAnItem().getTaxeYear());
        System.out.println(getAnItem().getTaxeYear());
        setAnItem(getFacade().similerTaxeTrimBoisson(getAnItem()));
        System.out.println(getAnItem());
        setRenderYear("false");
    }
    
     public void cancel(){
         selected =null;
         setRenderYear("false");
     }

//    public void create() {
//        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("TaxeTrimBoissonCreated"));
//        if (!JsfUtil.isValidationFailed()) {
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }
//    public void update() {
//        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("TaxeTrimBoissonUpdated"));
//    }
//
//    public void destroy() {
//        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("TaxeTrimBoissonDeleted"));
//        if (!JsfUtil.isValidationFailed()) {
//            selected = null; // Remove selection
//            items = null;    // Invalidate list of items to trigger re-query.
//        }
//    }
//    private void persist(PersistAction persistAction, String successMessage) {
//        if (selected != null) {
//            setEmbeddableKeys();
//            try {
//                if (null != persistAction) {
//                    switch (persistAction) {
//                        case CREATE:
//                            getFacade().createTaxeTrimBoisson(selected, SessionUtil.getConnectedUser());
//                            break;
//                        case UPDATE:
//                            getFacade().edit(selected);
//                            break;
//                        default:
//                            getFacade().remove(selected);
//                            break;
//                    }
//                }
//                JsfUtil.addSuccessMessage(successMessage);
//            } catch (EJBException ex) {
//                String msg = "";
//                Throwable cause = ex.getCause();
//                if (cause != null) {
//                    msg = cause.getLocalizedMessage();
//                }
//                if (msg.length() > 0) {
//                    JsfUtil.addErrorMessage(msg);
//                } else {
//                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//                }
//            } catch (Exception ex) {
//                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
//                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
//            }
//        }
//    }
    public TaxeTrimBoisson getTaxeTrimBoisson(java.lang.Long id) {
        return getFacade().find(id);
    }
//
//    public List<TaxeTrimBoisson> getItemsAvailableSelectMany() {
//        return getFacade().findAll();
//    }
//
//    public List<TaxeTrimBoisson> getItemsAvailableSelectOne() {
//        return getFacade().findAll();
//    }

    @FacesConverter(forClass = TaxeTrimBoisson.class)
    public static class TaxeTrimBoissonControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            TaxeTrimBoissonController controller = (TaxeTrimBoissonController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "taxeTrimBoissonController");
            return controller.getTaxeTrimBoisson(getKey(value));
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
            if (object instanceof TaxeTrimBoisson) {
                TaxeTrimBoisson o = (TaxeTrimBoisson) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), TaxeTrimBoisson.class.getName()});
                return null;
            }
        }

    }

}
