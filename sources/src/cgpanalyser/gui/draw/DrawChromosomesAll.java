/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.draw;

import cgpanalyser.Chromosome;
import cgpanalyser.Evolution;
import cgpanalyser.truthtable.GateFuctionsAll;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Class representing one chromosome to be drawn
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class DrawChromosomesAll {

   private final DrawVariables dVars;
   private final GateFuctionsAll gateFuncsAll;
   private final List<DrawChromosome> dChsAll;
   private final Point start;
   private final Evolution evo;
	private static final boolean isMain = true; //always true here because calle from MainWindow (mainPanel)

   public DrawChromosomesAll(DrawVariables dVars, GateFuctionsAll gateFuncsAll, List<Chromosome> chsAll, Evolution evo) {
      this.dVars = dVars;
      this.gateFuncsAll = gateFuncsAll;
      this.evo = evo;
      this.dChsAll = new ArrayList<>();
      this.start = new Point(dVars.getDrawingStart());
      for (Chromosome ch : chsAll) {
         this.dChsAll.add(new DrawChromosome(dVars, gateFuncsAll, ch, start, evo, isMain));
         start.x = getMaxXOfLastChromosome() + dVars.getGapChromosomeHorizontal();
      }
   }

   public static int computeWithOfChrom(Evolution evo, DrawVariables dVars, boolean isMain) {
      return dVars.getGapInputGate(isMain) + (evo.getChInfo().getCols() - 1) * (dVars.getGateWidth(isMain) + dVars.getGapGateHorizontal(isMain) + 2 * dVars.getGateWireLen(isMain)) + (dVars.getGateWidth(isMain) + 2 * dVars.getGateWireLen(isMain)) + dVars.getGapOutputGate(isMain);
   }

   public static int computeHeightOfChrom(Evolution evo, DrawVariables dVars, boolean isMain) {
      int inputsHeight = (evo.getChInfo().getNumberOfIns() - 1) * dVars.getGapIOVertical(isMain);
      int gatesHeight = dVars.getGateHeight(isMain) + (evo.getChInfo().getRows() - 1) * (dVars.getGateHeight(isMain) + dVars.getGapGateVertical(isMain)); //there is one more gate than gaps
      int outputsHeight = (evo.getChInfo().getNumberOfOuts() - 1) * dVars.getGapIOVertical(isMain);

      return Math.max(inputsHeight, Math.max(gatesHeight, outputsHeight));
   }

   public final int getWidthOfLastChrom() {
      int max = dChsAll.get(dChsAll.size() - 1).getListOfDOuts().get(0).getCenter().x; //x coordinate of outputs
      int min = dChsAll.get(dChsAll.size() - 1).getListOfDIns().get(0).getCenter().x; //x coordinate of inputs
      return max - min;
   }

   public final int getMaxXOfLastChromosome() {
      return dChsAll.get(dChsAll.size() - 1).getListOfDOuts().get(0).getCenter().x; //x coordinate of outputs
   }

   /**
    * @return the dCh
    */
   public List<DrawChromosome> getDChs() {
      return dChsAll;
   }

   public void paintMain(Graphics2D g, int[] selectedChromosomesGens) {
      for (DrawChromosome dCh : dChsAll) {
         dCh.paintMain(g, selectedChromosomesGens);
      }
   }

}
