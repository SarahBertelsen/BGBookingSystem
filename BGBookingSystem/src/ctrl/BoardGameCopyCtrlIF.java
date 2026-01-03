package ctrl;

import java.time.LocalDateTime;
import java.util.List;

import model.BoardGameCopy;
import model.Category;
import model.Level;

public interface BoardGameCopyCtrlIF {
	
	public List<BoardGameCopy> findAvailableBoardGameCopies(LocalDateTime date, String name, int noOfPlayers,
			Level level, Category category, int duration);
	
	public void addReservation(BoardGameCopy bgCopy, LocalDateTime date);
	
	public void removeReservation(BoardGameCopy bgCopy, LocalDateTime date);
	
	public void addBoardGameCopy(BoardGameCopy bgCopy);

}
