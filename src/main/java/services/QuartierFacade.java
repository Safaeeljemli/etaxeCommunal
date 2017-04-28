/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Quartier;
import bean.Secteur;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class QuartierFacade extends AbstractFacade<Quartier> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;
 
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuartierFacade() {
        super(Quartier.class);
    }
    public void clone(Quartier quartierSource, Quartier quartierDestination) {
        quartierDestination.setId(quartierSource.getId());
        quartierDestination.setName(quartierSource.getName());
        quartierDestination.setSecteur(quartierSource.getSecteur());
    }

    public Quartier clone(Quartier quartier) {
        Quartier cloned = new Quartier();
        clone(quartier, cloned);
        return cloned;
    }
    public List<Quartier> findBySecteur(Secteur secteur){
    return em.createQuery("SELECT qu FROM Quartier qu WHERE qu.secteur.id='"+secteur.getId()+"'").getResultList();
}
     public List<Quartier> findByNomQuartier(String nom){
    return em.createQuery("SELECT qu FROM Quartier qu WHERE qu.name='"+nom+"'").getResultList();
}
}
