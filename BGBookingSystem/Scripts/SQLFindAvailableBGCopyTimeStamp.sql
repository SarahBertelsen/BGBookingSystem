DECLARE @startTime DateTime2 = '2025-12-26 18:00:00'

SELECT boardGameCopyId, boardGameId FROM BoardGameCopy
WHERE BoardGameCopyId NOT IN(
SELECT boardGameCopyId FROM Booking
JOIN BoardGameBooking ON Booking.bookingId = BoardGameBooking.bookingId
WHERE DATEADD(HOUR, -3, Booking.date) <= @startTime AND @startTime <= DATEADD(HOUR, 3, Booking.date)
)

AND boardGameCopyId NOT IN(
	SELECT boardGameCopyId FROM BoardGameCopyReservation
WHERE DATEADD(MINUTE, -10, BoardGameCopyReservation.date) <= @startTime AND @startTime <= DATEADD(MINUTE, 10, BoardGameCopyReservation.date)
)