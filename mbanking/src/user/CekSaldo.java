/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import function.MyConnection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class CekSaldo {

//DEKLARASI KOMPONEN
  JFrame window = new JFrame("Check Saldo");
  JLabel lTotalSaldo = new JLabel("Total Saldo");
  JLabel lIntSaldo = new JLabel();
  JLabel lguide = new JLabel("<HTML><U>Back</U></HTML");

  JButton btnBack = new JButton("Back");
  
  public CekSaldo(Nasabah n) {
    window.setLayout(null);
    window.setSize(380, 210);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lIntSaldo);
    window.add(lTotalSaldo);
    window.add(btnBack);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lTotalSaldo.setBounds(0, 35, 370, 30);
    lTotalSaldo.setHorizontalAlignment(0);
    
    lIntSaldo.setBounds(0, 55, 370, 30);
    lIntSaldo.setHorizontalAlignment(0);
    
    lguide.setBounds(75, 35, 20, 30);
    btnBack.setBounds(145, 110, 80, 30);
   
    lIntSaldo.setText(String.valueOf(n.getSaldo()));
 
    btnBack.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        window.dispose();
        new MenuUtama(n);
      }
    });
  }

}
