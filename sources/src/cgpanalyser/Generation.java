/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class Generation {

   private final int genNumber;
   private Chromosome bestChromosome;

   public Generation(int genNumber) {
      this.genNumber = genNumber;
   }

   /**
    * @return the number
    */
   public int getGenNumber() {
      return genNumber;
   }

   public Chromosome getBestChromosome() {
      return bestChromosome;
   }

   /**
    * Equals based on generation number
    */
   public boolean equalsNumber(Generation g) {
      return this.getGenNumber() == g.getGenNumber();
   }

   /**
    * @param bestChromosome the bestChromosome to set
    */
   public void setBestChromosome(Chromosome bestChromosome) {
      this.bestChromosome = bestChromosome;
   }

}
