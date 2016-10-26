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
public class GateFuctionsAll {

   public static final byte logicFunctionsCount = 16;
   private final GateFunction[] gateFuncs;

   public GateFuctionsAll() {
      this.gateFuncs = new GateFunction[logicFunctionsCount];
   }

   public synchronized String getSymbol(byte index) {
      return gateFuncs[index].getSymbol();
   }

   /**
    * @return symbol if available, or string representation of param index
    */
   public String getSymbolIfAvailable(byte index) {
      String symbol;
      try {
         symbol = getSymbol(index);
      } catch (Exception e) {
         symbol = Byte.toString(index);
      }
      return symbol;
   }

   public synchronized Color getColor(byte index) {
      return gateFuncs[index].getColor();
   }

   /**
    * @return symbol if available, or string representation of param index
    */
   public Color getColorIfAvailable(byte index) {
      Color color;
      try {
         color = gateFuncs[index].getColor();
      } catch (Exception e) {
         color = Color.BLACK;
      }
      return color;
   }

   public synchronized String getName(byte index) {
      return gateFuncs[index].getName();
   }

   public String getExpression(byte index, String a, String b) {
      String name = getName(index);
      return LogicOperations.getExpression(name, a, b);
   }

   public synchronized void addFunction(GateFunction gF) {
      gateFuncs[gF.getIndex()] = gF;
   }

   /**
    * @return the gateFuncs
    */
   public synchronized GateFunction[] getGateFuncs() {
      return gateFuncs;
   }

}
