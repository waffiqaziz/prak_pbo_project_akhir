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
public class CountRow {

  public int countRow(Nasabah n) {
    PreparedStatement ps;
    ResultSet rs;
    int  row = 0;
    
    String query = "Select * from `log` WHERE `id_pengirim`=? OR `id_penerima`=?";
    
    try {
      MyConnection myConnection = new MyConnection();
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setInt(1, n.getAccNumber());
      ps.setInt(2, n.getAccNumber());
      rs = ps.executeQuery();

      while (rs.next()) {
        row++;
      }
      return row ;
    } catch (SQLException ex) {
      Logger.getLogger(CountRow.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -4;
  }
}
