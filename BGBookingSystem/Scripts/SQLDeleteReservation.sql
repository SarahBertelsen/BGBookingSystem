DECLARE @boardCopyGameId int = 1010
DECLARE @startTime DateTime2 = '2026-01-20 15:05:00'

DELETE FROM BoardGameCopyReservation
WHERE boardGameCopyId = @boardCopyGameId AND date <= @startTime AND @startTime <= DATEADD(MINUTE, 10, date)
