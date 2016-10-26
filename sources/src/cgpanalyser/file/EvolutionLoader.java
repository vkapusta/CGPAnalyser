/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.file;

import cgpanalyser.Chromosome;
import cgpanalyser.ChromosomeInfo;
import cgpanalyser.Evolution;
import cgpanalyser.Generation;
import cgpanalyser.functions.Functions;
import cgpanalyser.chromelements.Gate;
import cgpanalyser.chromelements.Input;
import cgpanalyser.chromelements.Output;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * used part of loading from master thesis of Jana Staurovska, changed most of it and accelerated
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class EvolutionLoader {

   private final char[] alphabet;

   public EvolutionLoader() {
      this.alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
   }

   public Evolution loadEvo(File f) {
      long startTime = System.nanoTime();

      ChromosomeInfo chInfo = null;
      List<Generation> listOfGenerations = new ArrayList<>();

      List<Input> listOfIns = new ArrayList<>();
      boolean inputsCreated = false;

      int maxfitness = Integer.MAX_VALUE;
      int bestFitness = -1;
      int bestChromosomeIndex = 0;
      int chromosomeIndex = 0;
      boolean firstGeneration = true;

      BufferedReader in = null;
      try {
         in = new BufferedReader(new FileReader(f));
         String line;
         int genNumber, fitness, numberOfIns = 0, numberOfOuts = 0, cols, rows; //temporary variables
         int usedBlocks, usedBlocksBest = Integer.MAX_VALUE;
         Chromosome ch = null;

         while ((line = in.readLine()) != null) {
            // parse the file
            if (line.startsWith("Maxfitness"))
               maxfitness = Integer.parseInt(line.split(" ")[1]);
            else if (line.startsWith("Generation")) {
               if ((!line.contains("/")) && (!line.contains("b:"))) {

                  if (chromosomeIndex > 0) { //generation finished
                     listOfGenerations.get(listOfGenerations.size() - 1).setBestChromosome(ch); // add best chromosome to list in generation
                     if (firstGeneration)
                        firstGeneration = false;
                  }
                  chromosomeIndex = 0;

                  // line with number of generation, save the number
                  genNumber = Functions.strToInt(line.split(" ")[1]);
                  Generation g = new Generation(genNumber);
                  listOfGenerations.add(g);
               }
            } else if (line.startsWith("{")) {
               if (listOfGenerations.isEmpty())
                  continue;
               // line with chromosome
               String[] tmp;
               //line = line.replace("(", ""); // remove ( 
               line = replace(line, "(", ""); // remove ( 
               tmp = line.split("}"); // split the chrom. in two parts
               //fitness = Functions.strToInt(tmp[0].split(", ")[0].replace("{", "")); // first part is fitness and used blocks, second is chromosme info and chromosome data
               fitness = Functions.strToInt(replace(tmp[0].split(", ")[0], "{", "", 1)); // first part is fitness and used blocks, second is chromosme info and chromosome data
               usedBlocks = Integer.parseInt(tmp[0].split(", ")[1]);

               // get info about chrom - parse only once
               if (chInfo == null) {
                  String secondPart = tmp[1];
                  //secondPart = secondPart.replaceAll("\\{", ""); // remove the first {
                  secondPart = replace(secondPart, "{", ""); // remove the first {
                  //secondPart = secondPart.replaceAll("\\ ", ""); // remove all white spaces
                  secondPart = replace(secondPart, " ", ""); // remove all white spaces
                  String[] info = secondPart.split(","); // split the string into parts

                  // save needed numbers
                  numberOfIns = Functions.strToInt(info[0]);
                  numberOfOuts = Functions.strToInt(info[1]);
                  cols = Functions.strToInt((info[2]));
                  rows = Functions.strToInt((info[3]));

                  chInfo = new ChromosomeInfo(numberOfIns, numberOfOuts, cols, rows);
               }

               //create list of inputs (only one)
               if (!inputsCreated) {
                  for (int i = 0; i < numberOfIns; i++) {
                     listOfIns.add(new Input(getInputName(i)));
                  }
                  inputsCreated = true;
               }

               //**************  Find best parent for next gen.
               if (firstGeneration)
                  if (fitness > bestFitness) { //first best in first generation
                     bestFitness = fitness;
                     bestChromosomeIndex = chromosomeIndex;
                  } else
                     continue; //not parent
               else if (chromosomeIndex != bestChromosomeIndex)
                  if (fitness == maxfitness) //used blocks optimization
                     if (usedBlocks <= usedBlocksBest) {
                        usedBlocksBest = usedBlocks;
                        bestFitness = fitness;
                        bestChromosomeIndex = chromosomeIndex;
                     } else
                        continue; //not parent
                  else if (fitness >= bestFitness) { //not reached best fitness but improving
                     bestFitness = fitness;
                     bestChromosomeIndex = chromosomeIndex;
                     usedBlocksBest = Integer.MAX_VALUE;
                  } else
                     continue; //not parent
               chromosomeIndex++;
               //************** 

               ch = new Chromosome(fitness, listOfGenerations.get(listOfGenerations.size() - 1), listOfIns);
               for (int i = 0; i < numberOfOuts; i++) {
                  Output output = new Output();
                  ch.addOut(output);
               }

               // third part is chrom
               String thirdPart = tmp[2];
               String[] gates = thirdPart.split("\\)"); // split second part into smaller pieces
               for (int i = 0; i < (gates.length - 1); i++) {
                  //gates[i] = gates[i].replace("[", ""); // remove [
                  gates[i] = replace(gates[i], "[", "", 1); // remove [
                  //gates[i] = gates[i].replace("]", ","); // replace ] with comma
                  gates[i] = replace(gates[i], "]", ",", 1); // replace ] with comma
                  String[] infos = gates[i].split(","); // split
                  // save numbers into new gate
                  Gate g = new Gate(Short.parseShort(infos[0]), Short.parseShort(infos[1]), Short.parseShort(infos[2]), Byte.parseByte(infos[3]));//chromosome, out, in1, in2, function
                  ch.addGate(g); // add gate to list
               }
               if (gates[gates.length - 1].contains(",")) { // more outputs (contains ",")
                  String[] tmp3;
                  tmp3 = gates[gates.length - 1].split(",");
                  for (int i = 0; i < tmp3.length; i++) {
                     // save each number of output to the list
                     ch.setOutNum(i, Functions.strToInt(tmp3[i]));
                  }
               } else // only one output, the last one in the field
                  ch.setOutNum(0, Functions.strToInt(gates[gates.length - 1]));

            }
         }
         listOfGenerations.get(listOfGenerations.size() - 1).setBestChromosome(ch);
      } catch (IOException ex) {
         //Logger.getLogger(EvolutionLoader.class.getName()).log(Level.SEVERE, null, ex);
         Functions.showErrorMsg(ex.getMessage(), "Wrong evolution file.");
      } catch (Exception ex) {
         Functions.showErrorMsg(ex.getMessage(), "Error while loading file.");
      } finally {
         Functions.closeStream(in);
      }

      long estimatedTime = System.nanoTime() - startTime;
      double time = (double) estimatedTime;
      System.out.println("done: " + time / 1000000000);
      //System.exit(8);

      return new Evolution(listOfGenerations, chInfo);
   }

   private char getInputName(int i) {
      return alphabet[i % alphabet.length]; //modulo just in case, should never happen (too many inputs)
   }

   /*
    * Licensed to the Apache Software Foundation (ASF) under one or more
    * contributor license agreements.  See the NOTICE file distributed with
    * this work for additional information regarding copyright ownership.
    * The ASF licenses this file to You under the Apache License, Version 2.0
    * (the "License"); you may not use this file except in compliance with
    * the License.  You may obtain a copy of the License at
    *
    *      http://www.apache.org/licenses/LICENSE-2.0
    *
    * Unless required by applicable law or agreed to in writing, software
    * distributed under the License is distributed on an "AS IS" BASIS,
    * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    * See the License for the specific language governing permissions and
    * limitations under the License.
    */
   /**
    * Represents a failed index search.
    *
    * @since 2.1
    */
   public static final int INDEX_NOT_FOUND = -1;

   /**
    * <p>
    * Replaces all occurrences of a String within another String.</p>
    *
    * <p>
    * A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *)        = null
    * StringUtils.replace("", *, *)          = ""
    * StringUtils.replace("any", null, *)    = "any"
    * StringUtils.replace("any", *, null)    = "any"
    * StringUtils.replace("any", "", *)      = "any"
    * StringUtils.replace("aba", "a", null)  = "aba"
    * StringUtils.replace("aba", "a", "")    = "b"
    * StringUtils.replace("aba", "a", "z")   = "zbz"
    * </pre>
    *
    * @see #replace(String text, String searchString, String replacement, int max)
    * @param text text to search and replace in, may be null
    * @param searchString the String to search for, may be null
    * @param replacement the String to replace it with, may be null
    * @return the text with any replacements processed, {@code null} if null String input
    */
   public static String replace(final String text, final String searchString, final String replacement) {
      return replace(text, searchString, replacement, -1);
   }

   /**
    * <p>
    * Replaces a String with another String inside a larger String, for the first {@code max} values of the search String.</p>
    *
    * <p>
    * A {@code null} reference passed to this method is a no-op.</p>
    *
    * <pre>
    * StringUtils.replace(null, *, *, *)         = null
    * StringUtils.replace("", *, *, *)           = ""
    * StringUtils.replace("any", null, *, *)     = "any"
    * StringUtils.replace("any", *, null, *)     = "any"
    * StringUtils.replace("any", "", *, *)       = "any"
    * StringUtils.replace("any", *, *, 0)        = "any"
    * StringUtils.replace("abaa", "a", null, -1) = "abaa"
    * StringUtils.replace("abaa", "a", "", -1)   = "b"
    * StringUtils.replace("abaa", "a", "z", 0)   = "abaa"
    * StringUtils.replace("abaa", "a", "z", 1)   = "zbaa"
    * StringUtils.replace("abaa", "a", "z", 2)   = "zbza"
    * StringUtils.replace("abaa", "a", "z", -1)  = "zbzz"
    * </pre>
    *
    * @param text text to search and replace in, may be null
    * @param searchString the String to search for, may be null
    * @param replacement the String to replace it with, may be null
    * @param max maximum number of values to replace, or {@code -1} if no maximum
    * @return the text with any replacements processed, {@code null} if null String input
    */
   public static String replace(final String text, final String searchString, final String replacement, int max) {
      if (isEmpty(text) || isEmpty(searchString) || replacement == null || max == 0)
         return text;
      int start = 0;
      int end = text.indexOf(searchString, start);
      if (end == INDEX_NOT_FOUND)
         return text;
      final int replLength = searchString.length();
      int increase = replacement.length() - replLength;
      increase = increase < 0 ? 0 : increase;
      increase *= max < 0 ? 16 : max > 64 ? 64 : max;
      final StringBuilder buf = new StringBuilder(text.length() + increase);
      while (end != INDEX_NOT_FOUND) {
         buf.append(text.substring(start, end)).append(replacement);
         start = end + replLength;
         if (--max == 0)
            break;
         end = text.indexOf(searchString, start);
      }
      buf.append(text.substring(start));
      return buf.toString();
   }

   /**
    * <p>
    * Checks if a CharSequence is empty ("") or null.</p>
    *
    * <pre>
    * StringUtils.isEmpty(null)      = true
    * StringUtils.isEmpty("")        = true
    * StringUtils.isEmpty(" ")       = false
    * StringUtils.isEmpty("bob")     = false
    * StringUtils.isEmpty("  bob  ") = false
    * </pre>
    *
    * <p>
    * NOTE: This method changed in Lang version 2.0. It no longer trims the CharSequence. That functionality is available in
    * isBlank().</p>
    *
    * @param cs the CharSequence to check, may be null
    * @return {@code true} if the CharSequence is empty or null
    * @since 3.0 Changed signature from isEmpty(String) to isEmpty(CharSequence)
    */
   public static boolean isEmpty(final CharSequence cs) {
      return cs == null || cs.length() == 0;
   }
   

}
