/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

import function.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mbanking.Login;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Nasabah {
  private String user_id, acc_number, full_name, user, email, telp, dateOfBirth;
  private int pin, saldo;

  public void setNasabah(String acc_number, String full_name, String user, String email, String telp, int pin){
    this.acc_number = acc_number;
    this.full_name = full_name;
    this.user = user;
    this.email = email;
    this.telp = telp;
    this.pin = pin;
  }
  
  public void setSaldo(){
    this.saldo = 100000;
  }
  
  public void setUser_id(String user_id){
    this.user_id = user_id;
  }
  
  public void setDate(String date){
    this.dateOfBirth = date;
  }
  
  public boolean register(){
    MyConnection myConnection = new MyConnection();
    PreparedStatement ps;    
    
    String query = "INSERT INTO `nasabah`(`acc_number`, `pin`, `full_name`, `user`, `email`, `telp`, `dateOfBirth`, `saldo`) VALUES (?,?,?,?,?,?,?,?)";
    
    try {
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setString(1, acc_number);
      ps.setInt(2, pin);
      ps.setString(3, full_name);
      ps.setString(4, user);
      ps.setString(5, email);
      ps.setString(6, telp);

      if (dateOfBirth != null) { // jika date kosong maka set null
        ps.setString(7, dateOfBirth);
      } else {
        ps.setNull(7, 0);
      }
      ps.setInt(8, saldo);

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
  
  public boolean login(String user, int pin, Nasabah n1){
    MyConnection myConnection = new MyConnection();
    PreparedStatement ps;
    ResultSet rs;
    
    String query = "SELECT * FROM `nasabah` WHERE `user` =? AND `pin` =?";
        
    try {
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setString(1, user);
      ps.setInt(2, pin);
      rs = ps.executeQuery();
      
      if (rs.next()) {
        // simpan data dalam variabel 
        String dtuserId = rs.getString(1);
        String dtacc_number = rs.getString(2);
        int dtpin = Integer.valueOf(rs.getString(3));
        String dtfull_name = rs.getString(4);
        String dtuser = rs.getString(5);
        String dtemail = rs.getString(6);
        String dttelp = rs.getString(7);
        String dtdate = rs.getString(8);
        int dtsaldo = Integer.valueOf(rs.getString(9));
        
        System.out.println("Saldo " + dtsaldo);
        System.out.println("USer ID " + dtuserId);
        
        n1.setNasabah(dtacc_number, dtfull_name, dtuser, dtemail, dttelp, dtpin);
        n1.setUser_id(dtuserId);
        n1.setDate(dtdate);
        n1.saldo = dtsaldo;
        
        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    } 
  }
  
  public boolean changePin(String newPin, String id){
    MyConnection myConnection = new MyConnection();
    PreparedStatement ps;
    ResultSet rs;
    String query = "UPDATE `nasabah` SET `pin`=? WHERE `user_id`=?";

    try {
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setString(1, newPin);
      ps.setString(2, id);
      int i = ps.executeUpdate();

      if (i == 1) { // jika change pin success
        return true;
      } else {
        return false;
      }
    } catch (SQLException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
      return false;
    }
  }
  
  public String getNama(){
    return full_name;
  }
  public String getUserID(){
    return user_id;
  }
  public int getSaldo(){
    return saldo;
  }
  public int getPin(){
    return pin;
  }
}
