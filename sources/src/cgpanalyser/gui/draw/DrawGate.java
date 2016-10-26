/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.draw;

import cgpanalyser.chromelements.Gate;
import cgpanalyser.truthtable.GateFuctionsAll;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.List;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class DrawGate {

   private final Gate gate;
   private final DrawChromosome dCh;
   private final DrawVariables dVars;
   private final GateFuctionsAll gateFuncsAll;
   private final boolean isMain; //is in main window (for zoom)

   private Point upperLeft; //upper left corner
   private Point in1; //input1 point
   private Point in2; //input2 point
   private Point out; //output point
   private final int indexRow;
   private final int indexCol;

   private boolean used; //is gate used in circuit

   //history of function and inputs (how many generations are they unchanged)
   private int historyFunc;
   private int historyIn1;
   private int historyIn2;

   //logicExpression of gate (for truth table)
   private boolean selected; //for highlighting selected gates for truth table

   public DrawGate(Gate gate, DrawChromosome dCh, Point dChStart, int indexRow, int indexCol, DrawVariables dVars, boolean isMain) {
      this.gate = gate;
      this.dCh = dCh;
      this.indexRow = indexRow;
      this.indexCol = indexCol;
      this.dVars = dVars;
      this.isMain = isMain;
      this.gateFuncsAll = dCh.getGateFucsAll();
      this.used = false;
      this.selected = false;
      this.setHistories(0, 0, 0);
      calculateAndSetCoords(dChStart);
   }

   public boolean contains(Point point) {
      return (point.x >= upperLeft.x) && (point.x <= upperLeft.x + dVars.getGateWidth(isMain))
              && (point.y >= upperLeft.y) && (point.y <= upperLeft.y + dVars.getGateHeight(isMain));
   }

   public final void calculateAndSetCoords(Point dChStart) {
      this.upperLeft = new Point(dChStart.x + dVars.getGapInputGate(isMain) + dVars.getGateWireLen(isMain) + (indexCol * (dVars.getGateWidth(isMain) + dVars.getGapGateHorizontal(isMain) + 2 * dVars.getGateWireLen(isMain))),
              dChStart.y + (indexRow * (dVars.getGateHeight(isMain) + dVars.getGapGateVertical(isMain))));
      this.in1 = new Point(upperLeft.x - dVars.getGateWireLen(isMain), upperLeft.y + (dVars.getGateHeight(isMain) / 3));
      this.in2 = new Point(upperLeft.x - dVars.getGateWireLen(isMain), upperLeft.y + ((dVars.getGateHeight(isMain) / 3) * 2));
      this.out = new Point(upperLeft.x + dVars.getGateWidth(isMain) + dVars.getGateWireLen(isMain), upperLeft.y + (dVars.getGateHeight(isMain) / 2));
   }

   protected void paintMain(Graphics2D g) {
      if (dVars.isDrawColorsFF())
         g.setColor(gateFuncsAll.getColorIfAvailable(gate.getFunction()));
      if (dVars.isDrawUsedOnlyMain() && !isUsed())
         g.setColor(dVars.getUsedColorMain());
      drawGate(g);
      drawOutput(g);
      drawInput1(g);
      drawInput2(g);

      g.setColor(dVars.getNormalColor());
   }

   void paintComparison(Graphics2D g, int gateIndex, DrawChromosome dCh2, boolean isDrawUsedOnlySelected, boolean isTtSelecting, boolean drawHistory, int maxHistory) {
      //gate
      if (!isTtSelecting)
         if (drawHistory) {
            Color historyFuncColor = dVars.getHistoryColor(historyFunc, maxHistory);
            if (isDrawUsedOnlySelected && !isUsed())
               historyFuncColor = dVars.getUsedColorSelected();
            partiallyFillGate(g, historyFuncColor, maxHistory);
            g.setColor(historyFuncColor);
         } else if (!isSameFunction(dCh2, gateIndex)) { //draw differences
            g.setColor(Color.BLUE);
            g.setStroke(new BasicStroke(dVars.getWireBoldStrokeWidth()));
         }
      if (isDrawUsedOnlySelected && !isUsed())
         g.setColor(dVars.getUsedColorSelected());
      if (isSelected())
         g.setColor(Color.RED);

      drawGate(g);
      //output
      drawOutput(g);

      //input1
      if (!isTtSelecting)
         if (drawHistory)
            g.setColor(dVars.getHistoryColor(historyIn1, maxHistory));
         else if (!isSameInput(dCh2, gateIndex, 1)) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(dVars.getWireBoldStrokeWidth()));
         } else {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(dVars.getWireNormalStrokeWidth()));
         }
      if (isDrawUsedOnlySelected && !isUsed())
         g.setColor(dVars.getUsedColorSelected());
      if (isSelected())
         g.setColor(Color.RED);
      drawInput1(g);

      //input2
      if (!isTtSelecting)
         if (drawHistory)
            g.setColor(dVars.getHistoryColor(historyIn2, maxHistory));
         else if (!isSameInput(dCh2, gateIndex, 2)) {
            g.setColor(Color.RED);
            g.setStroke(new BasicStroke(dVars.getWireBoldStrokeWidth()));
         } else {
            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(dVars.getWireNormalStrokeWidth()));
         }
      if (isDrawUsedOnlySelected && !isUsed())
         g.setColor(dVars.getUsedColorSelected());
      if (isSelected())
         g.setColor(Color.RED);

      drawInput2(g);

      g.setColor(Color.BLACK);
      g.setStroke(new BasicStroke(dVars.getWireNormalStrokeWidth()));

   }

   /**
    * based on history
    */
   private void partiallyFillGate(Graphics2D g, Color currentColor, int maxHistory) {
      g.setColor(new Color(currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue(), 100));
      //partially fill gate based on history
      int fillHeight = (int) Math.ceil((double) dVars.getGateHeight(isMain) / ((double) maxHistory / (double) historyFunc));
      g.fillRect(upperLeft.x, upperLeft.y + dVars.getGateHeight(isMain) - fillHeight, dVars.getGateWidth(isMain), fillHeight);
   }

   private void drawGate(Graphics2D g) {
      g.drawRect(upperLeft.x, upperLeft.y, dVars.getGateWidth(isMain), dVars.getGateHeight(isMain));
      g.drawString(gateFuncsAll.getSymbolIfAvailable(gate.getFunction()), upperLeft.x + 3, upperLeft.y + dVars.getGateHeight(isMain) - 3);
   }

   private void drawOutput(Graphics2D g) {
      //output
      g.drawLine(upperLeft.x + dVars.getGateWidth(isMain), getOut().y, getOut().x, getOut().y);
      g.drawString(Integer.toString(gate.getOut()), upperLeft.x + dVars.getGateWidth(isMain) + 2, out.y - 2);
   }

   private void drawInput1(Graphics2D g) {
      //input1
      g.drawLine(in1.x, in1.y, upperLeft.x, in1.y);
      g.drawString(Integer.toString(gate.getIn1()), in1.x, in1.y - 2);
      //wire going into this gate's input 1
      Point startPoint = getdCh().getPointOfElementOutput(getGate().getIn1());
      g.drawLine(startPoint.x, startPoint.y, in1.x, in1.y);
   }

   private void drawInput2(Graphics2D g) {
      Point startPoint;
      //input2
      g.drawLine(in2.x, in2.y, upperLeft.x, in2.y);
      g.drawString(Integer.toString(gate.getIn2()), in2.x, upperLeft.y + dVars.getGateHeight(isMain));
      //wire going into this gate's input 2
      startPoint = getdCh().getPointOfElementOutput(getGate().getIn2());
      g.drawLine(startPoint.x, startPoint.y, in2.x, in2.y);
   }

   private boolean isSameInput(DrawChromosome dCh2, int gateIndex, int input) {
      if (input == 1)
         return gate.getIn1() == dCh2.getListOfDGates().get(gateIndex).getGate().getIn1();
      else
         return gate.getIn2() == dCh2.getListOfDGates().get(gateIndex).getGate().getIn2();
   }

   private boolean isSameFunction(DrawChromosome dCh2, int gateIndex) {
      return getGate().getFunction() == dCh2.getListOfDGates().get(gateIndex).getGate().getFunction();
   }

   /**
    * Calculates shortest distance from point to line.
    *
    * taken from: http://www.ahristov.com/tutorial/geometry-games/point-line-distance.html
    *
    * @param A first point of line
    * @param B second point of line
    * @param P another point
    * @return shortest distance from point to line
    */
   public double pointToLineDistance(Point A, Point B, Point P) {
      double normalLength = Math.sqrt((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
      return Math.abs((P.x - A.x) * (B.y - A.y) - (P.y - A.y) * (B.x - A.x)) / normalLength;
   }

   /**
    * @return the out
    */
   public Point getOut() {
      return out;
   }

   /**
    * @return the gate
    */
   public Gate getGate() {
      return gate;
   }

   private String calculateLogicExpression(String a, String b) {
      String logicExpression = gateFuncsAll.getExpression(gate.getFunction(), a, b);
      //System.out.println(logicExpression);
      return logicExpression;
   }

   protected String calculateLogicEx(List<DrawGate> listOfDGates, List<DrawInput> listOfDIns) {
      int inputsSize = listOfDIns.size();
      String ex1, ex2;

      int input1Index = getGate().getIn1();
      if (input1Index < inputsSize) //is input
         ex1 = listOfDIns.get(input1Index).getLogicExpression();
      else
         ex1 = listOfDGates.get(input1Index - inputsSize).calculateLogicEx(listOfDGates, listOfDIns);

      int input2Index = getGate().getIn2();
      if (input2Index < inputsSize) //is input
         ex2 = listOfDIns.get(input2Index).getLogicExpression();
      else
         ex2 = listOfDGates.get(input2Index - inputsSize).calculateLogicEx(listOfDGates, listOfDIns);

      setSelected(true);
      return calculateLogicExpression(ex1, ex2);
   }

   void setUsed() {
      used = true;
   }

   /**
    * @return the used
    */
   public boolean isUsed() {
      return used;
   }

   /**
    * @return the historyFunc
    */
   public int getHistoryFunc() {
      return historyFunc;
   }

   /**
    * @param historyFunc the historyFunc to set
    */
   private void setHistoryFunc(int historyFunc) {
      this.historyFunc = historyFunc;
   }

   /**
    * @return the historyIn1
    */
   public int getHistoryIn1() {
      return historyIn1;
   }

   /**
    * @param historyIn1 the historyIn1 to set
    */
   private void setHistoryIn1(int historyIn1) {
      this.historyIn1 = historyIn1;
   }

   /**
    * @return the historyIn2
    */
   public int getHistoryIn2() {
      return historyIn2;
   }

   /**
    * @param historyIn2 the historyIn2 to set
    */
   private void setHistoryIn2(int historyIn2) {
      this.historyIn2 = historyIn2;
   }

   public final void setHistories(int historyFunc, int historyIn1, int historyIn2) {
      setHistoryFunc(historyFunc);
      setHistoryIn1(historyIn1);
      setHistoryIn2(historyIn2);
   }

   /**
    * @return the dCh
    */
   public DrawChromosome getdCh() {
      return dCh;
   }

   void setSelected(boolean selected) {
      this.selected = selected;
   }

   /**
    * @return the selected
    */
   public boolean isSelected() {
      return selected;
   }

}
