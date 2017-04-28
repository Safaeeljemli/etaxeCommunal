/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.History;
import bean.Userr;
import controller.util.SearchUtil;
import java.time.LocalDateTime;
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
public class HistoryFacade extends AbstractFacade<History> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HistoryFacade() {
        super(History.class);
    }

    public void createHistoryElement(Userr loadedUser, int type) {
        History connexionHistory = new History();
        connexionHistory.setUser(loadedUser);
        if (type == 1) {
            connexionHistory.setType(1);
            connexionHistory.setInOutTimeStamp(LocalDateTime.now());
        }
        if (type == 2) {
            connexionHistory.setType(2);
            connexionHistory.setInOutTimeStamp(LocalDateTime.now());
        }
        create(connexionHistory);
    }

    public List<History> findByConditions(String userName, Date dateMin, Date dateMax, int action) {
            String query = "SELECT h FROM History h WHERE 1=1 ";
            query += SearchUtil.addConstraint("h", "user.login", "=", userName);
            query += SearchUtil.addConstraint("h", "type", "=", action);
            query += SearchUtil.addConstraintMinMaxDate("h", "inOutTimeStamp", dateMin, dateMax);
            return getEntityManager().createQuery(query).getResultList();
    }

}
