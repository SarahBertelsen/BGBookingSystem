DROP TABLE BoardGameBooking;
				DROP TABLE ShelfBoardGameCopy;
				DROP TABLE BoardGameCopyReservation;
				DROP TABLE BoardGameCopy;
				DROP TABLE Booking;
				DROP TABLE Membership;
				DROP TABLE Customer;
				DROP TABLE [Table];
				DROP TABLE Shelf;
				DROP TABLE BoardGame;

CREATE TABLE BoardGame (
			boardGameId int IDENTITY (1000, 1)PRIMARY KEY NOT NULL,
			name varchar(50) NOT NULL,
			[level] int NOT NULL,
			noOfPlayers int NOT NULL,
			category varchar (100) NOT NULL,
			duration int NOT NULL,
			[description] varchar (1000) NOT NULL
			)
			CREATE TABLE Shelf(
			shelfNo int IDENTITY (1000, 1) PRIMARY KEY NOT NULL
			)
			CREATE TABLE [Table] (
			tableNo int IDENTITY (1000, 1) PRIMARY KEY NOT NULL,
			noOfSeats int NOT NULL
			)
			CREATE TABLE Customer (
			customerId int IDENTITY (1000, 1) PRIMARY KEY NOT NULL,
			fName varchar (50) NOT NULL,
			lName varchar (50) NOT NULL,
			phone varchar (11) NOT NULL
			)
			CREATE Table Membership (
			membershipId int IDENTITY (100, 1) PRIMARY KEY NOT NULL,
			customerId int NOT NULL REFERENCES [Customer] (customerId),
			startDate DATE,
			endDate DATE
			)
			CREATE TABLE Booking (
			bookingId int IDENTITY (1000, 1) PRIMARY KEY NOT NULL,
			customerId int NOT NULL REFERENCES [Customer] (customerId),
			noOfGuests int NOT NULL,
			[date] DATETIME2 NOT NULL,
			tableNo int NOT NULL REFERENCES [Table] (tableNo)
			)
			CREATE TABLE BoardGameCopy(
			boardGameCopyId int IDENTITY (1000, 1) PRIMARY KEY NOT NULL,
			boardGameId int NOT NULL REFERENCES [BoardGame] (boardGameId)
			)
			CREATE TABLE BoardGameCopyReservation (
			boardGameCopyId int NOT NULL REFERENCES [BoardGameCopy] (boardGameCopyID) ON DELETE CASCADE,
			[date] DATETIME2 NOT NULL,
			)
			CREATE TABLE ShelfBoardGameCopy (
			shelfNo int NOT NULL REFERENCES [Shelf] (shelfNo) ON DELETE CASCADE,
			boardGameCopyId int NOT NULL REFERENCES [BoardGameCopy] (boardGameCopyId) ON DELETE CASCADE
			)
			CREATE TABLE BoardGameBooking (
			bookingId int NOT NULL REFERENCES [Booking] (bookingId) ON DELETE CASCADE,
			boardGameCopyId int NOT NULL REFERENCES [BoardGameCopy] (boardGameCopyId) ON DELETE CASCADE
			)
			
			
