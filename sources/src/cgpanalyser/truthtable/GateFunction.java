/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.truthtable;

import java.awt.Color;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class GateFunction {

   private final byte index;
   private final String symbol;
   private final String name;
   private final Color color;

   public GateFunction(byte index, String symbol, String name, Color color) {
      this.index = index;
      this.symbol = symbol;
      this.name = name;
      this.color = color;
   }

   /**
    * @return the symbol
    */
   public String getSymbol() {
      return symbol;
   }

   /**
    * @return the name
    */
   public String getName() {
      return name;
   }

   /**
    * @return the color
    */
   public Color getColor() {
      return color;
   }

   /**
    * @return the index
    */
   public byte getIndex() {
      return index;
   }

}
