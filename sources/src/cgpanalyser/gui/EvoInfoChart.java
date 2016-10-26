/* ===========================================================
 * JFreeChart : a free chart library for the Java(tm) platform
 * ===========================================================
 *
 * (C) Copyright 2000-2004, by Object Refinery Limited and Contributors.
 *
 * Project Info:  http://www.jfree.org/jfreechart/index.html
 *
 * This library is free software; you can redistribute it and/or modify it under the terms
 * of the GNU Lesser General Public License as published by the Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * ------------------
 * BarChartDemo3.java
 * ------------------
 * (C) Copyright 2002-2004, by Object Refinery Limited and Contributors.
 *
 * Original Author:  David Gilbert (for Object Refinery Limited);
 * Contributor(s):   -;
 *
 * $Id: BarChartDemo3.java,v 1.15 2004/05/10 16:45:24 mungady Exp $
 *
 * Changes
 * -------
 * 22-Aug-2002 : Version 1 (DG);
 * 28-Jul-2003 : Updated custom renderer for API changes in 0.9.10 (DG);
 * 11-Nov-2003 : Renamed BarChartDemo3.java (DG);
 *
 */
//used BarChartDemo3.java as a template
package cgpanalyser.gui;

import cgpanalyser.truthtable.GateFuctionsAll;
import java.awt.Paint;
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

/**
 *
 */
public class EvoInfoChart extends JFrame {

   /**
    * Creates a new demo.
    *
    * @param title the frame title.
    */
   public EvoInfoChart(final String title, long[] gateFuncsCounts, GateFuctionsAll gateFuncsAll) {
      super(title);
      final CategoryDataset dataset = createDataset(gateFuncsCounts, gateFuncsAll);
      final JFreeChart chart = createChart(dataset, gateFuncsCounts, gateFuncsAll);
      final ChartPanel chartPanel = new ChartPanel(chart);
      chartPanel.setPreferredSize(new java.awt.Dimension(700, 400));
      chartPanel.setPopupMenu(null);
      chartPanel.setRangeZoomable(false);
      chartPanel.setDomainZoomable(false);
      setContentPane(chartPanel);
      pack();
      RefineryUtilities.centerFrameOnScreen(this);
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      setVisible(true);
   }

   private CategoryDataset createDataset(long[] gateFuncsCounts, GateFuctionsAll gateFuncsAll) {
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
      final String series1 = "";

      byte index = 0;
      for (long gateFuncCount : gateFuncsCounts) {
         //if (gateFuncCount != 0) {
         //System.out.println(gateFuncCount);
         dataset.addValue(gateFuncCount, series1, "F" + index + " (" + gateFuncsAll.getSymbolIfAvailable(index) + ")");
         //}
         index++;
      }

      return dataset;
   }

   private JFreeChart createChart(final CategoryDataset dataset, long[] gateFuncsCounts, GateFuctionsAll gateFuncsAll) {

      final JFreeChart chart = ChartFactory.createBarChart(null, "Function", "Usage", dataset, PlotOrientation.VERTICAL, false, true, false);
      
      //match colors of bars to colors in function file (or black if not specified)
      Paint[] colors = new Paint[gateFuncsCounts.length];
      for (byte i = 0; i < colors.length; i++) {
         colors[i] = gateFuncsAll.getColorIfAvailable(i);
      }
      final CategoryItemRenderer renderer = new CustomRenderer(colors);

      // get a reference to the plot for further customisation...
      final CategoryPlot plot = chart.getCategoryPlot();
      plot.setRenderer(renderer);
      ((BarRenderer) plot.getRenderer()).setBarPainter(new StandardBarPainter()); //no gradient

      CategoryAxis domainAxis = plot.getDomainAxis();
      domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
      //CategoryAxis axis = plot.getDomainAxis();
      //NumberAxis xAxis = (NumberAxis) plot.getDomainAxis(); //set x axis tick
      //xAxis.setVerticalTickLabels(true); //set x axis text rotation

      return chart;

   }

   /**
    * Starting point for the demonstration application.
    *
    * @param args ignored.
    */
   /*public static void main(final String[] args) {

    final EvoInfoChart demo = new EvoInfoChart("Gate usage");

    }*/
   /**
    * A custom renderer that returns a different color for each item in a single series.
    */
   class CustomRenderer extends BarRenderer {

      /**
       * The colors.
       */
      private final Paint[] colors;

      /**
       * Creates a new renderer.
       *
       * @param colors the colors.
       */
      public CustomRenderer(final Paint[] colors) {
         this.colors = colors;
      }

      /**
       * Returns the paint for an item. Overrides the default behaviour inherited from AbstractSeriesRenderer.
       *
       * @param row the series.
       * @param column the category.
       *
       * @return The item color.
       */
      @Override
      public Paint getItemPaint(final int row, final int column) {
         return this.colors[column % this.colors.length];
      }
   }

}
