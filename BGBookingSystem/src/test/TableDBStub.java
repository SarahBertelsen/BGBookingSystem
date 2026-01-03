package test;

import java.sql.ResultSet;

import db.TableDAO;
import model.Table;

public class TableDBStub implements TableDAO {

	@Override
	public Table findTableByNo(int tableNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Table buildObject(ResultSet rs) {
		// TODO Auto-generated method stub
		return null;
	}

}
