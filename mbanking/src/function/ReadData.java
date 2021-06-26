/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package function;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class ReadData {
  public String[][] readAllData() {
    // untuk menghitung jumlah baris
    CountRow cr = new CountRow();

    // untuk menyimpan data
    String data[][] = new String[cr.countRow()][5]; // ada 5 kolomwa

    try {
      MyConnection myConnection = new MyConnection();
      PreparedStatement ps;
      ResultSet rs;

      String query = "Select * from `log` WHERE `";
      ps = myConnection.getCOnnection().prepareStatement(query);
      rs = ps.executeQuery();

      int n = 0;
      while (rs.next()) { //konversi tabel ke string
        data[n][0] = rs.getString(1);
        data[n][1] = rs.getString(2);
        data[n][2] = rs.getString(3);
        data[n][3] = rs.getString(4);
        data[n][4] = rs.getString(5);
        n++;
      }
      return data;
    } catch (SQLException ex) {
      System.out.println("Read Data Gagal");
      Logger.getLogger(ReadData.class.getName()).log(Level.SEVERE, null, ex);
      return null;
    }
  }
}
