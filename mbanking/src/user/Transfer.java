/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import function.MyConnection;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Transfer {

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Transfer");
  JLabel lNoRecipient = new JLabel("No. Recipient Account");
  JLabel lAmount = new JLabel("Amount");

  JPasswordField pfNo = new JPasswordField(15);
  JTextField tfAmount = new JTextField(25);

  JButton btnTransfer = new JButton("Transfer");
  JButton btnReset = new JButton("Reset");

  private MyConnection myConnection = new MyConnection();
  private PreparedStatement ps = null;
  private ResultSet rs = null;
  private String query = null;
  private String temptString = null;
  private boolean cek = false;
  private int tempt = 0, tempSaldo = 0; // helper

  public Transfer(String id) {
    window.setLayout(null);
    window.setSize(380, 250);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lNoRecipient);
    window.add(lAmount);
    window.add(tfAmount);
    window.add(pfNo);
    window.add(btnTransfer);
    window.add(btnReset);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lNoRecipient.setBounds(80, 35, 70, 30);
    lAmount.setBounds(80, 75, 70, 30);

    tfAmount.setBounds(170, 75, 120, 30);
    pfNo.setBounds(170, 35, 120, 30);

    btnTransfer.setBounds(200, 155, 90, 30);
    btnReset.setBounds(80, 155, 90, 30);

    // sett mouse pointer
    lNoRecipient.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lAmount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnTransfer.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        System.out.println("Transfer ID " + id);

        // simpan input user  
        String accRecipient = String.valueOf(pfNo.getPassword()); // pin penerima
        int amount = Integer.valueOf(tfAmount.getText());

        if (accRecipient.isEmpty() || amount == 0) {
          JOptionPane.showMessageDialog(null, "All Form Must Filled");
        } else {
          // 1. cek no acc tujuan ada/tidak
          query = "SELECT `saldo` FROM `nasabah` WHERE `acc_number` =?";
          try {
            ps = myConnection.getCOnnection().prepareStatement(query);
            ps.setString(1, accRecipient);
            rs = ps.executeQuery();
            System.out.println("cek " + accRecipient);

            if (rs.next()) {
              cek = true;; // 
              temptString = rs.getString(1);
              tempSaldo = Integer.valueOf(temptString); // simpan saldo penermia
              System.out.println("ACC Penerima Ditemukan");
            } else {
              JOptionPane.showMessageDialog(null, "Incorrect Destination Number");
            }
          } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
          } 

          // 2. Jika PIN ditemukan, update saldo penerima
          if (cek) {
            tempt = tempSaldo + amount;
            temptString = String.valueOf(tempt); // ubah menjadi string

            query = "UPDATE `nasabah` SET `saldo`=? WHERE `acc_number`=?";
            try {
              ps = myConnection.getCOnnection().prepareStatement(query);
              ps.setString(1, temptString); // saldo diubah
              ps.setString(2, accRecipient);
              int i = ps.executeUpdate();

              if (i == 1) {
                cek = true;
              } else {
                JOptionPane.showMessageDialog(null, "Transfer Failed");
                System.err.println("Gagal Update Saldo Penerima");
                cek = false;
              }
            } catch (SQLException ex) {
              Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
              tempSaldo = 0;
              tempt = 0;
              temptString = null; // refresh
            }
          }

          // 3. ambil saldo pengirim
          query = "SELECT `saldo` FROM `nasabah` WHERE `user_id` =?";
          if (cek) {
            try {
              ps = myConnection.getCOnnection().prepareStatement(query);
              ps.setString(1, id);
              rs = ps.executeQuery();

              if (rs.next()) { // jika pin sama
                temptString = rs.getString(1);
                tempSaldo = Integer.valueOf(temptString); // simpan saldo pengirim
                cek = true;
                System.out.println("Saldo Pengirim Tersimpan");
              } else {
                cek = false;
                JOptionPane.showMessageDialog(null, "Transfer Failed");
              }
            } catch (SQLException ex) {
              Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
          }

          // 4. Kurangi saldo pengirim
          if (cek) {
            cek = false;
            tempt = tempSaldo - amount;
            temptString = String.valueOf(tempt);
            query = "UPDATE `nasabah` SET `saldo`=? WHERE `user_id`=?";
            try {
              ps = myConnection.getCOnnection().prepareStatement(query);
              ps.setString(1, temptString);
              ps.setString(2, id);
              int i = ps.executeUpdate();

              if (i == 1) { // jika pin sama
                JOptionPane.showMessageDialog(null, "Transfer Success");
                cek = true;
                System.out.println("Saldo Pengirim Berkurang");
              } else {
                JOptionPane.showMessageDialog(null, "Transfer Failed");
                cek = false;
              }
            } catch (SQLException ex) {
              Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
              tempSaldo = 0;
              tempt = 0;
              temptString = null; // refresh
            }
          }
        }
      }
    }
    );

    btnReset.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        pfNo.setText("");
        tfAmount.setText("");
      }
    });

// MOUSE LISTENER
    lNoRecipient.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        pfNo.requestFocusInWindow();
      }
    });
    lAmount.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfAmount.requestFocusInWindow();
      }
    });

    window.addWindowListener(new WindowAdapter() {

      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });

    pfNo.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          btnTransfer.doClick();
        }
      }
    });
    tfAmount.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          btnTransfer.doClick();
        }
      }
    });
  }
}
