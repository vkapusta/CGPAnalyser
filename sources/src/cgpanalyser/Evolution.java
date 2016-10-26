package cgpanalyser;

import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class Evolution {

   private final List<Generation> listOfGenerations;
   private final ChromosomeInfo chInfo;
   private final EvolutionInfo evoInfo;

   public Evolution(List<Generation> listOfGenerations, ChromosomeInfo chInfo) {
      this.listOfGenerations = listOfGenerations;
      this.chInfo = chInfo;
      this.evoInfo = new EvolutionInfo(listOfGenerations);
   }

   public List<Generation> getListOfGenerations() {
      return listOfGenerations;
   }

   public Generation getGeneration(int generation) {
      return listOfGenerations.get(generation);
   }

   /**
    * @return the chInfo
    */
   public ChromosomeInfo getChInfo() {
      return chInfo;
   }

   /**
    * @return the evoInfo
    */
   public EvolutionInfo getEvoInfo() {
      return evoInfo;
   }

}
