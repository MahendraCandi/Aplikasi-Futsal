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
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findByKdUser", query = "SELECT u FROM User u WHERE u.kdUser = :kdUser"),
    @NamedQuery(name = "User.findByNmUser", query = "SELECT u FROM User u WHERE u.nmUser = :nmUser"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByHakUser", query = "SELECT u FROM User u WHERE u.hakUser = :hakUser")})
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "kd_user")
    private String kdUser;
    @Column(name = "nm_user")
    private String nmUser;
    @Column(name = "password")
    private String password;
    @Column(name = "hak_user")
    private String hakUser;

    public User() {
    }

    public User(String kdUser) {
        this.kdUser = kdUser;
    }

    public String getKdUser() {
        return kdUser;
    }

    public void setKdUser(String kdUser) {
        this.kdUser = kdUser;
    }

    public String getNmUser() {
        return nmUser;
    }

    public void setNmUser(String nmUser) {
        this.nmUser = nmUser;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHakUser() {
        return hakUser;
    }

    public void setHakUser(String hakUser) {
        this.hakUser = hakUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kdUser != null ? kdUser.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.kdUser == null && other.kdUser != null) || (this.kdUser != null && !this.kdUser.equals(other.kdUser))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "aplikasifutsal.data.User[ kdUser=" + kdUser + " ]";
    }
    
}
