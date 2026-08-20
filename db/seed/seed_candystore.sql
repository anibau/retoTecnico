IF NOT EXISTS (SELECT 1 FROM dbo.CandystoreProducts)
BEGIN
    INSERT INTO dbo.CandystoreProducts (Name, Description, Price, Category, ImageUrl, IsActive) VALUES
    (N'Combo Canchita Grande', N'Canchita grande + gaseosa grande a elección.', 25.90, N'Combos', N'https://picsum.photos/seed/candy1/400/300', 1),
    (N'Combo Pareja', N'2 canchitas medianas + 2 gaseosas medianas.', 34.90, N'Combos', N'https://picsum.photos/seed/candy2/400/300', 1),
    (N'Canchita Mediana', N'Canchita salada o dulce, tamaño mediano.', 12.90, N'Snacks', N'https://picsum.photos/seed/candy3/400/300', 1),
    (N'Nachos con Queso', N'Nachos crocantes con salsa de queso cheddar.', 15.50, N'Snacks', N'https://picsum.photos/seed/candy4/400/300', 1),
    (N'Hot Dog Clásico', N'Hot dog con salsas a elección.', 13.90, N'Snacks', N'https://picsum.photos/seed/candy5/400/300', 1),
    (N'Gaseosa Grande', N'Bebida gaseosa 32oz, sabor a elección.', 9.90, N'Bebidas', N'https://picsum.photos/seed/candy6/400/300', 1),
    (N'Agua Mineral', N'Botella de agua mineral sin gas 500ml.', 6.50, N'Bebidas', N'https://picsum.photos/seed/candy7/400/300', 1),
    (N'Chocolate Barra', N'Barra de chocolate con leche 90g.', 7.90, N'Dulces', N'https://picsum.photos/seed/candy8/400/300', 1),
    (N'Gomitas Surtidas', N'Bolsa de gomitas de frutas surtidas 150g.', 8.50, N'Dulces', N'https://picsum.photos/seed/candy9/400/300', 1),
    (N'Helado Cono', N'Helado de vainilla o chocolate en cono crocante.', 10.90, N'Dulces', N'https://picsum.photos/seed/candy10/400/300', 1);
END
GO
