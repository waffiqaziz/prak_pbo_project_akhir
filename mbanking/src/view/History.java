/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import view.MainMenu;
import model.ReadData;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.table.DefaultTableModel;
import user.Nasabah;

/**
 *
 * @author Waffiq Aziz / 123190070
 */
public class History {

  ReadData rd = new ReadData();

//DEKLARASI KOMPONEN
  JFrame window = new JFrame("History Transaction");
  JTable tabel;
  DefaultTableModel tableModel;
  JScrollPane scrollPane;
  Object namaKolom[] = {"No Transaction", "Type", "Sender", "recipient", "Amount"};
  JButton btnBack = new JButton("Kembali");

  public History(Nasabah n) {
    window.setLayout(null);
    window.setSize(550, 300);
    window.setVisible(true);
    window.setLocationRelativeTo(null);
    window.setResizable(false);
    window.setDefaultCloseOperation(EXIT_ON_CLOSE); // running program berhenti jika tombol close ditekan

    window.add(btnBack);

    // sett bounds(m,n,o,p) >>> (sumbu-x,sumbu-y,panjang komponen, tinggi komponen)
    btnBack.setBounds(0, 230, 80, 30);

    if (rd.readAllData(n) == null) {
      JOptionPane.showMessageDialog(null, "Tidak Ada Data");
      tabel = new JTable(null, namaKolom); //tabel merupakan variabel untuk tabelnya dengan isi tablemodel
    } else {
      tabel = new JTable(rd.readAllData(n), namaKolom); //tabel merupakan variabel untuk tabelnya dengan isi tablemodel
    }

    scrollPane = new JScrollPane(tabel);
    window.add(scrollPane);

    scrollPane.setBounds(20, 35, 500, 170);
    scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

// action listener
    btnBack.addActionListener((ActionEvent arg0) -> {
      window.dispose();
      System.out.println("Yes");
      new MainMenu(n);
    });
  }
}
