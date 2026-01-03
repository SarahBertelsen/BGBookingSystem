package db;


import java.sql.ResultSet;

import model.Table;

public interface TableDAO {
	
	public Table findTableByNo(int tableNo);
	
	public Table buildObject(ResultSet rs);
	
	

}
