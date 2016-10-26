/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser;

/**
 * Stores info same for all chromosomes (number of circuit inputs/outputs)
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class ChromosomeInfo {

   private final int numberOfIns;
   private final int numberOfOuts;
   private final int cols;
   private final int rows;

   public ChromosomeInfo(int numberOfIns, int numberOfOuts, int cols, int rows) {
      this.numberOfIns = numberOfIns;
      this.numberOfOuts = numberOfOuts;
      this.cols = cols;
      this.rows = rows;
   }

   /**
    * @return the numberOfIns
    */
   public int getNumberOfIns() {
      return numberOfIns;
   }

   /**
    * @return the numberOfOuts
    */
   public int getNumberOfOuts() {
      return numberOfOuts;
   }

   /**
    * @return the rows
    */
   public int getRows() {
      return rows;
   }

   /**
    * @return the cols
    */
   public int getCols() {
      return cols;
   }
}
