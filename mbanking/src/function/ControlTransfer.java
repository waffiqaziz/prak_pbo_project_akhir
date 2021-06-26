/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package function;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import user.Nasabah;
/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class ControlTransfer {
  public boolean transfer(Nasabah n, String idPenerima, int jumlah){
    int tempt;
    if (n.getSaldoPenerima(idPenerima, jumlah) != -1) { // cek no penerima ada/tidak
      System.out.println("ACC Penerima Ditemukan");
      if (n.updateSaldoPenerima(idPenerima, n.getSaldoPenerima(idPenerima, jumlah))) { // update saldo penerima
        System.out.println("Update Saldo Penerima Berhasil");
        // Kurangi saldo pengirim
        tempt = n.getSaldo() - jumlah;
        System.out.println("tempt " + tempt);
        if (n.updateSaldoPengirim(tempt)) {
          System.out.println("Saldo Pengirim Berhasil dikurangi");
          return true;
        } else {
          System.out.println("Saldo Pengirim Gagal dikurangi");
          return false;
        }
      } else {
        System.err.println("Gagal Update Saldo Penerima");
        return false;
      }
    } else {
      System.out.println("ACC Penerima tidak Ditemukan");
      return false;
    }
  }
  
  public void writeHistory(Nasabah n, String idPenerima, int jumlah) {
    MyConnection myConnection = new MyConnection();
    PreparedStatement ps;

    String query = "INSERT INTO `log`(`type`, `id_pengirim`, `id_penerima`, `amount`) VALUES (?,?,?,?)";

    try {
      ps = myConnection.getCOnnection().prepareStatement(query);
      ps.setString(1, "Transfer");
      ps.setString(2, n.getUserID());
      ps.setString(3, idPenerima);
      ps.setInt(4, jumlah);

      // jika berhasil
      if (ps.executeUpdate() > 0) {
        System.out.println("Log berhasil");
      }
    } catch (SQLException ex) {
      System.out.println("log gagal");
      Logger.getLogger(ControlTransfer.class.getName()).log(Level.SEVERE, null, ex);
    }
  }
}
