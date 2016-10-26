/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.draw;

import cgpanalyser.chromelements.Output;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class DrawOutput {

   private final int d = 6; //diameter of circle

   private final Output out;
   private final DrawChromosome dCh;
   private final int index;
   private final DrawVariables dVars;
   private final boolean isMain;

   //history (how many generations is it unchanged)
   private int history;

   private Point center; //center ouf output

   public DrawOutput(Output out, DrawChromosome dCh, Point startOfOutputs, int index, DrawVariables dVars, boolean isMain) {
      this.out = out;
      this.dCh = dCh;
      this.index = index;
      this.dVars = dVars;
      this.isMain = isMain;
      setHistory(0);
      calculateAndSetCenter(startOfOutputs);
   }

   public final void calculateAndSetCenter(Point startOfOutputs) {
      this.center = new Point(startOfOutputs.x, startOfOutputs.y + (index * dVars.getGapIOVertical(isMain)));
   }

   protected void paintMain(Graphics2D g) {
      drawCircle(g, getCenter().x, getCenter().y, d);
      g.drawString(Integer.toString(out.getNumber()), center.x, center.y - 4);

      //wire going into this output
      Point startPoint = dCh.getPointOfElementOutput(getOut().getNumber());
      g.drawLine(startPoint.x, startPoint.y, getCenter().x, getCenter().y);
   }

   protected void paintComparison(Graphics2D g, int outputIndex, DrawChromosome dCh2, boolean isTtSelecting, boolean drawHistory, int maxHistory) {
      if (!isTtSelecting)
         if (drawHistory)
            g.setColor(dVars.getHistoryColor(history, maxHistory));
         else if (!isSameOutput(dCh2, outputIndex)) {
            g.setColor(Color.GREEN);
            g.setStroke(new BasicStroke(dVars.getWireBoldStrokeWidth()));
         } else {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(dVars.getWireNormalStrokeWidth()));
         }

      paintMain(g);
      g.drawString(Integer.toString(out.getNumber()), center.x, center.y - 4);
      g.setColor(Color.BLACK);
      g.setStroke(new BasicStroke(dVars.getWireNormalStrokeWidth()));
   }

   private boolean isSameOutput(DrawChromosome dCh2, int outputIndex) {
      return out.getNumber() == dCh2.getListOfDOuts().get(outputIndex).getOut().getNumber();
   }

   private void drawCircle(Graphics2D g, int x, int y, int d) {
      x = x - (d / 2);
      y = y - (d / 2);
      g.fillOval(x, y, d, d);
   }

   /**
    * @return the center
    */
   public Point getCenter() {
      return center;
   }

   /**
    * @return the out
    */
   public Output getOut() {
      return out;
   }

   /**
    * @return the history
    */
   public int getHistory() {
      return history;
   }

   /**
    * @param history the history to set
    */
   public final void setHistory(int history) {
      this.history = history;
   }

}
