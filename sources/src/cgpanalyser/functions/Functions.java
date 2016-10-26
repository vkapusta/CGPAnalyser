/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.functions;

import java.io.BufferedReader;
import javax.swing.JOptionPane;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public abstract class Functions {

   /**
    * Prevede cislo ve stringu na integer pripadne vrati nulu
    *
    * @param string textova reprezentace cisla
    * @return prislusny int nebo 0
    */
   public static int strToInt(String string) {
      int number;
      try {
         number = Integer.parseInt(string);
      } catch (NumberFormatException e) {
         number = 0;
      }

      return number;
   }

   public static void showErrorMsg(String text, String title) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         @Override
         public void run() {
            JOptionPane.showMessageDialog(null, text, title, JOptionPane.ERROR_MESSAGE);
         }
      });
   }

   public static void closeStream(BufferedReader in) {
      if (in != null)
         try {
            in.close();
         } catch (Exception ex) {
            System.err.println("Error closing stream.\n" + ex.getMessage());
         }
   }

}
