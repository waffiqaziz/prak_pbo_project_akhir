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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import function.ControlTransfer;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
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
  
  ControlTransfer control = new ControlTransfer();

  public Transfer(Nasabah n) {
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
    btnTransfer.addActionListener((ActionEvent arg0) -> {
      String idPenerima = String.valueOf(pfNo.getPassword()); // pin penerima
      int jumlah = Integer.valueOf(tfAmount.getText());
      if (idPenerima.isEmpty() || jumlah == 0) {
        JOptionPane.showMessageDialog(null, "All Form Must Filled");
      } else {
        if(control.transfer(n,idPenerima,jumlah)){ // transfer
          control.writeHistory(n, idPenerima, jumlah);
          window.dispose();
          new MenuUtama(n);
          JOptionPane.showMessageDialog(null, "Transfer Success");
        }else{
          JOptionPane.showMessageDialog(null, "Transfer Failed");
        }
      }
    });

    btnReset.addActionListener((ActionEvent arg0) -> {
      pfNo.setText("");
      tfAmount.setText("");
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
