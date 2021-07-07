/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

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
import control.ControlNasabah;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import user.Nasabah;

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
  JButton btnBack = new JButton("Back");
  
  ControlNasabah control = new ControlNasabah();

  public Transfer(Nasabah n) {
    window.setLayout(null);
    window.setSize(450, 255);
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
    window.add(btnBack);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lNoRecipient.setBounds(80, 35, 150, 30);
    lAmount.setBounds(80, 75, 70, 30);

    pfNo.setBounds(230, 35, 120, 30);
    tfAmount.setBounds(230, 75, 120, 30);

    btnTransfer.setBounds(270, 155, 90, 30);
    btnReset.setBounds(170, 155, 90, 30);
    btnBack.setBounds(70, 155, 90, 30);

    // sett mouse pointer
    lNoRecipient.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    lAmount.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

// ACTION LISTENER
    btnTransfer.addActionListener((ActionEvent arg0) -> {
      int jumlah = 0;
      int noAccPenerima = 0;
      
      try {
        jumlah = Integer.valueOf(tfAmount.getText());
        noAccPenerima = Integer.valueOf(String.valueOf(pfNo.getPassword())); // pin penerima

        if (control.transfer(n, noAccPenerima, jumlah)) { // transfer
          control.writeHistory(n, noAccPenerima, jumlah);
          window.dispose();
          new MainMenu(n);
          JOptionPane.showMessageDialog(null, "Transfer Success");
        } else {
          JOptionPane.showMessageDialog(null, "Transfer Failed");
        }

      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(null,"Fill All Columns" + "\nAmount and No. Recipiens Must be Numeric", "Error Message", JOptionPane.INFORMATION_MESSAGE);
      }

    });

    btnReset.addActionListener((ActionEvent arg0) -> {
      pfNo.setText("");
      tfAmount.setText("");
    });
    btnBack.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      new MainMenu(n);
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
      @Override
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
