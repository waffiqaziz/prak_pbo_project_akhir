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
public class CountRow {

  public int countRow() {
    MyConnection myConnection = new MyConnection();
    PreparedStatement ps;
    ResultSet rs;
    int  n = 0;
    try {
      String query = "Select * from `log`";
      ps = myConnection.getCOnnection().prepareStatement(query);
      rs = ps.executeQuery();

      while (rs.next()) {
        n++;
      }
      return n ;
    } catch (SQLException ex) {
      Logger.getLogger(CountRow.class.getName()).log(Level.SEVERE, null, ex);
    }
    return -4;
  }
}
