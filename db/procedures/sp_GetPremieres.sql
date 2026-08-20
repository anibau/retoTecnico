CREATE OR ALTER PROCEDURE dbo.sp_GetPremieres
AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, Title, Description, ImageUrl, DisplayOrder
    FROM dbo.Premieres
    WHERE IsActive = 1
    ORDER BY DisplayOrder;
END
GO
