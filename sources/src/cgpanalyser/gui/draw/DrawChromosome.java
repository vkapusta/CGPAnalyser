/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.draw;

import cgpanalyser.Chromosome;
import cgpanalyser.Evolution;
import cgpanalyser.chromelements.Gate;
import cgpanalyser.chromelements.Input;
import cgpanalyser.chromelements.Output;
import cgpanalyser.truthtable.GateFuctionsAll;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class DrawChromosome {

   private final DrawVariables dVars;
   private final boolean isMain;
   private final GateFuctionsAll gateFucsAll;
   private final Chromosome ch;
   private final Evolution evo;
   private final List<DrawInput> listOfDIns;
   private final List<DrawOutput> listOfDOuts;
   private final List<DrawGate> listOfDGates;
   private Point start;

   //history
   private boolean historyCalculated = false; //is history already canculated?
   private boolean drawHistory = false;
   private boolean isDrawUsedOnlySelected = true;
   private boolean isTtSelecting = false;
   private int maxHistory = 0;

   public DrawChromosome(DrawVariables dVars, GateFuctionsAll gateFucsAll, Chromosome ch, Point dChStart, Evolution evo, boolean isMain) {
      this.dVars = dVars;
      this.isMain = isMain;
      this.gateFucsAll = gateFucsAll;
      this.ch = ch;
      this.evo = evo;
      this.listOfDIns = new ArrayList<>();
      this.listOfDOuts = new ArrayList<>();
      this.listOfDGates = new ArrayList<>();
      this.start = new Point(dChStart);

      //create inputs and set coordinates
      int inputIndex = 0;
      for (Input in : ch.getListOfIns()) {
         listOfDIns.add(new DrawInput(in, dChStart, inputIndex, dVars, isMain));
         inputIndex++;
      }

      //create and set coordinates of gates
      int rows = evo.getChInfo().getRows();
      int currentRow = 1, currentCol = 0;
      for (Gate gate : ch.getListOfGates()) {
         listOfDGates.add(new DrawGate(gate, this, dChStart, currentRow - 1, currentCol, dVars, isMain));
         if (currentRow == rows) { //one column done (all rows in current column)
            currentRow = 0;
            currentCol++;
         }
         currentRow++;
      }

      //create and set coordinates of outputs
      int outputIndex = 0;
      int gatesMaxX = listOfDGates.get(listOfDGates.size() - 1).getOut().x; //max x coordinate of gates
      Point startOfOutputs = new Point(gatesMaxX + dVars.getGapOutputGate(isMain), dChStart.y);
      for (Output out : ch.getListOfOuts()) {
         listOfDOuts.add(new DrawOutput(out, this, startOfOutputs, outputIndex, dVars, isMain));
         outputIndex++;
      }

      findUsedElements();

   }

   public int getWidth() {
      return listOfDOuts.get(0).getCenter().x - listOfDIns.get(0).getCenter().x;
   }

   /**
    * updates coordinates because of changed zoom
    *
    * @param dChStart
    */
   public void updateDrawChromosomeCoordinates(Point dChStart) {
      this.start = new Point(dChStart);

      for (DrawInput dIn : listOfDIns) {
         dIn.calculateAndSetCenter(dChStart);
      }

      for (DrawGate dGate : listOfDGates) {
         dGate.calculateAndSetCoords(dChStart);
      }

      int gatesMaxX = listOfDGates.get(listOfDGates.size() - 1).getOut().x; //max x coordinate of gates
      Point startOfOutputs = new Point(gatesMaxX + dVars.getGapOutputGate(isMain), dChStart.y);
      for (DrawOutput dOut : listOfDOuts) {
         dOut.calculateAndSetCenter(startOfOutputs);
      }
   }

   private void findUsedElements() {
      int inputCount = listOfDIns.size();

      for (DrawOutput dOut : listOfDOuts) {
         int gateNum = dOut.getOut().getNumber();
         if (gateNum >= inputCount) {
            listOfDGates.get(gateNum - inputCount).setUsed();
            setUsedPredecessor(listOfDGates.get(gateNum - inputCount).getGate().getIn1(), inputCount);
            setUsedPredecessor(listOfDGates.get(gateNum - inputCount).getGate().getIn2(), inputCount);
         }
      }
   }

   private void setUsedPredecessor(int gateNum, int inputCount) {
      if (gateNum >= inputCount) {
         listOfDGates.get(gateNum - inputCount).setUsed();
         setUsedPredecessor(listOfDGates.get(gateNum - inputCount).getGate().getIn1(), inputCount);
         setUsedPredecessor(listOfDGates.get(gateNum - inputCount).getGate().getIn2(), inputCount);
      }
   }

   /**
    * @param gate index of gate
    * @return logic expression for gate (for truth table construction)
    */
   public String getAndCalculateLogicExpression(int gate) {
      setGatesUnSelected();

      DrawGate dGateSelected = listOfDGates.get(gate);
      String expression = dGateSelected.calculateLogicEx(listOfDGates, listOfDIns);

      return expression;
   }

   public void setGatesUnSelected() {
      for (DrawGate dGate : listOfDGates) {
         dGate.setSelected(false);
      }
   }

   /**
    * gets expression that is already calculated
    *
    * @param elementIndex
    * @return
    */
   /*protected String getLogicExpression(int elementIndex) {
    if (elementIndex < evo.getChInfo().getNumberOfIns()) //is input
    return getListOfDIns().get(elementIndex).getLogicExpression();
    else //gate
    return getListOfDGates().get(elementIndex - getListOfDIns().size()).getLogicExpression();
    }*/
   protected void paintMain(Graphics2D g, int[] selectedChromosomesGens) {
      for (DrawInput dIn : getListOfDIns()) {
         dIn.paintMain(g);
      }

      for (DrawOutput dOut : getListOfDOuts()) {
         dOut.paintMain(g);
      }

      for (DrawGate dGate : getListOfDGates()) {
         dGate.paintMain(g);
      }

      g.drawString("Generation " + getCh().getGeneration().getGenNumber() + "; Fitness " + getCh().getFitness(), getStart().x, getStart().y - dVars.getInfoTextHorizontalOffset());

      //mark selected chromosome with line above it
      if (selectedChromosomesGens[0] == getCh().getGeneration().getGenNumber() || selectedChromosomesGens[1] == getCh().getGeneration().getGenNumber()) {
         g.setStroke(new BasicStroke(dVars.getSelectedLineStroke()));
         if (selectedChromosomesGens[0] == selectedChromosomesGens[1]) { //selected as left and right chrom
            g.setColor(dVars.getLeftSelectedColor());
            g.drawLine(getStart().x, 0, getStart().x + (DrawChromosomesAll.computeWithOfChrom(evo, dVars, isMain) / 2), 0);
            g.setColor(dVars.getRightSelectedColor());
            g.drawLine(getStart().x + (DrawChromosomesAll.computeWithOfChrom(evo, dVars, isMain) / 2), 0, getStart().x + DrawChromosomesAll.computeWithOfChrom(evo, dVars, isMain), 0);
         } else if (selectedChromosomesGens[0] == getCh().getGeneration().getGenNumber()) { //selected as left chrom
            g.setColor(dVars.getLeftSelectedColor());
            g.drawLine(getStart().x, 0, getStart().x + DrawChromosomesAll.computeWithOfChrom(evo, dVars, isMain), 0);
         } else if (selectedChromosomesGens[1] == getCh().getGeneration().getGenNumber()) {
            g.setColor(dVars.getRightSelectedColor());
            g.drawLine(getStart().x, 0, getStart().x + DrawChromosomesAll.computeWithOfChrom(evo, dVars, isMain), 0);
         }
         setNormalStrokeAndColor(g);
      }
   }

   private void setNormalStrokeAndColor(Graphics2D g) {
      g.setStroke(new BasicStroke(dVars.getNormalStroke()));
      g.setColor(dVars.getNormalColor());
   }

   public void paintComparison(Graphics2D g, DrawChromosome dCh2) {
      for (DrawInput dIn : getListOfDIns()) {
         dIn.paintComparison(g);
      }

      int i = 0;
      for (DrawOutput dOut : getListOfDOuts()) {
         dOut.paintComparison(g, i, dCh2, isTtSelecting, isDrawHistory(), getMaxHistory());
         i++;
      }

      i = 0;
      for (DrawGate dGate : getListOfDGates()) {
         dGate.paintComparison(g, i, dCh2, isDrawUsedOnlySelected, isTtSelecting, isDrawHistory(), getMaxHistory());
         i++;
      }

      g.drawString("Generation " + getCh().getGeneration().getGenNumber() + "; Fitness " + getCh().getFitness(), getStart().x, getStart().y - dVars.getInfoTextHorizontalOffset());
   }

   public void calculateHistory() {
      if (historyCalculated) {
         setDrawHistory(true);
         return;
      }
      historyCalculated = true;

      int gen = getCh().getGeneration().getGenNumber();
      if (gen < 2) {
         setDrawHistory(true);
         return;
      }

      //calculate history of function, input1 and input2 (gate)
      for (int currentGate = 0; currentGate < getListOfDGates().size(); currentGate++) {

         byte f = getListOfDGates().get(currentGate).getGate().getFunction();
         int historyFunc = 0;
         boolean historyFuncFinished = false;

         int in1 = getListOfDGates().get(currentGate).getGate().getIn1();
         int historyIn1 = 0;
         boolean historyIn1Finished = false;

         int in2 = getListOfDGates().get(currentGate).getGate().getIn2();
         int historyIn2 = 0;
         boolean historyIn2Finished = false;

         for (int genIndex = (gen - 2); genIndex >= 0; genIndex--) {
            if (!historyFuncFinished)
               if (f == evo.getGeneration(genIndex).getBestChromosome().getListOfGates().get(currentGate).getFunction())
                  historyFunc++;
               else
                  historyFuncFinished = true;

            if (!historyIn1Finished)
               if (in1 == evo.getGeneration(genIndex).getBestChromosome().getListOfGates().get(currentGate).getIn1())
                  historyIn1++;
               else
                  historyIn1Finished = true;

            if (!historyIn2Finished)
               if (in2 == evo.getGeneration(genIndex).getBestChromosome().getListOfGates().get(currentGate).getIn2())
                  historyIn2++;
               else
                  historyIn2Finished = true;

            if (historyFuncFinished && historyIn1Finished && historyIn2Finished)
               break;
         }

         maxHistory = Math.max(maxHistory, Math.max(historyFunc, Math.max(historyIn1, historyIn2)));
         getListOfDGates().get(currentGate).setHistories(historyFunc, historyIn1, historyIn2);
      }

      //history of outputs
      for (int currentOut = 0; currentOut < getListOfDOuts().size(); currentOut++) {
         int in = getListOfDOuts().get(currentOut).getOut().getNumber();
         int historyIn = 0;

         for (int genIndex = (gen - 2); genIndex >= 0; genIndex--) {
            if (in == evo.getGeneration(genIndex).getBestChromosome().getListOfOuts().get(currentOut).getNumber())
               historyIn++;
            else
               break;
         }

         maxHistory = Math.max(maxHistory, historyIn);
         getListOfDOuts().get(currentOut).setHistory(historyIn);

      }

      setDrawHistory(true);
   }

   /**
    * @param index
    * @return Point of given element output (center of input, output coordinate of gate) for connecting wire
    */
   protected Point getPointOfElementOutput(int index) {
      if (index < evo.getChInfo().getNumberOfIns()) //is input
         return getListOfDIns().get(index).getCenter();
      else //gate
         return getListOfDGates().get(index - getListOfDIns().size()).getOut();
   }

   /**
    * @return the listOfDIns
    */
   public List<DrawInput> getListOfDIns() {
      return listOfDIns;
   }

   /**
    * @return the listOfDOuts
    */
   public List<DrawOutput> getListOfDOuts() {
      return listOfDOuts;
   }

   /**
    * @return the listOfDGates
    */
   public List<DrawGate> getListOfDGates() {
      return listOfDGates;
   }

   GateFuctionsAll getGateFucsAll() {
      return gateFucsAll;
   }

   /**
    * @return the ch
    */
   public Chromosome getCh() {
      return ch;
   }

   /**
    * @return the historyCalculated
    */
   public boolean isHistoryCalculated() {
      return historyCalculated;
   }

   /**
    * @param drawHistory the drawHistory to set
    */
   public void setDrawHistory(boolean drawHistory) {
      this.drawHistory = drawHistory;
   }

   /**
    * @return the drawHistory
    */
   public boolean isDrawHistory() {
      return drawHistory;
   }

   /**
    * @return the maxHistory
    */
   public int getMaxHistory() {
      return maxHistory;
   }

   /**
    * @return the start
    */
   public Point getStart() {
      return start;
   }

   /**
    * @param isDrawUsedOnlySelected the isDrawUsedOnlySelected to set
    */
   public void setIsDrawUsedOnlySelected(boolean isDrawUsedOnlySelected) {
      this.isDrawUsedOnlySelected = isDrawUsedOnlySelected;
   }

   /**
    * @return the isTtSelecting
    */
   public boolean isIsTtSelecting() {
      return isTtSelecting;
   }

   /**
    * @param isTtSelecting the isTtSelecting to set
    */
   public void setIsTtSelecting(boolean isTtSelecting) {
      this.isTtSelecting = isTtSelecting;
   }

}
