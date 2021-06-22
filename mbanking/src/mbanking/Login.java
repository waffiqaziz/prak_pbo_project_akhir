/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mbanking;

import user.MenuUtama;
import user.Register;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import user.Nasabah;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class Login extends JFrame {

  String user;
  String pin;

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Login");
  JLabel lUser = new JLabel("User ID");

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
    btnLogin.addActionListener((ActionEvent arg0) -> {
      Nasabah n1 = new Nasabah();
      
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
        if(n1.login(user, Integer.valueOf(pin), n1)){
          window.dispose();
          MenuUtama mm = new MenuUtama(n1);
          mm.pack();
          mm.setLocationRelativeTo(null); // center 
          JOptionPane.showMessageDialog(null, "Login Success");
        } else {
          JOptionPane.showMessageDialog(null, "Incorrect Username or Password" + " Login Failed");
          tfUser.requestFocusInWindow();
        }
      }
    });

    btnReset.addActionListener((ActionEvent arg0) -> {
      pfPass.setText("");
      tfUser.setText("");
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
      @Override
      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });

    tfUser.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          btnLogin.doClick();
        }
      }
    });
    pfPass.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          btnLogin.doClick();
        }
      }
    });
  }
}
