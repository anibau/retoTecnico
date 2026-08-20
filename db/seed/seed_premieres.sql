IF NOT EXISTS (SELECT 1 FROM dbo.Premieres)
BEGIN
    INSERT INTO dbo.Premieres (Title, Description, ImageUrl, DisplayOrder, IsActive) VALUES
    (N'Guardianes de la Galaxia: Nuevo Amanecer', N'El equipo enfrenta su misión más peligrosa para salvar el universo conocido.', N'https://picsum.photos/seed/movie1/600/400', 1, 1),
    (N'El Último Vuelo', N'Un piloto retirado debe volver a los cielos para evitar una catástrofe internacional.', N'https://picsum.photos/seed/movie2/600/400', 2, 1),
    (N'Sombras del Pasado', N'Un thriller psicológico sobre un detective que persigue un caso que lo obsesiona.', N'https://picsum.photos/seed/movie3/600/400', 3, 1),
    (N'Risas en Familia', N'Una comedia sobre tres generaciones que deben convivir bajo un mismo techo.', N'https://picsum.photos/seed/movie4/600/400', 4, 1),
    (N'Mundo Perdido: Origen', N'Una expedición científica descubre una civilización oculta en la Amazonía.', N'https://picsum.photos/seed/movie5/600/400', 5, 1),
    (N'Corazones de Acero', N'Un drama deportivo sobre un equipo que lucha por el campeonato nacional.', N'https://picsum.photos/seed/movie6/600/400', 6, 1);
END
GO
