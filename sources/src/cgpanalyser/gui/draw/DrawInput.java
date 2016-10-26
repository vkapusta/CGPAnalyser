/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.draw;

import cgpanalyser.chromelements.Input;
import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class DrawInput{

   private final int d = 6; //diameter of circle

   private final Input in;
   private final int index;
   private final DrawVariables dVars;
	private final boolean isMain;

   private Point center; //center of input

   /**
    *
    * @param in
    * @param dChStart start point of draw chromosome
    * @param index index of this input
    * @param dVars
    * @param isMain
    */
   public DrawInput(Input in, Point dChStart, int index, DrawVariables dVars, boolean isMain) {
      this.in = in;
      this.index = index;
      this.dVars = dVars;
		this.isMain = isMain;
      calculateAndSetCenter(dChStart);
   }

   public final void calculateAndSetCenter(Point start) {
      this.center =  new Point(start.x, start.y + (index * dVars.getGapIOVertical(isMain)));
   }
	
   protected void paintMain(Graphics2D g) {
      drawCircle(g, getCenter().x, getCenter().y, d);
      g.drawString(String.valueOf(getIn().getName()), center.x - 6, center.y - 4);
   }

   protected void paintComparison(Graphics2D g) {
      paintMain(g);
   }

   private void drawCircle(Graphics2D g, int x, int y, int d) {
      x = x - (d / 2);
      y = y - (d / 2);
      g.fillOval(x, y, d, d);
   }

   /**
    * @return the start
    */
   public Point getCenter() {
      return center;
   }

   /**
    * @return the logicExpression
    */
   public String getLogicExpression() {
      return String.valueOf(getIn().getName());
   }

   /**
    * @return the in
    */
   public Input getIn() {
      return in;
   }

}
