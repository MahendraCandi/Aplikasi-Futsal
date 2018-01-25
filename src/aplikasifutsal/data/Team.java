/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Candi-PC
 */
@Entity
@Table(name = "team")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Team.findAll", query = "SELECT t FROM Team t"),
    @NamedQuery(name = "Team.findByKdTeam", query = "SELECT t FROM Team t WHERE t.kdTeam = :kdTeam"),
    @NamedQuery(name = "Team.findByTglDaftar", query = "SELECT t FROM Team t WHERE t.tglDaftar = :tglDaftar"),
    @NamedQuery(name = "Team.findByNmTeam", query = "SELECT t FROM Team t WHERE t.nmTeam = :nmTeam"),
    @NamedQuery(name = "Team.findByKapten", query = "SELECT t FROM Team t WHERE t.kapten = :kapten"),
    @NamedQuery(name = "Team.findByNoHp", query = "SELECT t FROM Team t WHERE t.noHp = :noHp"),
    @NamedQuery(name = "Team.findByStatus", query = "SELECT t FROM Team t WHERE t.status = :status")})
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_team")
    private String kdTeam;
    @Column(name = "tgl_daftar")
    @Temporal(TemporalType.DATE)
    private Date tglDaftar;
    @Column(name = "nm_team")
    private String nmTeam;
    @Column(name = "kapten")
    private String kapten;
    @Column(name = "no_hp")
    private String noHp;
    @Column(name = "status")
    private String status;
    @Lob
    @Column(name = "keterangan")
    private String keterangan;

    public Team() {
    }

    public Team(String kdTeam) {
        this.kdTeam = kdTeam;
    }

    public String getKdTeam() {
        return kdTeam;
    }

    public void setKdTeam(String kdTeam) {
        this.kdTeam = kdTeam;
    }

    public Date getTglDaftar() {
        return tglDaftar;
    }

    public void setTglDaftar(Date tglDaftar) {
        this.tglDaftar = tglDaftar;
    }

    public String getNmTeam() {
        return nmTeam;
    }

    public void setNmTeam(String nmTeam) {
        this.nmTeam = nmTeam;
    }

    public String getKapten() {
        return kapten;
    }

    public void setKapten(String kapten) {
        this.kapten = kapten;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdTeam != null ? kdTeam.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Team)) {
            return false;
        }
        Team other = (Team) object;
        if ((this.kdTeam == null && other.kdTeam != null) || (this.kdTeam != null && !this.kdTeam.equals(other.kdTeam))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aplikasifutsal.data.Team[ kdTeam=" + kdTeam + " ]";
    }
    
}
