/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Locale;
import bean.TauxRetardBoisonTrim;
import bean.TauxTaxeBoisson;
import bean.TaxeAnnuelBoisson;
import bean.TaxeTrimBoisson;
import bean.Userr;
import controller.util.CalculeUtils;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class TaxeTrimBoissonFacade extends AbstractFacade<TaxeTrimBoisson> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TaxeTrimBoissonFacade() {
        super(TaxeTrimBoisson.class);
    }
    @EJB
    TaxeAnnuelBoissonFacade taxeAnnuelBoissonFacade;
    @EJB
    TauxTaxeBoissonFacade tauxTaxeBoissonFacade;
    @EJB
    TauxRetardBoisonTrimFacade tauxRetardBoisonTrimFacade;

    public void clone(TaxeTrimBoisson source, TaxeTrimBoisson destination) {
        destination.setChiffreAffaireHT(source.getChiffreAffaireHT());
        destination.setChiffreAffaireTTC(source.getChiffreAffaireTTC());
        destination.setDateActuel(source.getDateActuel());
        destination.setId(source.getId());
        destination.setLocal(source.getLocal());
        destination.setMontantRetardAutreMois(source.getMontantRetardAutreMois());
        destination.setMontantRetardPremierMois(source.getMontantRetardPremierMois());
        destination.setMontantTotalRetard(source.getMontantTotalRetard());
        destination.setMontantTotalTaxe(source.getMontantTotalTaxe());
        destination.setMontantTaxe(source.getMontantTaxe());
        destination.setNumeroTrim(source.getNumeroTrim());
        destination.setRedevable(source.getRedevable());
        destination.setTaxeAnnuelBoisson(source.getTaxeAnnuelBoisson());
        destination.setTaxeYear(source.getTaxeYear());
        destination.setUser(source.getUser());
    }

    public TaxeTrimBoisson clone(TaxeTrimBoisson source) {
        TaxeTrimBoisson cloned = new TaxeTrimBoisson();
        clone(source, cloned);
        return cloned;
    }

    //1:success -1:annee paye -2:trimester payee -3:anne sassi superrieur a anne actuel -4:trim pas passe  -5:tauxTaxeBoisson pas trouver 
    public int createTaxeTrimBoisson(TaxeTrimBoisson taxeTrimBoisson, Userr user) {
        if (taxeTrimBoisson.getTaxeYear().until(LocalDate.now(), ChronoUnit.DAYS) > 0) {
            Object[] respons = taxeAnnuelBoissonFacade.getTaxeAnnuel(taxeTrimBoisson.getLocal(), taxeTrimBoisson.getTaxeYear().getYear(), taxeTrimBoisson.getNumeroTrim());
            int codeRespons = (int) respons[1];
            if (codeRespons < 0) {
                return codeRespons;
            } else {
                TaxeAnnuelBoisson taxeAnnuelBoisson = (TaxeAnnuelBoisson) respons[0];
                LocalDate taxeDate = setTaxeDate(taxeTrimBoisson.getNumeroTrim(), taxeTrimBoisson.getTaxeYear().getYear());
                Long months = taxeDate.until(LocalDate.now(), ChronoUnit.MONTHS);
                if (months < 0) {
                    return -4;
                } else {
                    return createTaxeTrim(taxeTrimBoisson, months, user, taxeAnnuelBoisson);
                }
            }
        }
        System.out.println("-3");
        return -3;
    }

    public TaxeTrimBoisson similerTaxeTrimBoisson(TaxeTrimBoisson anItem) {
        System.out.println(anItem.getNumeroTrim() + "  " + anItem.getTaxeYear().getYear());
        LocalDate taxeDate = setTaxeDate(anItem.getNumeroTrim(), anItem.getTaxeYear().getYear());
        Long months = taxeDate.until(LocalDate.now(), ChronoUnit.MONTHS);
        TauxTaxeBoisson tauxTaxeBoisson = tauxTaxeBoissonFacade.findTauxTaxeByActivity(anItem.getLocal().getTypeLocal());
        if (tauxTaxeBoisson != null) {
            anItem = initTaxeTrim(anItem, new Userr(), new TaxeAnnuelBoisson());
            anItem = calculate(anItem.getChiffreAffaireHT(), tauxTaxeBoisson, months, anItem);
            return anItem;
        }
        return null;
    }

    private TaxeTrimBoisson initTaxeTrim(TaxeTrimBoisson taxeTrimBoisson, Userr user, TaxeAnnuelBoisson taxeAnnuelBoisson) {
        taxeTrimBoisson.setDateActuel(LocalDate.now());
        taxeTrimBoisson.setUser(user);
        taxeTrimBoisson.setTaxeAnnuelBoisson(taxeAnnuelBoisson);
        return taxeTrimBoisson;
    }

    private LocalDate setTaxeDate(int trim, int year) {
        System.out.println("hani f :: setTaxeDate");
        LocalDate taxeDate;
        switch (trim) {
            case 1:
                taxeDate = LocalDate.of(year, 3, 31);
                break;
            case 2:
                taxeDate = LocalDate.of(year, 6, 30);
                break;
            case 3:
                taxeDate = LocalDate.of(year, 9, 30);
                break;
            default:
                taxeDate = LocalDate.of(year, 12, 31);
        }
        return taxeDate;
    }

    public Double calculeChiffreAffaireHT(Locale locale, Double chiffreAffaireTTC) {
        return CalculeUtils.formatAndRoundNumber((chiffreAffaireTTC * 100) / (locale.getTypeLocal().getTva() + 100), RoundingMode.CEILING, 3);
    }

    public Double calculeChiffreAffaireTTC(Locale locale, Double chiffreAffaireHT) {
        return CalculeUtils.formatAndRoundNumber(chiffreAffaireHT + ((chiffreAffaireHT * locale.getTypeLocal().getTva()) / 100), RoundingMode.CEILING, 3);
    }

    private int createTaxeTrim(TaxeTrimBoisson taxeTrimBoisson, Long monthsRetard, Userr user, TaxeAnnuelBoisson taxeAnnuelBoisson) {
        TauxTaxeBoisson tauxTaxeBoisson = tauxTaxeBoissonFacade.findTauxTaxeByActivity(taxeTrimBoisson.getLocal().getTypeLocal());
        if (tauxTaxeBoisson != null) {
            taxeTrimBoisson = initTaxeTrim(taxeTrimBoisson, user, taxeAnnuelBoisson);
            taxeTrimBoisson = calculate(taxeTrimBoisson.getChiffreAffaireHT(), tauxTaxeBoisson, monthsRetard, taxeTrimBoisson);
            if (taxeTrimBoisson != null) {
                taxeAnnuelBoisson.setMontantTaxeannuel(taxeAnnuelBoisson.getMontantTaxeannuel() + taxeTrimBoisson.getMontantTotalTaxe());
                create(taxeTrimBoisson);
                return 1;
            }
            return -6;
        }
        return -5;
    }

    private TaxeTrimBoisson calculate(Double chiffreAffaireHT, TauxTaxeBoisson tauxTaxeBoisson, Long monthsRetard, TaxeTrimBoisson taxeTrimBoisson) {
        Double montantTetardAutreMois;
        Double montantTetardPremierMois;
        Double montantTaxeNormal = (chiffreAffaireHT * tauxTaxeBoisson.getTaux()) / 100;
        taxeTrimBoisson.setMontantTaxe(montantTaxeNormal);
        if (monthsRetard > 0) {
            TauxRetardBoisonTrim tauxRetardBoisonTrim = tauxRetardBoisonTrimFacade.findTauxTaxeByActivity(tauxTaxeBoisson);
            if (tauxRetardBoisonTrim != null) {
                montantTetardPremierMois = chiffreAffaireHT * tauxRetardBoisonTrim.getTauxRetardPremierMois() / 100;
                taxeTrimBoisson.setMontantRetardPremierMois(montantTetardPremierMois);
                monthsRetard--;
                if (monthsRetard > 0) {
                    montantTetardAutreMois = (monthsRetard * chiffreAffaireHT * tauxRetardBoisonTrim.getTauxRetardAutreMois()) / 100;
                    taxeTrimBoisson.setMontantRetardAutreMois(montantTetardAutreMois);
                    taxeTrimBoisson.setMontantTotalRetard(montantTetardAutreMois + montantTetardPremierMois);
                } else {
                    taxeTrimBoisson.setMontantTotalRetard(montantTetardPremierMois);
                }
                taxeTrimBoisson.setMontantTotalTaxe(montantTaxeNormal + taxeTrimBoisson.getMontantTotalRetard());
            } else {
                return null;
            }
        } else {
            taxeTrimBoisson.setMontantTotalTaxe(montantTaxeNormal);
        }
        return taxeTrimBoisson;
    }

