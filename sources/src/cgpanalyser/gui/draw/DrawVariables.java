/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.draw;

import java.awt.Color;
import java.awt.Point;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class DrawVariables {

	private final Point drawingStart = new Point(10, 30);
	
	//zoom
	private double zoomMain = 10;
	private double zoomSelected = 10;

	private final int gapChromosomeHorizontal = 40;

	//Chromosome constants
	private final int gapInputGate = 40; //horizontal gap between inputs and gates
	private final int gapOutputGate = 40; //horizontal gap between outputs and gates
	private final int gapGateVertical = 20; //vertical gap between gates
	private final int gapGateHorizontal = 50; //vertical gap between gates
	private final int gapIOVertical = 30; //vertical gap between inputs/outputs
	private final int infoTextHorizontalOffset = 14; //position of Generation... text above chromosome

	//Gate constants - size of gate
	private final int gateWidth = 32;
	private final int gateHeight = 42;
	private final int gateWireLen = 16;

	//Stroke constants for wire
	private final float wireBoldStrokeWidth = 2;
	private final float wireNormalStrokeWidth = 1;

	//Stroke and colors of selected chromosome line above
	private final float selectedLineStroke = 4;
	private final Color leftSelectedColor = Color.RED;
	private final Color rightSelectedColor = Color.BLUE;

	private final float normalStroke = 1;
	private final Color normalColor = Color.BLACK;

	//drawing all gates or used only
	private Color unusedColorMain = Color.LIGHT_GRAY; //in main window
	private final Color unusedColorSelected = Color.LIGHT_GRAY; //in selected/compare window
   
   //drawing colors based on function file
   private boolean drawColorsFF = true;

	//color palete for history
	private final int colorPaletteSize = 20;
	private Color[] colorPalette;
   
	public DrawVariables() {
		this.setZoom(zoomMain, true);
		this.setZoom(zoomSelected, false);
		this.createColorPalette();
	}

	/**
	 * @param main is zoom for main Window
	 * @return the zoomMain
	 */
	public double getZoom(boolean main) {
		return main ? zoomMain : zoomSelected;
	}

	public final void setZoom(double newZoom, boolean main) {
		newZoom = newZoom / 10.0;
		if (newZoom < 0.1)
			newZoom = 0.1;
		if (newZoom > 5.0)
			newZoom = 5.0;

		if (main)
			this.zoomMain = newZoom;
		else
			this.zoomSelected = newZoom;
	}

	/**
	 * @return the gapChromosomeHorizontal
	 */
	public int getGapChromosomeHorizontal() {
		return gapChromosomeHorizontal;
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gapInputGate
	 */
	public int getGapInputGate(boolean main) {
		return (int) (gapInputGate * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gapOutputGate
	 */
	public int getGapOutputGate(boolean main) {
		return (int) (gapOutputGate * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gapGateVertical
	 */
	public int getGapGateVertical(boolean main) {
		return (int) (gapGateVertical * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gapGateHorizontal
	 */
	public int getGapGateHorizontal(boolean main) {
		return (int) (gapGateHorizontal * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gapIOVertical
	 */
	public int getGapIOVertical(boolean main) {
		return (int) (gapIOVertical * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gateWidth
	 */
	public int getGateWidth(boolean main) {
		return (int) (gateWidth * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gateHeight
	 */
	public int getGateHeight(boolean main) {
		return (int) (gateHeight * getZoom(main));
	}

	/**
	 * @param main is zoom for main Window
	 * @return the gateWireLen
	 */
	public int getGateWireLen(boolean main) {
		return (int) (gateWireLen * getZoom(main));
	}

	/**
	 * @return the infoTextHorizontalOffset
	 */
	public int getInfoTextHorizontalOffset() {
		return infoTextHorizontalOffset;
	}

	/**
	 * @return the wireBoldStrokeWidth
	 */
	public float getWireBoldStrokeWidth() {
		return wireBoldStrokeWidth;
	}

	/**
	 * @return the wireNormalStrokeWidth
	 */
	public float getWireNormalStrokeWidth() {
		return wireNormalStrokeWidth;
	}

	/**
	 * @return the selectedLineStroke
	 */
	public float getSelectedLineStroke() {
		return selectedLineStroke;
	}

	/**
	 * @return the normalStroke
	 */
	public float getNormalStroke() {
		return normalStroke;
	}

	/**
	 * @return the leftSelectedColor
	 */
	public Color getLeftSelectedColor() {
		return leftSelectedColor;
	}

	/**
	 * @return the rightSelectedColor
	 */
	public Color getRightSelectedColor() {
		return rightSelectedColor;
	}

	/**
	 * @return the normalColor
	 */
	public Color getNormalColor() {
		return normalColor;
	}

	/**
	 * @return the drawUsedOnlyMain
	 */
	public boolean isDrawUsedOnlyMain() {
		return unusedColorMain != null;
	}

	/**
	 * @param unusedColor color of unused gates in main window, null - draw all gates
	 */
	public void setUsedColorMain(Color unusedColor) {
		this.unusedColorMain = unusedColor;
	}

	public Color getUsedColorMain() {
		return unusedColorMain;
	}

	public Color getUsedColorSelected() {
		return unusedColorSelected;
	}

	private void createColorPalette() {
		this.colorPalette = new Color[getColorPaletteSize()];
		int cut = (int) (getColorPaletteSize() / 6.6); //to cut end of pallet which is almost identical to begining
		for (int i = 0; i < getColorPaletteSize(); i++) {
			colorPalette[i] = Color.getHSBColor((float) i / (float) (getColorPaletteSize() + cut), 0.85f, 1.0f);
		}
	}

	/**
	 * @return the colorPalette
	 */
	public Color[] getColorPalette() {
		return colorPalette;
	}

	/**
	 * @return the colorPaletteSize
	 */
	public int getColorPaletteSize() {
		return colorPaletteSize;
	}

	/**
	 * @param currentHistory history count
	 * @param maxHistory (maxHistory >= history)
	 * @return color based on history and max history
	 */
	public Color getHistoryColor(int currentHistory, int maxHistory) {
		int paleteIndex = 0;

		if (currentHistory > 0)
			paleteIndex = (int) Math.ceil(((double) (colorPaletteSize - 1) * ((double) currentHistory) / (double) maxHistory));

		//System.out.println(paleteIndex);
		if (paleteIndex > 19)
			System.out.println("SHIIIIT");

		return colorPalette[paleteIndex];
	}

	public Point getDrawingStart() {
		return drawingStart;
	}

   /**
    * @return the drawColorsFF
    */
   public boolean isDrawColorsFF() {
      return drawColorsFF;
   }

   /**
    * @param drawColorsFF the drawColorsFF to set
    */
   public void setDrawColorsFF(boolean drawColorsFF) {
      this.drawColorsFF = drawColorsFF;
   }

}
