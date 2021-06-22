/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class UbahPin {

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

  public UbahPin(Nasabah n) {
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
    btnChange.addActionListener((ActionEvent arg0) -> {
      System.out.println("Change PIN " + n.getUserID());
      
      // Tangkap input user
      String newPin = String.valueOf(pfNewPin.getPassword());
      String oldPin = String.valueOf(pfOldPin.getPassword());
      String rePin = String.valueOf(pfRePin.getPassword());
      
      System.out.println("n " + n.getPin());
      System.out.println(oldPin);
      // 1. cek input user
      if (newPin.isEmpty() || oldPin.isEmpty() || rePin.isEmpty()) {
        pfOldPin.requestFocusInWindow();
        JOptionPane.showMessageDialog(null, "Fill All the Column");
      }
      if (!oldPin.equals(String.valueOf(n.getPin()))) { // 2. cek repin sama/tidak dengan newpin
        JOptionPane.showMessageDialog(null, "PIN not Same");
        pfRePin.requestFocusInWindow();
      } 
      if (n.changePin(newPin,n.getUserID())){
        JOptionPane.showMessageDialog(null, "Change PIN Success");
        window.dispose();
        new MenuUtama(n);
      } else {
        JOptionPane.showMessageDialog(null, "Change PIN Failed");       
      }
    });

    btnReset.addActionListener((ActionEvent arg0) -> {
      pfNewPin.setText("");
      pfOldPin.setText("");
      pfRePin.setText("");
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
}
