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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jdmar
 */
@Entity
@Table(name = "telefono")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telefono.findAll", query = "SELECT t FROM Telefono t")
    , @NamedQuery(name = "Telefono.findByCodTels", query = "SELECT t FROM Telefono t WHERE t.codTels = :codTels")
    , @NamedQuery(name = "Telefono.findByTelefono", query = "SELECT t FROM Telefono t WHERE t.telefono = :telefono")
    , @NamedQuery(name = "Telefono.findByTelDescrip", query = "SELECT t FROM Telefono t WHERE t.telDescrip = :telDescrip")})
public class Telefono implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codTels")
    private Integer codTels;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "telDescrip")
    private String telDescrip;
    @OneToMany(mappedBy = "codTels")
    private List<Departamento> departamentoList = new ArrayList<Departamento>();

    public Telefono() {
    }

    public Telefono(Integer codTels) {
        this.codTels = codTels;
    }

    public Telefono(Integer codTels, String telefono, String telDescrip) {
        this.codTels = codTels;
        this.telefono = telefono;
        this.telDescrip = telDescrip;
    }

   
    public Integer getCodTels() {
        return codTels;
    }

    public void setCodTels(Integer codTels) {
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

    @XmlTransient
    public List<Departamento> getDepartamentoList() {
        return departamentoList;
    }

    public void setDepartamentoList(List<Departamento> departamentoList) {
        this.departamentoList = departamentoList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codTels != null ? codTels.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Telefono)) {
            return false;
        }
        Telefono other = (Telefono) object;
        if ((this.codTels == null && other.codTels != null) || (this.codTels != null && !this.codTels.equals(other.codTels))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "uml.Telefono[ codTels=" + codTels + " ]";
    }
    
}
