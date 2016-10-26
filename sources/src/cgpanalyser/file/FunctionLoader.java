/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.file;

import cgpanalyser.functions.Functions;
import cgpanalyser.gui.MainWindow;
import cgpanalyser.truthtable.GateFuctionsAll;
import cgpanalyser.truthtable.GateFunction;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * used part of loading from master thesis of Jana Staurovska
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class FunctionLoader implements Runnable {

   private final File functionFile;
   private final GateFuctionsAll gateFuncsAll;
   private final MainWindow mainWindow;

   public FunctionLoader(File functionFile, GateFuctionsAll gateFuncsAll, MainWindow mainWindow) {
      this.functionFile = functionFile;
      this.gateFuncsAll = gateFuncsAll;
      this.mainWindow = mainWindow;
   }

   @Override
   public void run() {
      loadFunctionsFile(functionFile, gateFuncsAll, mainWindow);
      //System.out.println("Function file loaded");
   }

   private void loadFunctionsFile(File functionFile, GateFuctionsAll gateFuncsAll, MainWindow mainWindow) {
      BufferedReader in = null;

      try {

         in = new BufferedReader(new FileReader(functionFile));
         String line;
         while ((line = in.readLine()) != null) {
            if (line.startsWith("-")) //comment
               continue;

            String functionRecord[] = line.split(" ");

            if (functionRecord.length != 4)
               throw new NumberFormatException("Every line in the file should have 4 parts\nseparated with space. Comments start with \"-\".");

            byte index = Byte.parseByte(functionRecord[0]);
            String name = functionRecord[1].toLowerCase();
            String symbol = functionRecord[2];
            Color color = new Color(Integer.parseInt(functionRecord[3], 16));

            if (index < 0 || index > 15)
               throw new NumberFormatException("Number must be in range 0-15.");

            gateFuncsAll.addFunction(new GateFunction(index, symbol, name, color));
         }

         mainWindow.repaint();
      } catch (IOException | NumberFormatException ex) {
         Functions.showErrorMsg(ex.getMessage(), "Wrong function file");
      } catch (ArrayIndexOutOfBoundsException ex) {
         Functions.showErrorMsg("Wrong function numbering.", "Wrong function file");
      } finally {
         Functions.closeStream(in);
      }
   }

}
