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
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author jdmar
 */
@Named(value = "departamentosMBean")
@RequestScoped
public class DepartamentosMBean {

    /**
     * Creates a new instance of DepartamentosMBean
     */
    public DepartamentosMBean() {
    }    
    
    private int idDepartamento; 
    private String nombreDepto; 
    private int cantEmpleados; 
    private int codTels; 

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepto() {
        return nombreDepto;
    }

    public void setNombreDepto(String nombreDepto) {
        this.nombreDepto = nombreDepto;
    }

    public int getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(int cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    public int getCodTels() {
        return codTels;
    }

    public void setCodTels(int codTels) {
        this.codTels = codTels;
    }
    
    public List<Departamento> getTabla() 
    {
        List<Departamento> lst = new ArrayList<>();
        try {
            DepartamentoJpaController ctrl = new DepartamentoJpaController();
            lst = ctrl.findDepartamentoEntities();
             
        } catch (Exception e) {
            System.out.print(e);
        }
        return lst;
    }
    
    public void modificar()
    {
        try {
            DepartamentoJpaController ctrl = new DepartamentoJpaController();
            ctrl.edit(new Departamento(idDepartamento,nombreDepto,cantEmpleados, codTels));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void eliminar()
    {
        try {
            DepartamentoJpaController ctrl = new DepartamentoJpaController();
            ctrl.destroy(idDepartamento);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void agregar()
    {
        try {
            DepartamentoJpaController ctrl = new DepartamentoJpaController();
            ctrl.create(new Departamento(idDepartamento,nombreDepto,cantEmpleados,codTels));
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public String sesion()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return "deptos"; 
    }
}
