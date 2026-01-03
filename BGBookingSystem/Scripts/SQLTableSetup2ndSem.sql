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
[date] DATE NOT NULL,
[time] TIME NOT NULL,
tableNo int NOT NULL REFERENCES [Table] (tableNo)
)
CREATE TABLE BoardGameCopy(
boardGameCopyId int IDENTITY (1000, 1) PRIMARY KEY NOT NULL,
boardGameId int NOT NULL REFERENCES [BoardGame] (boardGameId)
)
CREATE TABLE BoardGameCopyReservation (
boardGameCopyId int NOT NULL REFERENCES [BoardGameCopy] (boardGameCopyID) ON DELETE CASCADE,
[date] DATE NOT NULL,
[time] TIME NOT NULL
)
CREATE TABLE ShelfBoardGameCopy (
shelfNo int NOT NULL REFERENCES [Shelf] (shelfNo) ON DELETE CASCADE,
boardGameCopyId int NOT NULL REFERENCES [BoardGameCopy] (boardGameCopyId) ON DELETE CASCADE
)
CREATE TABLE BoardGameBooking (
bookingId int NOT NULL REFERENCES [Booking] (bookingId) ON DELETE CASCADE,
boardGameCopyId int NOT NULL REFERENCES [BoardGameCopy] (boardGameCopyId) ON DELETE CASCADE
)