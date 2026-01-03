package model;

/**
 * Enums 
 */

public enum Category {
	NONE,
	FAMILY,
	STRATEGY,
	SOCIAL,
	SOCIAL_DEDUCTION;
	
	/**
	 * toString used for converting enums to Strings for GUI
	 */
	@Override
	public String toString() {
		return switch (this) {
		case NONE -> "Ingen";
		case FAMILY -> "Familie Spil";
        case STRATEGY -> "Strategi Spil";
        case SOCIAL -> "Socialt Spil";
        case SOCIAL_DEDUCTION -> "Social Deduction";
		};
	}
	
	/**
	 * toSqlString used for converting enums to Strings for SQL
	 * @return
	 */
	
	public String toSqlString() {
		return switch (this) {
		case NONE -> "NONE";
		case FAMILY -> "FAMILY";
        case STRATEGY -> "STRATEGY";
        case SOCIAL -> "SOCIAL";
        case SOCIAL_DEDUCTION -> "SOCIAL_DEDUCTION";
		};
	}
}
