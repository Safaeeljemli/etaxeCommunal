/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Locale;
import bean.Position;
import controller.util.SearchUtil;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author safa
 */
@Stateless
public class PositionFacade extends AbstractFacade<Position> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PositionFacade() {
        super(Position.class);
    }

    public Position findPositionByLocal(Locale locale) {
        String query = "SELECT pos FROM Position pos WHERE 1=1 ";
        query += SearchUtil.addConstraint("pos", "locale.id", "=", locale.getId());
        Position p=null;
        try {
            System.out.println(query);
            p = (Position) em.createQuery(query).getSingleResult();
        } catch (Exception e) {
            return p;
        }
        return p;
    }

}
