package com.tadkp;

import java.text.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WIF {
    private JPanel panel;
    private JPanel panel1;
    private JTextField inputMassa;
    private JTextField inputVolume;
    private JComboBox satuanMassa;
    private JComboBox satuanVolume;
    private JButton tombolHitung;
    private JLabel labelV;
    private JLabel labelM;
    private JLabel judul;
    private JComboBox jenisZatCair;
    private JLabel labelC;
    private JLabel hasil;
    private JLabel tip;
    private JPanel panelTip;

// Menjalankan Gui
    public static void main(String[] args) {
        JFrame frame = new JFrame("Program");
        frame.setContentPane(new WIF().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public WIF() {
        labelAwal();
        // Array untuk satuan ukur
        String satuanM[] = {"kg", "gr", "mg", "Ton (Metrik)"};
        String satuanV[] = {"m³", "cm³", "L", "mL"};
        String zatCair[] = {"Air (1000kg/m³)","Air Raksa (13550kg/m³)","Alkohol (800kg/m³)","Bensin (730kg/m³)",
                "Minyak Sawit (910kg/m³)", "Minyak Zaitun (900kg/m³)"};
        // Memasukkan data array ke combo box
        tambahSatuanM(satuanM); tambahSatuanV(satuanV); tambahZatCair(zatCair);

        // Array bilangan konversi (Satuan utama: kg dan m³)
        float konversiM[] = {1, 0.001f, 0.000001f, 1000};
        float konversiV[] = {1, 0.000001f, 0.001f, 1};
        // Array massa jenis cairan
        float mjc[] = {1000, 13550, 800, 730, 910, 900};

        tombolHitung.addActionListener (new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               // Mengambil nilai dari input user
               String massaSTR = inputMassa.getText(); String volumeSTR = inputVolume.getText();
               massaSTR = massaSTR.replaceAll(",",".");
               volumeSTR = volumeSTR.replaceAll(",",".");

               // Objek untuk pembulatan angka hasil
               DecimalFormat df = new DecimalFormat("#.##");

               try {
                   float massa = Float.parseFloat(massaSTR); float volume = Float.parseFloat(volumeSTR);
                   massa = massa*konversiM[satuanMassa.getSelectedIndex()];
                   volume = volume*konversiV[satuanVolume.getSelectedIndex()];

                   float mj = massa/volume;
                   float mjcair = mjc[jenisZatCair.getSelectedIndex()];

                   if (mj==mjcair) {
                       hasil.setText("<html><center>[Massa Jenis Benda] " + df.format(mj) + "kg/m³ = " + mjcair + "kg/m³ [Massa Jenis Cairan]<br>" +
                               "<br>" +
                               "Berdasarkan Perhitungan di dapat bahwa Massa jenis benda sama dengan massa jenis cairan.<br>" +
                               "<br>" +
                               "<font color=ff0000>Benda akan menetap pada posisi ditempatkannya benda pada cairan.<br>" +
                               "Benda hanya akan bergerak jika diberi usaha.</color></center></html>");
                   } else if (mj<mjcair) {
                       hasil.setText("<html><center>[Massa Jenis Benda] "+ df.format(mj)+"kg/m³ &lt; "+mjcair+"kg/m [Massa Jenis Cairan]<br>" +
                               "<br>" +
                               "Berdasarkan Perhitungan di dapat bahwa Massa jenis benda lebih kecil massa jenis cairan.<br>" +
                               "<br>" +
                               "<font color=ff0000>Benda akan mengapung jika ditempatkan pada cairan.</color><br>" +
                               "<br></center></html>");
                   } else {
                       hasil.setText("<html><center>[Mass Jenis Benda] " + df.format(mj) + "kg/m³ &gt; " + mjcair + "kg/m³ [Massa Jenis Cairan]<br>" +
                               "<br>" +
                               "Berdasarkan Perhitungan di dapat bahwa Massa jenis benda lebih besar massa jenis cairan.<br>" +
                               "<br>" +
                               "<font color=ff0000>Benda akan tenggelam jika ditempatkan pada cairan.</color><br>" +
                               "<br></center></html>");
                   }

               } catch(NumberFormatException Error) { //Error message
                   JOptionPane.showMessageDialog(panel, "Angka yang dimasukan tidak tepat",
                           "Error!", JOptionPane.WARNING_MESSAGE);
               }
           }
        });
    }

    void tambahSatuanM(String[] satuanmass) {
        for(int i = 0; i<satuanmass.length; i++) {
            satuanMassa.addItem(satuanmass[i]);
        }
    }
    void tambahSatuanV(String[] satuanvol) {
        for(int i = 0; i<satuanvol.length; i++) {
            satuanVolume.addItem(satuanvol[i]);
        }
    }
    void tambahZatCair(String[] zatCair) {
        for(int i = 0; i<zatCair.length; i++) {
            jenisZatCair.addItem(zatCair[i]);
        }
    }
    void labelAwal() {
        judul.setText("<html><center><font size=6> Program <br>" +
                "(Kalkulator Massa Jenis)</size></center></html>");
        hasil.setText("<html><br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "<br>" +
                "</html>");
        tip.setText("<html><center>Tips: Untuk mengukur volume benda dengan akurat,<br>" +
                "Saat menginput massa dan volume harus menggunakan angka yang tepat!<br>" +
                "Agar hasil perhitungan benar.</center></html>");
    }
}
