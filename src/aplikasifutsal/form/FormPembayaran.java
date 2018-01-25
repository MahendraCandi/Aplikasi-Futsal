/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.form;

import aplikasifutsal.AplikasiFutsal;
import aplikasifutsal.controller.AlatController;
import aplikasifutsal.controller.LapanganController;
import aplikasifutsal.controller.PembayaranController;
import aplikasifutsal.controller.SewaController;
import aplikasifutsal.controller.TeamController;
import aplikasifutsal.data.Alat;
import aplikasifutsal.data.DataSewa;
import aplikasifutsal.data.Pembayaran;
import aplikasifutsal.data.SewaLapangan;
import aplikasifutsal.data.Team;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author Candi-PC
 */
public class FormPembayaran extends javax.swing.JInternalFrame implements NavigatorFormInt{

    SewaController swCont=new SewaController(AplikasiFutsal.emf);
    TeamController tCont=new TeamController(AplikasiFutsal.emf);
    LapanganController lCont=new LapanganController(AplikasiFutsal.emf);
    AlatController aCont=new AlatController(AplikasiFutsal.emf);
    PembayaranController byCont=new PembayaranController(AplikasiFutsal.emf);
    DataSewa sewa=new DataSewa();
    Team team=new Team();
    SewaLapangan lapangan=new SewaLapangan();
    Alat alat=new Alat();
    Pembayaran bayar=new Pembayaran();
    public String kdSewa, kdTeam, kdLap, kdAlat;
    double totalLap=0, totalAlat=0, totalBayar=0, dp=0, sisaBayar=0, ubay=0, ukem=0;
    DefaultTableModel model;
    /**
     * Creates new form FormPembayaran
     */
    public FormPembayaran() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        model=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        model.addColumn("No Bayar");
        model.addColumn("Tanggal");
        model.addColumn("No Transaksi");
        model.addColumn("Total Jam");
        model.addColumn("Total Lapangan");
        model.addColumn("Kode Alat");
        model.addColumn("Jumlah Alat");
        model.addColumn("Total Alat");
        model.addColumn("Total Bayar");
        model.addColumn("Sisa Bayar");
        model.addColumn("Uang Bayar");
        model.addColumn("Uang Kembali");
        tableBayar.getTableHeader().setFont(new Font("Ubuntu Condensed", Font.ITALIC, 14));
        showTableBayar();
        renderTable();
    }
    
    private void showTableBayar(){
        tableBayar.setModel(byCont.showTable(model));
    }
    
    public void renderTable(){
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
        tableBayar.getColumnModel().getColumn(1).setCellRenderer(tbr);
    }
    
    private void cariTable(String cari){
        DefaultTableModel x=byCont.cari(model, cari);
        if(x.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Data tidak ada!");
        }else{
            tableBayar.setModel(byCont.cari(model, cari));
        }
    }
    
    private void setTanggal(){
        //membuat format waktu jam, hari bulan dan tahun dengan locale indonesia
        SimpleDateFormat sdf=new SimpleDateFormat("dd MMMM yyyy", Locale.forLanguageTag("in-ID"));
        Calendar cal=Calendar.getInstance();
        txtTanggal.setText(sdf.format(cal.getTime()));
    }
    
    public void pilihNoTransaksi(){
        PopSewa PP=new PopSewa();
        PP.FP=this;
        SimpleDateFormat sdf=new SimpleDateFormat("kk:mm", Locale.forLanguageTag("in-ID"));
        txtNoTrans.setText(kdSewa);
        sewa=swCont.findDataSewa(kdSewa);
        team=tCont.findTeam(kdTeam);
        lapangan=lCont.findSewaLapangan(kdLap);
        txtKasir.setText(sewa.getKdUser());
        txtNamaTeam.setText(team.getNmTeam());
        txtKapten.setText(team.getKapten());
        txtStatus.setText(team.getStatus());
        txtKodeLap.setText(kdLap);
        txtJenisLap.setText(lapangan.getJenisLap());
        txtHargaSewa.setText(String.valueOf(lapangan.getHrgLap()));
        txtJamMasuk.setText(sdf.format(sewa.getJamMasuk()));
        txtJamKeluar.setText(sdf.format(sewa.getJamKeluar()));
        txtDP.setText(String.valueOf(sewa.getDp()));
        selisihTanggal();
        hargaSewa();
    }
    
    public void pilihAlat(){
        PopAlat PA=new PopAlat();
        PA.FP=this;
        alat=aCont.findAlat(kdAlat);
        txtKodeAlat.setText(kdAlat);
        txtSewaAlat.setText(alat.getSewaAlat());
        txtHargaAlat.setText(String.valueOf(alat.getHrgAlat()));
        txtJumlahAlat.requestFocus();
        
    }
    
    public void selisihTanggal(){
        try{
            SimpleDateFormat sdf=new SimpleDateFormat("kk:mm");
            Date masuk=sdf.parse(txtJamMasuk.getText());
            Date keluar=sdf.parse(txtJamKeluar.getText());
            long longMasuk=masuk.getTime();
            long longKeluar=keluar.getTime();
            long x=(longMasuk/(60*60*1000));
            long y=longKeluar/(60*60*1000);
            for(long z=-7; z<=-4; z++){ 
                if((x==16 && y==z)||(x==15 && y==z) ||(x==14 && y==z) ||(x==13 && y==z) ||(x==12 && y==z) ||(x==11 && y==z) ){
                    y=y+24;
                    System.out.println(z);
                } 
            }
            long jam=y-x;
            txtTotalJam.setText(String.valueOf(jam));
        }catch(Exception ex){}
    }
    
    public void hargaSewa(){
        double TotalJam=Double.parseDouble(this.txtTotalJam.getText());
        double Harga=Double.parseDouble(this.txtHargaSewa.getText());
        totalLap=TotalJam*Harga;
        txtTotalSewa.setText(String.valueOf(totalLap));
        Bayar();
        sisaBayar();
    }
    
    private void hargaAlat(){
        int jumlah=Integer.parseInt(txtJumlahAlat.getText());
        double harga=Double.parseDouble(txtHargaAlat.getText());
        totalAlat=jumlah*harga;
        txtTotalAlat.setText(String.valueOf(totalAlat));
        Bayar();
        sisaBayar();
    }
    
    private void Bayar(){
        totalBayar=totalLap+totalAlat;
        txtTotalBayar.setText(String.valueOf(totalBayar));
    }
    
    private void sisaBayar(){
        dp=Double.parseDouble(txtDP.getText());
        sisaBayar=totalBayar-dp;
        txtSisaBayar.setText(String.valueOf(sisaBayar));
    }
    
    private void uangBayar(){
        ubay=Double.parseDouble(txtUbay.getText());
        if(ubay<sisaBayar){
            JOptionPane.showMessageDialog(null, "Uang bayar Kurang!");
        }else{
            ukem=ubay-sisaBayar;
            txtUkem.setText(String.valueOf(ukem));
        }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        txtNoBayar = new javax.swing.JTextField();
        txtNoTrans = new javax.swing.JTextField();
        txtKasir = new javax.swing.JTextField();
        txtNamaTeam = new javax.swing.JTextField();
        txtKapten = new javax.swing.JTextField();
        txtStatus = new javax.swing.JTextField();
        btCariTrans = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtTotalAlat = new javax.swing.JTextField();
        txtJumlahAlat = new javax.swing.JTextField();
        txtHargaAlat = new javax.swing.JTextField();
        txtSewaAlat = new javax.swing.JTextField();
        txtKodeAlat = new javax.swing.JTextField();
        btCariAlat = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtTotalSewa = new javax.swing.JTextField();
        txtTotalJam = new javax.swing.JTextField();
        txtJamKeluar = new javax.swing.JTextField();
        txtJamMasuk = new javax.swing.JTextField();
        txtHargaSewa = new javax.swing.JTextField();
        txtJenisLap = new javax.swing.JTextField();
        txtKodeLap = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtSisaBayar = new javax.swing.JTextField();
        txtDP = new javax.swing.JTextField();
        txtTotalBayar = new javax.swing.JTextField();
        txtUbay = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        txtUkem = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableBayar = new javax.swing.JTable();
        jLabel25 = new javax.swing.JLabel();
        txtCariData = new javax.swing.JTextField();
        btCari = new javax.swing.JButton();

        setTitle("Form Pembayaran");
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
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Pembayaran", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Condensed", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("No. Pembayaran");

        jLabel2.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Tanggal Pembayaran");

        jLabel3.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("No. Transaksi");

        jLabel4.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Kasir");

        jLabel5.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Nama Team");

        jLabel6.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Kapten");

        jLabel7.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Status");

        txtTanggal.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtNoBayar.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtNoTrans.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtKasir.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtNamaTeam.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtKapten.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtStatus.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        btCariTrans.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        btCariTrans.setText("Cari");
        btCariTrans.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariTransActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNoBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNamaTeam, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtKapten, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCariTrans, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtNoBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtNoTrans, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCariTrans))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtKasir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtNamaTeam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtKapten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(22, 69, 24));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Sewa Alat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Condensed", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Kode Alat");

        jLabel9.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Sewa Alat");

        jLabel10.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Harga Alat");

        jLabel11.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Jumlah Alat");

        jLabel12.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Total Harga Alat");

        txtTotalAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtJumlahAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtJumlahAlat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtJumlahAlatKeyPressed(evt);
            }
        });

        txtHargaAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtSewaAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        txtKodeAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        btCariAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        btCariAlat.setText("Cari");
        btCariAlat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariAlatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel11)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTotalAlat)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtJumlahAlat)
                        .addComponent(txtHargaAlat)
                        .addComponent(txtSewaAlat)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(txtKodeAlat, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(btCariAlat, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(1, 1, 1)))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtKodeAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCariAlat))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtSewaAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtHargaAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtJumlahAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txtTotalAlat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(22, 69, 24));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Informasi Lapangan", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Ubuntu Condensed", 0, 14), new java.awt.Color(255, 255, 255))); // NOI18N

        jLabel13.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Kode Lapangan");

        jLabel14.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Jenis Lapangan");

        jLabel15.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Harga Sewa");

        jLabel16.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Jam Masuk");

        jLabel17.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Jam Keluar");

        jLabel18.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Total Jam");

        jLabel19.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Total Sewa Lapangan");

        txtTotalSewa.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtTotalSewa.setForeground(new java.awt.Color(255, 255, 255));

        txtTotalJam.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtTotalJam.setForeground(new java.awt.Color(255, 255, 255));

        txtJamKeluar.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtJamKeluar.setForeground(new java.awt.Color(255, 255, 255));
        txtJamKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJamKeluarActionPerformed(evt);
            }
        });

        txtJamMasuk.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtJamMasuk.setForeground(new java.awt.Color(255, 255, 255));

        txtHargaSewa.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtHargaSewa.setForeground(new java.awt.Color(255, 255, 255));

        txtJenisLap.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtJenisLap.setForeground(new java.awt.Color(255, 255, 255));

        txtKodeLap.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        txtKodeLap.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtKodeLap, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJenisLap, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalJam, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTotalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(67, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txtKodeLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(txtJenisLap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtHargaSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txtJamMasuk, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(txtJamKeluar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(txtTotalJam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(txtTotalSewa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );

        jLabel20.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Total Pembayaran");

        jLabel21.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("DP");

        jLabel22.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Sisa Pembayaran");

        txtSisaBayar.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N

        txtDP.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N

        txtTotalBayar.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N

        txtUbay.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        txtUbay.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUbayKeyPressed(evt);
            }
        });

        jLabel23.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setText("Uang Bayar");

        txtUkem.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N

        jLabel24.setFont(new java.awt.Font("Ubuntu Condensed", 1, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Uang Kembali");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(18, 18, 18)
                                .addComponent(txtTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel22)
                                    .addComponent(jLabel21))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(28, 28, 28)
                                        .addComponent(txtDP, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(txtSisaBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(23, 23, 23)
                                .addComponent(txtUbay, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(23, 23, 23)
                                .addComponent(txtUkem, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(43, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(txtTotalBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(txtDP, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(txtSisaBayar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel23)
                            .addComponent(txtUbay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(txtUkem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Pembayaran", jPanel1);

        jPanel5.setBackground(new java.awt.Color(22, 69, 24));

        tableBayar.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        tableBayar.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tableBayar);

        jLabel25.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Cari Data");

        txtCariData.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 814, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel25)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCariData, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCari, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(txtCariData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
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

    private void txtJamKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJamKeluarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJamKeluarActionPerformed

    private void btCariTransActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariTransActionPerformed
        PopSewa PP=new PopSewa();
        PP.FP=this;
        PP.setVisible(true);
    }//GEN-LAST:event_btCariTransActionPerformed

    private void txtJumlahAlatKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtJumlahAlatKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            if("".equals(txtDP.getText())){
                JOptionPane.showMessageDialog(null, "No transaksi kosong!");
            }else{
                hargaAlat();
                txtUbay.requestFocus();
            }
        }
    }//GEN-LAST:event_txtJumlahAlatKeyPressed

    private void btCariAlatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariAlatActionPerformed
        PopAlat PA=new PopAlat();
        PA.FP=this;
        PA.setVisible(true);
    }//GEN-LAST:event_btCariAlatActionPerformed

    private void txtUbayKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUbayKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            uangBayar();
        }
    }//GEN-LAST:event_txtUbayKeyPressed

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        txtNoBayar.setEnabled(false);
        txtTanggal.setEnabled(false);
        txtNoTrans.setEnabled(false);
        btCariTrans.setEnabled(false);
        txtKasir.setEnabled(false);
        txtNamaTeam.setEnabled(false);
        txtKapten.setEnabled(false);
        txtStatus.setEnabled(false);
        txtKodeLap.setEnabled(false);
        txtJenisLap.setEnabled(false);
        txtHargaSewa.setEnabled(false);
        txtJamMasuk.setEnabled(false);
        txtJamKeluar.setEnabled(false);
        txtTotalJam.setEnabled(false);
        txtTotalSewa.setEnabled(false);
        txtKodeAlat.setEnabled(false);
        txtSewaAlat.setEnabled(false);
        txtHargaAlat.setEnabled(false);
        txtJumlahAlat.setEnabled(false);
        txtTotalAlat.setEnabled(false);
        txtTotalBayar.setEnabled(false);
        txtDP.setEnabled(false);
        txtSisaBayar.setEnabled(false);
        txtUbay.setEnabled(false);
        txtUkem.setEnabled(false);
        btCariAlat.setEnabled(false);
        txtCariData.setEnabled(false);
        btCari.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameActivated

    private void btCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariActionPerformed
        cariTable(txtCariData.getText());
    }//GEN-LAST:event_btCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCari;
    private javax.swing.JButton btCariAlat;
    private javax.swing.JButton btCariTrans;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
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
    private javax.swing.JTable tableBayar;
    private javax.swing.JTextField txtCariData;
    private javax.swing.JTextField txtDP;
    private javax.swing.JTextField txtHargaAlat;
    private javax.swing.JTextField txtHargaSewa;
    private javax.swing.JTextField txtJamKeluar;
    private javax.swing.JTextField txtJamMasuk;
    private javax.swing.JTextField txtJenisLap;
    private javax.swing.JTextField txtJumlahAlat;
    private javax.swing.JTextField txtKapten;
    private javax.swing.JTextField txtKasir;
    private javax.swing.JTextField txtKodeAlat;
    private javax.swing.JTextField txtKodeLap;
    private javax.swing.JTextField txtNamaTeam;
    private javax.swing.JTextField txtNoBayar;
    private javax.swing.JTextField txtNoTrans;
    private javax.swing.JTextField txtSewaAlat;
    private javax.swing.JTextField txtSisaBayar;
    private javax.swing.JTextField txtStatus;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotalAlat;
    private javax.swing.JTextField txtTotalBayar;
    private javax.swing.JTextField txtTotalJam;
    private javax.swing.JTextField txtTotalSewa;
    private javax.swing.JTextField txtUbay;
    private javax.swing.JTextField txtUkem;
    // End of variables declaration//GEN-END:variables

    @Override
    public void aktif() {
        btCariTrans.setEnabled(true);
        txtJumlahAlat.setEnabled(true);
        txtUbay.setEnabled(true);
        btCariAlat.setEnabled(true);
        txtCariData.setEnabled(true);
        btCari.setEnabled(true);
        setTanggal();
        bersih();
    }

    @Override
    public void bersih() {
        txtNoTrans.setText("");
        txtKasir.setText("");
        txtNamaTeam.setText("");
        txtKapten.setText("");
        txtStatus.setText("");
        txtKodeLap.setText("");
        txtJenisLap.setText("");
        txtHargaSewa.setText("");
        txtJamMasuk.setText("");
        txtJamKeluar.setText("");
        txtTotalJam.setText("");
        txtTotalSewa.setText("");
        txtKodeAlat.setText("");
        txtSewaAlat.setText("");
        txtHargaAlat.setText("");
        txtJumlahAlat.setText("");
        txtTotalAlat.setText("");
        txtTotalBayar.setText("");
        txtDP.setText("");
        txtSisaBayar.setText("");
        txtUbay.setText("");
        txtUkem.setText("");
        txtCariData.setText("");
        txtNoBayar.setText(byCont.kodePembayaranOto());
        showTableBayar();
    }

    @Override
    public void simpan() {
        if("".equals(txtNoBayar.getText())||"".equals(txtNoTrans.getText())||
                "".equals(txtKodeAlat.getText())||"".equals(txtUbay.getText())){
            JOptionPane.showMessageDialog(null, "Data belum lengkap!");
        }else{
            bayar.setNoBayar(txtNoBayar.getText());
            bayar.setTglBayar(new Date());
            bayar.setNoTrans(txtNoTrans.getText());
            bayar.setTotalJam(Double.parseDouble(txtTotalJam.getText()));
            bayar.setTotalLap(Double.parseDouble(txtTotalSewa.getText()));
            bayar.setKdAlat(txtKodeAlat.getText());
            bayar.setJumlahAlat(Integer.parseInt(txtJumlahAlat.getText()));
            bayar.setTotalAlat(Double.parseDouble(txtTotalAlat.getText()));
            bayar.setTotalBayar(Double.parseDouble(txtTotalBayar.getText()));
            bayar.setSisaBayar(Double.parseDouble(txtSisaBayar.getText()));
            bayar.setUbay(Double.parseDouble(txtUbay.getText()));
            bayar.setUkem(Double.parseDouble(txtUkem.getText()));
            try{
                byCont.save(bayar);
                swCont.updateKeterangan(txtNoTrans.getText());
            }catch(Exception ex){}
            JOptionPane.showMessageDialog(null, "Simpan data berhasil!");
            bersih();
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
