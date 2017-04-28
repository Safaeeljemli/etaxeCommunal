/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import bean.Device;
import bean.Userr;
import controller.util.DeviceUtil;
import controller.util.HashageUtil;
import controller.util.SessionUtil;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Lmarbouh Mhamùed
 */
@Stateless
public class UserFacade extends AbstractFacade<Userr> {

    @PersistenceContext(unitName = "mhamed.grp_eTaxeCommunal_war_1.0-SNAPSHOTPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UserFacade() {
        super(Userr.class);
    }
    @EJB
    private HistoryFacade historyFacade;
    @EJB
    private DeviceFacade deviceFacade;

    @Override
    public Userr find(Object id) {
        try {
            Userr user = (Userr) em.createQuery("select u from Userr u where u.login='" + id + "'").getSingleResult();
            if (user != null) {
                return user;
            }
        } catch (Exception e) {
            System.out.println("***** UserFacade Login error");
        }
        return null;
    }

    public int seConnnecter(Userr user) {
        System.out.println(user);
        if (user == null || user.getLogin() == null) {
            System.out.println("-5");
            return -5;
        } else {
            Userr loadedUser = null;
            loadedUser = find(user.getLogin());
            System.out.println("loadedUser.getPasswrd() " + loadedUser.getPasswrd());
            System.out.println("user.getPasswrd : " + user.getPasswrd());
            System.out.println("user.HashPasswrd : " + HashageUtil.sha256(user.getPasswrd()));
            if (loadedUser == null) {
                return -4;
            } else if (loadedUser.isBlocked() == true) {
                return -2;
            } else if (!loadedUser.getPasswrd().equals(HashageUtil.sha256(user.getPasswrd()))) {
                if (loadedUser.getNbrCnx() < 3) {
                    loadedUser.setNbrCnx(loadedUser.getNbrCnx() + 1);
                } else if (loadedUser.getNbrCnx() >= 3) {
                    loadedUser.setBlocked(true);
                    edit(loadedUser);
                }
                return -3;
            } else {
                loadedUser.setNbrCnx(0);
//                loadedUser.setConnected(true);
                edit(loadedUser);
                user = clone(loadedUser);
                if (SessionUtil.registerUser(user)) {
                    Device device = DeviceUtil.getDevice(user);
                    deviceFacade.verifieDeviceAndCreate(device);
                    historyFacade.createHistoryElement(user, 1);
                    return 1;
                } else {
                    return -1;
                }
            }
        }
    }

    public void seDeConnnecter() {
        Userr connectedUser = SessionUtil.getConnectedUser();
        historyFacade.createHistoryElement(connectedUser, 2);
        SessionUtil.unRegisterUser(connectedUser);

    }

    public void clone(Userr source, Userr destination) {
        destination.setLogin(source.getLogin());
        destination.setBlocked(source.isBlocked());
        destination.setCommune(source.getCommune());
        destination.setAdressage(source.isAdressage());
        destination.setLocals(source.isLocals());
        destination.setRedevable(source.isRedevable());
        destination.setTaxes(source.isTaxes());
        destination.setEmail(source.getEmail());
        destination.setNbrCnx(source.getNbrCnx());
        destination.setNom(source.getNom());
        destination.setPasswrd(null);
        destination.setPrenom(source.getPrenom());
        destination.setAdminn(source.isAdminn());
    }

    public Userr clone(Userr source) {
        Userr cloned = new Userr();
        clone(source, cloned);
        return cloned;
    }

}
