/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui;

import cgpanalyser.gui.draw.DrawChromosomesAll;
import cgpanalyser.Chromosome;
import cgpanalyser.Evolution;
import cgpanalyser.functions.AA;
import cgpanalyser.GraphDataSampler;
import cgpanalyser.gui.draw.DrawVariables;
import cgpanalyser.truthtable.GateFuctionsAll;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author PC
 */
public class MainPanel extends JPanel {

   private MainWindow mainWindow;
   private Evolution evo;
   private GraphDataSampler dataSampler;
   private DrawVariables dVars;
   private GateFuctionsAll gateFuncsAll;
   private DrawChromosomesAll dChsAll;
   private int dChsToDrawCount;
   private boolean isCrosshairEventPending; //determines wheter there is crosshair event to serve (by repainting MainPanel with chromosomes)
   private int genNumberStart; //generation from which to start drawing chromosomes
   private int[] selectedChromosomesGens;

   /**
    * Creates new form PanelMain
    */
   public MainPanel() {
      this.dChsToDrawCount = 0;
      this.evo = null;
      this.isCrosshairEventPending = false;
      this.selectedChromosomesGens = new int[]{1, 1};
      initComponents();
   }

   public MainPanel(DrawVariables dVars, GateFuctionsAll gateFuncsAll, MainWindow mainWindow) {
      this();
      this.dVars = dVars;
      this.gateFuncsAll = gateFuncsAll;
      this.mainWindow = mainWindow;
   }

   /**
    * called from XYChartPanel chartProgress()
    *
    * @param genNumberStart generation from which to start drawing chromosomes
    */
   public void drawChromosomesOnCrosshairEvent(int genNumberStart) {
      this.genNumberStart = genNumberStart;
      this.isCrosshairEventPending = true;
      repaint();
   }

   public void zoomChroms(double zoom) {
      dVars.setZoom(zoom, true);
      isCrosshairEventPending = true;
      repaint();
   }

   private void constructDrawChromosomes() {
      if (evo == null)
         return;
      if (dataSampler == null)
         return;

      int currentDChsToDrawCount = computeDrawChromsomesToDrawCount();

      if (!isCrosshairEventPending) { //repaint by MainPanel
         if (dChsToDrawCount == currentDChsToDrawCount) //dont change drawChromosomes when count is same
            return;
      } else
         isCrosshairEventPending = false;

      dChsToDrawCount = currentDChsToDrawCount;

      List<Chromosome> chromosomes = new ArrayList<>();
      for (int i = 0; i < dataSampler.getLastToDisplay().size(); i++) {
         if (dataSampler.getLastToDisplay().get(i).getGenNumber() == genNumberStart) {
            for (int j = 0; j < dChsToDrawCount; j++) {
               if ((i + j) < dataSampler.getLastToDisplay().size())
                  chromosomes.add(dataSampler.getLastToDisplay().get(i + j).getBestChromosome());
               else
                  break;
            }
            break;
         }
      }

      dChsAll = new DrawChromosomesAll(dVars, gateFuncsAll, chromosomes, evo);
   }

   private int computeDrawChromsomesToDrawCount() {
      int count = this.getWidth() / DrawChromosomesAll.computeWithOfChrom(evo, dVars, true);
      count++; //count--; for debugging only
      //System.out.println("width: " + this.getWidth() + ", chWidth: " + DrawChromosomesAll.computeWithOfChrom(evo, dVars) + ", count: " + count);
      return (count > 1) ? count : 1;
   }

   @Override
   public void paint(Graphics g) { //called from this.drawChromosomes(...), this.mouseClicked(...)
      super.paint(g);

      constructDrawChromosomes();

      Graphics2D g2 = (Graphics2D) g;
      AA.setAA(g2, true);
      AA.setAAText(g2, true);

      if (getdChsAll() != null)
         getdChsAll().paintMain(g2, selectedChromosomesGens);
   }

   /**
    * This method is called from within the constructor to initialize the form. WARNING: Do NOT modify this code. The content of
    * this method is always regenerated by the Form Editor.
    */
   @SuppressWarnings("unchecked")
   // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
   private void initComponents() {

      setBackground(new java.awt.Color(255, 255, 255));
      setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
      addMouseWheelListener(new java.awt.event.MouseWheelListener() {
         public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
            MainPanel.this.mouseWheelMoved(evt);
         }
      });
      addMouseListener(new java.awt.event.MouseAdapter() {
         public void mouseClicked(java.awt.event.MouseEvent evt) {
            MainPanel.this.mouseClicked(evt);
         }
      });

      javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
      this.setLayout(layout);
      layout.setHorizontalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 1089, Short.MAX_VALUE)
      );
      layout.setVerticalGroup(
         layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
         .addGap(0, 245, Short.MAX_VALUE)
      );
   }// </editor-fold>//GEN-END:initComponents

   private void mouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClicked
      if (evo == null)
         return;

      int clickedChromNumber = (evt.getX() / (DrawChromosomesAll.computeWithOfChrom(evo, dVars, true) + dVars.getGapChromosomeHorizontal()));

      for (int i = 0; i < dataSampler.getLastToDisplay().size(); i++) {
         if (dataSampler.getLastToDisplay().get(i).getGenNumber() == genNumberStart)
            if ((i + clickedChromNumber) < dataSampler.getLastToDisplay().size()) {
               if (SwingUtilities.isLeftMouseButton(evt))
                  selectedChromosomesGens[0] = dataSampler.getLastToDisplay().get(i + clickedChromNumber).getGenNumber();
               else if (SwingUtilities.isRightMouseButton(evt))
                  selectedChromosomesGens[1] = dataSampler.getLastToDisplay().get(i + clickedChromNumber).getGenNumber();
               System.out.println("selectedChromosomesGens: [" + selectedChromosomesGens[0] + "," + selectedChromosomesGens[1] + "]");
            }
      }
      repaint();
   }//GEN-LAST:event_mouseClicked

   private void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_mouseWheelMoved
      mainWindow.addZoomMainValue(evt.getWheelRotation());
   }//GEN-LAST:event_mouseWheelMoved


   // Variables declaration - do not modify//GEN-BEGIN:variables
   // End of variables declaration//GEN-END:variables
   /**
    * @param evo the evo to set
    */
   public void setEvo(Evolution evo) {
      this.evo = evo;
   }

   /**
    * @param dataSampler the dataSampler to set
    */
   public void setDataSampler(GraphDataSampler dataSampler) {
      this.dataSampler = dataSampler;
   }

   /**
    * @return the dChsAll
    */
   public DrawChromosomesAll getdChsAll() {
      return dChsAll;
   }

   /**
    * @return the selectedChromosomesGens
    */
   public int[] getSelectedChromosomesGens() {
      return selectedChromosomesGens;
   }
}
