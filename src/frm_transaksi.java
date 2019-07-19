/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dimasast
 * 
 *
 */

import Koneksi.koneksiDB;
import java.sql.*;
import java.sql.Statement;
import java.sql.ResultSet;

import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.DriverManager;
import java.sql.Connection;


public class frm_transaksi extends javax.swing.JFrame {

    //membuat objek    
    private DefaultTableModel model;
    
    //deklarasi variabel
    String noJual, kdProduk, nmProduk, xtotal,kdPenyewa,nmPenyewa,tanggal, kdTransaksi,TARIF,lama,user,ALAMAT,PEKERJAAN;
    int hrg_jual, qty;
    double total, bayar, kembali, sTotal;
    Connection con;
    Statement st;
    ResultSet rs;
    PreparedStatement ps;
    String sql;
    
    public frm_transaksi() {
        initComponents();
    koneksi();
    nofaktur();
  //membuat obyek
        model = new DefaultTableModel();
        
        //memberi nama header pada tabel
        tblTransaksi.setModel(model);
        model.addColumn("KODE TRANSAKSI");
        model.addColumn("KODE KAMAR");
        model.addColumn("NAMA KAMAR");
        model.addColumn("HARGA SEWA");
        model.addColumn("KODE PENYEWA");
        model.addColumn("NAMA PENYEWA");
        model.addColumn("TANGGAL SEWA");
        model.addColumn("LAMA (BULAN)");
        model.addColumn("JUMLAH BAYAR");
        
         getDataProduk();
    }
public void getDataProduk(){
        //kosongkan tabel
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        
        //eksekusi koneksi dan kirimkan query ke database
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk membaca data dari tabel gaji        
            String sql = "SELECT * FROM transaksi";
            ResultSet res = stat.executeQuery(sql);
            
            //baca data
            while(res.next()){
                //membuat obyek berjenis array
                Object[] obj = new Object[9];
                obj[0]=res.getString("id_transaksi");
                obj[1]=res.getString("id_kost");
                obj[2]=res.getString("nama_kost");
                obj[3]=res.getString("tarif");
                obj[4]=res.getString("id_penyewa");
                obj[5]=res.getString("nama_penyewa");
                obj[6]=res.getString("tgl_sewa");
                obj[7]=res.getString("lama_sewa");
                obj[8]=res.getString("harga_sewa");
               
                model.addRow(obj);
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    //fungsi untuk menampilkan data pada textbox
    public void dataProduk(){   
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM kamarkost WHERE id_kost = '"+ txtidkost.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
            
            
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               txtnmkost.setText(res.getString("nama"));
               txtHrg.setText(res.getString("tarif"));
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
   
    
    public void datapenyewa(){   
        try{
            //tes koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
           
            //perintah sql untuk membaca data dari tabel produk
            String sql = "SELECT * FROM penyewa WHERE id_penyewa = '"+ txtidsewa.getText() +"'";
            ResultSet res = stat.executeQuery(sql);
            
            
                        
            //baca data dan tampilkan pada text produk dan harga
            while(res.next()){
                //membuat obyek berjenis array
               txtnmsewa.setText(res.getString("nama"));
               txtalamat.setText(res.getString("kabupaten"));
               txtkerja.setText(res.getString("pekerjaan"));
               txtcatat.setText(res.getString("catatan"));
   
            }
        }catch(SQLException err){
           JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
   
    
    
//fungsi untuk memasukan barang yang sudah dipilih pada tabel sementara
    public void masukTabel(){
        try{
            String kdTransaksi=txtidtrans.getText();
            String kdProduk=txtidkost.getText();
            String nmProduk=txtnmkost.getText();
          String TARIF=txtHrg.getText();
            String kdPenyewa=txtidsewa.getText();
            String nmPenyewa=txtnmsewa.getText();
           String tanggal=txttanggal.getText();
            String lama=txtlama.getText();
            double hrg=Double.parseDouble(txtHrg.getText());
            int jml=Integer.parseInt(txtlama.getText());
            sTotal = hrg*jml;
            total = total + sTotal;
            xtotal=Double.toString(total);
            lblTotal.setText(xtotal);
            model.addRow(new Object[]{kdTransaksi,kdProduk,nmProduk,TARIF,kdPenyewa,nmPenyewa,tanggal,lama,hrg,jml,sTotal});
        }
        catch(Exception e){
            System.out.println("Error : "+e);
        }
    }
    
    public void simpanDataTransaksi(){ 
        //proses perhitungan uang kembalian
        bayar = Double.parseDouble(txtBayar.getText());
        kembali = bayar - total;
        String xkembali=Double.toString(kembali);
        lblKembali.setText(xkembali);
       
        //uji koneksi dan eksekusi perintah
        try{
            //test koneksi
            Statement stat = (Statement) koneksiDB.getKoneksi().createStatement();
            
            //perintah sql untuk simpan data
            String  sql =   "INSERT INTO transaksi(id_transaksi,id_penyewa, nama_penyewa,id_kost,nama_kost,tarif,tgl_sewa,lama_sewa,harga_sewa)"
                            + "VALUES('"+ txtidtrans.getText() +"','"+ txtidsewa.getText()+"','"+ txtnmsewa.getText()+"','"+ txtidkost.getText()+"','"+ txtnmkost.getText()+"','"+ txtHrg.getText()+"','"+ txttanggal.getText() +"','"+ txtlama.getText() +"','"+ total +"')";
            PreparedStatement p = (PreparedStatement) koneksiDB.getKoneksi().prepareStatement(sql);
            p.executeUpdate();
            
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
 public void reset(){
        kdProduk ="" ;
        nmProduk ="" ;
      
        TARIF ="" ;
        tanggal ="" ;
        kdPenyewa ="" ;
        nmPenyewa ="" ;
        lama ="" ;
        user="" ;
        ALAMAT="" ;
        PEKERJAAN="" ;
      
       
        txtidkost.setText(kdProduk);
        txtnmkost.setText(nmProduk);
        
        txtHrg.setText(TARIF);
        txttanggal.setText(tanggal);
        txtidsewa.setText(kdPenyewa);
        txtnmsewa.setText(nmPenyewa);
        txtlama.setText(lama);
        txtcatat.setText(user);
        txtalamat.setText(ALAMAT);
        txtkerja.setText(PEKERJAAN);
        txtBayar.setText("");
        lblTotal.setText("");
        lblKembali.setText("");
        
        
        
    }
 
     public void koneksi(){
       try {
            String url ="jdbc:mysql://localhost/kost";
            String user="root";
            String pass="";
            Class.forName("com.mysql.jdbc.Driver");
            con =DriverManager.getConnection(url,user,pass);
            st = con.createStatement();
            System.out.println("koneksi berhasil;");
        } catch (Exception e) {
            System.err.println("koneksi gagal" +e.getMessage());
        }
    }
    
    private void nofaktur(){
       try {
             sql = "SELECT MAX(RIGHT(id_transaksi,6)) AS NO FROM transaksi";
            st = con.createStatement();
            ResultSet rsjual = st.executeQuery(sql);
            while (rsjual.next()) {
                if (rsjual.first() == false) {
                 txtidtrans.setText("TR-000001");
                } else {
                    rsjual.last();
                    int auto_id = rsjual.getInt(1) + 1;
                    String no = String.valueOf(auto_id);
                    int NomorJual = no.length();
                    //MENGATUR jumlah 0
                    for (int j = 0; j < 6 - NomorJual; j++) {
                        no = "0" + no;
                    }
                   txtidtrans.setText("TR-" + no);
                }
            }
 
            rsjual.close();
            con.close();
            
           }catch(Exception e){
          JOptionPane.showMessageDialog(this, "ERROR: \n" + e.toString(),
                    "Kesalahan", JOptionPane.WARNING_MESSAGE);
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

        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        txtidtrans = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        txtidkost = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtnmkost = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtHrg = new javax.swing.JTextField();
        txtkerja = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtalamat = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtcatat = new javax.swing.JTextField();
        txtidsewa = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txtnmsewa = new javax.swing.JTextField();
        txttanggal = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtlama = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        tblbaru = new javax.swing.JButton();
        tblSimpan = new javax.swing.JButton();
        cmdKeranjang = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        tblkeluar = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        lblKembali = new javax.swing.JTextField();
        txtBayar = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        label = new javax.swing.JLabel();
        lblTotal = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblTransaksi = new javax.swing.JTable();

        jButton1.setText("jButton1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setMinimumSize(new java.awt.Dimension(750, 550));
        setResizable(false);
        addWindowStateListener(new java.awt.event.WindowStateListener() {
            public void windowStateChanged(java.awt.event.WindowEvent evt) {
                formWindowStateChanged(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(106, 72, 724, -1));

        jPanel3.setBackground(new java.awt.Color(248, 148, 6));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtidtrans.setEditable(false);
        txtidtrans.setBackground(new java.awt.Color(108, 122, 137));
        txtidtrans.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtidtransMouseClicked(evt);
            }
        });
        jPanel3.add(txtidtrans, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 10, 157, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("NOMOR TRANSAKSI");
        jPanel3.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(519, 13, 150, -1));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 870, 40));

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel1.setText("TRANSAKSI SEWA KOST");
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, 14));

        jPanel5.setBackground(new java.awt.Color(44, 62, 80));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtidkost.setBackground(new java.awt.Color(204, 204, 204));
        txtidkost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidkost.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidkostKeyReleased(evt);
            }
        });
        jPanel5.add(txtidkost, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 20, 120, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("/ BULAN");
        jPanel5.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 70, -1));

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("NAMA KAMAR KOST");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, 108, -1));

        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("ALAMAT");
        jPanel5.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, -1, -1));

        txtnmkost.setBackground(new java.awt.Color(108, 122, 137));
        txtnmkost.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmkost.setEnabled(false);
        jPanel5.add(txtnmkost, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 50, 180, -1));

        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("PEKERJAAN");
        jPanel5.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 120, -1, -1));

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("HARGA");
        jPanel5.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 45, -1));

        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("CATATAN");
        jPanel5.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 140, -1, 21));

        txtHrg.setBackground(new java.awt.Color(108, 122, 137));
        txtHrg.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtHrg.setEnabled(false);
        jPanel5.add(txtHrg, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 120, -1));

        txtkerja.setBackground(new java.awt.Color(108, 122, 137));
        txtkerja.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtkerja.setEnabled(false);
        jPanel5.add(txtkerja, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 110, 230, -1));

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("KODE PENYEWA");
        jPanel5.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 20, -1, -1));

        txtalamat.setBackground(new java.awt.Color(108, 122, 137));
        txtalamat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtalamat.setEnabled(false);
        jPanel5.add(txtalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 80, 230, -1));

        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("NAMA PENYEWA");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, -1, -1));

        txtcatat.setBackground(new java.awt.Color(108, 122, 137));
        txtcatat.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtcatat.setEnabled(false);
        jPanel5.add(txtcatat, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 140, 230, -1));

        txtidsewa.setBackground(new java.awt.Color(204, 204, 204));
        txtidsewa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtidsewa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtidsewaKeyReleased(evt);
            }
        });
        jPanel5.add(txtidsewa, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, 180, -1));

        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("TANGGAL SEWA");
        jPanel5.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        txtnmsewa.setBackground(new java.awt.Color(108, 122, 137));
        txtnmsewa.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txtnmsewa.setEnabled(false);
        jPanel5.add(txtnmsewa, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, 230, -1));

        txttanggal.setBackground(new java.awt.Color(204, 204, 204));
        txttanggal.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(txttanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 180, -1));

        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("LAMA SEWA");
        jPanel5.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("KODE KAMAR KOST");
        jPanel5.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        txtlama.setBackground(new java.awt.Color(204, 204, 204));
        txtlama.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jPanel5.add(txtlama, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 110, 70, -1));

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 810, 190));

        jPanel6.setBackground(new java.awt.Color(248, 148, 6));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblbaru.setBackground(new java.awt.Color(34, 167, 240));
        tblbaru.setForeground(new java.awt.Color(255, 255, 255));
        tblbaru.setText("BARU");
        tblbaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblbaruActionPerformed(evt);
            }
        });
        jPanel6.add(tblbaru, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 110, 30));

        tblSimpan.setBackground(new java.awt.Color(34, 167, 240));
        tblSimpan.setForeground(new java.awt.Color(255, 255, 255));
        tblSimpan.setText("BAYAR");
        tblSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblSimpanActionPerformed(evt);
            }
        });
        jPanel6.add(tblSimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 120, 30));

        cmdKeranjang.setBackground(new java.awt.Color(34, 167, 240));
        cmdKeranjang.setForeground(new java.awt.Color(255, 255, 255));
        cmdKeranjang.setText("SEWA");
        cmdKeranjang.setMinimumSize(new java.awt.Dimension(160, 40));
        cmdKeranjang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdKeranjangActionPerformed(evt);
            }
        });
        jPanel6.add(cmdKeranjang, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 10, 130, 30));

        jButton3.setBackground(new java.awt.Color(34, 167, 240));
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("CETAK STRUK");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel6.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, 130, 30));

        tblkeluar.setBackground(new java.awt.Color(34, 167, 240));
        tblkeluar.setForeground(new java.awt.Color(255, 255, 255));
        tblkeluar.setText("KELUAR");
        tblkeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tblkeluarActionPerformed(evt);
            }
        });
        jPanel6.add(tblkeluar, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 10, 120, 30));

        getContentPane().add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, 850, 50));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel9.setText("KEMBALI");
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 360, -1, -1));

        lblKembali.setBackground(new java.awt.Color(108, 122, 137));
        lblKembali.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblKembali.setEnabled(false);
        getContentPane().add(lblKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, 330, 70));

        txtBayar.setBackground(new java.awt.Color(204, 204, 204));
        txtBayar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        getContentPane().add(txtBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 420, 320, 50));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setText("BAYAR");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 430, -1, -1));

        label.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        label.setText("TOTAL");
        getContentPane().add(label, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        lblTotal.setBackground(new java.awt.Color(108, 122, 137));
        lblTotal.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblTotal.setEnabled(false);
        getContentPane().add(lblTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 370, 320, 44));

        jPanel1.setBackground(new java.awt.Color(44, 62, 80));

        tblTransaksi.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblTransaksi);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 810, 220));

        setSize(new java.awt.Dimension(855, 749));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void cmdKeranjangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmdKeranjangActionPerformed
        //memanggil fungsi masuk tabel sementara
        masukTabel();
        
    }//GEN-LAST:event_cmdKeranjangActionPerformed

    private void txtidkostKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidkostKeyReleased
        //memanggil fungsi data produk
        dataProduk();
        
    }//GEN-LAST:event_txtidkostKeyReleased

    private void tblSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblSimpanActionPerformed
        //memanggil fungsi simpan data transaksi
        simpanDataTransaksi();
       
       
    }//GEN-LAST:event_tblSimpanActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         reset();
       
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtidsewaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtidsewaKeyReleased
        datapenyewa();
    }//GEN-LAST:event_txtidsewaKeyReleased

    private void formWindowStateChanged(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowStateChanged

    private void tblbaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblbaruActionPerformed
        reset();
        new frm_transaksi().setVisible(true);
    }//GEN-LAST:event_tblbaruActionPerformed

    private void tblkeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tblkeluarActionPerformed
       this.dispose();
        new frm_utama().setVisible(true);
    }//GEN-LAST:event_tblkeluarActionPerformed

    private void txtidtransMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtidtransMouseClicked
        koneksi();
        nofaktur();
    }//GEN-LAST:event_txtidtransMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
       
        new frm_cetakfaktur().setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frm_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frm_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frm_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frm_transaksi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frm_transaksi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cmdKeranjang;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel label;
    private javax.swing.JTextField lblKembali;
    private javax.swing.JTextField lblTotal;
    private javax.swing.JButton tblSimpan;
    private javax.swing.JTable tblTransaksi;
    private javax.swing.JButton tblbaru;
    private javax.swing.JButton tblkeluar;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextField txtHrg;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtcatat;
    private javax.swing.JTextField txtidkost;
    private javax.swing.JTextField txtidsewa;
    private javax.swing.JTextField txtidtrans;
    private javax.swing.JTextField txtkerja;
    private javax.swing.JTextField txtlama;
    private javax.swing.JTextField txtnmkost;
    private javax.swing.JTextField txtnmsewa;
    private javax.swing.JTextField txttanggal;
    // End of variables declaration//GEN-END:variables
}