INSERT INTO [Table] VALUES (6)
INSERT INTO [Table] VALUES (4)
INSERT INTO [Table] VALUES (4)
INSERT INTO [Table] VALUES (4)
INSERT INTO [Table] VALUES (6)
INSERT INTO [Table] VALUES (6)
INSERT INTO [Table] VALUES (8)
INSERT INTO [Table] VALUES (8)
INSERT INTO [Table] VALUES (8)
INSERT INTO [Table] VALUES (8)
INSERT INTO [Table] VALUES (2)
INSERT INTO [Table] VALUES (2)
INSERT INTO [Table] VALUES (2)
INSERT INTO [Table] VALUES (10)
INSERT INTO [Table] VALUES (10)
INSERT INTO BoardGame VALUES ('Horse Race', 0, 6, 'FAMILY', 30, 'Et hurtigt hyggeligt spil for hele familien! Få fat i den hurtigste hest og være den første over målstregen.')
INSERT INTO BoardGame VALUES ('Avalon', 1, 10, 'SOCIAL_DEDUCTION', 45, 'Hvem skal med ud og udforske? I dette spil kan enhver være din modstander, hvem stoler du på, hvem stoler du ikke på?')
INSERT INTO BoardGame VALUES ('Stratego', 2, 2, 'STRATEGY', 20, 'Opsæt din bræt for at snyde din modstander. Spillet Stratego giver dig muligheden for at udnytte uendelige mængde strategier.')
INSERT INTO BoardGame VALUES ('Seven Wonders', 1, 5, 'STRATEGY', 45, 'Opbyg din imperium! Fokuserer du på krig, videnskab eller monumenter? Slå dine modstandere med taktisk udnyttelse af ressourcer.')
INSERT INTO BoardGame VALUES ('Bang', 1, 7, 'SOCIAL_DEDUCTION', 15, 'Bang! Bang! Siger det når du viser din skytte evner til dine medspillere, så er spørgsmålet bare, om du skød din ven eller din modstander.')
INSERT INTO BoardGame VALUES ('Skak', 1, 2, 'STRATEGY', 15, 'Test dine evner med det klassiske 1500 år gamle spil Skak!')
INSERT INTO BoardGame VALUES ('BezzerWizzer', 0, 4, 'FAMILY', 60, 'Har du quiz hatten på? Spillet BezzerWizzer tester din generelle forståelse indenfor forskellige evner. Mangler du viden om et emne? Så tag en makker med på holdet.')
INSERT INTO BoardGame VALUES ('Partners', 0, 4, 'SOCIAL', 45, 'Har du styr på, hvad din partner tænker? Spillet Partners udnytter strategi og god forståelse for hvad din partner tænker.')
INSERT INTO Shelf DEFAULT VALUES
INSERT INTO Shelf DEFAULT VALUES
INSERT INTO Shelf DEFAULT VALUES
INSERT INTO Shelf DEFAULT VALUES
INSERT INTO Shelf DEFAULT VALUES
INSERT INTO Customer VALUES ('John', 'Olsen','+4512131415')
INSERT INTO Customer VALUES ('Pia', 'Poulsen','+4598877665')
INSERT INTO Customer VALUES ('Ole', 'Olesen','+4577668899')
INSERT INTO Customer VALUES ('Mads', 'Kjærsgaard','+4545781232')
INSERT INTO Customer VALUES ('Ronald', 'Barbaren','+4554687981')
INSERT INTO Customer VALUES ('Kims', 'Chips','+4512765592')
INSERT INTO Membership VALUES (1000, '2026-12-14','2026-1-14')
INSERT INTO Membership VALUES (1002, '2026-12-29','2026-1-29')
INSERT INTO Membership VALUES (1004, '2026-12-24','2026-6-24')
INSERT INTO Booking VALUES (1000, 6, '2026-12-26 18:00:00', 1000)
INSERT INTO Booking VALUES (1002, 4, '2026-01-20 16:00:00', 1002)
INSERT INTO Booking VALUES (1000, 8, '2026-12-28 16:45:00', 1006)
INSERT INTO BoardGameCopy VALUES (1000)
INSERT INTO BoardGameCopy VALUES (1000)
INSERT INTO BoardGameCopy VALUES (1001)
INSERT INTO BoardGameCopy VALUES (1001)
INSERT INTO BoardGameCopy VALUES (1002)
INSERT INTO BoardGameCopy VALUES (1003)
INSERT INTO BoardGameCopy VALUES (1003)
INSERT INTO BoardGameCopy VALUES (1003)
INSERT INTO BoardGameCopy VALUES (1004)
INSERT INTO BoardGameCopy VALUES (1004)
INSERT INTO BoardGameCopy VALUES (1005)
INSERT INTO BoardGameCopy VALUES (1006)
INSERT INTO BoardGameCopy VALUES (1006)
INSERT INTO BoardGameCopy VALUES (1006)
INSERT INTO BoardGameCopy VALUES (1006)
INSERT INTO BoardGameCopy VALUES (1006)
INSERT INTO BoardGameCopyReservation VALUES (1000, '2026-12-26 16:45:00')
INSERT INTO BoardGameCopyReservation VALUES (1007, '2026-1-20 16:00:00')
INSERT INTO BoardGameCopyReservation VALUES (1001, '2026-12-28 16:45:00')
INSERT INTO BoardGameCopyReservation VALUES (1010, '2026-01-20 15:00:00')
INSERT INTO ShelfBoardGameCopy VALUES (1000, 1000)
INSERT INTO ShelfBoardGameCopy VALUES (1000, 1000)
INSERT INTO ShelfBoardGameCopy VALUES (1000, 1001)
INSERT INTO ShelfBoardGameCopy VALUES (1000, 1001)
INSERT INTO ShelfBoardGameCopy VALUES (1001, 1002)
INSERT INTO ShelfBoardGameCopy VALUES (1002, 1003)
INSERT INTO ShelfBoardGameCopy VALUES (1002, 1003)
INSERT INTO ShelfBoardGameCopy VALUES (1002, 1003)
INSERT INTO ShelfBoardGameCopy VALUES (1003, 1004)
INSERT INTO ShelfBoardGameCopy VALUES (1003, 1004)
INSERT INTO ShelfBoardGameCopy VALUES (1003, 1005)
INSERT INTO ShelfBoardGameCopy VALUES (1004, 1006)
INSERT INTO ShelfBoardGameCopy VALUES (1004, 1006)
INSERT INTO ShelfBoardGameCopy VALUES (1004, 1007)
INSERT INTO ShelfBoardGameCopy VALUES (1004, 1007)
INSERT INTO ShelfBoardGameCopy VALUES (1004, 1007)
INSERT INTO BoardGameBooking VALUES (1000, 1000)
INSERT INTO BoardGameBooking VALUES (1001, 1007)
INSERT INTO BoardGameBooking VALUES (1002, 1001)