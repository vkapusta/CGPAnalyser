/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.gui.chart;

import cgpanalyser.Generation;
import cgpanalyser.GraphDataSampler;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class ChartCreator {

	public JFreeChart create(GraphDataSampler gDataSampler) {
		return createChart(createDataset(gDataSampler));
	}

	private XYDataset createDataset(GraphDataSampler gDataSampler) {
		if (gDataSampler == null)
			return null;

		XYSeries series1 = new XYSeries("Generation, Fitness");

		for (Generation generation : gDataSampler.getLastToDisplay()) { 
			series1.add(generation.getGenNumber(), generation.getBestChromosome().getFitness());
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(series1);
		return dataset;
	}

	private JFreeChart createChart(XYDataset dataset) {
		JFreeChart chart = ChartFactory.createXYLineChart(null, "Generation", "Fitness", dataset, PlotOrientation.VERTICAL, false, true, false);

		if (dataset != null) {
			XYPlot plot = (XYPlot) chart.getPlot();
			setCrosshairProperties(plot, true);
			setCrosshairDefaultValue(plot, dataset);
			setBothAxisProperties(plot, dataset);
			setShapes(plot);
			//setRenderer(plot, dataset);
		}

		return chart;
	}

	private void setCrosshairProperties(XYPlot plot, boolean visible) {
		plot.setDomainCrosshairVisible(visible);
		plot.setDomainCrosshairLockedOnData(true);
		plot.setRangeCrosshairVisible(visible);
		plot.setRangeCrosshairLockedOnData(true);
	}

	/**
	 * Sets value of crosshair to first item in chart.
	 *
	 * @param jfreechart
	 */
	private void setCrosshairDefaultValue(XYPlot plot, XYDataset dataset) {
		if (dataset.getItemCount(0) != 0) {
			plot.setDomainCrosshairValue(dataset.getXValue(0, 0));
			plot.setRangeCrosshairValue(dataset.getYValue(0, 0));
		}
	}

	private void setBothAxisProperties(XYPlot plot, XYDataset dataset) {
		NumberAxis xAxis = (NumberAxis) plot.getDomainAxis(); //set x axis tick
		xAxis.setVerticalTickLabels(true); //set x axis text rotation
		//xAxis.setTickUnit(new NumberTickUnit(100));
		//xAxis.setLowerBound(1); 
		//xAxis.setUpperBound(1000);

		NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
		//yAxis.setTickUnit(new NumberTickUnit(100));
	}

	private static void setShapes(XYPlot plot) {
		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setBaseShapesVisible(true);
	}

	/*public static void setRenderer(XYPlot plot, XYDataset dataset) {
		XYLineAndShapeRenderer r1 = (XYLineAndShapeRenderer) plot.getRenderer();
		Shape shape = r1.getBaseShape();
		
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer() {
			@Override
			public Paint getItemPaint(int row, int col) {
				System.out.println(col + "," + dataset.getY(row, col));
				double y = dataset.getYValue(row, col);
				if (y <= 200)
					return Color.GREEN;
				else
					return Color.RED;

				//return super.getItemPaint(row, col);
			}
		};
		
		plot.setRenderer(renderer);
		
		plot.getRenderer().setBaseShape(shape);
	}*/

}
