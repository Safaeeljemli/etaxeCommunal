/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import bean.Commune;
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
public class SecteurFacade extends AbstractFacade<Secteur> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SecteurFacade() {
        super(Secteur.class);
    }

     public void clone(Secteur secteurSource, Secteur secteurDestination) {
        secteurDestination.setId(secteurSource.getId());
        secteurDestination.setName(secteurSource.getName());
        secteurDestination.setCommune(secteurSource.getCommune());
    }

    public Secteur clone(Secteur secteur) {
        Secteur cloned = new Secteur();
        clone(secteur, cloned);
        return cloned;
    }
    public List<Secteur> findSecteureByCommun(Commune commune){
        return  em.createQuery("SELECT s FROM Secteur s WHERE s.commune.id='"+commune.getId()+"'").getResultList();
    }
    public List<Secteur> secteureByName(String nom){
    return em.createQuery("SELECT qu FROM Quartier qu WHERE s.commune.name='"+nom+"'").getResultList();
}
}
