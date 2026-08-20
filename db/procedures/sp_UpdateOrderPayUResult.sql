CREATE OR ALTER PROCEDURE dbo.sp_UpdateOrderPayUResult
    @ReferenceCode NVARCHAR(100),
    @PayUTransactionId NVARCHAR(100),
    @PayUOperationDate BIGINT,
    @PayUState NVARCHAR(30),
    @PayUResponseCode NVARCHAR(30)
AS
BEGIN
    SET NOCOUNT ON;
    UPDATE dbo.Orders
    SET PayUTransactionId = @PayUTransactionId,
        PayUOperationDate = @PayUOperationDate,
        PayUState = @PayUState,
        PayUResponseCode = @PayUResponseCode,
        Status = CASE WHEN @PayUState = 'APPROVED' THEN 'APPROVED' ELSE 'FAILED' END,
        UpdatedAt = SYSUTCDATETIME()
    WHERE ReferenceCode = @ReferenceCode;
END
GO
