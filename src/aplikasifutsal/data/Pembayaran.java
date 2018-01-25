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
@Table(name = "pembayaran")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pembayaran.findAll", query = "SELECT p FROM Pembayaran p"),
    @NamedQuery(name = "Pembayaran.findByNoBayar", query = "SELECT p FROM Pembayaran p WHERE p.noBayar = :noBayar"),
    @NamedQuery(name = "Pembayaran.findByTglBayar", query = "SELECT p FROM Pembayaran p WHERE p.tglBayar = :tglBayar"),
    @NamedQuery(name = "Pembayaran.findByNoTrans", query = "SELECT p FROM Pembayaran p WHERE p.noTrans = :noTrans"),
    @NamedQuery(name = "Pembayaran.findByTotalJam", query = "SELECT p FROM Pembayaran p WHERE p.totalJam = :totalJam"),
    @NamedQuery(name = "Pembayaran.findByTotalLap", query = "SELECT p FROM Pembayaran p WHERE p.totalLap = :totalLap"),
    @NamedQuery(name = "Pembayaran.findByKdAlat", query = "SELECT p FROM Pembayaran p WHERE p.kdAlat = :kdAlat"),
    @NamedQuery(name = "Pembayaran.findByJumlahAlat", query = "SELECT p FROM Pembayaran p WHERE p.jumlahAlat = :jumlahAlat"),
    @NamedQuery(name = "Pembayaran.findByTotalAlat", query = "SELECT p FROM Pembayaran p WHERE p.totalAlat = :totalAlat"),
    @NamedQuery(name = "Pembayaran.findByTotalBayar", query = "SELECT p FROM Pembayaran p WHERE p.totalBayar = :totalBayar"),
    @NamedQuery(name = "Pembayaran.findBySisaBayar", query = "SELECT p FROM Pembayaran p WHERE p.sisaBayar = :sisaBayar"),
    @NamedQuery(name = "Pembayaran.findByUbay", query = "SELECT p FROM Pembayaran p WHERE p.ubay = :ubay"),
    @NamedQuery(name = "Pembayaran.findByUkem", query = "SELECT p FROM Pembayaran p WHERE p.ukem = :ukem")})
public class Pembayaran implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "no_bayar")
    private String noBayar;
    @Column(name = "tgl_bayar")
    @Temporal(TemporalType.DATE)
    private Date tglBayar;
    @Column(name = "no_trans")
    private String noTrans;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "total_jam")
    private Double totalJam;
    @Column(name = "total_lap")
    private Double totalLap;
    @Column(name = "kd_alat")
    private String kdAlat;
    @Column(name = "jumlah_alat")
    private Integer jumlahAlat;
    @Column(name = "total_alat")
    private Double totalAlat;
    @Column(name = "total_bayar")
    private Double totalBayar;
    @Column(name = "sisa_bayar")
    private Double sisaBayar;
    @Column(name = "ubay")
    private Double ubay;
    @Column(name = "ukem")
    private Double ukem;

    public Pembayaran() {
    }

    public Pembayaran(String noBayar) {
        this.noBayar = noBayar;
    }

    public String getNoBayar() {
        return noBayar;
    }

    public void setNoBayar(String noBayar) {
        this.noBayar = noBayar;
    }

    public Date getTglBayar() {
        return tglBayar;
    }

    public void setTglBayar(Date tglBayar) {
        this.tglBayar = tglBayar;
    }

    public String getNoTrans() {
        return noTrans;
    }

    public void setNoTrans(String noTrans) {
        this.noTrans = noTrans;
    }

    public Double getTotalJam() {
        return totalJam;
    }

    public void setTotalJam(Double totalJam) {
        this.totalJam = totalJam;
    }

    public Double getTotalLap() {
        return totalLap;
    }

    public void setTotalLap(Double totalLap) {
        this.totalLap = totalLap;
    }

    public String getKdAlat() {
        return kdAlat;
    }

    public void setKdAlat(String kdAlat) {
        this.kdAlat = kdAlat;
    }

    public Integer getJumlahAlat() {
        return jumlahAlat;
    }

    public void setJumlahAlat(Integer jumlahAlat) {
        this.jumlahAlat = jumlahAlat;
    }

    public Double getTotalAlat() {
        return totalAlat;
    }

    public void setTotalAlat(Double totalAlat) {
        this.totalAlat = totalAlat;
    }

    public Double getTotalBayar() {
        return totalBayar;
    }

    public void setTotalBayar(Double totalBayar) {
        this.totalBayar = totalBayar;
    }

    public Double getSisaBayar() {
        return sisaBayar;
    }

    public void setSisaBayar(Double sisaBayar) {
        this.sisaBayar = sisaBayar;
    }

    public Double getUbay() {
        return ubay;
    }

    public void setUbay(Double ubay) {
        this.ubay = ubay;
    }

    public Double getUkem() {
        return ukem;
    }

    public void setUkem(Double ukem) {
        this.ukem = ukem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (noBayar != null ? noBayar.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pembayaran)) {
            return false;
        }
        Pembayaran other = (Pembayaran) object;
        if ((this.noBayar == null && other.noBayar != null) || (this.noBayar != null && !this.noBayar.equals(other.noBayar))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aplikasifutsal.data.Pembayaran[ noBayar=" + noBayar + " ]";
    }
    
}
