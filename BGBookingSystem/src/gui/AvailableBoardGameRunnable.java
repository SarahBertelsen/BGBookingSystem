package gui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import db.DatabaseException;
import model.BoardGameCopy;

public class AvailableBoardGameRunnable implements Runnable {
	FindAvailableBoardGameFrame frame;

	public AvailableBoardGameRunnable(FindAvailableBoardGameFrame frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		while (true) {
			tryBoardGameUpdate();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				break;
			}
		}
	}
	
	/**
	 * Checks if the list of available boardgames has changed, and updates only if they have.
	 * 
	 */
	public void tryBoardGameUpdate() {
		if (frame.isVisible()) {
			try {
				List<BoardGameCopy> previousBGCopies = frame.getBgCopies();
				List<BoardGameCopy> bgCopies = frame.findBoardGameCopies();
				if (!hasSameCopies(previousBGCopies, bgCopies)) {
					frame.setBgCopies(bgCopies);
					frame.updateGameList();
				}
			} catch(DatabaseException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	/**
	 * 
	 * 
	 * @param a A list of board game copies
	 * @param b Another list of board game copies to compare
	 * @return Returns true if the lists contains the same boardgames
	 */
	public boolean hasSameCopies(List<BoardGameCopy> a, List<BoardGameCopy> b) {
	    boolean success = true;
		if (a.size() != b.size()) {
			success = false;
		}
		
		if (success) {
			Set<Integer> setA = a.stream()
		            .map(BoardGameCopy::getCopyId)
		            .collect(Collectors.toSet());

		    Set<Integer> setB = b.stream()
		            .map(BoardGameCopy::getCopyId)
		            .collect(Collectors.toSet());
		    success = setA.equals(setB);
		}

	    return success;
	}

}
