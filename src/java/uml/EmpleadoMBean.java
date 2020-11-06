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
@Named(value = "empleadoMBean")
@RequestScoped
public class EmpleadoMBean {

    /**
     * Creates a new instance of EmpleadoMBean
     */
    public EmpleadoMBean() {
    }    
    
    private int codEmpleado; 
    private String nombres; 
    private String apellidos; 
    private int idDepartamento; 
    private Float salario; 
    private int edad; 
    private int idUsuario; 

    public int getCodEmpleado() {
        return codEmpleado;
    }

    public void setCodEmpleado(int codEmpleado) {
        this.codEmpleado = codEmpleado;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public Float getSalario() {
        return salario;
    }

    public void setSalario(Float salario) {
        this.salario = salario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public List<Empleado> getTabla() 
    {
        List<Empleado> lst = new ArrayList<>();
        try {
            EmpleadoJpaController ctrl = new EmpleadoJpaController();
            lst = ctrl.findEmpleadoEntities();
             
        } catch (Exception e) {
            System.out.print(e);
        }
        return lst;
    }
    
    public void modificar()
    {
        try {
            EmpleadoJpaController ctrl = new EmpleadoJpaController();
            ctrl.edit(new Empleado(codEmpleado,nombres,apellidos,idDepartamento,salario,edad,idUsuario));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void eliminar()
    {
        try {
            EmpleadoJpaController ctrl = new EmpleadoJpaController();
            ctrl.destroy(codEmpleado);
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public void agregar()
    {
        try {
            EmpleadoJpaController ctrl = new EmpleadoJpaController();
            ctrl.create(new Empleado(codEmpleado,nombres,apellidos,idDepartamento,salario,edad,idUsuario));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
    public String sesion()
    {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        return "empl"; 
    }
    
}
