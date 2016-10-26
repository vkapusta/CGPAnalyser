/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser;

import cgpanalyser.gui.MainWindow;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class start {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {

      try {
         setSystemLaF();
      } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
         Logger.getLogger(start.class.getName()).log(Level.SEVERE, null, ex);
      }

      java.awt.EventQueue.invokeLater(new Runnable() {

         @Override
         public void run() {
            MainWindow windowMain = new MainWindow();
            windowMain.pack();
            windowMain.setLocationRelativeTo(null);
            windowMain.setVisible(true);
         }
      });
   }

   private static void setSystemLaF() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
   }
}
