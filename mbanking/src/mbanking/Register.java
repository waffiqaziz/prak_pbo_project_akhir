/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbanking;

import com.toedter.calendar.JDateChooser;
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
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Register {

  MyConnection con = new MyConnection();

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Register");
  JLabel lName = new JLabel("Full Name");
  JLabel lPin = new JLabel("ATM PIN");
  JLabel lEmail = new JLabel("Email");
  JLabel lTelp = new JLabel("No. Telephone");
  JLabel lDate = new JLabel("Date of Birth");
  JLabel lUser = new JLabel("UserID");

  JLabel lguide = new JLabel("<HTML><U>Have Account</U></HTML");

  JPasswordField pfPin = new JPasswordField();
  JTextField tfName = new JTextField(20);
  JTextField tfEmail = new JTextField();
  JTextField tfTelp = new JTextField(15);
  JTextField tfUser = new JTextField(15);
  JDateChooser dcDate = new JDateChooser();
  JButton btnRegis = new JButton("Regis");
  JButton btnReset = new JButton("Reset");

  public Register() {
    window.setLayout(null);
    window.setSize(450, 420);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lPin);
    window.add(lName);
    window.add(lEmail);
    window.add(lTelp);
    window.add(lDate);
    window.add(lUser);
    window.add(lguide);
    window.add(pfPin);
    window.add(tfEmail);
    window.add(dcDate);
    window.add(tfName);
    window.add(tfTelp);
    window.add(tfUser);
    window.add(btnRegis);
    window.add(btnReset);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lName.setBounds(90, 35, 70, 30);
    lPin.setBounds(90, 75, 50, 30);
    lEmail.setBounds(90, 115, 35, 30);
    lTelp.setBounds(90, 155, 90, 30);
    lDate.setBounds(90, 195, 80, 30);
    lUser.setBounds(90, 235, 50, 30);
    tfName.setBounds(200, 35, 150, 30);
    pfPin.setBounds(200, 75, 150, 30);
    tfEmail.setBounds(200, 115, 150, 30);
    tfTelp.setBounds(200, 155, 150, 30);
    dcDate.setBounds(200, 195, 150, 30);
    tfUser.setBounds(200, 235, 150, 30);
    lguide.setBounds(180, 275, 150, 30);

    btnRegis.setBounds(240, 315, 90, 30);
    btnReset.setBounds(110, 315, 90, 30);

// SETT MOUSE POINTER
    lName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lPin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lEmail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lTelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lDate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnRegis.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
// TODO add your handling code here:
        String date = null;
        String name = tfName.getText();
        String user = tfUser.getText();
        String email = tfEmail.getText();
        String telp = tfTelp.getText();
        String pin = String.valueOf(pfPin.getPassword());

        // cek jika ada kolom yang belum di isi
        if (user.equals("") || email.equals("") || pin.equals("") || telp.equals("")) {
          JOptionPane.showMessageDialog(null, "All Form Must Filled");
        }
        if (dcDate.getDate() != null) { // jika date terisi
          SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd"); // format tahun-bulan-hari
          date = dateformat.format(dcDate.getDate());
          System.out.println("Cek Date");
          System.out.println(date);
        } else {
          PreparedStatement ps;
          ResultSet rs;
          boolean cek = false;

          // cek apakah username sudah ada/belum
          String query = "SELECT pin FROM `nasabah` WHERE `pin` =?";
          try {
            MyConnection myConnection = new MyConnection();

            ps = myConnection.con.prepareStatement(query);
            ps.setString(1, pin);
            rs = ps.executeQuery();

            if (rs.next()) { // jika ada username yang sama
              cek = true;
            } else {
              cek = false;
            }
          } catch (SQLException ex) {
            Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
          }
          
          if (!cek) { // jika tidak ada user name yang sama, maka akan di masukkan kedalam database
            query = "INSERT INTO `nasabah`(`full_name`, `user`, `email`, `telp`, `pin`, `dateOfBirth`) VALUES (?,?,?,?,?,?)";

            try {
              MyConnection myConnection = new MyConnection();

              ps = myConnection.con.prepareStatement(query);
              ps.setString(1, name);
              ps.setString(2, user);
              ps.setString(3, email);
              ps.setString(4, telp);
              ps.setString(5, pin);

              if (date != null) { // jika date kosong maka set null
                ps.setString(6, date);
              } else {
                ps.setNull(6, 0);
              }
              ps.setString(6, date);

              // jika berhasil
              if (ps.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "New User Add");
              }
            } catch (SQLException ex) {
              Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
            }
          } else {
            JOptionPane.showMessageDialog(null, "ATM has been Registered as M-Banking");
          }
        }
      }
    });

    btnReset.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        pfPin.setText("");
        tfUser.setText("");
      }
    });

// MOUSE LISTENER
    lguide.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("Login Form Clicked");
        window.dispose();
        new Login();
      }
    });
    lUser.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfUser.requestFocusInWindow();
      }
    });
    lEmail.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfEmail.requestFocusInWindow();
      }
    });
    lName.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfName.requestFocusInWindow();
      }
    });
    lDate.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        dcDate.requestFocusInWindow();
      }
    });
    lPin.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        pfPin.requestFocusInWindow();
      }
    });
    lTelp.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfTelp.requestFocusInWindow();
      }
    });

// WINDOWS LISTENER   
    window.addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });
  }
}
