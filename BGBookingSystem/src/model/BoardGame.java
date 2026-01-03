package model;

import java.util.Objects;

public class BoardGame {
	private String name;
	private Level level;
	private int noOfPlayers;
	private Category category;
	private int durationInMinutes;
	private int boardGameId;
	private String description;
	
	/**
	 * Constructor for BoardGame using all attributes
	 * @param boardGameId
	 * @param name
	 * @param level
	 * @param noOfPlayers
	 * @param category
	 * @param durationInMinutes
	 * @param description
	 */

	public BoardGame(int boardGameId, String name, Level level, int noOfPlayers, Category category,
			int durationInMinutes, String description) {
		this.boardGameId = boardGameId;
		this.name = name;
		this.level = level;
		this.noOfPlayers = noOfPlayers;
		this.category = category;
		this.durationInMinutes = durationInMinutes;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Level getLevel() {
		return level;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	public int getNoOfPlayers() {
		return noOfPlayers;
	}

	public void setNoOfPlayers(int noOfPlayers) {
		this.noOfPlayers = noOfPlayers;
	}

	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}

	public int getDurationInMinutes() {
		return durationInMinutes;
	}

	public void setDurationInMinutes(int durationInMinutes) {
		this.durationInMinutes = durationInMinutes;
	}

	public int getBoardGameId() {
		return boardGameId;
	}

	public void setBoardGameId(int boardGameId) {
		this.boardGameId = boardGameId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * 
	 */
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || getClass() != o.getClass()) return false;
	    BoardGame boardGame = (BoardGame) o;
	    return boardGameId == boardGame.boardGameId;
	}

	@Override
	public int hashCode() {
	    return Objects.hash(boardGameId);
	}

}
