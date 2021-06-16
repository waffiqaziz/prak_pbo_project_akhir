/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbanking;

import user.MainMenu;
import user.Register;
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
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Login extends JFrame {

  String user;
  String pin;

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Login");
  JLabel lUser = new JLabel("Username");

  JTextField tfUser = new JTextField();
  JLabel lPass = new JLabel("PIN");

  JPasswordField pfPass = new JPasswordField();
  JLabel lguide = new JLabel("<HTML><U>Dont Have Account?</U></HTML");

  JButton btnLogin = new JButton("Login");
  JButton btnReset = new JButton("Reset");

  public Login() {
    window.setLayout(null);
    window.setSize(380, 250);
    //  window.setDefaultCloseOperation(3);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lUser);
    window.add(tfUser);
    window.add(pfPass);
    window.add(lPass);
    window.add(lguide);
    window.add(btnLogin);
    window.add(btnReset);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lUser.setBounds(80, 35, 65, 30);
    lPass.setBounds(80, 75, 20, 30);
    lguide.setBounds(130, 115, 120, 30);

    tfUser.setBounds(170, 35, 120, 30);
    pfPass.setBounds(170, 75, 120, 30);

    btnLogin.setBounds(200, 155, 90, 30);
    btnReset.setBounds(80, 155, 90, 30);

    // sett mouse pointer
    lUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lPass.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnLogin.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        MyConnection myConnection = new MyConnection();
        PreparedStatement ps = null;
        ResultSet rs;
        rs = null;

        // Tangkap input user        
        user = tfUser.getText();
        pin = String.valueOf(pfPass.getPassword());

        if (user.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Fill User Coloumn");
          tfUser.requestFocusInWindow();
        }
        if (pin.isEmpty()) {
          JOptionPane.showMessageDialog(null, "Fill PIN Coloumn");
          pfPass.requestFocusInWindow();
        } else {
          String query = "SELECT * FROM `nasabah` WHERE `user` =? AND `pin` =?";

          try {
            ps = myConnection.getCOnnection().prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pin);
            rs = ps.executeQuery();
            
            if (rs.next()) {
              // simpan data userID dan full name dalam variabel
              String id = rs.getString(1); // userID
              String id2 = rs.getString(2); // full name
              System.out.println(id);
              window.dispose();
              MainMenu mm = new MainMenu(id,id2);
              mm.pack();
              mm.setLocationRelativeTo(null); // center
              JOptionPane.showMessageDialog(null, "Login Success");
            } else {
              JOptionPane.showMessageDialog(null, "Incorrect Username or Password" + " Login Failed");
            }
          } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
          } finally { // https://stackoverflow.com/a/2225275/12159309
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
      }
    });

    btnReset.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        pfPass.setText("");
        tfUser.setText("");
      }
    });

// MOUSE LISTENER
    lguide.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("Register Form Clicked");
        window.dispose();
        new Register();
      }
    });
    lPass.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        pfPass.requestFocusInWindow();
      }
    });
    lUser.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfUser.requestFocusInWindow();
      }
    });

    window.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });
  }
}
