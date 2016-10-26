/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser;

import cgpanalyser.chromelements.Gate;
import cgpanalyser.chromelements.Input;
import cgpanalyser.chromelements.Output;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class Chromosome {

   private final int fitness;
   private final Generation generation;

   private final List<Input> listOfIns;
   private final List<Output> listOfOuts;
   private final List<Gate> listOfGates;

   public Chromosome(int fitness, Generation generation, List<Input> listOfIns) {
      this.fitness = fitness;
      this.generation = generation;
      this.listOfIns = listOfIns;
      this.listOfOuts = new ArrayList<>();
      this.listOfGates = new ArrayList<>();
   }

   public void addOut(Output output) {
      getListOfOuts().add(output);
   }

   public void addGate(Gate g) {
      getListOfGates().add(g);
   }

   public void setOutNum(int outIndex, int outNum) {
      getListOfOuts().get(outIndex).setNumber((short) outNum);
   }

   /**
    * @return the fitness
    */
   public int getFitness() {
      return fitness;
   }

   /**
    * @return the listOfIns
    */
   public List<Input> getListOfIns() {
      return listOfIns;
   }

   /**
    * @return the listOfOuts
    */
   public List<Output> getListOfOuts() {
      return listOfOuts;
   }

   /**
    * @return the listOfGates
    */
   public List<Gate> getListOfGates() {
      return listOfGates;
   }

   /**
    * @return the generation
    */
   public Generation getGeneration() {
      return generation;
   }

}
