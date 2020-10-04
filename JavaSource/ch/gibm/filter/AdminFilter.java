package ch.gibm.filter;

import ch.gibm.entity.User;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AdminFilter implements Filter {

    private static List<String> allowedURIs;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (allowedURIs == null) {
            allowedURIs = new ArrayList<String>();
            allowedURIs.add(filterConfig.getInitParameter("loginActionURI"));
            allowedURIs.add("/JSFApp/javax.faces.resource/main.css.xhtml");
            allowedURIs.add("/JSFApp/javax.faces.resource/theme.css.xhtml");
            allowedURIs.add("/JSFApp/javax.faces.resource/primefaces.js.xhtml");
            allowedURIs.add("/JSFApp/javax.faces.resource/primefaces.css.xhtml");

            allowedURIs.add("/JSFApp/javax.faces.resource/jquery/jquery.js.xhtml");
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession(); //1

        if (session.isNew()) { //2
            doLogin(servletRequest, servletResponse, req);
            return;
        }

        User user = (User) session.getAttribute("user"); //3

        if (user == null && !allowedURIs.contains(req.getRequestURI())) { //4
            System.out.println(req.getRequestURI());
            doLogin(servletRequest, servletResponse, req);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse); //5
    }

    @Override
    public void destroy() {

    }

    protected void doLogin(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/pages/public/login.xhtml");
        rd.forward(request, response);
    }

    protected void accessDenied(ServletRequest request, ServletResponse response, HttpServletRequest req) throws ServletException, IOException {
        RequestDispatcher rd = req.getRequestDispatcher("/pages/public/accessDenied.xhtml");
        rd.forward(request, response);
    }
}
