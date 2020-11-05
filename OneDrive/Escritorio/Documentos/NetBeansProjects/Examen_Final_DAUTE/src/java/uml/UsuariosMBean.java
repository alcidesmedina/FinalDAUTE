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
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
/**
 *
 * @author andie
 */
@Named(value = "usuariosMBean")
@RequestScoped
public class UsuariosMBean {

    /**
     * Creates a new instance of UsuariosMBean
     */
    
    
    public UsuariosMBean() {
    }
    
    private int idusuario; 
    private String usuario; 
    private String contra; 
    private String nivel; 

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContra() {
        return contra;
    }

    public void setContra(String contra) {
        this.contra = contra;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }
    
    public List<Usuarios> getTabla() 
    {
        List<Usuarios> lst = new ArrayList<>();
        try {
            UsuariosJpaController ctrl = new UsuariosJpaController();
            lst = ctrl.findUsuariosEntities();
             
        } catch (Exception e) {
            System.out.print(e);
        }
        return lst;
    }
    
    public void modificar()
    {
        try {
            UsuariosJpaController ctrl = new UsuariosJpaController();
            ctrl.edit(new Usuarios(idusuario, usuario, contra, nivel));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void eliminar()
    {
        try {
            UsuariosJpaController ctrl = new UsuariosJpaController();
            ctrl.destroy(idusuario);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void agregar()
    {
        try {
            UsuariosJpaController ctrl = new UsuariosJpaController();
            ctrl.create(new Usuarios(idusuario, usuario, contra, nivel));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    
    public String sestemp()
    {
        HttpSession session = SessionUtils.getSession();
        session.setAttribute("username", "temp");
        return "sestemp";
    }
    
    
    public String validateUsernamePassword()
    {
        try {
            List<Usuarios> ls = new ArrayList<>();
            UsuariosJpaController ctrl = new UsuariosJpaController();
            ls = ctrl.findUsuariosEntities();

            if(usuario.isEmpty() && contra.isEmpty())
            {
                FacesContext context = FacesContext.getCurrentInstance();
                context.addMessage(null, new FacesMessage("Debe ingresar usuario y contraseña."));
            }
            else
            {
                for(Usuarios us : ls)
                {
                    if(us.getUsuario().equals(usuario) && us.getContra().equals(contra))
                    {
                        setNivel(us.getNivel());
                        String uss = us.getUsuario();
                        String niv = us.getNivel();
                        HttpSession session = SessionUtils.getSession();
                        session.setAttribute("username", uss);
                        session.setAttribute("level", niv);
                        //FacesContext.getCurrentInstance().getExternalContext().dispatch("../index.xhtml");
                        return "admin"; 
                    }
                    else
                    {
                        FacesContext context = FacesContext.getCurrentInstance();
                        context.addMessage(null, new FacesMessage("Usuario o contraseña inválidos. Intente de nuevo."));
                    }
                }
                
            }
        } catch (Exception e) {
            System.out.print(e);
        }
        return "login_index";
    }
    
    public String logout()
    {
        
        HttpSession session = SessionUtils.getSession();
	session.invalidate();
        
	return "salir"; 
    }
    
    public String sesion()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return "usua"; 
    }
    
    
}
