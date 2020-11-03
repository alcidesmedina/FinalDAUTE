/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andie
 */
@Named(value = "sessionUtils")
@RequestScoped
public class SessionUtils {

    /**
     * Creates a new instance of SessionUtils
     */
    public SessionUtils() {
    }
    
    public static HttpSession getSession()
    {
	return (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() 
    {
	return (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
    }

    public static String getUserName()
    {
	HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	return session.getAttribute("username").toString();
    }

    public static String getUserId()
    {
	HttpSession session = getSession();
	if (session != null) return (String) session.getAttribute("userid");
        else return null;
    }
    
}
