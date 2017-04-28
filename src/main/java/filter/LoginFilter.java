package filter;

import bean.Userr;
import controller.util.SessionUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Lmarbouh Mhamùed
 */
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        Userr user = SessionUtil.getConnectedUser();
        if (user == null || !user.isBlocked()) {
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath+"/faces/Login.xhtml");
        } else {
            chain.doFilter(request, response);
        }

    }

    @Override
    public void destroy() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
