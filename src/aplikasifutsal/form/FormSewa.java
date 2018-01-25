/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.form;

import aplikasifutsal.AplikasiFutsal;
import aplikasifutsal.controller.LapanganController;
import aplikasifutsal.controller.SewaController;
import aplikasifutsal.controller.TeamController;
import aplikasifutsal.data.DataSewa;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Font;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Candi-PC
 */
public class FormSewa extends javax.swing.JInternalFrame implements NavigatorFormInt{

    TeamController tCont=new TeamController(AplikasiFutsal.emf);
    LapanganController lCont=new LapanganController(AplikasiFutsal.emf);
    SewaController swCont=new SewaController(AplikasiFutsal.emf);
    DataSewa sewa=new DataSewa();
    public String kdT, nmT, kpten, hp, stat, kdL, jnsL,hrg;
    String kodeUser;
    DefaultTableModel model;
    /**
     * Creates new form FormSewa
     */
    public FormSewa(String kode) {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        kodeUser=kode;
        model=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.addColumn("No. Transaksi");
        model.addColumn("Tanggal");
        model.addColumn("Kode Team");
        model.addColumn("Nama Team");
        model.addColumn("Kode Lapangan");
        model.addColumn("Jenis Lapangan");
        model.addColumn("DP");
        model.addColumn("Tanggal Masuk");
        model.addColumn("Jam Masuk");
        model.addColumn("Jam Keluar");
        model.addColumn("Kasir");
        model.addColumn("Keterangan");
        tableSewa.getTableHeader().setFont(new Font("Ubuntu Condensed", Font.ITALIC, 14));
        showTableSewa();
        renderTableJam();
        renderTableTgl();
    }
    
    private void showTableSewa(){
        tableSewa.setModel(swCont.showTable(model));
    }
    
    private void cariTable(String cari){
        DefaultTableModel x=swCont.cari(model, cari);
        if(x.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Data tidak ada!");
        }else{
            tableSewa.setModel(swCont.cari(model, cari));
        }
    }
    
    public void renderTableTgl(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdf.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableSewa.getColumnModel().getColumn(1).setCellRenderer(tbr);
        tableSewa.getColumnModel().getColumn(7).setCellRenderer(tbr);
    }
    
