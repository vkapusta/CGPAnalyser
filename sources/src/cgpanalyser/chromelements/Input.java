/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cgpanalyser.chromelements;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class Input {
   private final char name;
   
   public Input(char name){
      this.name = name;
   }

	/**
	 * @return the name
	 */
	public char getName() {
		return name;
	}
}
