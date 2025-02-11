CREATE DATABASE Uslugi;
GO

USE Uslugi;

CREATE TABLE Klienci (
	IdKlienta int IDENTITY(1, 1) PRIMARY KEY,
	Imie varchar(255) NOT NULL,
	Nazwisko varchar(255) NOT NULL
)

CREATE TABLE Sklepy (
	IdSklepu int IDENTITY(1, 1) PRIMARY KEY,
	Nazwa varchar(255) NOT NULL
)

CREATE TABLE Produkty (
	IdProduktu int IDENTITY(1, 1) PRIMARY KEY,
	Nazwa varchar(255) NOT NULL
)

CREATE TABLE Oferty (
	IdOferty int IDENTITY(1, 1) PRIMARY KEY,
	Cena float NOT NULL,
	IdProduktu int NOT NULL,
	IdSklepu int NOT NULL,
	CONSTRAINT Cena_O CHECK (Cena > 0),
	CONSTRAINT FK_IdProduktu_O FOREIGN KEY (IdProduktu) REFERENCES Produkty (IdProduktu),
	CONSTRAINT FK_IdSklepu_O FOREIGN KEY (IdSklepu) REFERENCES Sklepy (IdSklepu)
)

CREATE TABLE Zakupy (
	IdZakupu int IDENTITY(1, 1) PRIMARY KEY,
	DataZ date NOT NULL,
	CzasZ time NOT NULL,
	IdSklepu int NOT NULL,
	IdKlienta int NOT NULL,
	CONSTRAINT FK_IdSklepu_Z FOREIGN KEY (IdSklepu) REFERENCES Sklepy (IdSklepu),
	CONSTRAINT FK_IdKlienta_Z FOREIGN KEY (IdKlienta) REFERENCES Klienci (IdKlienta)
)

CREATE TABLE Nabycia (
	IdNabywa int IDENTITY(1, 1) PRIMARY KEY,
	Ilosc int NOT NULL,
	IdOferty int NOT NULL,
	IdZakupu int NOT NULL,
	CONSTRAINT Ilosc_N CHECK (Ilosc > 0),
	CONSTRAINT FK_IdOferty_N FOREIGN KEY (IdOferty) REFERENCES Oferty (IdOferty),
	CONSTRAINT FK_IdZakupu_N FOREIGN KEY (IdZakupu) REFERENCES Zakupy (IdZakupu)
);
