/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.toedter.calendar.JDateChooser;
import control.ControlNasabah;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import model.MyConnection;
import user.Nasabah;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Register {

  MyConnection con = new MyConnection();

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Register");
  JLabel lNo = new JLabel("Account Number");
  JLabel lPin = new JLabel("ATM PIN");
  JLabel lName = new JLabel("Full Name");
  JLabel lEmail = new JLabel("Email");
  JLabel lTelp = new JLabel("No. Telephone");
  JLabel lDate = new JLabel("Date of Birth");
  JLabel lUser = new JLabel("UserID");

  JLabel lguide = new JLabel("<HTML><U>Have Account?</U></HTML");

  JTextField tfNo = new JTextField(15);
  JPasswordField pfPin = new JPasswordField(4);
  JTextField tfName = new JTextField(20);
  JTextField tfEmail = new JTextField();
  JTextField tfTelp = new JTextField(15);
  JTextField tfUser = new JTextField(15);
  JDateChooser dcDate = new JDateChooser();
  JButton btnRegis = new JButton("Regis");
  JButton btnReset = new JButton("Reset");

  public Register() {
    window.setLayout(null);
    window.setSize(450, 460);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lNo);
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
    window.add(tfNo);
    window.add(tfName);
    window.add(tfTelp);
    window.add(tfUser);
    window.add(btnRegis);
    window.add(btnReset);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lNo.setBounds(90, 35, 95, 30);
    lPin.setBounds(90, 75, 50, 30);
    lEmail.setBounds(90, 155, 35, 30);
    lName.setBounds(90, 115, 70, 30);
    lTelp.setBounds(90, 195, 90, 30);
    lDate.setBounds(90, 235, 80, 30);
    lUser.setBounds(90, 275, 50, 30);
    tfNo.setBounds(200, 35, 150, 30);
    pfPin.setBounds(200, 75, 150, 30);
    tfName.setBounds(200, 115, 150, 30);
    tfEmail.setBounds(200, 155, 150, 30);
    tfTelp.setBounds(200, 195, 150, 30);
    dcDate.setBounds(200, 235, 150, 30);
    tfUser.setBounds(200, 275, 150, 30);
    lguide.setBounds(180, 315, 150, 30);

    btnRegis.setBounds(240, 355, 90, 30);
    btnReset.setBounds(110, 355, 90, 30);

// SETT MOUSE POINTER
    lNo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lName.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lPin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lEmail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lTelp.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lDate.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lUser.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnRegis.addActionListener((ActionEvent arg0) -> {
      Nasabah n = new Nasabah();
      ControlNasabah cn = new ControlNasabah();
      String date = null;
      int pin = 0;
      
      String name = tfName.getText();
      String user = tfUser.getText();
      String email = tfEmail.getText();
      String telp = tfTelp.getText();
      
      try { // cek PIN harus angka
        pin = Integer.valueOf(String.valueOf(pfPin.getPassword()));
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "PIN must be Numeric", "Error Message", JOptionPane.INFORMATION_MESSAGE);
      }
      
      // cek jika kolom ada yang kosong
      if (user.equals("") || name.equals("") || email.equals("") || telp.equals("") || dcDate.getDate() == null) {
        JOptionPane.showMessageDialog(null, "All Form Must Filled");
      } else {
        try { // untuk memastikan input apda acc number harus berupa angka
          int no = Integer.valueOf(tfNo.getText());
          
          // cek jika ada kolom yang belum di isi
          SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd"); // format tahun-bulan-hari
          date = dateformat.format(dcDate.getDate());
          System.out.println("Cek Date " + date);
          
          if (!cn.checkAccNumber(no)) { // jika tidak ada pin yang sama, maka akan di masukkan kedalam database
            n.setNasabah(no, name, user, email, telp);
            n.setPin(pin);
            n.setSaldo(100000); // set saldo awal 100000
            n.setDate(date);
            
            if (cn.register(n)) { // register berhasil
              window.dispose();
              new Login();
              JOptionPane.showMessageDialog(null, "New User Add");
            }
            
          } else {
            JOptionPane.showMessageDialog(null, "ATM has been Registered as M-Banking");
            tfNo.requestFocus(); // akan fokus ke acc number
          }
        } catch (NumberFormatException e) {
          JOptionPane.showMessageDialog(null, "Acccount Number must be Numeric", "Error Message", JOptionPane.INFORMATION_MESSAGE);
        }
      }
    });

    btnReset.addActionListener((ActionEvent arg0) -> {
      pfPin.setText("");
      tfUser.setText("");
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
    lNo.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        tfNo.requestFocusInWindow();
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
