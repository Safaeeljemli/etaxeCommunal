/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import bean.Commune;
import bean.Quartier;
import bean.Rue;
import bean.Secteur;
import bean.TaxeTrimBoisson;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import services.TaxeTrimBoissonFacade;

/**
 *
 * @author PC
 */
@Named("statistiqueController")
@SessionScoped
//@ViewScoped
public class StatistiqueController implements Serializable {
    @EJB
    private services.TaxeTrimBoissonFacade taxeFacade;
  
    @EJB
    private services.CommuneFacade ejbFacadeC;
    @EJB
    private services.SecteurFacade secteurFacade;
    private BarChartModel barModel;
    @EJB
    private services.QuartierFacade quartierFacade;
    @EJB
    private services.RueFacade rueFacade;
    private int anneeDeb;
    private int anneeFin;
    private Rue rue=null;
    private Quartier quartier=null;
    private Secteur secteur=null;
    private Commune commune=null;
    private  List<TaxeTrimBoisson> taxes;

    public StatistiqueController() {

    }

    public void createBarChar() {
        System.out.println("createBarChar");
//        taxes = taxeFacade.findStat(anneeDeb, anneeFin, rue, quartier, commune, secteur);
//        setBarModel(taxeFacade.intiBarModel(taxes, anneeDeb, anneeFin));
        getBarModel().setTitle("Statistique");
        getBarModel().setLegendPosition("ne");
        getBarModel().setAnimate(true);
        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel("trimestre");
        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel("Montant");
        yAxis.setMin(0);
        yAxis.setMax(10000);
    }
        //        ChartSeries annee2 = new ChartSeries();
        //        annee1.setLabel("Annee Debut");
        //        annee2.setLabel("Annee Fin");
        //        for (int i = 1; i <= 4; i++) {
        //
        //            annee1.set("Trimestre" + i, taxeFacade.findStatByAnnee(rue, anneeDeb, i));//findStatByAnnee(rue, annee1,i));
        //
        //            annee2.set("Trimestre" + i, taxeFacade.findStatByAnnee(rue, anneeFin, i));
        //        }
        //
        //        barModel.addSeries(annee1);
        //        barModel.addSeries(annee2);
        //        return barModel;
        //    }
    public void secteureByCommun() {
        if (commune != null) {
            commune.setSecteurs(secteurFacade.findSecteureByCommun(commune));
            getSecteur().setQuartiers(new ArrayList<Quartier>());
            getQuartier().setRues(new ArrayList<Rue>());
        } else {
            commune.setSecteurs(new ArrayList<Secteur>());
        }
    }
    
    public void quartierBySecteure() {
        if (secteur != null) {
            secteur.setQuartiers(quartierFacade.findBySecteur(secteur));
        } else {
            secteur.setQuartiers(new ArrayList<Quartier>());
        }
    }

    public void rueByQuartier() {
        if (quartier != null) {
            quartier.setRues(rueFacade.findByQuartier(quartier));
        } else {
            quartier.setRues(new ArrayList<Rue>());
        }
    }
    
    public List<Commune> getCommunesAvailableSelectOne() {
        return ejbFacadeC.findAll();
    }

    public BarChartModel getBarModel() {
        if (barModel == null) {
            barModel = new BarChartModel();
        }
        return barModel;
    }

    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    public int getAnneeDeb() {
        return anneeDeb;
    }

    public void setAnneeDeb(int anneeDeb) {
        this.anneeDeb = anneeDeb;
    }

    public int getAnneeFin() {
        return anneeFin;
    }

    public void setAnneeFin(int anneeFin) {
        this.anneeFin = anneeFin;
    }

    public Rue getRue() {
        if (rue == null) {
            rue = new Rue();
        }
        return rue;
    }

    public void setRue(Rue rue) {
        this.rue = rue;
    }

    public TaxeTrimBoissonFacade getTaxeFacade() {
        return taxeFacade;
    }

    public void setTaxeFacade(TaxeTrimBoissonFacade taxeFacade) {
        this.taxeFacade = taxeFacade;
    }

    public Quartier getQuartier() {
        return quartier;
    }

    public void setQuartier(Quartier quartier) {
        this.quartier = quartier;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Commune getCommune() {
        return commune;
    }

    public void setCommune(Commune commune) {
        this.commune = commune;
    }

    public List<TaxeTrimBoisson> getTaxes() {
        return taxes;
    }

    public void setTaxes(List<TaxeTrimBoisson> taxes) {
        this.taxes = taxes;
    }
    

}
