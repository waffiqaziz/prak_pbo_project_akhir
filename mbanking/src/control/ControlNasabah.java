/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package control;

import model.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import user.Nasabah;
import view.Login;
import view.Register;
/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class ControlNasabah {
  public boolean checkAccNumber(int accNumber){ // cek apakah acc sudah ada/belum
    PreparedStatement ps;
    ResultSet rs;
    
    String query = "SELECT pin FROM `nasabah` WHERE `acc_number` =?";
    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, accNumber);
      rs = ps.executeQuery();

      if (rs.next()) { // jika ada username yang sama
        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
    } 
    return false;
  }

// METHOD TRANSFER
  public boolean transfer(Nasabah n, int noAccPenerima, int jumlah){
    int tempt;
    
  // ambil saldo penerima + jumlah
    int saldoPenerima = getSaldoPenerima(noAccPenerima);
    
    if (saldoPenerima != -1) { // cek no penerima ada/tidak (bernilai -1 jika tidak ditemukan)
      System.out.println("ACC Penerima Ditemukan");
      
    // tambahkan saldo penerima dengan jumlah uang yang dikirim
      saldoPenerima += jumlah;
      
      if (updateSaldoPenerima(noAccPenerima, saldoPenerima)) { // update saldo penerima
        System.out.println("Update Saldo Penerima Berhasil");
        
      // Kurangi saldo pengirim
        tempt = n.getSaldo() - jumlah;
        System.out.println("tempt " + tempt);
        
        if (updateSaldoPengirim(n,tempt)) { // kurangi saldo pengirim
          System.out.println("Saldo Pengirim Berhasil dikurangi");
          n.setSaldo(tempt);
          return true;
        } else {
          System.err.println("Saldo Pengirim Gagal dikurangi");
          return false;
        }
        
      } else {
        System.err.println("Gagal Update Saldo Penerima");
        return false;
      }
      
    } else {
      System.err.println("ACC Penerima tidak Ditemukan");
      return false;
    }
  }
  
  public boolean updateSaldoPengirim(Nasabah n, int tempt) {
    PreparedStatement ps;

    String query = "UPDATE `nasabah` SET `saldo`=? WHERE `user_id`=?";

    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, tempt);
      ps.setString(2, n.getUserID());
      int i = ps.executeUpdate();

      return i == 1; // jika pin sama

    } catch (SQLException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
  }
  
  public int getSaldoPenerima(int noAccPenerima) {
    PreparedStatement ps;
    ResultSet rs;

    String query = "SELECT `saldo` FROM `nasabah` WHERE `acc_number` =?";

    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, noAccPenerima);
      rs = ps.executeQuery();
      System.out.println("cek " + noAccPenerima);

      if (rs.next()) {
        int tempt;
        tempt = rs.getInt(1);
        return tempt;
      } else {
        return -1;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
      return -1;
    }
  }
  
  public boolean updateSaldoPenerima(int noAccPenerima, int tempt) {
    PreparedStatement ps;

    String query = "UPDATE `nasabah` SET `saldo`=? WHERE `acc_number`=?";

    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, tempt); // saldo diubah
      ps.setInt(2, noAccPenerima);
      int i = ps.executeUpdate();

      return i == 1;
    } catch (SQLException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
  }  
  
  public void writeHistory(Nasabah n, int noAccPenerima, int jumlah) {
    PreparedStatement ps;

    String query = "INSERT INTO `log`(`type`, `id_pengirim`, `id_penerima`, `amount`) VALUES (?,?,?,?)";

    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setString(1, "Transfer");
      ps.setInt(2, n.getAccNumber());
      ps.setInt(3, noAccPenerima);
      ps.setInt(4, jumlah);

      // jika berhasil
      if (ps.executeUpdate() > 0) {
        System.out.println("Log berhasil");
      }
    } catch (SQLException ex) {
      System.out.println("log gagal");
      Logger.getLogger(ControlNasabah.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
  
  public boolean register(Nasabah n){
      PreparedStatement ps;    

      String query = "INSERT INTO `nasabah`(`acc_number`, `pin`, `full_name`, `user`, `email`, `telp`, `dateOfBirth`, `saldo`) VALUES (?,?,?,?,?,?,?,?)";

      try {
        MyConnection myConnection = new MyConnection();
        ps = myConnection.getCOnnection().prepareStatement(query);
        ps.setInt(1, n.getAccNumber());
        ps.setInt(2, n.getPin());
        ps.setString(3, n.getNama());
        ps.setString(4, n.getUser());
        ps.setString(5, n.getEmail());
        ps.setString(6, n.getTelp());

        if (n.getDateOfBirth() != null) { // jika date kosong maka set null
          ps.setString(7, n.getDateOfBirth());
        } else {
          ps.setNull(7, 0);
        }
        ps.setInt(8, n.getSaldo());

        // jika berhasil
        if (ps.executeUpdate() > 0) {
          return true;
        }
      } catch (SQLException ex) {
        Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
        return false;
      }
      return false;
  }
  
  public boolean login(String user, int pin, Nasabah n){
    PreparedStatement ps;
    ResultSet rs;
    
    String query = "SELECT * FROM `nasabah` WHERE `user` =? AND `pin` =?";

    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setString(1, user);
      ps.setInt(2, pin);
      rs = ps.executeQuery();
      
      if (rs.next()) {
        
        // simpan data dalam variabel 
        String dtuserId = rs.getString(1);
        int dtacc_number = rs.getInt(2);
        int dtpin = Integer.valueOf(rs.getString(3));
        String dtfull_name = rs.getString(4);
        String dtuser = rs.getString(5);
        String dtemail = rs.getString(6);
        String dttelp = rs.getString(7);
        String dtdate = rs.getString(8);
        int dtsaldo = Integer.valueOf(rs.getString(9));
        
        System.out.println("Saldo " + dtsaldo);
        System.out.println("USer ID " + dtuserId);
        
        // simpan dalam atribut
          n.setNasabah(dtacc_number, dtfull_name, dtuser, dtemail, dttelp);
          n.setPin(dtpin);
          n.setUser_id(dtuserId);
          n.setDate(dtdate);
          n.setSaldo(dtsaldo);
        
        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    } 
  }
  
  public boolean changePin(int newPin, String id){
    PreparedStatement ps;
    String query = "UPDATE `nasabah` SET `pin`=? WHERE `user_id`=?";

    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, newPin);
      ps.setString(2, id);
      int i = ps.executeUpdate();

      return i == 1; // jika change pin success
      
    } catch (SQLException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
  }
}
