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
@Table(name = "sewa_lapangan")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SewaLapangan.findAll", query = "SELECT s FROM SewaLapangan s"),
    @NamedQuery(name = "SewaLapangan.findByKdLap", query = "SELECT s FROM SewaLapangan s WHERE s.kdLap = :kdLap"),
    @NamedQuery(name = "SewaLapangan.findByJenisLap", query = "SELECT s FROM SewaLapangan s WHERE s.jenisLap = :jenisLap"),
    @NamedQuery(name = "SewaLapangan.findByHrgLap", query = "SELECT s FROM SewaLapangan s WHERE s.hrgLap = :hrgLap")})
public class SewaLapangan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_lap")
    private String kdLap;
    @Column(name = "jenis_lap")
    private String jenisLap;
    @Basic(optional = false)
    @Column(name = "hrg_lap")
    private double hrgLap;

    public SewaLapangan() {
    }

    public SewaLapangan(String kdLap) {
        this.kdLap = kdLap;
    }

    public SewaLapangan(String kdLap, double hrgLap) {
        this.kdLap = kdLap;
        this.hrgLap = hrgLap;
    }

    public String getKdLap() {
        return kdLap;
    }

    public void setKdLap(String kdLap) {
        this.kdLap = kdLap;
    }

    public String getJenisLap() {
        return jenisLap;
    }

    public void setJenisLap(String jenisLap) {
        this.jenisLap = jenisLap;
    }

    public double getHrgLap() {
        return hrgLap;
    }

    public void setHrgLap(double hrgLap) {
        this.hrgLap = hrgLap;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdLap != null ? kdLap.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SewaLapangan)) {
            return false;
        }
        SewaLapangan other = (SewaLapangan) object;
        if ((this.kdLap == null && other.kdLap != null) || (this.kdLap != null && !this.kdLap.equals(other.kdLap))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aplikasifutsal.data.SewaLapangan[ kdLap=" + kdLap + " ]";
    }
    
}
