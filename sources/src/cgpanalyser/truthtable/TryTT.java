/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cgpanalyser.truthtable;

import com.quew8.ttg.MainController;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class TryTT {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
		genTT();
	}

	private static void genTT() {
		MainController mC = new MainController();
		List<String> truthTableHeader = new ArrayList<>();
		List<List<String>> truthTableData = new ArrayList<>();


		try {
         mC.generateTT("out = (((bb^aa)~.(aa.aa))+(cc.(cc^cc)))[aa bb cc]", truthTableHeader, truthTableData);
		} catch (RuntimeException e) {
			System.out.println(e.getMessage());
		}

		printList(truthTableHeader);
		print2DList(truthTableData);
	}

	private static void printList(List<String> truthTableHeader) {
		for (String str : truthTableHeader) {
			System.out.print(str + " ");
		}
		System.out.print("\n");
	}

	private static void print2DList(List<List<String>> truthTableData) {
		for (List<String> row : truthTableData) {
			for (String col : row) {
				System.out.print(col + " ");
			}
			System.out.print("\n");
		}
	}

}
