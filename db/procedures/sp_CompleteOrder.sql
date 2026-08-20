CREATE OR ALTER PROCEDURE dbo.sp_CompleteOrder
    @TransactionId NVARCHAR(100),
    @Email NVARCHAR(200),
    @DocumentNumber NVARCHAR(30),
    @OperationDate BIGINT,
    @ResultCode INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;

    DECLARE @OrderId INT;

    SELECT @OrderId = Id
    FROM dbo.Orders
    WHERE PayUTransactionId = @TransactionId
      AND Email = @Email
      AND DocumentNumber = @DocumentNumber
      AND PayUOperationDate = @OperationDate
      AND Status = 'APPROVED';

    IF @OrderId IS NULL
    BEGIN
        SET @ResultCode = 1;
        RETURN;
    END

    UPDATE dbo.Orders
    SET Status = 'COMPLETED',
        UpdatedAt = SYSUTCDATETIME()
    WHERE Id = @OrderId;

    SET @ResultCode = 0;
END
GO
