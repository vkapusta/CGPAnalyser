/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.truthtable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class TruthTableUtils {

   public static String getUsedInputs(String expression) {
      List<String> usedInputsList = new ArrayList<>();
      
      Pattern pattern = Pattern.compile("\\w+");
      Matcher matcher = pattern.matcher(expression);

      String currentString;
      while (matcher.find()) {
         currentString = matcher.group();
         if (!usedInputsList.contains(currentString))
            usedInputsList.add(currentString);
      }
      Collections.sort(usedInputsList);

      return String.join(" ", usedInputsList);
   }

   public static void printList(List<String> truthTableHeader) {
      for (String str : truthTableHeader) {
         System.out.print(str + " ");
      }
      System.out.print("\n");
   }

   public static void print2DList(List<List<String>> truthTableData) {
      for (List<String> row : truthTableData) {
         for (String col : row) {
            System.out.print(col + " ");
         }
         System.out.print("\n");
      }
   }
}
