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
@Named(value = "empleadoMBean")
@RequestScoped
public class EmpleadoMBean {

    /**
     * Creates a new instance of EmpleadoMBean
     */
    public EmpleadoMBean() {
    }    
    
    int codEmpleado; 
    String nombres; 
    String apellidos; 
    Departamento idDepartamento; 
    double salario; 
    int edad; 
    Usuarios idUsuario; 

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

    public Departamento getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Departamento idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public Usuarios getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuarios idUsuario) {
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
            ctrl.edit(new Empleado(codEmpleado, nombres, apellidos, Float.NaN, edad, idDepartamento, idUsuario));
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
            ctrl.create(new Empleado(codEmpleado, nombres, apellidos, Float.NaN, edad, idDepartamento, idUsuario));
        } catch (Exception e) {
            System.out.print(e);
        }
    }
    
}
