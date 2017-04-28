package controller;

import bean.Activite;
import bean.Commune;
import bean.Locale;
import bean.Position;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import controller.util.JsfUtil;
import controller.util.JsfUtil.PersistAction;
import controller.util.SessionUtil;
import java.io.IOException;
import services.LocaleFacade;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

@Named("localeController")
@SessionScoped
public class LocaleController implements Serializable {

    @EJB
    private services.LocaleFacade ejbFacade;
    private List<Locale> items = null;
    private Locale selected;

    //anything else in this controller is generated code
    //added Code
    //les Facades
    @EJB
    private services.CommuneFacade communeFacade;
    @EJB
    private services.PositionFacade positionFacade;
    @EJB
    private services.SecteurFacade secteurFacade;
    @EJB
    private services.QuartierFacade quartierFacade;
    @EJB
    private services.RueFacade rueFacade;
    @EJB
    private services.RedevableFacade redevableFacade;

    //les Variables
    private List<Commune> communes;
    private List<Activite> activites;
    private Commune thisCommun = null;
    private Secteur thisSecteur = null;
    private Quartier thisQyartie = null;
    private List<Locale> locals = null;
    private String proprieteId;
    private String gerantId;
    private String desabaleSave = "true";
    private String desabaleProp = "false";
    private String desabaleGerant = "false";
    private String desabeledPosition = "true";
    private MapModel emptyModel;
    private Position position;
    private String gson;

    private Double lat = 0D;
    private Double lng = 0D;

    /*
    *
    *getters and setters
    *
    *
     */
    public List<Locale> getLocals() {
        return locals == null ? new ArrayList() : locals;
    }

    public void setLocals(List<Locale> locals) {
        this.locals = locals;
    }

    @PostConstruct
    public void init() {
        emptyModel = new DefaultMapModel();
    }

    public String getDesabeledPosition() {
        return desabeledPosition;
    }

    public void setDesabeledPosition(String desabeledPosition) {
        this.desabeledPosition = desabeledPosition;
    }

    public MapModel getEmptyModel() {
        return emptyModel;
    }

    public void setEmptyModel(MapModel emptyModel) {
        this.emptyModel = emptyModel;
    }

    public List<Commune> getCommunes() {
        if (communes == null) {
            communes = communeFacade.findAll();
        }
        return communes;

    }

    public Position getPosition() {
        if (position == null) {
            position = new Position();
        }
        position.setId(positionFacade.generateId("Position", "id"));
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getGson() {
        return gson;
    }

    public void setGson(String gson) {
        this.gson = gson;
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

    public Commune getThisCommun() {
        if (thisCommun == null) {
            thisCommun = new Commune();
        }
        return thisCommun;
    }

    public void setThisCommun(Commune thisCommun) {
        this.thisCommun = thisCommun;
    }

    public List<Commune> getCommunesAvailableSelectOne() {
        return communeFacade.findAll();
    }

    public Secteur getThisSecteur() {
        if (thisSecteur == null) {
            thisSecteur = new Secteur();
        }
        return thisSecteur;
    }

    public void setThisSecteur(Secteur thisSecteur) {
        this.thisSecteur = thisSecteur;
    }

    public Quartier getThisQyartie() {
        if (thisQyartie == null) {
            thisQyartie = new Quartier();
        }
        return thisQyartie;
    }

    public void setThisQyartie(Quartier thisQyartie) {
        this.thisQyartie = thisQyartie;
    }

    public List<Activite> getActivites() {
        return activites;
    }

    public void setActivites(List<Activite> activites) {
        this.activites = activites;
    }

    public String getProprieteId() {
        return proprieteId;
    }

    public void setProprieteId(String proprieteId) {
        this.proprieteId = proprieteId;
    }

    public String getGerantId() {
        return gerantId;
    }

    public void setGerantId(String gerantId) {
        this.gerantId = gerantId;
    }

    public String getDesabaleSave() {
        return desabaleSave;
    }

    public void setDesabaleSave(String desabaleSave) {
        this.desabaleSave = desabaleSave;
    }

    public String getDesabaleProp() {
        return desabaleProp;
    }

    public void setDesabaleProp(String desabaleProp) {
        this.desabaleProp = desabaleProp;
    }

    public String getDesabaleGerant() {
        return desabaleGerant;
    }

    public void setDesabaleGerant(String desabaleGerant) {
        this.desabaleGerant = desabaleGerant;
    }


    /*
    *
    * END getters and setters
    *
    *
     */
 /*
    *
    *methodes
    *
    *
     */
    public void secteureByCommun() {

//        if (thisCommun != null) {
        try {
            getThisCommun().setSecteurs(secteurFacade.findSecteureByCommun(getThisCommun()));
            getThisSecteur().setQuartiers(new ArrayList<Quartier>());//pour enisialiser si la commune et changer
            getThisQyartie().setRues(new ArrayList<Rue>());//appel avec get pour eviter le cas ou ThisSecteur et ThisQyartie son null
            getSelected().setComplementAdress("");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("veiller choisire une Commune");
        }
//        } else {
//            thisCommun.setSecteurs(new ArrayList<Secteur>());
//        }
    }

    public void quartierBySecteure() {
//        if (thisSecteur != null) {
        try {
            getThisSecteur().setQuartiers(quartierFacade.findBySecteur(getThisSecteur()));
            getThisQyartie().setRues(new ArrayList<Rue>());
            getSelected().setComplementAdress("");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("veiller choisire un Secteur");
        }
//        } else {
//            thisSecteur.setQuartiers(new ArrayList<Quartier>());
//        }
    }

    public void rueByQuartier() {
//        if (thisQyartie != null) {
        try {
            getThisQyartie().setRues(rueFacade.findByQuartier(getThisQyartie()));
            getSelected().setComplementAdress("");
        } catch (Exception e) {
            JsfUtil.addErrorMessage("veiller choisire un Qyartie");
        }
//        } else {
//            thisQyartie.setRues(new ArrayList<Rue>());
//        }
    }

    public void saveLocal() throws IOException {
        create();
        JsfUtil.addSuccessMessage("add location by Clicking on the local position in the Map");
        setDesabeledPosition("false");
    }

    public void toAddPosition() throws IOException {
        SessionUtil.redirect("/eTaxeCommunal/faces/secured/locale/LocalMap.xhtml");
    }

    public void cancelCreation() {
        setSelected(null);
        setDesabaleGerant("false");
        setDesabaleProp("false");
        setDesabaleSave("true");
        setDesabeledPosition("true");
        setActivites(null);
        setThisCommun(null);
        setGerantId("");
        setProprieteId("");
        emptyModel = null;
    }

    //1 to desableEnable  -1 to ignore
    public void lookUpGerant(int desableEnable) {
        List<Redevable> redevablesRc = redevableFacade.findRedevable(1, gerantId, null, null, null);
        List<Redevable> redevablesCin = redevableFacade.findRedevable(1, null, gerantId, null, null);
        if (redevablesRc.isEmpty() && redevablesCin.isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("GerantSearchIsEmpty"));
        } else {
            if (!redevablesRc.isEmpty()) {
                getSelected().setGerant(redevablesRc.get(0));

            } else {
                getSelected().setGerant(redevablesCin.get(0));
            }
            if (desableEnable > 0) {
                setDesabaleGerant("true");
                setDesabaleSave("false");
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("successe"));
        }
    }

    public void lookUpProprietaire(int desableEnable) {
        List<Redevable> redevablesRc = redevableFacade.findRedevable(2, proprieteId, null, null, null);
        List<Redevable> redevablesCin = redevableFacade.findRedevable(2, null, proprieteId, null, null);
        if (redevablesRc.isEmpty() && redevablesCin.isEmpty()) {
            JsfUtil.addErrorMessage(ResourceBundle.getBundle("/Bundle").getString("ProprietaireSearchIsEmpty"));
        } else {
            if (!redevablesRc.isEmpty()) {
                getSelected().setPropriete(redevablesRc.get(0));

            } else {
                getSelected().setPropriete(redevablesCin.get(0));
            }
            if (desableEnable > 0) {
                setDesabaleProp("true");
                setDesabaleSave("false");
            }
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("successe"));
        }
    }

