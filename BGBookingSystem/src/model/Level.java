package model;

/**
 * Enum setup for Level
 */
public enum Level {
	NONE(-1),
	BEGINNER(0),
	INTERMEDIATE(1),
	EXPERT(2);
	
	private final int value;
	
	private Level(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	/**
	 * toString used to convert enums to Strings
	 */
	@Override
	public String toString() {
		return switch (this) {
		case NONE -> "Ingen";
		case BEGINNER -> "Begynder";
        case INTERMEDIATE -> "Mellem";
        case EXPERT -> "Ekspert";
		};
	}
	
	public static Level fromInt(int value) {
		for (Level l : values()) {
			if (l.value == value) {
				return l;
			}
		}
		return null;
	}
}
