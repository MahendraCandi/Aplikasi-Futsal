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
@Table(name = "data_sewa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataSewa.findAll", query = "SELECT d FROM DataSewa d"),
    @NamedQuery(name = "DataSewa.findByNoTrans", query = "SELECT d FROM DataSewa d WHERE d.noTrans = :noTrans"),
    @NamedQuery(name = "DataSewa.findByTglTrans", query = "SELECT d FROM DataSewa d WHERE d.tglTrans = :tglTrans"),
    @NamedQuery(name = "DataSewa.findByKdTeam", query = "SELECT d FROM DataSewa d WHERE d.kdTeam = :kdTeam"),
    @NamedQuery(name = "DataSewa.findByKdLap", query = "SELECT d FROM DataSewa d WHERE d.kdLap = :kdLap"),
    @NamedQuery(name = "DataSewa.findByDp", query = "SELECT d FROM DataSewa d WHERE d.dp = :dp"),
    @NamedQuery(name = "DataSewa.findByTglMasuk", query = "SELECT d FROM DataSewa d WHERE d.tglMasuk = :tglMasuk"),
    @NamedQuery(name = "DataSewa.findByJamMasuk", query = "SELECT d FROM DataSewa d WHERE d.jamMasuk = :jamMasuk"),
    @NamedQuery(name = "DataSewa.findByJamKeluar", query = "SELECT d FROM DataSewa d WHERE d.jamKeluar = :jamKeluar"),
    @NamedQuery(name = "DataSewa.findByKdUser", query = "SELECT d FROM DataSewa d WHERE d.kdUser = :kdUser")})
public class DataSewa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_trans")
    private String noTrans;
    @Column(name = "tgl_trans")
    @Temporal(TemporalType.DATE)
    private Date tglTrans;
    @Column(name = "kd_team")
    private String kdTeam;
    @Column(name = "kd_lap")
    private String kdLap;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dp")
    private Double dp;
    @Column(name = "tgl_masuk")
    @Temporal(TemporalType.DATE)
    private Date tglMasuk;
    @Column(name = "jam_masuk")
    @Temporal(TemporalType.TIME)
    private Date jamMasuk;
    @Column(name = "jam_keluar")
    @Temporal(TemporalType.TIME)
    private Date jamKeluar;
    @Column(name = "kd_user")
    private String kdUser;
    @Lob
    @Column(name = "keterangan")
    private String keterangan;

    public DataSewa() {
    }

    public DataSewa(String noTrans) {
        this.noTrans = noTrans;
    }

    public String getNoTrans() {
        return noTrans;
    }

    public void setNoTrans(String noTrans) {
        this.noTrans = noTrans;
    }

    public Date getTglTrans() {
        return tglTrans;
    }

    public void setTglTrans(Date tglTrans) {
        this.tglTrans = tglTrans;
    }

    public String getKdTeam() {
        return kdTeam;
    }

    public void setKdTeam(String kdTeam) {
        this.kdTeam = kdTeam;
    }

    public String getKdLap() {
        return kdLap;
    }

    public void setKdLap(String kdLap) {
        this.kdLap = kdLap;
    }

    public Double getDp() {
        return dp;
    }

    public void setDp(Double dp) {
        this.dp = dp;
    }

    public Date getTglMasuk() {
        return tglMasuk;
    }

    public void setTglMasuk(Date tglMasuk) {
        this.tglMasuk = tglMasuk;
    }

    public Date getJamMasuk() {
        return jamMasuk;
    }

    public void setJamMasuk(Date jamMasuk) {
        this.jamMasuk = jamMasuk;
    }

    public Date getJamKeluar() {
        return jamKeluar;
    }

    public void setJamKeluar(Date jamKeluar) {
        this.jamKeluar = jamKeluar;
    }

    public String getKdUser() {
        return kdUser;
    }

    public void setKdUser(String kdUser) {
        this.kdUser = kdUser;
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
        hash += (noTrans != null ? noTrans.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataSewa)) {
            return false;
        }
        DataSewa other = (DataSewa) object;
        if ((this.noTrans == null && other.noTrans != null) || (this.noTrans != null && !this.noTrans.equals(other.noTrans))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aplikasifutsal.data.DataSewa[ noTrans=" + noTrans + " ]";
    }
    
}
