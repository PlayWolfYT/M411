/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbzsogr.view;

import ch.bbzsogr.model.CSVReader;
import ch.bbzsogr.model.CSVWriter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.FileChooserUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PlayWolfYT
 */
public class StudentUI extends javax.swing.JFrame {

    /**
     * Creates new form StudentUI
     */
    public StudentUI() {
        initComponents();
        Object[][] data = CSVReader.getData();
        clearTable();
        fillTable(data);
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

        while (model.getRowCount() > 0) {
            model.removeRow(0);
        }
    }

    private void fillTable(Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();
        for (Object[] row : data) {
            model.addRow(row);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        studentTable = new javax.swing.JTable();
        saveButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        studentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "Benutzername", "Vorname", "Nachname", "Klasse 1", "Klasse 2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane2.setViewportView(studentTable);

        saveButton.setText("Speichern");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        loadButton.setText("Laden");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(loadButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveButton)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(loadButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        // TODO add your handling code here:
        System.out.println("Button Clicked");
        DefaultTableModel model = (DefaultTableModel) studentTable.getModel();

        Object[][] data = new Object[model.getRowCount()][model.getColumnCount()];

        for (int i = 0; i < model.getRowCount(); i++) {
            for (int j = 0; j < model.getColumnCount(); j++) {
                data[i][j] = model.getValueAt(i, j);
            }
        }

        try {
            JFileChooser chooser = new JFileChooser(new File("C:/temp"));

            for (FileFilter ff : chooser.getChoosableFileFilters()) {
                chooser.removeChoosableFileFilter(ff);
            }

            chooser.setFileFilter(new FileNameExtensionFilter("Comma-Separated Values (*.csv)", "csv"));

            chooser.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent fileChosen) {
                    if (fileChosen.getActionCommand().equalsIgnoreCase("CancelSelection")) {
                        JOptionPane.showMessageDialog(null, "Speichern abgebrochen", "Abgebrochen", JOptionPane.INFORMATION_MESSAGE, null);
                    } else if (fileChosen.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
                        File out = chooser.getSelectedFile();
                        if (out.getName().endsWith(".csv") || !out.getName().contains(".")) {
                            if (out.exists()) {
                                int override = JOptionPane.showConfirmDialog(null, "Die Datei existiert bereits. Möchten Sie sie überschreiben?", "Überschreiben?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                                if (override == 0) {
                                    try {
                                        CSVWriter.writeCSV(data, out);
                                    } catch (Exception ex) {
                                        JOptionPane.showMessageDialog(null, "Die Datei konnte nicht gespeichert werden.", "Fehler", JOptionPane.ERROR_MESSAGE, null);
                                    }
                                    JOptionPane.showMessageDialog(null, "Die Daten wurden erfolgreich gespeichert", "Gespeichert", JOptionPane.INFORMATION_MESSAGE, null);

                                }
                            }
                        } else {
                            JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine \".csv\"-Datei aus.", "Invalide Datei", JOptionPane.ERROR_MESSAGE, null);
                        }
                    }
                }
            });

            chooser.showSaveDialog(null);

        } catch (Exception ex) {
            Logger.getLogger(StudentUI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveButtonActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        // TODO add your handling code here:
        JFileChooser chooser = new JFileChooser(new File("C:/temp"));
        chooser.setFileFilter(new FileNameExtensionFilter("Comma-Separated Values (*.csv)", "csv"));
        chooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent fileChosen) {
                if (fileChosen.getActionCommand().equalsIgnoreCase("CancelSelection")) {
                    JOptionPane.showMessageDialog(null, "Laden abgebrochen", "Abgebrochen", JOptionPane.INFORMATION_MESSAGE, null);
                } else if (fileChosen.getActionCommand().equalsIgnoreCase("ApproveSelection")) {
                    File in = chooser.getSelectedFile();
                    if ((in.getName().endsWith(".csv") || !in.getName().contains(".")) && in.exists()) {
                        try {
                            CSVReader.setFile(in);
                            clearTable();
                            fillTable(CSVReader.getData());
                        } catch (Exception ex) {
                            JOptionPane.showMessageDialog(null, "Die Datei konnte nicht geladen werden.", "Fehler", JOptionPane.ERROR_MESSAGE, null);
                        }
                        JOptionPane.showMessageDialog(null, "Die Daten wurden erfolgreich geladen", "Geladen", JOptionPane.INFORMATION_MESSAGE, null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Bitte wählen Sie eine existierende \".csv\"-Datei aus.", "Invalide Datei", JOptionPane.ERROR_MESSAGE, null);
                    }
                }
            }
        });

        chooser.showOpenDialog(null);
    }//GEN-LAST:event_loadButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StudentUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StudentUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton saveButton;
    private javax.swing.JTable studentTable;
    // End of variables declaration//GEN-END:variables
}