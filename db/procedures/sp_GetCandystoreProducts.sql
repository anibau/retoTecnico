CREATE OR ALTER PROCEDURE dbo.sp_GetCandystoreProducts
AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, Name, Description, Price, Category
    FROM dbo.CandystoreProducts
    WHERE IsActive = 1
    ORDER BY Category, Name;
END
GO
