/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.chart;

import cgpanalyser.GraphDataSampler;
import cgpanalyser.gui.MainPanel;
import java.awt.event.MouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.ChartProgressEvent;
import org.jfree.chart.event.ChartProgressListener;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class XYChartPanel extends ChartPanel implements ChartProgressListener {

   private MainPanel panelMain;
   private GraphDataSampler dataSampler;
   private int domainCrosshairValue;
   private XYMouseMarker mouseMarker;
   //private int domainCrosshairIndex;
   //private int markerStart;
   //private int markerEnd;

   /**
    * Contructor only for Gui builder!
    */
   public XYChartPanel() {
      super(null);
   }

   public XYChartPanel(JFreeChart jFreeChart, MainPanel panelMain) {
      super(jFreeChart);
      this.panelMain = panelMain;
      this.setPropertiesAndListeners();
   }

   @Override
   public void setChart(JFreeChart chart) {
      super.setChart(chart);
      domainCrosshairValue = Integer.MIN_VALUE;
      if (chart != null) //because of gui builder
         setPropertiesAndListeners();
   }

   private void setPropertiesAndListeners() {
      setPopupMenu(null); //no popUp menu on right mouse click
      setRangeZoomable(false);
      setDomainZoomable(false);

      mouseMarker = new XYMouseMarker(this);
      removeOldXYMouseMarkerListeners(mouseMarker);
      addMouseListener(mouseMarker);
      addMouseMotionListener(mouseMarker);
   }

   private void removeOldXYMouseMarkerListeners(XYMouseMarker mouseMarker) {
      MouseListener[] mouseListeners = this.getMouseListeners();
      for (MouseListener currentMouseListener : mouseListeners) {
         if (currentMouseListener.getClass() == mouseMarker.getClass())
            removeMouseListener(currentMouseListener);
      }
   }

   @Override
   public void chartProgress(ChartProgressEvent event) {
      if (event.getType() != ChartProgressEvent.DRAWING_FINISHED)
         return;

      JFreeChart jfreechart = this.getChart();
      if (jfreechart != null) {
         int currentDomainCrosshairValue = getDomainCrosshairValue(jfreechart);
         if (domainCrosshairValue != currentDomainCrosshairValue) {
            domainCrosshairValue = currentDomainCrosshairValue;
            System.out.println("domainCrosshairValue:" + currentDomainCrosshairValue);
            panelMain.drawChromosomesOnCrosshairEvent(domainCrosshairValue);
         }
      }
   }

   private int getDomainCrosshairValue(JFreeChart jfreechart) {
      XYPlot xyplot = (XYPlot) jfreechart.getPlot();
      return (int) xyplot.getDomainCrosshairValue();
   }

   public void setNextCrosshairValue(JFreeChart jfreechart) {
      XYPlot plot = (XYPlot) jfreechart.getPlot();
      XYDataset dataset = plot.getDataset();

      //set crosshair to next value if not on last already
      for (int i = 0; i < dataSampler.getLastToDisplay().size() - 1; i++) { //find generation in datasampler.todisplay
         if ((int) plot.getDomainCrosshairValue() == dataSampler.getLastToDisplay().get(i).getGenNumber()) {
            plot.setDomainCrosshairValue(dataset.getXValue(0, i + 1));
            plot.setRangeCrosshairValue(dataset.getYValue(0, i + 1));
            break;
         }
      }

   }

   public void setPrevCrosshairValue(JFreeChart jfreechart) {
      XYPlot plot = (XYPlot) jfreechart.getPlot();
      XYDataset dataset = plot.getDataset();

      //set crosshair to previous value if not on first already
      for (int i = 0; i < dataSampler.getLastToDisplay().size(); i++) { //find generation in datasampler.todisplay
         if ((int) plot.getDomainCrosshairValue() == dataSampler.getLastToDisplay().get(i).getGenNumber()) {
            if (i > 0) {
               plot.setDomainCrosshairValue(dataset.getXValue(0, i - 1));
               plot.setRangeCrosshairValue(dataset.getYValue(0, i - 1));
            }
            break;
         }
      }

   }

   /*protected void setMarkerBounds(int markerStart, int markerEnd) {
    this.markerStart = markerStart;
    this.markerEnd = markerEnd;
    }*/
   public void setDataSampler(GraphDataSampler dataSampler) {
      this.dataSampler = dataSampler;
   }
   
   /**
    * @return the markerActive
    */
   public boolean isMarkerActive() {
      return mouseMarker.isMarkerActive();
   }
   
   /**
    * @return the markerStart rounded up
    */
   public int getMarkerStart() {
      return (int) Math.ceil(mouseMarker.getMarkerStart());
   }

   /**
    * @return the markerEnd rouded down
    */
   public int getMarkerEnd() {
      return mouseMarker.getMarkerEnd().intValue();
   }

}
