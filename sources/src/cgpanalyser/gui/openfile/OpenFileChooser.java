/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.openfile;

import java.awt.Component;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author PC
 *
 * trida filechooser pro nalezeni pisemky a vraceni cesty k souboru
 */
public class OpenFileChooser {

   private final JFileChooser chooser;
   private final Component parent;

   public OpenFileChooser(Component parent, FileFilter fileFilter) {
      this.parent = parent;
      this.chooser = new JFileChooser();
      chooser.addChoosableFileFilter(fileFilter);
      chooser.setFileFilter(fileFilter);
   }

	/**
	 * ziskani cesty pro otevreni souboru
	 * @return cesta k souboru pro otevreni
	 */
   public File getFilePath() {
      File filepath;
      int returnVal = chooser.showOpenDialog(parent);
      if (returnVal == JFileChooser.APPROVE_OPTION) {
         filepath = chooser.getSelectedFile();
         //System.out.println(filepath.getAbsolutePath());
         return filepath;
      }
      return null;
   }
}
