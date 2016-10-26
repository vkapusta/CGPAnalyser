/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.functions;

import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public abstract class AA {

	/**
	 * Set anti-aliasing for text
	 * @param graphics2D
	 * @param aa
	 */
	public static void setAAText(Graphics2D graphics2D, boolean aa) {
		if (aa)
			graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		else
			graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
	}

	/**
	 * Set anti-aliasing
	 * @param graphics2D
	 * @param aa
	 */
	public static void setAA(Graphics2D graphics2D, boolean aa) {
		if (aa)
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		else
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
	}
}
