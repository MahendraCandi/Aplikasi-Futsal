/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.data;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Candi-PC
 */
@Entity
@Table(name = "alat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Alat.findAll", query = "SELECT a FROM Alat a"),
    @NamedQuery(name = "Alat.findByKdAlat", query = "SELECT a FROM Alat a WHERE a.kdAlat = :kdAlat"),
    @NamedQuery(name = "Alat.findBySewaAlat", query = "SELECT a FROM Alat a WHERE a.sewaAlat = :sewaAlat"),
    @NamedQuery(name = "Alat.findByHrgAlat", query = "SELECT a FROM Alat a WHERE a.hrgAlat = :hrgAlat")})
public class Alat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_alat")
    private String kdAlat;
    @Basic(optional = false)
    @Column(name = "sewa_alat")
    private String sewaAlat;
    @Basic(optional = false)
    @Column(name = "hrg_alat")
    private double hrgAlat;

    public Alat() {
    }

    public Alat(String kdAlat) {
        this.kdAlat = kdAlat;
    }

    public Alat(String kdAlat, String sewaAlat, double hrgAlat) {
        this.kdAlat = kdAlat;
        this.sewaAlat = sewaAlat;
        this.hrgAlat = hrgAlat;
    }

    public String getKdAlat() {
        return kdAlat;
    }

    public void setKdAlat(String kdAlat) {
        this.kdAlat = kdAlat;
    }

    public String getSewaAlat() {
        return sewaAlat;
    }

    public void setSewaAlat(String sewaAlat) {
        this.sewaAlat = sewaAlat;
    }

    public double getHrgAlat() {
        return hrgAlat;
    }

    public void setHrgAlat(double hrgAlat) {
        this.hrgAlat = hrgAlat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdAlat != null ? kdAlat.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Alat)) {
            return false;
        }
        Alat other = (Alat) object;
        if ((this.kdAlat == null && other.kdAlat != null) || (this.kdAlat != null && !this.kdAlat.equals(other.kdAlat))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aplikasifutsal.data.Alat[ kdAlat=" + kdAlat + " ]";
    }
    
}
