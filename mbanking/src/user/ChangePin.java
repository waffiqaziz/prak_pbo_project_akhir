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
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import function.MyConnection;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class ChangePin {

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Change PIN");
  JLabel lOldPin = new JLabel("Old PIN");
  JLabel lRePin = new JLabel("Retype PIN");

  JPasswordField pfOldPin = new JPasswordField();
  JPasswordField pfRePin = new JPasswordField();
  JLabel lNewPin = new JLabel("New PIN");

  JPasswordField pfNewPin = new JPasswordField();
  JLabel lguide = new JLabel("<HTML><U>Forget Old PIN?</U></HTML");

  JButton btnChange = new JButton("Change");
  JButton btnReset = new JButton("Reset");

  public ChangePin(String id) {
    window.setLayout(null);
    window.setSize(380, 290);
    //  window.setDefaultCloseOperation(3);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lOldPin);
    window.add(pfOldPin);
    window.add(pfNewPin);
    window.add(pfRePin);
    window.add(lNewPin);
    window.add(lRePin);
    window.add(lguide);
    window.add(btnChange);
    window.add(btnReset);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lOldPin.setBounds(80, 35, 45, 30);
    lNewPin.setBounds(80, 75, 50, 30);
    lRePin.setBounds(80, 115, 70, 30);
    lguide.setBounds(140, 155, 120, 30);

    pfOldPin.setBounds(170, 35, 120, 30);
    pfNewPin.setBounds(170, 75, 120, 30);
    pfRePin.setBounds(170, 115, 120, 30);

    btnChange.setBounds(200, 195, 90, 30);
    btnReset.setBounds(80, 195, 90, 30);

    // sett mouse pointer
    lOldPin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lNewPin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lRePin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnChange.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        System.out.println("Change PIN " + id);

        MyConnection myConnection = new MyConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = null;
        boolean cek = false;

        // Tangkap input user  
        String newPin = String.valueOf(pfNewPin.getPassword());
        String oldPin = String.valueOf(pfOldPin.getPassword());
        String rePin = String.valueOf(pfRePin.getPassword());

        /*
         1. cek kolom sudah diisi semua/belum
         2. cek jika newPin sama/tdk dengan rePin
         3. jika iya, dilanjutkan dengan cek pin yang dimasukkan benar/tdk
         4. jika iya, dilanjutkan untuk update pin
         */
        
        // 1. cek input user
        if (oldPin.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Please Fill User Coloumn");
          pfOldPin.requestFocusInWindow();
          cek = true;
        }
        if (newPin.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Please Fill New PIN Coloumn");
          pfNewPin.requestFocusInWindow();
          cek = true;
        }
        if (rePin.isEmpty())  {
          JOptionPane.showMessageDialog(null, "Please Fill Retype PIN Coloumn");
          pfNewPin.requestFocusInWindow();
          cek = true;
        } else if (!cek) {
          cek = false;
          // 2. cek repin sama/tidak dengan newpin
          if (!(newPin.equals(rePin))) {
            JOptionPane.showMessageDialog(null, "PIN not Same");
            pfRePin.requestFocusInWindow();
          } else {
            System.out.println("NewPin = RePin");

            // 3. cek pin apakah benar
            query = "SELECT `pin` FROM `nasabah` WHERE `user_id` =?";
            try {
              ps = myConnection.getCOnnection().prepareStatement(query);
              ps.setString(1, id);
              rs = ps.executeQuery();
//              System.out.println("cek");

              String pinCheck = "";
              if (rs.next()) {
                pinCheck = rs.getString(1);
              }

              if (pinCheck.equals(oldPin)) { // jika oldpin benar
                System.out.println("Old Pin Correct");
                cek = true;
              } else {
                JOptionPane.showMessageDialog(null, "Incorrect PIN");
                pfOldPin.setText(null);
                pfOldPin.requestFocusInWindow();
                cek = false;
              }
            } catch (SQLException ex) {
              Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (cek == true) {
              // 4. update pin
              query = "UPDATE `nasabah` SET `pin`=? WHERE `user_id`=?";
              try {
                ps = myConnection.getCOnnection().prepareStatement(query);
                ps.setString(1, newPin);
                ps.setString(2, id);
                int i = ps.executeUpdate();

                if (i == 1) { // jika pin sama
                  JOptionPane.showMessageDialog(null, "Change PIN Success");
                } else {
                  JOptionPane.showMessageDialog(null, "Change PIN Failed");
                }
              } catch (SQLException ex) {
                Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
              } finally { // close
                closeConnection(ps,rs,myConnection);
              }
            }
          }
        }
      }
    });

    btnReset.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        pfNewPin.setText("");
        pfOldPin.setText("");
        pfRePin.setText("");
      }
    });

// MOUSE LISTENER
    lguide.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("Register Form Clicked");
        JOptionPane.showMessageDialog(null, "Please Contact Customer Service");
      }
    });
    lNewPin.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        pfNewPin.requestFocusInWindow();
      }
    });
    lOldPin.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        pfOldPin.requestFocusInWindow();
      }
    });
    lRePin.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        pfRePin.requestFocusInWindow();
      }
    });

    window.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });
  }
  
  void closeConnection(PreparedStatement ps, ResultSet rs, MyConnection myConnection) {
    if (rs != null) {
      try {
        rs.close();
      } catch (SQLException e) {
        /* Ignored */
      }
    }
    if (ps != null) {
      try {
        ps.close();
      } catch (SQLException e) {
        /* Ignored */
      }
    }
    if (myConnection.getCOnnection() != null) {
      try {
        myConnection.getCOnnection().close();
      } catch (SQLException e) {
        /* Ignored */
      }
    }
  }
}
