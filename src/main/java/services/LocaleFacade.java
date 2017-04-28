/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Activite;
import bean.Commune;
import bean.Locale;
import bean.Quartier;
import bean.Redevable;
import bean.Rue;
import bean.Secteur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import controller.util.SearchUtil;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class LocaleFacade extends AbstractFacade<Locale> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public LocaleFacade() {
        super(Locale.class);
    }

    public List<Locale> findLocals(String nom, Commune commune, Secteur secteur, Quartier quartier, Rue rue,
            String complementAdress, Activite typeLocal, Redevable gerant,
            Redevable propritaire) {

        String query = "SELECT l FROM Locale l WHERE 1=1 ";
        query += SearchUtil.addConstraint("l", "nom", "=", nom);
        if (commune != null) {
            query += SearchUtil.addConstraint("l", "rue.quartier.secteur.commune.id", "=", commune.getId());
        }
        if (secteur != null) {
            query += SearchUtil.addConstraint("l", "rue.quartier.secteur.id", "=", secteur.getId());
        }
        if (quartier != null) {
            query += SearchUtil.addConstraint("l", "rue.quartier.id", "=", quartier.getId());
        }
        if (rue != null) {
            query += SearchUtil.addConstraint("l", "rue.id", "=", rue.getId());
        }
        if (!"".equals(complementAdress)) {
            query += SearchUtil.addConstraint("l", "complementAdress", "=", complementAdress);
        }
        if (typeLocal != null) {
            query += SearchUtil.addConstraint("l", "typeLocal.id", "=", typeLocal.getId());
        }
        if (gerant != null) {
            query += SearchUtil.addConstraint("l", "gerant.id", "=", gerant.getId());
        }
        if (propritaire != null) {
            query += SearchUtil.addConstraint("l", "propriete.id", "=", propritaire.getId());
        }
        System.out.println("hahya requet  :: " + query);
        return em.createQuery(query).getResultList();
    }

    public List<Locale> findLocalsByRue(Rue rue) {
        String query = "SELECT l FROM Locale l WHERE 1=1 ";
        query += SearchUtil.addConstraint("l", "rue.id", "=", rue.getId());
        return em.createQuery(query).getResultList();
    }

}
