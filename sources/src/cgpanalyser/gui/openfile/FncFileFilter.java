/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.openfile;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class FncFileFilter extends FileFilter {

   @Override
   public boolean accept(File f) {
      if (f.isDirectory())
         return true;
      return f.getName().endsWith(".txt");
   }

   @Override
   public String getDescription() {
      return "Function files (*.txt)";
   }
}
