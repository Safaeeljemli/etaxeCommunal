/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import bean.Activite;
import bean.TauxTaxeBoisson;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class TauxTaxeBoissonFacade extends AbstractFacade<TauxTaxeBoisson> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxTaxeBoissonFacade() {
        super(TauxTaxeBoisson.class);
    }
    
    public TauxTaxeBoisson findTauxTaxeByActivity(Activite activite){
        try {
             return (TauxTaxeBoisson) em.createQuery("SELECT t FROM TauxTaxeBoisson t WHERE t.activite.id="+activite.getId()).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

}
