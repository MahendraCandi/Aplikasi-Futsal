/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import aplikasifutsal.data.Team;
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
public class TeamController implements Serializable{
    private EntityManagerFactory emf=null;
    
    public TeamController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(Team team)throws Exception{
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(team);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
     public void update(Team team) throws Exception{
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(team);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode) throws Exception{
        EntityManager em=getEntityManager();
        Team tm;
        try{
            tm=em.getReference(Team.class, kode);
            tm.getKdTeam();
            em.getTransaction().begin();
            em.remove(tm);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public Team findTeam(String kode){
        EntityManager em = getEntityManager();
        try{
            return em.find(Team.class, kode);
        }finally{}
    }
    
    public String kodeTeamOto(){
        String kode="Team-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("select a from Team a order by a.kdTeam desc");
            q.setMaxResults(1);
            Team tm=(Team) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Team-000");
                String nomorurut = tm.getKdTeam().substring(5);
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
            Query q=em.createQuery("SELECT a.kdTeam, a.nmTeam, a.kapten, a.noHp, a.tglDaftar, a.status, a.keterangan from Team a");
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
            Query q=em.createQuery("SELECT a.kdTeam, a.nmTeam, a.kapten, a.noHp, a.tglDaftar, a.status, a.keterangan from Team a WHERE a.kdTeam like '%"+cari+"%'"
                                 + " or a.nmTeam like '%"+cari+"%'"
                                 + " or a.kapten like '%"+cari+"%'"
                                 + " or a.noHp like '%"+cari+"%'"
                                 + " or a.tglDaftar like '%"+cari+"%'"
                                 + " or a.keterangan like '%"+cari+"%'"
                                 + " or a.status like '%"+cari+"%'");
            List<Object> hasil=(List<Object>) q.getResultList();
                Iterator itr = hasil.iterator();
                while(itr.hasNext()){
                Object[] obj = (Object[]) itr.next();
                model.addRow(obj);
            }
            return model;
        }finally{}
    }
    
    //PopTeam
    public DefaultTableModel showPopTable(DefaultTableModel model){
        EntityManager em=getEntityManager();
        try{
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            Query q=em.createQuery("SELECT a.kdTeam, a.nmTeam, a.kapten, a.noHp, a.tglDaftar, a.status, a.keterangan from Team a WHERE a.keterangan like '%Belum Registrasi%'");
            List<Object> hasil=(List<Object>) q.getResultList();
            Iterator itr = hasil.iterator();
            while(itr.hasNext()){
                Object[] obj = (Object[]) itr.next();
                model.addRow(obj);
            }
            return model;
        }finally{}
    }
    
    public void updateKeterangan(String kode){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            Query q=em.createQuery("UPDATE Team k SET k.keterangan = 'Telah Registrasi' where k.kdTeam = '"+kode+"'");
            q.executeUpdate();
            em.getTransaction().commit();
        }catch(Exception ex){
            em.close();
        }
    }
    
}
