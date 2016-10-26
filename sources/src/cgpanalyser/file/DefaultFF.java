/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.file;

import cgpanalyser.functions.Functions;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public abstract class DefaultFF {

   public static void createDefaultFF() {
      new Thread() {
         @Override
         public void run() {
            try {
               doWrite();
            } catch (IOException ex) {
               java.awt.EventQueue.invokeLater(new Runnable() {
                  @Override
                  public void run() {
                     Functions.showErrorMsg(ex.getMessage(), "Error creating file.");
                  }
               });
            }
         }
      }.start();
   }

   private static void doWrite() throws IOException {

      try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream("defaultFF.txt"), "utf-8"))) {
         writer.write("- Comment line start with \"-\"\n"
                 + "- Number: 0 to 15, linked to function numbering in evolution record file\n"
                 + "- Name: choose from the table below, NOT case-sensitive\n"
                 + "- Symbol: use whatever symbol desired (will be shown in gate)\n"
                 + "- Color: hexa representation of color of gates with the function\n"
                 + "-\n"
                 + "- operation	 name	alternative name\n"
                 + "- F_0        F		0\n"
                 + "- F_1        AND	.\n"
                 + "- F_2        A.~B	.~\n"
                 + "- F_3        A		A\n"
                 + "- F_4        ~A.B	~A.\n"
                 + "- F_5        B		B\n"
                 + "- F_6        XOR	^\n"
                 + "- F_7        OR		+\n"
                 + "- F_8        NOR	~+\n"
                 + "- F_9        XNOR	~^\n"
                 + "- F_(10)     ~B		~B\n"
                 + "- F_(11)     A+~B	+~\n"
                 + "- F_(12)     ~A		~A\n"
                 + "- F_(13)     ~A+B	~A+\n"
                 + "- F_(14)     NAND	~.\n"
                 + "- F_(15)     T		1\n"
                 + "-Number Name Symbol Color\n"
                 + "0 and & E68A2E\n"
                 + "1 or + 7ACC29\n"
                 + "2 xor ^ 00A3CC\n"
                 + "3 nand ~& 7A1F99");
         
         writer.close();
      }
   }
}
