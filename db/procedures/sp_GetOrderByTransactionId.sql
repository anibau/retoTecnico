CREATE OR ALTER PROCEDURE dbo.sp_GetOrderByTransactionId
    @TransactionId NVARCHAR(100)
AS
BEGIN
    SET NOCOUNT ON;
    SELECT Id, ReferenceCode, Email, FullName, DocumentNumber, PayUState, PayUOperationDate, Status
    FROM dbo.Orders
    WHERE PayUTransactionId = @TransactionId;
END
GO
