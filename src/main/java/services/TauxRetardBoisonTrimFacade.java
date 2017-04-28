/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.TauxTaxeBoisson;
import bean.TauxRetardBoisonTrim;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class TauxRetardBoisonTrimFacade extends AbstractFacade<TauxRetardBoisonTrim> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TauxRetardBoisonTrimFacade() {
        super(TauxRetardBoisonTrim.class);
    }

    public TauxRetardBoisonTrim findTauxTaxeByActivity(TauxTaxeBoisson tauxBoissonTaxe) {
        try {
            return (TauxRetardBoisonTrim) em.createQuery("SELECT tr FROM TauxRetardBoisonTrim tr WHERE tr.tauxBoissonTaxe.id=" + tauxBoissonTaxe.getId()).getSingleResult();

        } catch (NoResultException e) {
            return null;
        }
    }

}
