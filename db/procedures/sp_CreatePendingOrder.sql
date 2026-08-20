-- El detalle de la orden (carrito) se inserta con sp_AddOrderItem en un loop desde el backend,
-- en vez de usar un Table-Valued Parameter: simplifica el mapeo desde JdbcTemplate/SimpleJdbcCall
-- sin perder atomicidad, ya que ambos SP corren dentro de la misma transacción abierta por Spring.
CREATE OR ALTER PROCEDURE dbo.sp_CreatePendingOrder
    @ReferenceCode NVARCHAR(100),
    @Email NVARCHAR(200),
    @FullName NVARCHAR(200),
    @DocumentType NVARCHAR(20),
    @DocumentNumber NVARCHAR(30),
    @Amount DECIMAL(10,2),
    @Currency NVARCHAR(10),
    @OrderId INT OUTPUT
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRANSACTION;

    INSERT INTO dbo.Orders (ReferenceCode, Email, FullName, DocumentType, DocumentNumber, Amount, Currency, Status, CreatedAt)
    VALUES (@ReferenceCode, @Email, @FullName, @DocumentType, @DocumentNumber, @Amount, @Currency, 'PENDING', SYSUTCDATETIME());

    SET @OrderId = SCOPE_IDENTITY();

    COMMIT TRANSACTION;
END
GO
