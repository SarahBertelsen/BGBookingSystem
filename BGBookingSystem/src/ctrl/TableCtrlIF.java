package ctrl;

import java.time.LocalDateTime;
import java.util.List;
import model.Table;

public interface TableCtrlIF {

	public Table findTableByCriteria(LocalDateTime date, int noOfGuests);
}
