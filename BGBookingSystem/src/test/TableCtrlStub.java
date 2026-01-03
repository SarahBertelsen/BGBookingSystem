package test;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

import ctrl.TableCtrlIF;
import model.Table;

public class TableCtrlStub implements TableCtrlIF{
	private Table tableToReturn;
	
	public void setTableToReturn(Table table) {
		this.tableToReturn = table;
	}
	

	@Override
	public Table findTableByCriteria(LocalDateTime date, int noOfGuests) {
		return tableToReturn;
	}

}
