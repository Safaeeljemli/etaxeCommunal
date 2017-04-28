/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author PC
 */
public class StatistiqueFacade {
    
    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }
}
