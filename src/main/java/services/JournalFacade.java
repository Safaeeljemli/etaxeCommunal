/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Journal;
import controller.util.SearchUtil;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class JournalFacade extends AbstractFacade<Journal> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public JournalFacade() {
        super(Journal.class);
    }

    public List<Journal> findByConditions(String userName, Date dateMin, Date dateMax, int action) {
        String query = "SELECT j FROM Journal j WHERE 1=1 ";
        query += SearchUtil.addConstraint("j", "user.login", "=", userName);
        query += SearchUtil.addConstraint("j", "typeDaction", "=", action);
        query += SearchUtil.addConstraintMinMaxDate("j", "dateDeModification", dateMin, dateMax);
        return getEntityManager().createQuery(query).getResultList();
    }

}
