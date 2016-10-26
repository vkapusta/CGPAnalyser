/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.chart;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import org.jfree.chart.plot.IntervalMarker;
import org.jfree.chart.plot.Marker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.Layer;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public final class XYMouseMarker extends MouseAdapter {

   private Marker marker;
   private Double markerStart = Double.NaN;
   private Double markerEnd = Double.NaN;
   private final XYPlot plot;
   private final XYChartPanel panel;
   private boolean markerActive;
   private final Color markerColor = new Color(0xDD, 0xFF, 0xDD, 0x75);

   public XYMouseMarker(XYChartPanel panel) {
      this.panel = panel;
      this.plot = (XYPlot) panel.getChart().getPlot();
      this.markerActive = false;
      //this.dataset = plot.getDataset();
      //this.chart = panel.getChart();
   }

   @Override
   public void mousePressed(MouseEvent e) {
      markerStart = getXPosition(e);
      //System.out.println("Pressed");
   }

   @Override
   public void mouseReleased(MouseEvent e) {
      markerEnd = getXPosition(e);
      updateMarker();
      //System.out.println("Released");
   }

   @Override
   public void mouseDragged(MouseEvent e) {
      markerEnd = getXPosition(e);
      updateMarker();
   }

   private void updateMarker() {
      if (marker != null) {
         plot.removeDomainMarker(marker, Layer.BACKGROUND);
         markerActive = false;
         //System.out.println("removeDomainMarker, markerStart: " + markerStart + ", markerEnd: " + markerEnd);
      }
      if (!(markerStart.isNaN() || markerEnd.isNaN()))
         if (getMarkerEnd() > getMarkerStart()) {
            marker = new IntervalMarker(getMarkerStart(), getMarkerEnd());
            marker.setPaint(markerColor);
            plot.addDomainMarker(marker, Layer.BACKGROUND);
            markerActive = true;
            //System.out.println("addDomainMarker, markerStart: " + markerStart + ", markerEnd: " + markerEnd);
         }
   }

   private Double getXPosition(MouseEvent e) {
      Rectangle2D plotArea = panel.getScreenDataArea();
      return plot.getDomainAxis().java2DToValue(e.getPoint().getX(), plotArea, plot.getDomainAxisEdge());
   }

   /**
    * @return the markerActive
    */
   public boolean isMarkerActive() {
      return markerActive;
   }

   /**
    * @return the markerStart
    */
   public Double getMarkerStart() {
      return markerStart;
   }

   /**
    * @return the markerEnd
    */
   public Double getMarkerEnd() {
      return markerEnd;
   }

}
