/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser;

import cgpanalyser.chromelements.Gate;
import cgpanalyser.functions.Functions;
import cgpanalyser.gui.EvoInfoChart;
import cgpanalyser.truthtable.GateFuctionsAll;
import java.util.List;

/**
 * Info about gate usage in whole evolution
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class EvolutionInfo {

   private final List<Generation> listOfGenerations;
   private final byte logicFunctionsCount = GateFuctionsAll.logicFunctionsCount;
   private final long[] gateFuncsCounts;
   private volatile boolean computed;

   public EvolutionInfo(List<Generation> listOfGenerations) {
      this.listOfGenerations = listOfGenerations;
      gateFuncsCounts = new long[logicFunctionsCount];
      computed = false;
   }

   public void computeAndShowEvolutionInfo(GateFuctionsAll gateFuncsAll) {
      if (!isComputed())
         new Thread() {
            @Override
            public void run() {
               try {
                  //compute function usage count
                  for (Generation generation : listOfGenerations) {
                     for (Gate gate : generation.getBestChromosome().getListOfGates()) {
                        incrementGateFuncsCounts(gate.getFunction());
                     }
                  }
                  setComputed(true);
               } catch (ArrayIndexOutOfBoundsException e) {
                  Functions.showErrorMsg("Functions must be numbered from 0 to " + (GateFuctionsAll.logicFunctionsCount - 1) + ".", "Wrong fuction numbering");
                  return;
               }
               showResults(gateFuncsAll);
            }
         }.start();
      else
         showResults(gateFuncsAll);
   }

   private void showResults(GateFuctionsAll gateFuncsAll) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            new EvoInfoChart("Function usage in whole evolution.", getGateFuncsCounts(), gateFuncsAll);
         }
      });
   }

   private void incrementGateFuncsCounts(byte index) {
      getGateFuncsCounts()[index]++;
   }

   /**
    * @return the computed
    */
   private boolean isComputed() {
      return computed;
   }

   /**
    * @param computed the computed to set
    */
   private void setComputed(boolean computed) {
      this.computed = computed;
   }

   /**
    * @return the gateFuncsCounts
    */
   public long[] getGateFuncsCounts() {
      return gateFuncsCounts;
   }

}
