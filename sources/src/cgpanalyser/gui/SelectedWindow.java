/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui;

import cgpanalyser.Chromosome;
import cgpanalyser.Evolution;
import cgpanalyser.GraphDataSampler;
import cgpanalyser.gui.draw.DrawChromosome;
import cgpanalyser.gui.draw.DrawChromosomesAll;
import cgpanalyser.gui.draw.DrawVariables;
import cgpanalyser.truthtable.DialogTruthTable;
import cgpanalyser.truthtable.DialogTruthTableDouble;
import cgpanalyser.truthtable.GateFuctionsAll;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

/**
 *
 * @author @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class SelectedWindow extends javax.swing.JFrame {

   private DrawVariables dVars;
   private GateFuctionsAll gateFuncsAll;
   private final Evolution evo;
   private final GraphDataSampler dataSampler;

   private DrawChromosome[] dChsSelected;
   private final Point[] start;
   private final int paletteHeight = 70;
   private String[] expression; //expression of selected parts of chromosomes

   /**
    * Creates new form WindowSelected, for Gui builder only
    */
   public SelectedWindow() {
      initComponents();
      this.evo = null;
      this.dataSampler = null;
      this.start = null;
   }

   /**
    * Creates Window woth selected chromosomes
    *
    * @param dVars
    * @param gateFuncsAll
    * @param evo
    * @param dataSampler
    * @param ch1Gen generation of chromosome 1
    * @param ch2Gen generation of chromosome 2
    */
   public SelectedWindow(DrawVariables dVars, GateFuctionsAll gateFuncsAll, Evolution evo, GraphDataSampler dataSampler, int ch1Gen, int ch2Gen) {
      this.dVars = dVars;
      this.gateFuncsAll = gateFuncsAll;
      this.evo = evo;
      this.dataSampler = dataSampler;

      this.expression = new String[]{"", ""};

      this.start = new Point[2];
      this.start[0] = new Point(dVars.getDrawingStart());
      DrawChromosome dCh1 = new DrawChromosome(dVars, gateFuncsAll, getChromosome(ch1Gen), start[0], evo, false);
      this.start[1] = new Point(getMaxXOfChromosome(dCh1) + dVars.getGapChromosomeHorizontal(), this.start[0].y);
      DrawChromosome dCh2 = new DrawChromosome(dVars, gateFuncsAll, getChromosome(ch2Gen), start[1], evo, false);
      dChsSelected = new DrawChromosome[]{dCh1, dCh2};

      initComponents();

      setTitle("Chromosome comparison | Fitness difference: " + Math.abs(dCh1.getCh().getFitness() - dCh2.getCh().getFitness()));
      setPanelSelectedSize(evo, dVars);
      this.pack();
      this.setLocationRelativeTo(null);
      this.setVisible(true);
   }

   protected void updateDrawChromosomeCoordinates() {
      dChsSelected[0].updateDrawChromosomeCoordinates(start[0]);
      this.start[1] = new Point(getMaxXOfChromosome(dChsSelected[0]) + dVars.getGapChromosomeHorizontal(), this.start[0].y);
      dChsSelected[1].updateDrawChromosomeCoordinates(start[1]);
      setPanelSelectedSize(evo, dVars);
   }

   private void setPanelSelectedSize(Evolution evo1, DrawVariables dVars1) {
      int widthPom = DrawChromosomesAll.computeWithOfChrom(evo1, dVars1, false) * 2 + dVars1.getGapChromosomeHorizontal() + 30;
      int heightPom = DrawChromosomesAll.computeHeightOfChrom(evo, dVars, false) + paletteHeight;
      panelSelected.setPreferredSize(new Dimension(widthPom, heightPom));
      panelSelected.revalidate();
   }

   /**
    * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
    * this method is always regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      jPanel1 = new javax.swing.JPanel();
      btnPrevSampler1 = new javax.swing.JButton();
      btnNextSampler1 = new javax.swing.JButton();
      btnPrevSampler2 = new javax.swing.JButton();
      btnNextSampler2 = new javax.swing.JButton();
      btnPrevAll1 = new javax.swing.JButton();
      btnNextAll1 = new javax.swing.JButton();
      btnPrevAll2 = new javax.swing.JButton();
      btnNextAll2 = new javax.swing.JButton();
      jLabel1 = new javax.swing.JLabel();
      jPanel2 = new javax.swing.JPanel();
      checkBoxGreyUnused = new javax.swing.JCheckBox();
      checkBoxHistory1 = new javax.swing.JCheckBox();
      checkBoxHistory2 = new javax.swing.JCheckBox();
      checkBoxTTSel = new javax.swing.JCheckBox();
      btnTT = new javax.swing.JButton();
      scrollPane = new javax.swing.JScrollPane();
      panelSelected = new cgpanalyser.gui.SelectedPanel(this, dChsSelected, dVars, this, evo);

      setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

      btnPrevSampler1.setText("<<");
      btnPrevSampler1.setToolTipText("Back in sampled data (graph) of first chromosome.");
      btnPrevSampler1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnPrevSampler1ActionPerformed(evt);
         }
      });

      btnNextSampler1.setText(">>");
      btnNextSampler1.setToolTipText("Forward in sampled data (graph) of first chromosome.");
      btnNextSampler1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnNextSampler1ActionPerformed(evt);
         }
      });

      btnPrevSampler2.setText("<<");
      btnPrevSampler2.setToolTipText("Back in sampled data (graph) of second chromosome.");
      btnPrevSampler2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnPrevSampler2ActionPerformed(evt);
         }
      });

      btnNextSampler2.setText(">>");
      btnNextSampler2.setToolTipText("Forward in sampled data (graph) of second chromosome.");
      btnNextSampler2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnNextSampler2ActionPerformed(evt);
         }
      });

      btnPrevAll1.setText("<");
      btnPrevAll1.setToolTipText("Back in all generations of first chromosome.");
      btnPrevAll1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnPrevAll1ActionPerformed(evt);
         }
      });

      btnNextAll1.setText(">");
      btnNextAll1.setToolTipText("Forward in all generations of first chromosome.");
      btnNextAll1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnNextAll1ActionPerformed(evt);
         }
      });

      btnPrevAll2.setText("<");
      btnPrevAll2.setToolTipText("Back in all generations of second chromosome.");
      btnPrevAll2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnPrevAll2ActionPerformed(evt);
         }
      });

      btnNextAll2.setText(">");
      btnNextAll2.setToolTipText("Forward in all generations of second chromosome.");
      btnNextAll2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnNextAll2ActionPerformed(evt);
         }
      });

      jLabel1.setText("--");

      javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
      jPanel1.setLayout(jPanel1Layout);
      jPanel1Layout.setHorizontalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(btnPrevSampler1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnNextSampler1)
            .addGap(6, 6, 6)
            .addComponent(btnPrevAll1)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnNextAll1)
            .addGap(18, 18, 18)
            .addComponent(jLabel1)
            .addGap(18, 18, 18)
            .addComponent(btnPrevSampler2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnNextSampler2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnPrevAll2)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(btnNextAll2)
            .addGap(0, 0, Short.MAX_VALUE))
      );
      jPanel1Layout.setVerticalGroup(
         jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel1Layout.createSequentialGroup()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(btnPrevSampler1)
               .addComponent(btnNextSampler1)
               .addComponent(btnPrevSampler2)
               .addComponent(btnNextSampler2)
               .addComponent(btnPrevAll1)
               .addComponent(btnNextAll1)
               .addComponent(btnPrevAll2)
               .addComponent(btnNextAll2)
               .addComponent(jLabel1))
            .addGap(6, 6, 6))
      );

      checkBoxGreyUnused.setSelected(true);
      checkBoxGreyUnused.setText("Grey Unused");
      checkBoxGreyUnused.setToolTipText("Gery unused elements.");
      checkBoxGreyUnused.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            checkBoxGreyUnusedActionPerformed(evt);
         }
      });

      checkBoxHistory1.setText("Show history 1");
      checkBoxHistory1.setToolTipText("Show history of chromosome 1.");
      checkBoxHistory1.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            checkBoxHistory1ActionPerformed(evt);
         }
      });

      checkBoxHistory2.setText("Show history 2");
      checkBoxHistory2.setToolTipText("Show history of chromosome 2.");
      checkBoxHistory2.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            checkBoxHistory2ActionPerformed(evt);
         }
      });

      checkBoxTTSel.setText("Gate TT selection");
      checkBoxTTSel.setToolTipText("Enable Selection of gates for truth table.");
      checkBoxTTSel.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            checkBoxTTSelActionPerformed(evt);
         }
      });

      btnTT.setText("Show TT");
      btnTT.setToolTipText("Show truth table of selected sub-chromosome(s).");
      btnTT.setEnabled(false);
      btnTT.addActionListener(new java.awt.event.ActionListener() {
         public void actionPerformed(java.awt.event.ActionEvent evt) {
            btnTTActionPerformed(evt);
         }
      });

      javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
      jPanel2.setLayout(jPanel2Layout);
      jPanel2Layout.setHorizontalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(checkBoxGreyUnused)
            .addGap(12, 12, 12)
            .addComponent(checkBoxHistory1)
            .addGap(12, 12, 12)
            .addComponent(checkBoxHistory2)
            .addGap(12, 12, 12)
            .addComponent(checkBoxTTSel)
            .addGap(12, 12, 12)
            .addComponent(btnTT)
            .addGap(0, 0, Short.MAX_VALUE))
      );
      jPanel2Layout.setVerticalGroup(
         jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
               .addComponent(checkBoxGreyUnused)
               .addComponent(checkBoxHistory1)
               .addComponent(checkBoxHistory2)
               .addComponent(checkBoxTTSel)
               .addComponent(btnTT))
            .addGap(6, 6, 6))
      );

      javax.swing.GroupLayout panelSelectedLayout = new javax.swing.GroupLayout(panelSelected);
      panelSelected.setLayout(panelSelectedLayout);
      panelSelectedLayout.setHorizontalGroup(
         panelSelectedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 1180, Short.MAX_VALUE)
      );
      panelSelectedLayout.setVerticalGroup(
         panelSelectedLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 408, Short.MAX_VALUE)
      );

      scrollPane.setViewportView(panelSelected);

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
      getContentPane().setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
               .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
               .addComponent(scrollPane))
            .addContainerGap())
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(scrollPane)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(6, 6, 6))
      );

      pack();
   }// </editor-fold>//GEN-END:initComponents

   private void btnPrevSampler1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevSampler1ActionPerformed
      setPrevSampler(0);
   }//GEN-LAST:event_btnPrevSampler1ActionPerformed

   private void btnNextSampler1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextSampler1ActionPerformed
      setNextSampler(0);
   }//GEN-LAST:event_btnNextSampler1ActionPerformed

   private void btnPrevSampler2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevSampler2ActionPerformed
      setPrevSampler(1);
   }//GEN-LAST:event_btnPrevSampler2ActionPerformed

   private void btnNextSampler2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextSampler2ActionPerformed
      setNextSampler(1);
   }//GEN-LAST:event_btnNextSampler2ActionPerformed

   private void btnPrevAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevAll1ActionPerformed
      setPrevAll(0);
   }//GEN-LAST:event_btnPrevAll1ActionPerformed

   private void btnNextAll1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextAll1ActionPerformed
      setNextAll(0);
   }//GEN-LAST:event_btnNextAll1ActionPerformed

   private void btnPrevAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevAll2ActionPerformed
      setPrevAll(1);
   }//GEN-LAST:event_btnPrevAll2ActionPerformed

   private void btnNextAll2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextAll2ActionPerformed
      setNextAll(1);
   }//GEN-LAST:event_btnNextAll2ActionPerformed

   private void setPrevSampler(int cmpChromIndex) {
      for (int newChIndex = (dataSampler.getLastToDisplay().size() - 1); newChIndex >= 0; newChIndex--) {
         if (dataSampler.getLastToDisplay().get(newChIndex).getGenNumber() < dChsSelected[cmpChromIndex].getCh().getGeneration().getGenNumber()) {
            setNewSelectedChromosome(cmpChromIndex, dataSampler.getLastToDisplay().get(newChIndex).getGenNumber());
            break;
         }
      }
   }

   private void setNextSampler(int cmpChromIndex) {
      for (int newChIndex = 0; newChIndex < dataSampler.getLastToDisplay().size(); newChIndex++) {
         if (dataSampler.getLastToDisplay().get(newChIndex).getGenNumber() > dChsSelected[cmpChromIndex].getCh().getGeneration().getGenNumber()) {
            setNewSelectedChromosome(cmpChromIndex, dataSampler.getLastToDisplay().get(newChIndex).getGenNumber());
            break;
         }
      }
   }

   private void setNextAll(int cmpChromIndex) {
      //BEVARE: generations are numbered from 1
      int currentGen = dChsSelected[cmpChromIndex].getCh().getGeneration().getGenNumber();
      if (currentGen < evo.getListOfGenerations().size())
         setNewSelectedChromosome(cmpChromIndex, currentGen + 1);
   }

   private void setPrevAll(int cmpChromIndex) {
      //BEVARE: generations are numbered from 1
      int currentGen = dChsSelected[cmpChromIndex].getCh().getGeneration().getGenNumber();
      if (currentGen > 1)
         setNewSelectedChromosome(cmpChromIndex, currentGen - 1);
   }

   private void setNewSelectedChromosome(int cmpChromIndex, int generation) {
      dChsSelected[cmpChromIndex] = new DrawChromosome(dVars, gateFuncsAll, getChromosome(generation), start[cmpChromIndex], evo, false);
      checkBoxHistory1ActionPerformed(null);
      checkBoxHistory2ActionPerformed(null);
   }

   private Chromosome getChromosome(int generation) {
      return evo.getGeneration(generation - 1).getBestChromosome();
   }

   private static int getMaxXOfChromosome(DrawChromosome dCh) {
      return dCh.getListOfDOuts().get(dCh.getListOfDOuts().size() - 1).getCenter().x;
   }

   private void checkBoxGreyUnusedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxGreyUnusedActionPerformed
      dChsSelected[0].setIsDrawUsedOnlySelected(checkBoxGreyUnused.isSelected());
      dChsSelected[1].setIsDrawUsedOnlySelected(checkBoxGreyUnused.isSelected());
      repaint();
   }//GEN-LAST:event_checkBoxGreyUnusedActionPerformed

   private void checkBoxHistory1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxHistory1ActionPerformed
      if (checkBoxHistory1.isSelected())
         dChsSelected[0].calculateHistory();
      else
         dChsSelected[0].setDrawHistory(false);
      repaint();
   }//GEN-LAST:event_checkBoxHistory1ActionPerformed

   private void checkBoxHistory2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxHistory2ActionPerformed
      if (checkBoxHistory2.isSelected())
         dChsSelected[1].calculateHistory();
      else
         dChsSelected[1].setDrawHistory(false);
      repaint();
   }//GEN-LAST:event_checkBoxHistory2ActionPerformed

   private void checkBoxTTSelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBoxTTSelActionPerformed
      dChsSelected[0].setIsTtSelecting(checkBoxTTSel.isSelected());
      dChsSelected[1].setIsTtSelecting(checkBoxTTSel.isSelected());
      if (checkBoxTTSel.isSelected()) {
         checkBoxHistory1.setSelected(false);
         checkBoxHistory2.setSelected(false);
         checkBoxHistory1ActionPerformed(evt);
         checkBoxHistory2ActionPerformed(evt);
         checkBoxHistory1.setEnabled(false);
         checkBoxHistory2.setEnabled(false);
         btnTT.setEnabled(true);
      } else {
         panelSelected.setGatesUnSelected();
         checkBoxHistory1.setEnabled(true);
         checkBoxHistory2.setEnabled(true);
         expression[0] = "";
         expression[1] = "";
         btnTT.setEnabled(false);
         repaint();
      }
   }//GEN-LAST:event_checkBoxTTSelActionPerformed

   private void btnTTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTTActionPerformed
      if (!expression[0].isEmpty() && !expression[1].isEmpty())
         new DialogTruthTableDouble(null, false, expression, dChsSelected[0].getCh().getGeneration().getGenNumber(), dChsSelected[1].getCh().getGeneration().getGenNumber());
      else if (!expression[0].isEmpty())
         new DialogTruthTable(null, false, expression[0], dChsSelected[0].getCh().getGeneration().getGenNumber());
      else if (!expression[1].isEmpty())
         new DialogTruthTable(null, false, expression[1], dChsSelected[1].getCh().getGeneration().getGenNumber());
   }//GEN-LAST:event_btnTTActionPerformed

   void setExpression(int dChToCheckIndex, String expression) {
      this.expression[dChToCheckIndex] = expression;
   }

   protected boolean isCheckBoxTTSelected() {
      return checkBoxTTSel.isSelected();
   }

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
         java.util.logging.Logger.getLogger(SelectedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (InstantiationException ex) {
         java.util.logging.Logger.getLogger(SelectedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (IllegalAccessException ex) {
         java.util.logging.Logger.getLogger(SelectedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      } catch (javax.swing.UnsupportedLookAndFeelException ex) {
         java.util.logging.Logger.getLogger(SelectedWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
      }
      //</editor-fold>
      //</editor-fold>

      /* Create and display the form */
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            new SelectedWindow().setVisible(true);
         }
      });
   }

   // Variables declaration - do not modify//GEN-BEGIN:variables
   private javax.swing.JButton btnNextAll1;
   private javax.swing.JButton btnNextAll2;
   private javax.swing.JButton btnNextSampler1;
   private javax.swing.JButton btnNextSampler2;
   private javax.swing.JButton btnPrevAll1;
   private javax.swing.JButton btnPrevAll2;
   private javax.swing.JButton btnPrevSampler1;
   private javax.swing.JButton btnPrevSampler2;
   private javax.swing.JButton btnTT;
   private javax.swing.JCheckBox checkBoxGreyUnused;
   private javax.swing.JCheckBox checkBoxHistory1;
   private javax.swing.JCheckBox checkBoxHistory2;
   private javax.swing.JCheckBox checkBoxTTSel;
   private javax.swing.JLabel jLabel1;
   private javax.swing.JPanel jPanel1;
   private javax.swing.JPanel jPanel2;
   private cgpanalyser.gui.SelectedPanel panelSelected;
   private javax.swing.JScrollPane scrollPane;
   // End of variables declaration//GEN-END:variables

}
