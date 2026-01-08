package gui;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.SwingUtilities;

import db.DatabaseException;
import model.BoardGameCopy;

public class AvailableBoardGameRunnable implements Runnable {
	FindAvailableBoardGameFrame frame;
	private List<BoardGameCopy> buffer = null;
	private final Object lock = new Object();

	private boolean running = true;
	private boolean paused = true;

	public AvailableBoardGameRunnable(FindAvailableBoardGameFrame frame) {
		this.frame = frame;
	}

	@Override
	public void run() {
		while (running) {
			try {
				synchronized (lock) {
					while (paused) {
						lock.wait();
					}
				}
				tryBoardGameUpdate();
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
//		try {
//		while(!Thread.currentThread().isInterrupted()) {
//				produce(); //Producer updates the buffer
//				List<BoardGameCopy> data = consume();
//				
//				SwingUtilities.invokeLater(() -> {
//					frame.setBgCopies(data);
//					frame.updateGameList();
//				});
//
//					Thread.sleep(2000);
//				}			
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//			}

	}

	public void pause() {
		synchronized (lock) {
			paused = true;
		}
	}

	public void resume() {
		synchronized (lock) {
			paused = false;
			lock.notifyAll();
		}
	}

//	/**
//	 * Producer method. Collects new data from the database, compares the new list
//	 * of board games to the current list. We use a lock object when the buffer is
//	 * updated.
//	 */
//	public synchronized void produce() {
//		List<BoardGameCopy> newCopies = frame.findBoardGameCopies();
//			
//		//Update buffer and wake consumer if data has changed
//		synchronized(lock) {
//			while(!hasSameCopies(buffer, newCopies)){
//				buffer = newCopies;
//				notifyAll();
//			}
//		}
//	}
//	
//	/**
//	 * Consumer method. Wait for the buffer and returns new data.
//	 * @return List<BoardGameCopy> the updated list of board game copies.
//	 * @throws InterruptedException
//	 */
//	public synchronized List<BoardGameCopy> consume() throws InterruptedException {
//		synchronized (lock) {
//			while (buffer == null) {
//				wait(); //Wait until producer has updated the buffer
//			}
//			List<BoardGameCopy> result = buffer;
//			buffer = null; //Empty the buffer
//			notifyAll();
//			return result;
//		}
//	}

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
			Set<Integer> setA = a.stream().map(BoardGameCopy::getCopyId).collect(Collectors.toSet());

			Set<Integer> setB = b.stream().map(BoardGameCopy::getCopyId).collect(Collectors.toSet());
			success = setA.equals(setB);
		}

		return success;
	}

// GAMLE METODER:
//	
//	public void oldRun() {
//		while (true) {
//			tryBoardGameUpdateOldVersion();
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				Thread.currentThread().interrupt();
//				break;
//			}
//		}
//	}
//	
//	/**
//	 * Checks if the list of available boardgames has changed, and updates only if they have.
//	 * 
//	 */
	public void tryBoardGameUpdate() {
		if (frame.isVisible()) {
			try {
				List<BoardGameCopy> previousBGCopies = frame.getBgCopies();
				List<BoardGameCopy> bgCopies = frame.findBoardGameCopies();
				if (!hasSameCopies(previousBGCopies, bgCopies)) {
					frame.setBgCopies(bgCopies);
					frame.updateGameList();
				}
			} catch (DatabaseException e) {
				e.printStackTrace();
			}

		}
	}

}
