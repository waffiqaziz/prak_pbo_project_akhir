/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import model.MyConnection;
import javax.swing.JOptionPane;
import user.Nasabah;

/**
 *
 * @author Waffiq Aziz
 */
public class Main {

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) {
    java.awt.EventQueue.invokeLater(new Runnable() {
      public void run() {
        MyConnection myConnection = new MyConnection();
        if (myConnection.getCOnnection() == null) {
          JOptionPane.showMessageDialog(null, "Tidak dapat terhubung ke database. Pastikan Xampp sudah aktif!", "Pemberitahuan", JOptionPane.INFORMATION_MESSAGE);
        } else {
          new Login();
//Nasabah n = new Nasabah();
//          new ChangePIN(n);
        }
      }
    });
  }
}
