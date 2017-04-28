package filter;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import controller.util.SessionUtil;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lmarbouh Mhamùed
 */
public class SessionExpired implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("Session Created");
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            SessionUtil.redirect("/eTaxeCommunal/faces/Login");
        } catch (IOException ex) {
            Logger.getLogger(SessionExpired.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
