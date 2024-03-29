package controller.util;

import bean.Commune;
import bean.Redevable;
import bean.Userr;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

public class SessionUtil {

    private static final SessionUtil instance = new SessionUtil();
    private static List<Userr> connectedUsers = new ArrayList();

    private SessionUtil() {
    }

//    public static void attachUserToCommune(Userr user) {
////        Commune thisCommune = user.getCommune();
////        if (!thisCommune.getUsers().contains(user)) {
////            thisCommune.getUsers().add(user);
////        }
//        registerUser(user);
//    }
    public static boolean registerUser(Userr user) {
        if (!isConnected(user)) {
            setAttribute("user", user);
            connectedUsers.add(user);
            return true;
        }
        return false;
    }

    public static void unRegisterUser(Userr user) {
        if (user != null && isConnected(user)) {
            connectedUsers.remove(user);
            getSession().invalidate();
        }
    }

    private static boolean isConnected(Userr user) {
        for (Userr aUser : connectedUsers) {
            if (aUser.getLogin().equals(user.getLogin())) {
                return true;
            }
        }
        return false;
    }

    public static Userr getConnectedUser() {
        return (Userr) getAttribute("user");
    }

//    public static void registerRedevable(Redevable redevable) {
//        setAttribute("redevable", redevable);
//    }
//
//    public static Redevable getConnectedRedevable() {
//        return (Redevable) getAttribute("redevable");
//    }
    public static Commune getCurrentCommune() {
        Userr user = getConnectedUser();
        if (user != null) {
            return user.getCommune();
        }
        return new Commune();
    }

    public static SessionUtil getInstance() {
        return instance;
    }

    public static HttpSession getSession() {
        return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static void redirect(String pagePath) throws IOException {
        if (!pagePath.endsWith(".xhtml")) {
            pagePath += ".xhtml";
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect(pagePath);

    }

    private static boolean isContextOk(FacesContext fc) {
        boolean res = (fc != null
                && fc.getExternalContext() != null
                && fc.getExternalContext().getSession(false) != null);
        return res;
    }

    private static HttpSession getSession(FacesContext fc) {
        return (HttpSession) fc.getExternalContext().getSession(false);
    }

    public static Object getAttribute(String cle) {
        FacesContext fc = FacesContext.getCurrentInstance();
        Object res = null;
        if (isContextOk(fc)) {
            res = getSession(fc).getAttribute(cle);
        }
        return res;
    }

    public static void setAttribute(String cle, Object valeur) {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null && fc.getExternalContext() != null) {
            getSession(fc).setAttribute(cle, valeur);
        }
    }
}
