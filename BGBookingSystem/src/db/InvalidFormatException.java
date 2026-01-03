package db;

public class InvalidFormatException extends RuntimeException{
	public static final String INVALID_NUMBER = "Telefonnummeret er ikke gyldigt. Det skal starte med +45 og have 8 cifre efter.";
	public static final String INVALID_NAME = "Navnet er ikke gyldigt. Første bogstav skal være stort, og der må ikke være tal.";
	
	public static final String INVALID_DATE_TIME = "Datoen er ikke gyldig. Den skal have formatet ÅR-MÅNED-DAG TIME:MINUT og kan ikke være i fortiden. \n Der kan kun bookes man-tors mellem 11:00 og 22:00 samt mellem 11:00 og 00:00 fre-lør. \n Eksempel: 2025-12-08 \n 18:00";
	public static final String INVALID_DATE = "Datoen er ikke gyldig. Den skal have formatet ÅR-MÅNED-DAG TIME:MINUT og kan ikke være i fortiden. \n Eksempel: 2025-12-08 \n 18:00";
	public static final String INVALID_TIME = "Tidspunktet er ikke gyldigt. Der kan kun bookes man-tors mellem 11:00 og 19:00 samt mellem 11:00 og 21:00 fre-lør.";
	
	public static final String INVALID_NO_OF_GAMES = "Du kan maksimalt tilføje 3 brætspil til bookingen.";
	public static final String INVALID_GAMES = "Der blev ikke fundet nogle matchende brætspil.";
	
	public static final String NO_AVAILABLE_TABLES = "Der blev ikke fundet nogle ledige borde på denne dato.";
	
	public static final String UNEXPECTED_BOOKING_ISSUE = "Der var et uventet problem med at oprette din booking... Prøv igen.";
	public static final String ADD_CUSTOMER_ISSUE = "Kundeinformationerne kunne ikke tilføjes til din booking... Prøv igen.";
	
	public InvalidFormatException(String message) {
		super(message);
	}
	
	public InvalidFormatException(String message, Throwable clause) {
		super(message, clause);
	}

}
