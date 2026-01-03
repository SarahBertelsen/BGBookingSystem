package model;

public class BoardGameCopy {
	private int copyId;
	private BoardGame game;
	
	/**
	 * Constructor for BoardGameCopy
	 * @param copyId
	 * @param game
	 */
	
	public BoardGameCopy(int copyId, BoardGame game) {
		this.copyId = copyId;
		this.game = game;
	}
	
	public BoardGameCopy(BoardGame game) {
		this.game = game;
	}

	public BoardGame getBoardGame() {
		return game;
	}

	public void setBoardGame(BoardGame game) {
		this.game = game;
	}

	public int getCopyId() {
		return copyId;
	}

	public void setCopyId(int copyId) {
		this.copyId = copyId;
	}
	
}