//    public List<TaxeTrimBoisson> findStat(int anneeDeb, int anneeFin, Rue rue, Quartier quartier, Commune commune, Secteur secteur) {
//        String query = "SELECT sum(montantTotalTaxe) FROM TaxeTrimBoisson t where 1=1";
//        if (anneeDeb > 0 && anneeFin > 0) {
//            query += "AND t.taxeAnnuelBoisson.annee in(" + anneeDeb + "," + anneeFin + ")";
//        }
//        if (rue == null) {
//            if (quartier == null) {
//                if (secteur == null) {
//                    if (commune != null) {
//                        query += SearchUtil.addConstraint("t.local", "rue.quartier.secteur.commune.id", "=", commune.getId());
//                    }
//                } else {
//                    query += SearchUtil.addConstraint("t.local", "rue.quartier.secteur.id", "=", secteur.getId());
//                }
//            } else {
//                query += SearchUtil.addConstraint("t.local", "rue.quartier.id", "=", quartier.getId());
//            }
//        } else {
//            query += SearchUtil.addConstraint("t.local", "rue.id", "=", rue.getId());
//        }
//        System.out.println(query);
//        return em.createQuery(query).getResultList();
//    }
//
////    public Double findStatByAnnee(Rue rue, int annee, int trim) {
////        List<Locale> locals = localeFacade.findLocalsByRue(rue);
////        String query = null;
////        for (Locale local : locals) {
////            query = "SELECT sum(montantTotalTaxe) FROM TaxeTrimBoisson t WHERE t.local= " + local + "AND t.taxeAnnuelBoisson.annee like '%-" + annee + "' AND t.numeroTrim like '%-" + trim + "'";
////        }
////        return (Double) em.createQuery(query).getSingleResult();
////    }
////
////    public List<Double> findStatByTrim(Rue rue, int annee) {
////        List<Double> stat = new ArrayList<>();
////
////        for (int i = 1; i <= 4; i++) {
////            stat.add(findStatByAnnee(rue, annee, i));
////        }
////        return stat;
////    }
//    //init barChart
//    public BarChartModel intiBarModel(List<TaxeTrimBoisson> taxes, int anneeDeb, int anneeFin) {
//        BarChartModel barModel = new BarChartModel();
//        ChartSeries annee1 = new ChartSeries();
//        ChartSeries annee2 = new ChartSeries();
//        annee1.setLabel("" + anneeDeb);
//        annee2.setLabel("" + anneeFin);
//        for (int i = 1; i < 5; i++) {
//            Double a = 0.0;
//            Double b = 0.0;
//            for (TaxeTrimBoisson taxeTrimB : taxes) {
//                if (taxeTrimB.getTaxeAnnuelBoisson().getAnnee() == anneeDeb && taxeTrimB.getNumeroTrim() == i) {
//                    a += taxeTrimB.getMontantTotalTaxe();
//                }
//                if (taxeTrimB.getTaxeAnnuelBoisson().getAnnee() == anneeFin && taxeTrimB.getNumeroTrim() == i) {
//                    b += taxeTrimB.getMontantTotalTaxe();
//                }
//            }
//            annee2.set("Trimestre" + i, b);
//            annee1.set("Trimestre" + i, a);
//        }
//        barModel.addSeries(annee1);
//        barModel.addSeries(annee2);
//        return barModel;
//    }
//
    public List<TaxeTrimBoisson> findByTaxeAnnuel(TaxeAnnuelBoisson taxeAnnuelBoisson) {
        return em.createQuery("SELECT ttb FROM TaxeTrimBoisson ttb WHERE ttb.taxeAnnuelBoisson.id=" + taxeAnnuelBoisson.getId()).getResultList();
    }

}
