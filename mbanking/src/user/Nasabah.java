/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package user;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Nasabah{
  private String user_id , full_name, user, email, telp, dateOfBirth;
  private int pin, saldo, acc_number;

// SETTER
    public void setNasabah(int acc_number, String full_name, String user, String email, String telp){
      this.acc_number = acc_number;
      this.full_name = full_name;
      this.user = user;
      this.email = email;
      this.telp = telp;
    }
    public void setPin(int pin){
      this.pin = pin;
    }
    public void setSaldo(int saldo){
      this.saldo = saldo;
    }
    public void setUser_id(String user_id){
      this.user_id = user_id;
    }
    public void setDate(String date){
      this.dateOfBirth = date;
    }
    
// GETTER  
    public String getUserID(){
      return user_id;
    }
    public String getNama(){
      return full_name;
    }
    public String getUser(){
      return user;
    }
    public String getEmail(){
      return email;
    }
    public String getTelp(){
      return telp;
    }
    public String getDateOfBirth(){
      return dateOfBirth;
    }
    public int getPin(){
      return pin;
    }
    public int getSaldo(){
      return saldo;
    }
    public int getAccNumber(){
      return acc_number;
    }
}
