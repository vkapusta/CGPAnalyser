/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cgpanalyser.truthtable;

import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Table model of truth table.
 *
 * @author Vlastimil Kapusta, xkapus02@stud.fit.vutbr.cz
 */
public class ListTableModel extends AbstractTableModel {

	private final List<String> header;
	private final List<List<String>> data;

	public ListTableModel(List<String> header, List<List<String>> truthTable2DList) {
		this.header = header;
		this.data = truthTable2DList;
	}

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	public int getColumnCount() {
		return header.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return data.get(rowIndex).get(columnIndex);
	}

	@Override
	public String getColumnName(int col) {
		return header.get(col);
	}

}
