/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasifutsal.form;

import aplikasifutsal.AplikasiFutsal;
import aplikasifutsal.controller.AlatController;
import aplikasifutsal.data.Alat;
import java.awt.Font;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Candi-PC
 */
public class FormAlat extends javax.swing.JInternalFrame implements NavigatorFormInt{

    AlatController aCont=new AlatController(AplikasiFutsal.emf);
    Alat alat=new Alat();
    DefaultTableModel model;
    /**
     * Creates new form FormFasilitas
     */
    public FormAlat() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)this.getUI()).setNorthPane(null);
        this.setBorder(null);
        model=new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            
        };
        model.addColumn("Kode Alat");
        model.addColumn("Nama Alat");
        model.addColumn("Harga Alat");
        tableAlat.getTableHeader().setFont(new Font("Ubuntu Condensed", Font.ITALIC, 14));
        seleksiTable();
    }
    
    private void showTableAlat(){
        tableAlat.setModel(aCont.showTable(model));
    }

    private void seleksiTable(){
        tableAlat.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int baris=tableAlat.getSelectedRow();       //dapatkan baris yang terseleksi
                if(baris != -1){                        //jika baris bukan sama dengan -1 atau tidak ada
                    txtKode.setText(tableAlat.getValueAt(baris, 0).toString());
                    txtNama.setText(tableAlat.getValueAt(baris, 1).toString());
                    txtHarga.setText(tableAlat.getValueAt(baris, 2).toString());
                }
            }
        });
    }
    
    private void cariTable(String cari){
        DefaultTableModel x=aCont.cari(model, cari);
        if(x.getRowCount()==0){
            JOptionPane.showMessageDialog(null, "Data tidak ada!");
        }else{
            tableAlat.setModel(aCont.cari(model, cari));
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
        jLabel1 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        txtKode = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableAlat = new javax.swing.JTable();
        btCari = new javax.swing.JButton();
        txtCari = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        setTitle("Form Alat");
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
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Harga");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 160, -1, -1));

        txtHarga.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel1.add(txtHarga, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 160, 220, -1));

        txtKode.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel1.add(txtKode, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 220, -1));

        jLabel2.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Kode Alat");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 60, -1, -1));

        jLabel3.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Nama Alat");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, -1, -1));

        txtNama.setFont(new java.awt.Font("Ubuntu Condensed", 0, 18)); // NOI18N
        jPanel1.add(txtNama, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 110, 220, -1));

        jTabbedPane1.addTab("Master Alat", jPanel1);

        jPanel2.setBackground(new java.awt.Color(22, 69, 24));

        tableAlat.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        tableAlat.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Kode Alat", "Nama Alat", "Harga"
            }
        ));
        jScrollPane1.setViewportView(tableAlat);

        btCari.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        btCari.setText("Cari");
        btCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCariActionPerformed(evt);
            }
        });

        txtCari.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Ubuntu Condensed", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cari Alat");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCari)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Lihat Data", jPanel2);

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

    private void formInternalFrameActivated(javax.swing.event.InternalFrameEvent evt) {//GEN-FIRST:event_formInternalFrameActivated
        txtKode.setEnabled(false);
        txtNama.setEnabled(false);
        txtHarga.setEnabled(false);
        txtCari.setEnabled(false);
        btCari.setEnabled(false);
    }//GEN-LAST:event_formInternalFrameActivated

    private void btCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCariActionPerformed
        cariTable(txtCari.getText());
    }//GEN-LAST:event_btCariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable tableAlat;
    private javax.swing.JTextField txtCari;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtKode;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables

    @Override
    public void aktif() {
        txtNama.setEnabled(true);
        txtHarga.setEnabled(true);
        txtCari.setEnabled(true);
        btCari.setEnabled(true);
        bersih();
    }

    @Override
    public void bersih() {
        txtNama.setText("");
        txtHarga.setText("");
        txtCari.setText("");
        txtKode.setText(aCont.kodeAlatOto());
        showTableAlat();
    }

    @Override
    public void simpan() {
        if("".equals(txtNama.getText())||"".equals(txtHarga.getText())){
            JOptionPane.showMessageDialog(null, "Data belum lengkap!");
        }else{
            alat=aCont.findAlat(txtKode.getText());
            Alat alt=new Alat();
            if(alat==null){
                alt.setKdAlat(txtKode.getText());
                alt.setSewaAlat(txtNama.getText());
                alt.setHrgAlat(Double.parseDouble(txtHarga.getText()));
                try{
                    aCont.save(alt);
                }catch(Exception ex){}
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
            }else{
                alt.setKdAlat(txtKode.getText());
                alt.setSewaAlat(txtNama.getText());
                alt.setHrgAlat(Double.parseDouble(txtHarga.getText()));
                try{
                    aCont.update(alt);
                }catch(Exception ex){}
                JOptionPane.showMessageDialog(null, "Data berhasil diupdate!");
            }
            bersih();
        }
        
    }

    @Override
    public void hapus(){
        try{
            aCont.delete(txtKode.getText());
            JOptionPane.showMessageDialog(null, "Data telah dihapus!");
        }catch(Exception ex){}
        bersih();
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