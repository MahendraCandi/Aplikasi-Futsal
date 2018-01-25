/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import aplikasifutsal.data.Alat;
import aplikasifutsal.data.Pembayaran;
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
public class PembayaranController implements Serializable{
    private EntityManagerFactory emf=null;
    
    public PembayaranController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Pembayaran pembayaran)throws Exception{
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(pembayaran);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
     public void update(Pembayaran pembayaran) throws Exception{
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(pembayaran);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode) throws Exception{
        EntityManager em=getEntityManager();
        Pembayaran byr;
        try{
            byr=em.getReference(Pembayaran.class, kode);
            byr.getNoBayar();
            em.getTransaction().begin();
            em.remove(byr);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public Pembayaran findPembayaran(String kode){
        EntityManager em = getEntityManager();
        try{
            return em.find(Pembayaran.class, kode);
        }finally{}
    }
    
    public String kodePembayaranOto(){
        String kode="Bayar-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("select a from Pembayaran a order by a.noBayar desc");
            q.setMaxResults(1);
            Pembayaran byr=(Pembayaran) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Bayar-000");
                String nomorurut = byr.getNoBayar().substring(6);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){}
        return kode;
    }
    
    //belum fix
    public DefaultTableModel showTable(DefaultTableModel model){
        EntityManager em=getEntityManager();
        try{
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            Query q=em.createQuery("SELECT a.noBayar, a.tglBayar, a.noTrans, a.totalJam, a.totalLap, a.kdAlat, a.jumlahAlat, a.totalAlat, a.totalBayar, a.sisaBayar, a.ubay, a.ukem from Pembayaran a");
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
            Query q=em.createQuery("SELECT a.noBayar, a.tglBayar, a.noTrans, a.totalJam, a.totalLap, a.kdAlat, a.jumlahAlat, a.totalAlat, a.totalBayar, a.sisaBayar, a.ubay, a.ukem from Pembayaran a WHERE a.noBayar like '%"+cari+"%'"
                                 + " or a.tglBayar like '%"+cari+"%'"
                                 + " or a.totalJam like '%"+cari+"%'"
                                 + " or a.totalLap like '%"+cari+"%'"
                                 + " or a.kdAlat like '%"+cari+"%'"
                                 + " or a.jumlahAlat like '%"+cari+"%'"
                                 + " or a.totalAlat like '%"+cari+"%'"
                                 + " or a.totalBayar like '%"+cari+"%'"
                                 + " or a.ubay like '%"+cari+"%'"
                                 + " or a.ukem like '%"+cari+"%'");
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
