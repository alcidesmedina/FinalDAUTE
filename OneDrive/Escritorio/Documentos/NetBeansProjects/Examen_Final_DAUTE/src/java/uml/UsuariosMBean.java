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
    
}
