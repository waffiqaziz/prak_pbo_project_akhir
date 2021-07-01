/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import user.Nasabah;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class ReadData {
  public String[][] readAllData(Nasabah n) {
    // untuk menghitung jumlah baris
    CountRow cr = new CountRow();

    // untuk menyimpan data
    String data[][] = new String[cr.countRow(n)][5]; // ada 5 kolomwa

    try {
      MyConnection myConnection = new MyConnection();
      PreparedStatement ps;
      ResultSet rs;

      String query = "Select * from `log` WHERE `id_pengirim`=? OR `id_penerima`=?";
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, n.getAccNumber());
      ps.setInt(2, n.getAccNumber());
      rs = ps.executeQuery();

      int row = 0;
      while (rs.next()) { //konversi tabel ke string
        data[row][0] = rs.getString(1);
        data[row][1] = rs.getString(2);
        data[row][2] = rs.getString(3);
        data[row][3] = rs.getString(4);
        data[row][4] = rs.getString(5);
        row++;
      }
      return data;
    } catch (SQLException ex) {
      System.out.println("Read Data Gagal");
      Logger.getLogger(ReadData.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }
}
