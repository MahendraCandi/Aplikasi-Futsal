/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.controller;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.HashMap;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Boat
 */
public class LaporanController implements Serializable{
    private EntityManagerFactory emf=null;
    
    public LaporanController(EntityManagerFactory emf){
        this.emf=emf;
    }
    
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    
    
    public void cetakAlat(){
        EntityManager em=null;
        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            File file=new File("");
            String namafile=file.getAbsolutePath()+"\\report\\reportAlat.jasper";
            JasperPrint jprint=JasperFillManager.fillReport (namafile, new HashMap(),connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
                viewer.setFitPageZoomRatio();
                viewer.setVisible(true);
                em.getTransaction().commit();
                em.close();
                connect.close();
        }catch(Exception e){}
    }
        
        public void cetakLapangan(){
        EntityManager em=null;
        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            File file=new File("");
            String namafile=file.getAbsolutePath()+"\\report\\reportLapangan.jasper";
            JasperPrint jprint=JasperFillManager.fillReport (namafile, new HashMap(),connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
                viewer.setFitPageZoomRatio();
                viewer.setVisible(true);
                em.getTransaction().commit();
                em.close();
                connect.close();
            
        }catch(Exception e){e.printStackTrace();
        }
    }   
        
        public void cetakPembayaran(){
        EntityManager em=null;
        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            File file=new File("");
            String namafile=file.getAbsolutePath()+"\\report\\reportPembayaran.jasper";
            JasperPrint jprint=JasperFillManager.fillReport (namafile, new HashMap(),connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
                viewer.setFitPageZoomRatio();
                viewer.setVisible(true);
                em.getTransaction().commit();
                em.close();
                connect.close();
            
        }catch(Exception e){e.printStackTrace();
        }
    }
        
        public void cetakSewa(){
        EntityManager em=null;
        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            File file=new File("");
            String namafile=file.getAbsolutePath()+"\\report\\reportSewa.jasper";
            JasperPrint jprint=JasperFillManager.fillReport (namafile, new HashMap(),connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
                viewer.setFitPageZoomRatio();
                viewer.setVisible(true);
                em.getTransaction().commit();
                em.close();
                connect.close();
        }catch(Exception e){e.printStackTrace();
        }
    }
        
        public void cetakTeam(){
        EntityManager em=null;
        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            File file=new File("");
            String namafile=file.getAbsolutePath()+"\\report\\reportTeam.jasper";
            JasperPrint jprint=JasperFillManager.fillReport (namafile, new HashMap(),connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
                viewer.setFitPageZoomRatio();
                viewer.setVisible(true);
                em.getTransaction().commit();
                connect.close();
        }catch(Exception e){e.printStackTrace();
        }
    }
        
        public void cetakUser(){
        EntityManager em=null;
        try{
            em=emf.createEntityManager();
            em.getTransaction().begin();
            Connection connect=em.unwrap(Connection.class);
            File file=new File("");
            String namafile=file.getAbsolutePath()+"\\report\\reportUser.jasper";
            JasperPrint jprint=JasperFillManager.fillReport (namafile, new HashMap(),connect);
            JasperViewer viewer=new JasperViewer(jprint, false);
                viewer.setFitPageZoomRatio();
                viewer.setVisible(true);
                em.getTransaction().commit();
                connect.close();
        }catch(Exception e){}
    }
}
