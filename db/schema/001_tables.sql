IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Premieres')
BEGIN
    CREATE TABLE dbo.Premieres (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Title NVARCHAR(200) NOT NULL,
        Description NVARCHAR(1000) NULL,
        ImageUrl NVARCHAR(500) NOT NULL,
        DisplayOrder INT NOT NULL DEFAULT 0,
        IsActive BIT NOT NULL DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'CandystoreProducts')
BEGIN
    CREATE TABLE dbo.CandystoreProducts (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        Name NVARCHAR(200) NOT NULL,
        Description NVARCHAR(1000) NULL,
        Price DECIMAL(10,2) NOT NULL,
        Category NVARCHAR(100) NULL,
        ImageUrl NVARCHAR(500) NULL,
        IsActive BIT NOT NULL DEFAULT 1,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME()
    );
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'Orders')
BEGIN
    CREATE TABLE dbo.Orders (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        ReferenceCode NVARCHAR(100) NOT NULL,
        Email NVARCHAR(200) NOT NULL,
        FullName NVARCHAR(200) NOT NULL,
        DocumentType NVARCHAR(20) NULL,
        DocumentNumber NVARCHAR(30) NOT NULL,
        Amount DECIMAL(10,2) NOT NULL,
        Currency NVARCHAR(10) NOT NULL DEFAULT 'PEN',
        Status NVARCHAR(20) NOT NULL DEFAULT 'PENDING', -- PENDING | APPROVED | FAILED | COMPLETED
        PayUTransactionId NVARCHAR(100) NULL,
        PayUOperationDate BIGINT NULL,
        PayUState NVARCHAR(30) NULL,
        PayUResponseCode NVARCHAR(30) NULL,
        CreatedAt DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
        UpdatedAt DATETIME2 NULL,
        CONSTRAINT UQ_Orders_ReferenceCode UNIQUE (ReferenceCode)
    );

    CREATE INDEX IX_Orders_PayUTransactionId ON dbo.Orders (PayUTransactionId);
END
GO

IF NOT EXISTS (SELECT * FROM sys.tables WHERE name = 'OrderItems')
BEGIN
    CREATE TABLE dbo.OrderItems (
        Id INT IDENTITY(1,1) PRIMARY KEY,
        OrderId INT NOT NULL,
        ProductId INT NOT NULL,
        ProductNameSnapshot NVARCHAR(200) NULL,
        UnitPriceSnapshot DECIMAL(10,2) NULL,
        Quantity INT NOT NULL,
        Subtotal DECIMAL(10,2) NOT NULL,
        CONSTRAINT FK_OrderItems_Orders FOREIGN KEY (OrderId) REFERENCES dbo.Orders (Id)
    );
END
GO
