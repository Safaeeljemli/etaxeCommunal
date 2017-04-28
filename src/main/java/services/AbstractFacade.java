/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package services;

import bean.Journal;
import bean.Userr;
import com.google.gson.Gson;
import controller.util.SessionUtil;
import java.time.LocalDateTime;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.inject.Instance;
import javax.persistence.EntityManager;

/**
 *
 * @author Lmarbouh Mhamùed
 * @param <T>
 */
public abstract class AbstractFacade<T> {

    @EJB
    private JournalFacade journalFacade;
    private Class<T> entityClass;
    private Gson gson;
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    public void edit(T entity, Object id){
        Journal journal=new Journal();
        journal.setDateDeModification(LocalDateTime.now());
        journal.setTypeDaction(1);
        journal.setUser(SessionUtil.getConnectedUser());
        journal.setNewValue(entity.toString());
        if (id instanceof Double) {
            journal.setOldeValue(find((Double)id).toString());
        }
        if (id instanceof String) {
            journal.setOldeValue(find((String)id).toString());
        }
        edit(entity);
        journalFacade.create(journal);
    }

    public void remove(T entity) {
        Journal journal=new Journal();
        journal.setDateDeModification(LocalDateTime.now());
        journal.setTypeDaction(2);
        journal.setUser(SessionUtil.getConnectedUser());
        journal.setOldeValue(entity.toString());
        getEntityManager().remove(getEntityManager().merge(entity));
        journalFacade.create(journal);
    }

    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0] + 1);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }
    
    public Long generateId(String beanName, String idName) {
        List<Long> maxId = getEntityManager().createQuery(" Select max(item." + idName + ") FROM " + beanName + " item").getResultList();
        if (maxId == null || maxId.isEmpty() || maxId.get(0) == null) {
            return 1L;
        }
        return maxId.get(0) + 1;
    }

        

}
