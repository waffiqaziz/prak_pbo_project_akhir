/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import control.ControlNasabah;
import view.MainMenu;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import user.Nasabah;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class ChangePIN {

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
  JButton btnBack = new JButton("Back");
  
  ControlNasabah cn = new ControlNasabah();

  public ChangePIN(Nasabah n) {
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
    window.add(btnBack);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lOldPin.setBounds(80, 35, 45, 30);
    lNewPin.setBounds(80, 75, 50, 30);
    lRePin.setBounds(80, 115, 70, 30);
    lguide.setBounds(140, 155, 120, 30);

    pfOldPin.setBounds(170, 35, 120, 30);
    pfNewPin.setBounds(170, 75, 120, 30);
    pfRePin.setBounds(170, 115, 120, 30);

    btnChange.setBounds(230, 195, 90, 30);
    btnReset.setBounds(140, 195, 90, 30);
    btnBack.setBounds(50, 195, 90, 30);

    // sett mouse pointer
    lOldPin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lNewPin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lRePin.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lguide.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnChange.addActionListener((ActionEvent arg0) -> {
      int newPin = 0, oldPin = 0, rePin = 1;
      boolean cek = true;
      
      try{
        rePin = Integer.valueOf(String.valueOf(pfRePin.getPassword()));
        newPin = Integer.valueOf(String.valueOf(pfNewPin.getPassword()));
        oldPin = Integer.valueOf(String.valueOf(pfOldPin.getPassword()));
        
        if(rePin != newPin) {
          JOptionPane.showMessageDialog(null, "Retype and New PIN not Same");
          pfRePin.requestFocusInWindow();
          cek = false;
        }

        if (oldPin == n.getPin() && cek) { // cek pin lama dengan pin input user sama/tidak
          if (cn.changePin(newPin, n.getUserID())) { // update pin
            n.setPin(newPin);
            JOptionPane.showMessageDialog(null, "Change PIN Success");
            window.dispose();
            new MainMenu(n);
          }
        } else {
          JOptionPane.showMessageDialog(null, "PIN not Same" + "\nChange PIN Failed", "Error Message", JOptionPane.INFORMATION_MESSAGE);
          pfOldPin.requestFocusInWindow();
        }

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null, "Fill in all the columns" + "\nPIN Must be Numeric", "Error Message", JOptionPane.INFORMATION_MESSAGE);
        Logger.getLogger(ChangePIN.class.getName()).log(Level.SEVERE, null, ex);
      }
    });

    btnReset.addActionListener((ActionEvent arg0) -> {
      pfNewPin.setText("");
      pfOldPin.setText("");
      pfRePin.setText("");
    });
    btnBack.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      new MainMenu(n);
    });

// MOUSE LISTENER
    lguide.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        System.out.println("CS Clicked");
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
}
