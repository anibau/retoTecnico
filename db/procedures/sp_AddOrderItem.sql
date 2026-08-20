CREATE OR ALTER PROCEDURE dbo.sp_AddOrderItem
    @OrderId INT,
    @ProductId INT,
    @ProductNameSnapshot NVARCHAR(200),
    @UnitPriceSnapshot DECIMAL(10,2),
    @Quantity INT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO dbo.OrderItems (OrderId, ProductId, ProductNameSnapshot, UnitPriceSnapshot, Quantity, Subtotal)
    VALUES (@OrderId, @ProductId, @ProductNameSnapshot, @UnitPriceSnapshot, @Quantity, @UnitPriceSnapshot * @Quantity);
END
GO
