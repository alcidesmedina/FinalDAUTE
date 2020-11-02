/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uml;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author andie
 */
@Entity
@Table(name = "departamento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Departamento.findAll", query = "SELECT d FROM Departamento d")
    , @NamedQuery(name = "Departamento.findByIdDepartamento", query = "SELECT d FROM Departamento d WHERE d.idDepartamento = :idDepartamento")
    , @NamedQuery(name = "Departamento.findByNombreDepto", query = "SELECT d FROM Departamento d WHERE d.nombreDepto = :nombreDepto")
    , @NamedQuery(name = "Departamento.findByCantEmpleados", query = "SELECT d FROM Departamento d WHERE d.cantEmpleados = :cantEmpleados")})
public class Departamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idDepartamento")
    private Integer idDepartamento;
    @Column(name = "nombreDepto")
    private String nombreDepto;
    @Column(name = "cantEmpleados")
    private Integer cantEmpleados;
    @OneToMany(mappedBy = "idDepartamento")
    private List<Empleado> empleadoList = new ArrayList<Empleado>();
    @JoinColumn(name = "codTels", referencedColumnName = "codTels")
    @ManyToOne
    private Telefono codTels;

    public Departamento() {
    }

    public Departamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    Departamento(int idDepartamento, String nombreDepto, int cantEmpleados, Telefono codTels) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(Integer idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombreDepto() {
        return nombreDepto;
    }

    public void setNombreDepto(String nombreDepto) {
        this.nombreDepto = nombreDepto;
    }

    public Integer getCantEmpleados() {
        return cantEmpleados;
    }

    public void setCantEmpleados(Integer cantEmpleados) {
        this.cantEmpleados = cantEmpleados;
    }

    @XmlTransient
    public List<Empleado> getEmpleadoList() {
        return empleadoList;
    }

    public void setEmpleadoList(List<Empleado> empleadoList) {
        this.empleadoList = empleadoList;
    }

    public Telefono getCodTels() {
        return codTels;
    }

    public void setCodTels(Telefono codTels) {
        this.codTels = codTels;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idDepartamento != null ? idDepartamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Departamento)) {
            return false;
        }
        Departamento other = (Departamento) object;
        if ((this.idDepartamento == null && other.idDepartamento != null) || (this.idDepartamento != null && !this.idDepartamento.equals(other.idDepartamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uml.Departamento[ idDepartamento=" + idDepartamento + " ]";
    }
    
}
