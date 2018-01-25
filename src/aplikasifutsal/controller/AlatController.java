/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import aplikasifutsal.data.Alat;
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
public class AlatController implements Serializable{
    private EntityManagerFactory emf=null;
    
    public AlatController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Alat alat)throws Exception{
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(alat);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
     public void update(Alat alat) throws Exception{
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(alat);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode) throws Exception{
        EntityManager em=getEntityManager();
        Alat alt;
        try{
            alt=em.getReference(Alat.class, kode);
            alt.getKdAlat();
            em.getTransaction().begin();
            em.remove(alt);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public Alat findAlat(String kode){
        EntityManager em = getEntityManager();
        try{
            return em.find(Alat.class, kode);
        }finally{}
    }
    
    public String kodeAlatOto(){
        String kode="Alat-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("select a from Alat a order by a.kdAlat desc");
            q.setMaxResults(1);
            Alat alt=(Alat) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Alat-000");
                String nomorurut = alt.getKdAlat().substring(5);
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
            Query q=em.createQuery("SELECT a.kdAlat, a.sewaAlat, a.hrgAlat from Alat a");
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
            Query q=em.createQuery("SELECT a.kdAlat, a.sewaAlat, a.hrgAlat from Alat a WHERE a.kdAlat like '%"+cari+"%'"
                                 + " or a.sewaAlat like '%"+cari+"%'"
                                 + " or a.hrgAlat like '%"+cari+"%'");
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
