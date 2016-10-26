/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cgpanalyser.chromelements;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class Gate {
   
   private final short in1;
   private final short in2;
   private final short out;
   private final byte function;

   public Gate(short out, short in1, short in2, byte function) {
      this.out = out;
      this.in1 = in1;
      this.in2 = in2;
      this.function = function;
   }

   /**
    * @return the in1
    */
   public short getIn1() {
      return in1;
   }

   /**
    * @return the in2
    */
   public short getIn2() {
      return in2;
   }

   /**
    * @return the out
    */
   public short getOut() {
      return out;
   }

   /**
    * @return the function
    */
   public byte getFunction() {
      return function;
   }

}
