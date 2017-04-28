/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import bean.Quartier;
import bean.Rue;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class RueFacade extends AbstractFacade<Rue> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RueFacade() {
        super(Rue.class);
    }
    public void clone(Rue rueSource, Rue rueDestination) {
        rueDestination.setId(rueSource.getId());
        rueDestination.setName(rueSource.getName());
        rueDestination.setQuartier(rueSource.getQuartier());
    }

    public Rue clone(Rue rue) {
        Rue cloned = new Rue();
        clone(rue, cloned);
        return cloned;
    }
    public List<Rue> findByQuartier(Quartier quartier){
    return em.createQuery("SELECT rue FROM Rue rue WHERE rue.quartier.id='"+quartier.getId()+"'").getResultList();
}

}
