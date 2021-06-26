/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import function.MyConnection;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import mbanking.Login;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class MenuUtama extends JFrame {

  MyConnection myConnection = new MyConnection();

  String user;
  String pin;

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Main Menu");
  JLabel lGuide = new JLabel();

  JButton btnSaldo= new JButton("Check Saldo");
  JButton btnChangePin = new JButton("Change Pin");
  JButton btnWithdraw = new JButton("Withdraw (not yet)");
  JButton btnTranfer = new JButton("Transfer");
  JButton btnHistory = new JButton("History Transaction");
  JButton btnLogout = new JButton("Logout");
  
  public MenuUtama(Nasabah n) {
    lGuide.setText("Welcome " + n.getNama());
    lGuide.setVerticalTextPosition(0);
    window.setLayout(null);
    window.setSize(380, 390);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan
    
//ADD COMPONENT
    window.add(lGuide);
    window.add(btnSaldo);
    window.add(btnChangePin);
    window.add(btnWithdraw);
    window.add(btnHistory);
    window.add(btnTranfer);
    window.add(btnLogout);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lGuide.setBounds(0, 25, 370, 30);
    btnSaldo.setBounds(120, 75, 130, 30);
    btnTranfer.setBounds(120, 115, 130, 30);
    btnWithdraw.setBounds(120, 155, 130, 30);
    btnHistory.setBounds(95, 195, 180, 30);
    btnChangePin.setBounds(120, 235, 130, 30);
    btnLogout.setBounds(120, 275, 130, 30);

    lGuide.setHorizontalAlignment(0);
// ACTION LISTENER
    btnChangePin.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      new UbahPin(n);
    });
    btnSaldo.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      new CekSaldo(n);
    });
    btnTranfer.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      new Transfer(n);
    });
    btnHistory.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      new History(n);
    });
    btnLogout.addActionListener((ActionEvent arg0) -> {
      int yes = JOptionPane.showConfirmDialog(
              null,
              "Are you sure want to Logout",
              "Confirm Logout",
              JOptionPane.YES_NO_OPTION);
      if (yes == JOptionPane.YES_OPTION){
        window.dispose();
        new Login();
      } else;
    });

    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });
  }
}