    public void renderTableJam(){
        TableCellRenderer tbr = new DefaultTableCellRenderer(){
            SimpleDateFormat sdf=new SimpleDateFormat("kk:mm", Locale.forLanguageTag("in-ID"));
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column){
                if(value instanceof Date){
                    value = sdf.format(value);
                }
                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            }
        };
        tableSewa.getColumnModel().getColumn(8).setCellRenderer(tbr);
        tableSewa.getColumnModel().getColumn(9).setCellRenderer(tbr);
    }
    
    private void setTanggal(){
        //membuat format waktu jam, hari bulan dan tahun dengan locale indonesia
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
        Calendar cal=Calendar.getInstance();
        txtTanggal.setText(sdf.format(cal.getTime()));
    }
    
    public void pilihTeam(){
        PopTeam PP=new PopTeam();
        PP.FS=this;
        txtKodeTeam.setText(kdT);
        txtNama.setText(nmT);
        txtKapten.setText(kpten);
        txtTelpon.setText(hp);
        txtStatus.setText(stat);
    }
    
    public void pilihLapangan(){
        PopLapangan PP=new PopLapangan();
        PP.FS=this;
        txtKodeLap.setText(kdL);
        txtJenis.setText(jnsL);
        txtHarga.setText(hrg);
    }
    
    private void jamSpinner(JSpinner spin){
        try{
            Date date=new Date();
            SpinnerDateModel sm=new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
            spin.setModel(sm);
            JSpinner.DateEditor de=new JSpinner.DateEditor(spin, "kk:00");
            spin.setEditor(de);
        }catch(Exception ex){}
    }
    
    private void setJDC(JDateChooser JDC){
        JDC.setLocale(Locale.forLanguageTag("in-ID")); //membuat locale indonesia
        JDC.setDateFormatString("EEEE, dd MMMM yyyy");
        JDC.setMinSelectableDate(new Date());
        JDC.setDate(new Date());
    }
    
    private void selisihTanggal(){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("HH:mm");
            String masuk=sdf.format(txtJamMasuk.getValue());
            String keluar=sdf.format(txtJamKeluar.getValue());
            DateFormat df=new SimpleDateFormat("HH:mm");
            Date msk=df.parse(masuk);
            Date klr=df.parse(keluar);
            long longMasuk=msk.getTime();
            long longKeluar=klr.getTime();
            long x=(longMasuk/(60*60*1000));
            long y=longKeluar/(60*60*1000);
            double hrg=Double.parseDouble(txtHarga.getText());
            double dp=Double.parseDouble(txtDP.getText());
            for(long z=-7; z<=-4; z++){ 
                if((x==16 && y==z)||(x==15 && y==z) ||(x==14 && y==z) ||(x==13 && y==z) ||(x==12 && y==z) ||(x==11 && y==z) ){
                    y=y+24;
                } 
            }
            if(x==y){
                JOptionPane.showMessageDialog(null, "Jam masuk dan keluar tidak boleh sama!");
            }else if(y<x){
                JOptionPane.showMessageDialog(null, "Jam keluar tidak valid!");
            }else if(dp>=hrg){
                JOptionPane.showMessageDialog(null, "DP tidak valid!");
            }else{
                simpanRegistrasi();
                bersih();
            }    
        }catch(Exception ex){}
    }
    
    private void simpanRegistrasi(){
        sewa.setNoTrans(txtNo.getText());
        sewa.setTglTrans(new Date());
        sewa.setKdTeam(txtKodeTeam.getText());
        sewa.setKdLap(txtKodeLap.getText());
        sewa.setDp(Double.parseDouble(txtDP.getText()));
        sewa.setTglMasuk(jdcTanggalMasuk.getDate());
        sewa.setJamMasuk((Date) txtJamMasuk.getValue());
        sewa.setJamKeluar((Date) txtJamKeluar.getValue());
        sewa.setKdUser(kodeUser);
        sewa.setKeterangan("Belum Bayar");
        try{
            swCont.save(sewa);
            tCont.updateKeterangan(txtKodeTeam.getText());
        }catch(Exception ex){}
        JOptionPane.showMessageDialog(null, "Data telah disimpan!");
    }
    
    
    private boolean validasiJam(){
        boolean valid=false;
        try{
            Date masuk=(Date) txtJamMasuk.getValue();
            Date keluar=(Date) txtJamKeluar.getValue();
            Date tg=(jdcTanggalMasuk.getDate());
            List<Object> hasil=swCont.findJam(txtKodeLap.getText(), tg);
            Iterator itr=hasil.iterator();
            Date jm1;
            Date jm2;
            
            while(itr.hasNext()){
                Object[] obj=(Object[]) itr.next();
                jm1=(Date) ((obj[2]));
                jm2=(Date) (obj[3]);
                if((masuk.getTime() > jm1.getTime() && masuk.getTime() < jm2.getTime()) ||
                    (keluar.getTime() > jm1.getTime() && keluar.getTime() < jm2.getTime()) ||
                    (masuk.getTime() == jm1.getTime() && keluar.getTime() == jm2.getTime())
                    ){
                    valid=true;
                }
            }

        }catch(Exception ex){}
        return valid;
    }         
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtKodeTeam = new javax.swing.JTextField();
        txtNama = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtKapten = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTelpon = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtStatus = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btCariTeam = new javax.swing.JButton();
        txtTanggal = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtNo = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtDP = new javax.swing.JTextField();
        txtKodeLap = new javax.swing.JTextField();
        btCariLap = new javax.swing.JButton();
        txtJenis = new javax.swing.JTextField();
        txtHarga = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jdcTanggalMasuk = new com.toedter.calendar.JDateChooser();
        txtJamKeluar = new javax.swing.JSpinner();
        txtJamMasuk = new javax.swing.JSpinner();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableSewa = new javax.swing.JTable();
        jLabel15 = new javax.swing.JLabel();
        txtCari = new javax.swing.JTextField();
        btCari = new javax.swing.JButton();

        setTitle("Form Registrasi");
        addInternalFrameListener(new javax.swing.event.InternalFrameListener() {
            public void internalFrameActivated(javax.swing.event.InternalFrameEvent evt) {
                formInternalFrameActivated(evt);
            }
            public void internalFrameClosed(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameClosing(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeactivated(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameDeiconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameIconified(javax.swing.event.InternalFrameEvent evt) {
            }
            public void internalFrameOpened(javax.swing.event.InternalFrameEvent evt) {
            }
        });

        jPanel1.setBackground(new java.awt.Color(22, 69, 24));

        jPanel2.setBackground(new java.awt.Color(22, 69, 24));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Registrasi", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Condensed", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kode Team");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, -1, 20));

        txtKodeTeam.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtKodeTeam, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 110, 120, -1));

        txtNama.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 150, 190, -1));

        jLabel3.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Team");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, -1, 20));

        txtKapten.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtKapten, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 190, 190, -1));

        jLabel5.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Kapten");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, -1, 20));

        txtTelpon.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtTelpon, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 190, -1));

        jLabel6.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("No HP");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 230, -1, 20));

        txtStatus.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtStatus, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 190, -1));

        jLabel7.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Status");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, 20));

        btCariTeam.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        btCariTeam.setText("Cari");
        btCariTeam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariTeamActionPerformed(evt);
            }
        });
        jPanel2.add(btCariTeam, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 110, -1, -1));

        txtTanggal.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtTanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 70, 190, -1));

        jLabel1.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Tgl. Transaksi");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, -1, 30));

        jLabel8.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("No Transaksi");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        txtNo.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel2.add(txtNo, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 30, 190, -1));

        jPanel3.setBackground(new java.awt.Color(22, 69, 24));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Lapangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Condensed", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("DP");
        jPanel3.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, -1, -1));

        jLabel9.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Kode Lapangan");
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 36, -1, 20));

        jLabel10.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Jenis Lapangan");
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 76, -1, 20));

        jLabel11.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Harga Sewa");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));

        txtDP.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel3.add(txtDP, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 150, 200, -1));

        txtKodeLap.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel3.add(txtKodeLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 130, -1));

        btCariLap.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        btCariLap.setText("Cari");
        btCariLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariLapActionPerformed(evt);
            }
        });
        jPanel3.add(btCariLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));

        txtJenis.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel3.add(txtJenis, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 70, 200, -1));

        txtHarga.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        txtHarga.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtHargaActionPerformed(evt);
            }
        });
        jPanel3.add(txtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 200, -1));

        jPanel4.setBackground(new java.awt.Color(22, 69, 24));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Jam Sewa Lapangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Condensed", 0, 18), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Jam Keluar");
        jPanel4.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jLabel13.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Tanggal Masuk");
        jPanel4.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        jLabel14.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Jam Masuk");
        jPanel4.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        jdcTanggalMasuk.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel4.add(jdcTanggalMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 200, 30));

        txtJamKeluar.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        txtJamKeluar.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), null, new java.util.Date(), java.util.Calendar.HOUR));
        jPanel4.add(txtJamKeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 130, 200, -1));

        txtJamMasuk.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        txtJamMasuk.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(), null, java.util.Calendar.HOUR_OF_DAY));
        jPanel4.add(txtJamMasuk, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 80, 200, -1));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Registrasi Lapangan", jPanel1);

        jPanel5.setBackground(new java.awt.Color(22, 69, 24));

        tableSewa.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        tableSewa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableSewa);

        jLabel15.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Cari");

        txtCari.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        btCari.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        btCari.setText("Cari");
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 725, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCari, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lihat Data", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btCariTeamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariTeamActionPerformed
        PopTeam PT=new PopTeam();
        PT.FS=this;
        PT.setVisible(true);
    }//GEN-LAST:event_btCariTeamActionPerformed

    private void btCariLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariLapActionPerformed
        PopLapangan PT=new PopLapangan();
        PT.FS=this;
        PT.setVisible(true);
    }//GEN-LAST:event_btCariLapActionPerformed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        txtNo.setEnabled(false);
        txtTanggal.setEnabled(false);
        txtKodeTeam.setEnabled(false);
        btCariTeam.setEnabled(false);
        txtNama.setEnabled(false);
        txtKapten.setEnabled(false);
        txtTelpon.setEnabled(false);
        txtStatus.setEnabled(false);
        txtKodeLap.setEnabled(false);
        btCariLap.setEnabled(false);
        txtJenis.setEnabled(false);
        txtHarga.setEnabled(false);
        txtDP.setEnabled(false);
        jdcTanggalMasuk.setEnabled(false);
        txtJamMasuk.setEnabled(false);
        txtJamKeluar.setEnabled(false);
        txtCari.setEnabled(false);
        btCari.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameActivated

    private void btCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariActionPerformed
        cariTable(txtCari.getText());
    }//GEN-LAST:event_btCariActionPerformed

    private void txtHargaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtHargaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtHargaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCari;
    private javax.swing.JButton btCariLap;
    private javax.swing.JButton btCariTeam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private com.toedter.calendar.JDateChooser jdcTanggalMasuk;
    private javax.swing.JTable tableSewa;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtDP;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JSpinner txtJamKeluar;
    private javax.swing.JSpinner txtJamMasuk;
    private javax.swing.JTextField txtJenis;
    private javax.swing.JTextField txtKapten;
    private javax.swing.JTextField txtKodeLap;
    private javax.swing.JTextField txtKodeTeam;
    private javax.swing.JTextField txtNama;
    private javax.swing.JTextField txtNo;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTelpon;
    // End of variables declaration//GEN-END:variables

    @Override
    public void aktif() {
        btCariTeam.setEnabled(true);
        btCariLap.setEnabled(true);
        txtDP.setEnabled(true);
        jdcTanggalMasuk.setEnabled(true);
        txtJamMasuk.setEnabled(true);
        txtJamKeluar.setEnabled(true);
        txtCari.setEnabled(true);
        btCari.setEnabled(true);
        setTanggal();
    }

    @Override
    public void bersih() {
        txtKodeTeam.setText("");
        txtNama.setText("");
        txtKapten.setText("");
        txtTelpon.setText("");
        txtStatus.setText("");
        txtKodeLap.setText("");
        txtJenis.setText("");
        txtHarga.setText("");
        txtDP.setText("");
        txtCari.setText("");
        setJDC(jdcTanggalMasuk);
        jamSpinner(txtJamMasuk);
        jamSpinner(txtJamKeluar);
        txtNo.setText(swCont.kodeDataSewaOto());
        showTableSewa();
    }

    @Override
    public void simpan(){
        if(txtKodeTeam.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Team belum dipilih!");
        }else if(txtKodeLap.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Lapangan Belum dipilih!");
        }else if(txtDP.getText().equalsIgnoreCase("")){
            JOptionPane.showMessageDialog(null, "Masukan jumlah DP!");
        }else{
            if(validasiJam()==true){
                JOptionPane.showMessageDialog(null, "Jam ini sudah di boking!");
            }else{
                selisihTanggal();
            }
        }
    }

    @Override
    public void hapus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void cari() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void tampil() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
