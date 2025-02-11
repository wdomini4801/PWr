--Task 2
CREATE TABLE Dominiak.DIM_TIME(
	PK_Time INT PRIMARY KEY,
	Rok INT NOT NULL,
	Kwartal INT NOT NULL,
	Miesiac INT NOT NULL,
	Miesiac_slownie VARCHAR(20) NOT NULL,
	Dzien_tyg_slownie VARCHAR(20) NOT NULL,
	Dzien_miesiaca INT NOT NULL
);

CREATE TABLE Dominiak.DayHelper (
	DayID INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(20) NOT NULL
);

INSERT INTO Dominiak.DayHelper VALUES
('poniedziałek'),('wtorek'),('środa'),('czwartek'),('piątek'),('sobota'),('niedziela');

CREATE TABLE Dominiak.MonthHelper(
	MonthID INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(20) NOT NULL
);

INSERT INTO Dominiak.MonthHelper VALUES
('styczeń'),('luty'),('marzec'),('kwiecień'),('maj'),('czerwiec'),('lipiec'),
('sierpień'),('wrzesień'),('październik'),('listopad'),('grudzień');

WITH data AS (
	SELECT DISTINCT
	DATEPART(YYYY, soh.OrderDate) * 10000 + DATEPART(M, soh. OrderDate) *
	100 + DATEPART(D, soh.OrderDate) AS Pk_Time,
	DATEPART(YYYY, OrderDate) AS Rok,
	DATEPART(Q, OrderDate) AS Kwartal,
	DATEPART(M, OrderDate) AS Miesiac,
	mh.Name As Miesiac_slownie,
	dh.Name As Dzien_tyg_slownie,
	DATEPART(D, OrderDate) As Dzien_miesiaca
	FROM
	Sales.SalesOrderHeader soh 
	JOIN Dominiak.MonthHelper mh ON DATEPART(M, OrderDate) = mh.MonthID 
	JOIN Dominiak.DayHelper dh ON CASE WHEN DATEPART(DW, OrderDate) - 1 =
	0 THEN 7 ELSE DATEPART(DW,OrderDate) - 1 END = dh.DayID
	UNION
	SELECT DISTINCT
	DATEPART(YYYY, soh.ShipDate) * 10000 + DATEPART(M, soh.ShipDate) * 100
	+ DATEPART(D, soh.ShipDate) AS PK_Time,
	DATEPART(YYYY, ShipDate) AS Rok,
	DATEPART(Q, ShipDate) AS Kwartal,
	DATEPART(M, ShipDate) AS Miesiac,
	mh.Name As Miesiac_slownie,
	dh.Name As Dzien_tyg_slownie,
	DATEPART(D, ShipDate) As Dzien_miesiaca
	FROM
	Sales.SalesOrderHeader soh 
	JOIN Dominiak.MonthHelper mh ON DATEPART(M, ShipDate) = mh.MonthID 
	JOIN Dominiak.DayHelper dh ON CASE WHEN DATEPART(DW, ShipDate) - 1 =
	0 THEN 7 ELSE DATEPART(DW,ShipDate) - 1 END = dh.DayID
)
INSERT INTO Dominiak.DIM_TIME
SELECT * FROM data;

SELECT * FROM Dominiak.DIM_TIME


--Task 3
UPDATE Dominiak.DIM_PRODUCT
SET Color = COALESCE(Color, 'Unknown')
WHERE Color IS NULL;

UPDATE Dominiak.DIM_PRODUCT
SET SubCategoryName = COALESCE(SubCategoryName, 'Unknown')
WHERE SubCategoryName IS NULL;

UPDATE Dominiak.DIM_CUSTOMER
SET CountryRegionCode = COALESCE(CountryRegionCode, '000')
WHERE CountryRegionCode IS NULL;

UPDATE Dominiak.DIM_CUSTOMER
SET [Group] = COALESCE([Group], 'Unknown')
WHERE [Group] IS NULL;

SELECT * FROM Dominiak.FACT_SALES
