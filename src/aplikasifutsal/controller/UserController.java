/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import aplikasifutsal.data.User;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Candi-PC
 */
public class UserController implements Serializable{
    private EntityManagerFactory emf=null;
    
    public UserController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(User user)throws Exception{
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
     public void update(User user) throws Exception{
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(user);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode) throws Exception{
        EntityManager em=getEntityManager();
        User us;
        try{
            us=em.getReference(User.class, kode);
            us.getKdUser();
            em.getTransaction().begin();
            em.remove(us);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public User findUser(String kode){
        EntityManager em = getEntityManager();
        try{
            return em.find(User.class, kode);
        }finally{}
    }
    
    public String kodeUserOto(){
        String kode="User-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("select a from User a order by a.kdUser desc");
            q.setMaxResults(1);
            User us=(User) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("User-000");
                String nomorurut = us.getKdUser().substring(5);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){}
        return kode;
    }
    
    public DefaultTableModel showTable(DefaultTableModel model){
        EntityManager em=getEntityManager();
        try{
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            Query q=em.createQuery("SELECT a.kdUser, a.nmUser, a.password, a.hakUser from User a");
            List<Object> hasil=(List<Object>) q.getResultList();
            Iterator itr = hasil.iterator();
            while(itr.hasNext()){
                Object[] obj = (Object[]) itr.next();
                model.addRow(obj);
            }
            return model;
        }finally{}
    }
    
    public DefaultTableModel cari(DefaultTableModel model, String cari){
        EntityManager em=getEntityManager();
        try{
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
                            //SQL = SELECT * FROM `kereta` WHERE `KodeKereta` like '%Cimahi%' or `StasiunTujuan` like '%Cimahi%'
            Query q=em.createQuery("SELECT a.kdUser, a.nmUser, a.password, a.hakUser from User a WHERE a.kdUser like '%"+cari+"%'"
                                 + " or a.nmUser like '%"+cari+"%'"
                                 + " or a.password like '%"+cari+"%'"
                                 + " or a.hakUser like '%"+cari+"%'");
            List<Object> hasil=(List<Object>) q.getResultList();
                Iterator itr = hasil.iterator();
                while(itr.hasNext()){
                Object[] obj = (Object[]) itr.next();
                model.addRow(obj);
            }
            return model;
        }finally{}
    }
}