    public void addLocalesMarkersToMap() {
        setLocals(ejbFacade.findLocals(null, thisCommun, thisSecteur, thisQyartie, selected.getRue(), selected.getComplementAdress(), null, null, null));
        MapModel LocalsModel = new DefaultMapModel();
        for (Locale item : getLocals()) {
            Position p = positionFacade.find(item.getId());
            if (p != null) {
                System.out.println("name :" + item.getNom() + ", lat :" + p.getLat() + ", lng :" + p.getLng());
                LocalsModel.addOverlay(new Marker(new LatLng(p.getLat(), p.getLng()), item.getNom()));
            }
        }
        setEmptyModel(LocalsModel);

    }

    public void marke() throws IOException {
        getPosition().setLat(getLat());
        getPosition().setLng(getLng());
        getSelected().setPosition(getPosition());
//        getPosition().setLocale(getSelected());
        System.out.println(getPosition());
        positionFacade.create(getPosition());
        getFacade().edit(getSelected());
        cancelCreation();
        redirect();
    }

    public void redirect() throws IOException {
        SessionUtil.redirect("/eTaxeCommunal/faces/secured/redevable/CreateNewRedevable.xhtml");
    }

    public void cancelPositionAddition() throws IOException {
        cancelCreation();
        redirect();
    }

    /*
    *
    *END methodes
    *
    *
     */
    //
    //
    //
    //end of edded code
    public LocaleController() {
    }

    public Locale getSelected() {
        if (selected == null) {
            selected = new Locale();
            selected.setId(ejbFacade.generateId("Locale", "id"));
        }
        return selected;
    }

    public void setSelected(Locale selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
    }

    protected void initializeEmbeddableKey() {
    }

    private LocaleFacade getFacade() {
        return ejbFacade;
    }

    public void prepareCreate() {
        cancelCreation();
        initializeEmbeddableKey();
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("LocaleCreated"));
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("LocaleUpdated"));
    }

    public void destroy(Locale locale) {
        if (locale != null) {
            getFacade().remove(getSelected());
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("LocaleDeleted"));
            selected = null;
            items = null;
        }
    }

    public List<Locale> getItems() {
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
                    getFacade().create(getSelected());
                } else if (persistAction == PersistAction.UPDATE) {
                    getFacade().edit(getSelected(), getSelected().getId());
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

    public Locale getLocale(java.lang.Long id) {
        return getFacade().find(id);
    }

    public List<Locale> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Locale> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Locale.class)
    public static class LocaleControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            LocaleController controller = (LocaleController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "localeController");
            return controller.getLocale(getKey(value));
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
            if (object instanceof Locale) {
                Locale o = (Locale) object;
                return getStringKey(o.getId());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Locale.class.getName()});
                return null;
            }
        }

    }

}
