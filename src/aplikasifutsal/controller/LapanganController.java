/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import aplikasifutsal.data.Alat;
import aplikasifutsal.data.SewaLapangan;
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
public class LapanganController implements Serializable {
    private EntityManagerFactory emf=null;
    
    public LapanganController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(SewaLapangan sewaLapangan)throws Exception{
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(sewaLapangan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
     public void update(SewaLapangan sewaLapangan) throws Exception{
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(sewaLapangan);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode) throws Exception{
        EntityManager em=getEntityManager();
        SewaLapangan lap;
        try{
            lap=em.getReference(SewaLapangan.class, kode);
            lap.getKdLap();
            em.getTransaction().begin();
            em.remove(lap);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public SewaLapangan findSewaLapangan(String kode){
        EntityManager em = getEntityManager();
        try{
            return em.find(SewaLapangan.class, kode);
        }finally{}
    }
    
    public String kodeSewaLapanganOto(){
        String kode="Lap-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("select a from SewaLapangan a order by a.kdLap desc");
            q.setMaxResults(1);
            SewaLapangan lap=(SewaLapangan) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Lap-000");
                String nomorurut = lap.getKdLap().substring(4);
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
            Query q=em.createQuery("SELECT a.kdLap, a.jenisLap, a.hrgLap from SewaLapangan a");
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
            Query q=em.createQuery("SELECT a.kdLap, a.jenisLap, a.hrgLap from SewaLapangan a WHERE a.kdLap like '%"+cari+"%'"
                                 + " or a.jenisLap like '%"+cari+"%'"
                                 + " or a.hrgLap like '%"+cari+"%'");
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
