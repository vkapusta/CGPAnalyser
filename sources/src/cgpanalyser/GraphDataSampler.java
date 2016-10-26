/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JSpinner;

/**
 * Class for selecting data from evolution for graph
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class GraphDataSampler {

   private final int maxToDisplaySize = 1000; //maximum displayable items

   private final Evolution evo;
   private final List<List<Generation>> toDisplayLists;
   private final List<Integer> fitnessDeltaThreshold;
   private final JSpinner spinnerFitDeltaThreshold;

   public GraphDataSampler(Evolution evo, JSpinner spinnerFitDeltaThreshold) {
      this.evo = evo;
      this.spinnerFitDeltaThreshold = spinnerFitDeltaThreshold;
      this.toDisplayLists = new ArrayList<>();
      this.fitnessDeltaThreshold = new ArrayList<>();
   }

   public void resample(int newThreshold) {
      int genFromIndex = getLastToDisplay().get(0).getGenNumber() - 1;
      int genToIndex = getLastToDisplay().get(getLastToDisplay().size() - 1).getGenNumber() - 1;

      zoomOut(false); //not necessary (allow manual zoomOut or not)
      fitnessDeltaThreshold.add(newThreshold);
      List<Generation> toDisplay = sample(genFromIndex, genToIndex);
      toDisplayLists.add(toDisplay);
   }

   /**
    * Samples data in selected range based on current fitnessDeltaThreshold and adds sampled list into toDisplayLists.
    *
    * @param genFromIndex index of start generation (from 0) in evo list
    * @param genToIndex index of final generation (from 0) in evo list
    */
   public final void sampleAndAddData(int genFromIndex, int genToIndex) {

      //check and clip params bounds
      if (!toDisplayLists.isEmpty()) {
         if (genFromIndex < (getLastToDisplay().get(0).getGenNumber() - 1))
            genFromIndex = getLastToDisplay().get(0).getGenNumber() - 1;

         if (genToIndex > (getLastToDisplay().get(getLastToDisplay().size() - 1).getGenNumber() - 1))
            genToIndex = (getLastToDisplay().get(getLastToDisplay().size() - 1).getGenNumber() - 1);
      }

      guessThreshold(genFromIndex, genToIndex);

      List<Generation> toDisplay;
      while (true) {
         toDisplay = sample(genFromIndex, genToIndex);
         if (toDisplay.size() > maxToDisplaySize)
            fitnessDeltaThreshold.set(fitnessDeltaThreshold.size() - 1, getLastFitDelThres() + (getLastFitDelThres() / 2) + 1); //increase fitness delta threshold to reduce sampled data size (+1 in case of fitnessDeltaThreshold[last]==0)
         else
            break;
      }

      spinnerFitDeltaThreshold.setValue(getLastFitDelThres());

      toDisplayLists.add(toDisplay);

      System.out.println("toDisplay gens: " + genFromIndex + " - " + genToIndex);
      System.out.println("toDisplay.size(): " + toDisplay.size());
   }

   /**
    * Guesses ideal threshold based on maximum fitness delta.
    */
   private void guessThreshold(int genFromIndex, int genToIndex) {
      int maxFitnessDelta = 0;
      int currentFitnessDelta;

      Generation currentGeneration;
      Generation prevGeneration = evo.getGeneration(genFromIndex);
      Chromosome currentBestCh;
      Chromosome previousBestCh = prevGeneration.getBestChromosome();

      //find max fitness delta
      for (int i = genFromIndex; i <= genToIndex; i++) {
         currentGeneration = evo.getListOfGenerations().get(i);
         currentBestCh = currentGeneration.getBestChromosome();
         currentFitnessDelta = getFitnessDelta(currentBestCh, previousBestCh);

         if (currentFitnessDelta > maxFitnessDelta)
            maxFitnessDelta = currentFitnessDelta;

         previousBestCh = currentBestCh;
      }

      fitnessDeltaThreshold.add(maxFitnessDelta / 2);

      System.out.println("--maxFitnessDelta: " + maxFitnessDelta + " --fitnessThreshold: " + fitnessDeltaThreshold);

   }

   private List<Generation> sample(int genFromIndex, int genToIndex) {
      List<Generation> toDisplay = new ArrayList<>();
      Generation currentGeneration;
      Generation prevGeneration = evo.getGeneration(genFromIndex);
      Chromosome currentBestCh;
      Chromosome previousBestCh = prevGeneration.getBestChromosome();
      int currentFitnessDelta;
      int maxFitnessDelta = 0;

      toDisplay.add(prevGeneration); //add first generation
      for (int i = genFromIndex; i <= genToIndex; i++) {
         currentGeneration = evo.getListOfGenerations().get(i);
         currentBestCh = currentGeneration.getBestChromosome();
         currentFitnessDelta = getFitnessDelta(currentBestCh, previousBestCh);

         if (currentFitnessDelta >= getLastFitDelThres())
            addToDisplay(toDisplay, prevGeneration, currentGeneration);

         if (currentFitnessDelta > maxFitnessDelta)
            maxFitnessDelta = currentFitnessDelta;

         previousBestCh = currentBestCh;
         prevGeneration = currentGeneration;
      }
      addToDisplay(toDisplay, prevGeneration); //add last generation
      return toDisplay;
   }

   private static int getFitnessDelta(Chromosome currentBest, Chromosome previousBest) {
      return Math.abs(currentBest.getFitness() - previousBest.getFitness());
   }

   /**
    * Adds 2 generations into toDisplay list, checks if prevGeneration is already added.
    *
    * @param toDisplay
    * @param prevGeneration
    * @param currentGeneration
    */
   private void addToDisplay(List<Generation> toDisplay, Generation prevGeneration, Generation currentGeneration) {
      if (!alreadyAdded(prevGeneration, toDisplay))
         toDisplay.add(prevGeneration);
      toDisplay.add(currentGeneration);
   }

   /**
    * Adds generation into toDisplay list, checks if it is already added.
    *
    * @param toDisplay
    * @param generation
    */
   private void addToDisplay(List<Generation> toDisplay, Generation generation) {
      if (!alreadyAdded(generation, toDisplay))
         toDisplay.add(generation);
   }

   /**
    * checks only last item in list
    */
   private static boolean alreadyAdded(Generation g, List<Generation> gList) {
      if (gList.isEmpty())
         return false;
      else
         return g.equalsNumber(gList.get(gList.size() - 1));
   }

   public List<Generation> getToDisplay(int i) {
      return toDisplayLists.get(i);
   }

   /**
    * @return the last toDisplay
    */
   public List<Generation> getLastToDisplay() {
      return toDisplayLists.get(toDisplayLists.size() - 1);
   }

   /**
    * @return the fitnessDeltaThreshold
    */
   /*public int getFitnessDeltaThreshold() {
    return fitnessDeltaThreshold.get(fitnessDeltaThreshold.size() - 1);
    }*/
   public boolean isZoomableOut() {
      return toDisplayLists.size() > 1;
   }

   public void zoomOut(boolean setSpinner) {
      toDisplayLists.remove(toDisplayLists.size() - 1);
      fitnessDeltaThreshold.remove(fitnessDeltaThreshold.size() - 1);

      if (setSpinner)
         spinnerFitDeltaThreshold.setValue(getLastFitDelThres());
   }

   private int getLastFitDelThres() {
      return fitnessDeltaThreshold.get(fitnessDeltaThreshold.size() - 1);
   }

}
