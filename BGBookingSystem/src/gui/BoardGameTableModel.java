package gui;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import model.BoardGame;
import model.BoardGameCopy;

public class BoardGameTableModel extends AbstractTableModel {
	private List<BoardGame> games;
	private static final String[] COL_NAMES = {"Name", "Players", "Level", "Category", "Duration"};
	
	public BoardGameTableModel(List<BoardGame> games) {
		this.games = games;
		if(this.games == null) {
			this.games = new ArrayList<>();
		}
	}
	@Override
	public int getRowCount() {
		return games.size();
	}

	@Override
	public int getColumnCount() {
		return COL_NAMES.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		BoardGame game = games.get(rowIndex);
		String res = "";
		switch(columnIndex) {
		case 0: res = game.getName(); 
		break;
		
		case 1: res = "" + game.getNoOfPlayers();
		break;
		
		case 2: res = game.getLevel().toString();
		break;
		
		case 3: res = game.getCategory().toString();
		break;
		
		case 4: res = "" + game.getDurationInMinutes();
		break;
		
		default: res = "<UNKNOWN " + columnIndex + ">";
		}
		return res;
	}
	
	@Override
	public String getColumnName(int col) {
		return COL_NAMES[col];
	}
	
	public BoardGame getDataAt(int rowIndex) {
		return games.get(rowIndex);
	}

	/**
	 * Notify the listener mechanism in AbstractTableModel and trigger the boardGameCopyTable to 
	 * re-read the structure of the table.
	 * @param bgCopies
	 */
	public void setData(List<BoardGame> games) {
		this.games = games;
		super.fireTableDataChanged();
	}
}
