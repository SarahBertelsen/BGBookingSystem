DECLARE @Date DATE = '2025-12-20';
DECLARE @StartTime TIME = '14:00';
DECLARE @EndTime TIME   = '17:00';

SELECT t.*
FROM dbo.[Table] t
WHERE t.tableNo NOT IN (
    SELECT b.tableNo
    FROM dbo.Booking b
    WHERE b.date = @Date
      AND (
            (b.time BETWEEN @StartTime AND @EndTime)
            OR
            (DATEADD(HOUR, 3, b.time) > @StartTime AND b.time < @StartTime)
          )
          );