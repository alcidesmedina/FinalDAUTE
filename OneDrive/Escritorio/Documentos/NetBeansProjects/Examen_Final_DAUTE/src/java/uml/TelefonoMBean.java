/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author andie
 */
@Named(value = "telefonoMBean")
@RequestScoped
public class TelefonoMBean {

    /**
     * Creates a new instance of TelefonoMBean
     */
    public TelefonoMBean() {
    }

    public TelefonoMBean(int codTels) {
        this.codTels = codTels;
    }
        
    private int codTels;
    private String telefono; 
    private String telDescrip; 

    public int getCodTels() {
        return codTels;
    }

    public void setCodTels(int codTels) {
        this.codTels = codTels;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getTelDescrip() {
        return telDescrip;
    }

    public void setTelDescrip(String telDescrip) {
        this.telDescrip = telDescrip;
    }
    
    public List<Telefono> getTabla() 
    {
        List<Telefono> lst = new ArrayList<>();
        try {
            TelefonoJpaController ctrl = new TelefonoJpaController();
            lst = ctrl.findTelefonoEntities();
             
        } catch (Exception e) {
            System.out.print(e);
        }
        return lst;
    }
    
    public void modificar()
    {
        try {
            TelefonoJpaController ctrl = new TelefonoJpaController();
            ctrl.edit(new Telefono(codTels, telefono, telDescrip));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void eliminar()
    {
        try {
            TelefonoJpaController ctrl = new TelefonoJpaController();
            ctrl.destroy(codTels);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void agregar()
    {
        try {
            TelefonoJpaController ctrl = new TelefonoJpaController();
            ctrl.create(new Telefono(codTels, telefono, telDescrip));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public String sesion()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return "tele"; 
    }
    
    public String indexsesion()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return "indexses"; 
    }
    
}
