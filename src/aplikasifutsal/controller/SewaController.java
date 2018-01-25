/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import aplikasifutsal.data.DataSewa;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class SewaController {
    private EntityManagerFactory emf=null;
    
    public SewaController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    public void save(DataSewa sewa)throws Exception{
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            em.persist(sewa);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
     public void update(DataSewa sewa) throws Exception{
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            em.merge(sewa);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public void delete(String kode) throws Exception{
        EntityManager em=getEntityManager();
        DataSewa sw;
        try{
            sw=em.getReference(DataSewa.class, kode);
            sw.getNoTrans();
            em.getTransaction().begin();
            em.remove(sw);
            em.getTransaction().commit();
        }catch(Exception ex){}
    }
    
    public DataSewa findDataSewa(String kode){
        EntityManager em = getEntityManager();
        try{
            return em.find(DataSewa.class, kode);
        }finally{}
    }
    
    public String kodeDataSewaOto(){
        String kode="Trans-001";
        EntityManager em=null;
        try{
            em=getEntityManager();
            Query q=em.createQuery("select a from DataSewa a order by a.noTrans desc");
            q.setMaxResults(1);
            DataSewa sw=(DataSewa) q.getSingleResult();
            if(q!=null){
                DecimalFormat formatnomor = new DecimalFormat("Trans-000");
                String nomorurut = sw.getNoTrans().substring(6);
                kode=formatnomor.format(Double.parseDouble(nomorurut)+1);
            }
        }catch(NoResultException ex){}
        return kode;
    }
    
    //FormSewa
    public DefaultTableModel showTable(DefaultTableModel model){
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            Connection connect=em.unwrap(Connection.class);
            Statement st=(Statement) connect.createStatement();
            ResultSet rs=st.executeQuery("SELECT no_trans, tgl_trans, kd_team, team.nm_team, kd_lap, sewa_lapangan.jenis_lap, dp, tgl_masuk, jam_masuk, jam_keluar, kd_user, data_sewa.keterangan\n" +
                                         "FROM data_sewa\n" +
                                         "INNER JOIN team USING (kd_team)\n" +
                                         "INNER JOIN sewa_lapangan USING (kd_lap) ORDER BY no_trans DESC");
            while(rs.next()){
                Object[] obj = new Object[12];
                obj[0]=rs.getString("no_trans");
                obj[1]=rs.getDate("tgl_trans");
                obj[2]=rs.getString("kd_team");
                obj[3]=rs.getString("nm_team");
                obj[4]=rs.getString("kd_lap");
                obj[5]=rs.getString("jenis_lap");
                obj[6]=rs.getString("dp");
                obj[7]=rs.getDate("tgl_masuk");
                obj[8]=rs.getTime("jam_masuk");
                obj[9]=rs.getTime("jam_keluar");
                obj[10]=rs.getString("kd_user");
                obj[11]=rs.getString("keterangan");
                model.addRow(obj);
            }
        }catch(Exception ex){}
        return model;
    }
    
    //PopSewa
        public DefaultTableModel showPopTable(DefaultTableModel model){
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            Connection connect=em.unwrap(Connection.class);
            Statement st=(Statement) connect.createStatement();
            ResultSet rs=st.executeQuery("SELECT no_trans, tgl_trans, kd_team, team.nm_team, kd_lap, sewa_lapangan.jenis_lap, dp, tgl_masuk, jam_masuk, jam_keluar, kd_user, data_sewa.keterangan\n" +
                                         "FROM data_sewa\n" +
                                         "INNER JOIN team USING (kd_team)\n" +
                                         "INNER JOIN sewa_lapangan USING (kd_lap)\n" +
                                         "WHERE data_sewa.keterangan like '%Belum Bayar%'");
            while(rs.next()){
                Object[] obj = new Object[12];
                obj[0]=rs.getString("no_trans");
                obj[1]=rs.getDate("tgl_trans");
                obj[2]=rs.getString("kd_team");
                obj[3]=rs.getString("nm_team");
                obj[4]=rs.getString("kd_lap");
                obj[5]=rs.getString("jenis_lap");
                obj[6]=rs.getString("dp");
                obj[7]=rs.getDate("tgl_masuk");
                obj[8]=rs.getTime("jam_masuk");
                obj[9]=rs.getTime("jam_keluar");
                obj[10]=rs.getString("kd_user");
                obj[11]=rs.getString("keterangan");
                model.addRow(obj);
            }
        }catch(Exception ex){}
        return model;
    }
    
    public DefaultTableModel cari(DefaultTableModel model, String cari){
        EntityManager em=getEntityManager();
        try{
            em.getTransaction().begin();
            model.getDataVector().removeAllElements();
            model.fireTableDataChanged();
            Connection connect=em.unwrap(Connection.class);
            Statement st=(Statement) connect.createStatement();
            ResultSet rs=st.executeQuery("SELECT no_trans, tgl_trans, kd_team, team.nm_team, kd_lap, sewa_lapangan.jenis_lap, dp, tgl_masuk, jam_masuk, jam_keluar, kd_user, data_sewa.keterangan\n" +
                                        "FROM data_sewa\n" +
                                        "INNER JOIN team USING (kd_team)\n" +
                                        "INNER JOIN sewa_lapangan USING (kd_lap)\n" +
                                        "WHERE no_trans like '%"+cari+"%'\n" +
                                        "OR tgl_trans like '%"+cari+"%'\n" +
                                        "OR kd_team like '%"+cari+"%'\n" +
                                        "OR  nm_team like '%"+cari+"%'\n" +
                                        "OR  kd_lap like '%"+cari+"%'\n" +
                                        "OR  jenis_lap like '%"+cari+"%'\n" +
                                        "OR  tgl_masuk like '%"+cari+"%'\n" +
                                        "OR  jam_masuk like '%"+cari+"%'\n" +
                                        "OR  data_sewa.keterangan like '%"+cari+"%'\n" +
                                        "OR  kd_user like '%"+cari+"%' ORDER BY no_trans DESC");
            while(rs.next()){
                Object[] obj = new Object[12];
                obj[0]=rs.getString("no_trans");
                obj[1]=rs.getDate("tgl_trans");
                obj[2]=rs.getString("kd_team");
                obj[3]=rs.getString("nm_team");
                obj[4]=rs.getString("kd_lap");
                obj[5]=rs.getString("jenis_lap");
                obj[6]=rs.getString("dp");
                obj[7]=rs.getDate("tgl_masuk");
                obj[8]=rs.getTime("jam_masuk");
                obj[9]=rs.getTime("jam_keluar");
                obj[10]=rs.getString("kd_user");
                obj[11]=rs.getString("keterangan");
                model.addRow(obj);
            }
        }catch(Exception ex){}
        return model;
    }
    
    public void updateKeterangan(String kode){
        EntityManager em = getEntityManager();
        try{
            em.getTransaction().begin();
            Query q=em.createQuery("UPDATE DataSewa k SET k.keterangan = 'Sudah Bayar' where k.noTrans = '"+kode+"'");
            q.executeUpdate();
            em.getTransaction().commit();
        }catch(Exception ex){
            em.close();
        }
    }
    
    //formSewa Validasi lapangan, tanggal, jam masuk, jam keluar
    public List<Object> findJam(String kd, Date tgl){
        EntityManager em=getEntityManager();
        List<Object> hasil=new ArrayList<Object>();
        hasil.add("");
        try{
            Query q=em.createQuery("SELECT s.kdLap, s.tglMasuk, s.jamMasuk, s.jamKeluar FROM DataSewa s WHERE s.kdLap = :kode AND s.tglMasuk = :tanggal");
            q.setParameter("kode", kd);
            q.setParameter("tanggal", tgl);
            hasil=(List<Object>) q.getResultList();
        }catch(NoResultException ex){}
        return hasil;
    } 
}
