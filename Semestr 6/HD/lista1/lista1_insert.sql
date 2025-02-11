INSERT INTO Klienci(Imie, Nazwisko)
VALUES ('Adam', 'Nowak'), ('Michał', 'Wiśniewski'), ('Janusz', 'Kowalski');

INSERT INTO Sklepy(Nazwa)
VALUES ('Biedronka'), ('Kaufland');

INSERT INTO Produkty(Nazwa)
VALUES ('Mleko 1 l'), ('Płatki owsiane'), ('Sok pomarańczowy');

INSERT INTO Oferty(Cena, IdProduktu, IdSklepu)
VALUES (2.99, 1, 1), (3.99, 1, 2), (6.11, 2, 2);

INSERT INTO Zakupy(DataZ, CzasZ, IdSklepu, IdKlienta)
VALUES ('2023-03-18', '16:40:29', 1, 1), ('2023-03-18', '18:00:01', 1, 1), ('2023-03-18', '10:18:44', 2, 3);

INSERT INTO Nabycia(Ilosc, IdOferty, IdZakupu)
VALUES (2, 1, 1), (1, 2, 3), (3, 3, 3);


INSERT INTO Oferty(Cena, IdProduktu)
VALUES (2.99, 2);

INSERT INTO Oferty(Cena, IdProduktu, IdSklepu)
VALUES (2.50, 15, 1);
