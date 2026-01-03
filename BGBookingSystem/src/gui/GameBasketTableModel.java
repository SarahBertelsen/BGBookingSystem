package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.BoardGameCopy;

public class GameBasketTableModel extends AbstractTableModel {
	private List<BoardGameCopy> reservedCopies;
	private static final String[] COL_NAMES = {"Reserveret Spil"};
	
	public GameBasketTableModel(List<BoardGameCopy> reservedCopies) {
		this.reservedCopies = reservedCopies;
		if(this.reservedCopies == null) {
			this.reservedCopies = new ArrayList<>();
		}
	}
	@Override
	public int getRowCount() {
		return reservedCopies.size();
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BoardGameCopy reservedCopy = reservedCopies.get(rowIndex);
		String res = "";
		switch(columnIndex) {
		case 0: res = reservedCopy.getBoardGame().getName(); 
		break;
		default: res = "<UNKNOWN " + columnIndex + ">";
		}
		return res;
	}
	
	@Override
	public String getColumnName(int col) {
		return COL_NAMES[col];
	}
	
	public BoardGameCopy getDataAt(int rowIndex) {
		return reservedCopies.get(rowIndex);
	}

	/**
	 * Notify the listener mechanism in AbstractTableModel and trigger the boardGameCopyTable to 
	 * re-read the structure of the table.
	 * @param bgCopies
	 */
	public void setData(List<BoardGameCopy> reservedCopies) {
		this.reservedCopies = reservedCopies;
		super.fireTableDataChanged();
	}
}