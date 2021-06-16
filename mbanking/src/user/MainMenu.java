/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;
import mbanking.MyConnection;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class MainMenu extends JFrame {

  MyConnection myConnection = new MyConnection();

  String user;
  String pin;

  //DEKLARASI KOMPONEN
  JFrame window = new JFrame("Main Menu");
  JLabel lGuide = new JLabel();

  JButton btnChangePin = new JButton("Change Pin");
  JButton btnTransaction = new JButton("Transaction (not yet)");
  JButton btnHistory = new JButton("History Transaction (not yet)");

  public MainMenu(String id1, String id2) {

    lGuide.setText("Welcome " + id2);
    lGuide.setVerticalTextPosition(0);
    window.setLayout(null);
    window.setSize(380, 300);
    //  window.setDefaultCloseOperation(3);
    window.setVisible(true);
    window.setLocationRelativeTo(null); // center
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

//ADD COMPONENT
    window.add(lGuide);
    window.add(btnChangePin);
    window.add(btnHistory);
    window.add(btnTransaction);

// SETT BOUNDS
// sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    lGuide.setBounds(0, 35, 380, 30);
    btnChangePin.setBounds(125, 75, 130, 30);
    btnHistory.setBounds(115, 115, 150, 30);
    btnTransaction.setBounds(125, 155, 130, 30);

// ACTION LISTENER
    btnChangePin.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent arg0) {
        window.dispose();
        new ChangePin(id1);
      }
    });

    window.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent e) {
        System.out.println("Closed");
      }
    });
  }
}
