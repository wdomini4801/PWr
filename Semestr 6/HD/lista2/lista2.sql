--Query 1
CREATE TABLE Sprzedaz( 
pracID int, 
prodID int, 
"Nazwa produktu" VARCHAR(255), 
Rok int, 
Liczba int
)

INSERT INTO Sprzedaz(pracID, prodID, "Nazwa produktu", Rok, Liczba)
SELECT SalesPersonID AS 'pracId', sod.ProductID AS 'prodID', p.Name AS 'Nazwa 
produktu', YEAR(OrderDate) Rok, SUM(sod.OrderQty) Liczba 
FROM Sales.SalesOrderDetail sod 
JOIN Sales.SalesOrderHeader soh ON sod.SalesOrderID = soh.SalesOrderID
JOIN Production.Product p ON p.ProductID = sod.ProductID
GROUP BY SalesPersonID, sod.ProductID, p.Name, YEAR(OrderDate)
ORDER BY YEAR(OrderDate), SalesPersonID, sod.ProductID;

SELECT * FROM Sprzedaz

--Query 1a
SELECT * FROM
	(SELECT soh.SalesPersonID AS "pracID", sod.ProductID AS "prodID",
	product.Name AS "NazwaProduktu", YEAR(OrderDate) AS "Rok", COALESCE(sod.OrderQty,0) AS TempRes
	FROM Sales.SalesOrderDetail sod 
	JOIN Sales.SalesOrderHeader soh ON sod.SalesOrderID = soh.SalesOrderID
	JOIN Production.Product product ON product.ProductID = sod.ProductID) Sprzedaz
PIVOT( SUM("TempRes") FOR ROK IN ([2011],[2012],[2013],[2014]) ) SprzedazPivot
ORDER BY pracID, prodID, NazwaProduktu;

--Query 1b
SELECT * FROM
	(SELECT soh.SalesPersonID AS 'pracID',
	product.Name AS 'NazwaProduktu', YEAR(OrderDate) Rok,
	CAST(ROW_NUMBER() OVER (
	PARTITION BY soh.SalesPersonID, YEAR(OrderDate)
	ORDER BY SUM(sod.OrderQty)) AS VARCHAR) AS Ranking
	FROM Sales.SalesOrderDetail sod 
	JOIN Sales.SalesOrderHeader soh ON sod.SalesOrderID = soh.SalesOrderID
	JOIN Production.Product product ON product.ProductID = sod.ProductID
	GROUP BY soh.SalesPersonID, sod.ProductID, product.Name, YEAR(OrderDate)) Sprzedaz
PIVOT( Max([NazwaProduktu]) FOR Ranking IN ([1],[2],[3],[4],[5])) RankingPivot
ORDER BY pracID, Rok;

--Query 2a
SELECT YEAR(OrderDate) Rok, Month(OrderDate) Miesiac, COUNT(DISTINCT(CustomerID)) AS 'Liczba roznych klientow'
FROM Sales.SalesOrderHeader
GROUP BY YEAR(OrderDate), MONTH(OrderDate)
ORDER BY YEAR(OrderDate), MONTH(OrderDate);

--Query 2b
SELECT * FROM (
	SELECT DISTINCT YEAR(OrderDate) Rok, MONTH(OrderDate) Miesiac, CustomerID 'Liczba_roznych_klientow'
	FROM Sales.SalesOrderHeader) Sprzedaz
PIVOT(COUNT(Liczba_roznych_klientow) FOR Miesiac IN
([1],[2],[3],[4],[5],[6],[7],[8],[9],[10],[11],[12])) MiesiacePivot
ORDER BY 1, 2;

--Query 3
SELECT * FROM (
	SELECT
	FirstName + ' ' + LastName 'Imie i nazwisko', 
	YEAR(SalesOrderHeader.OrderDate) Rok, 
	COUNT(SalesOrderHeader.SalesOrderID) Sprzedaz
	FROM Person.Person Person
	JOIN HumanResources.Employee Employee ON Person.BusinessEntityID = Employee.BusinessEntityID
	JOIN Sales.SalesPerson SalesPerson ON Employee.BusinessEntityID = SalesPerson.BusinessEntityID
	JOIN Sales.SalesOrderHeader SalesOrderHeader ON SalesOrderHeader.SalesPersonID = SalesPerson.BusinessEntityID
	WHERE YEAR(Employee.HireDate) = 2011
	GROUP BY FirstName + ' ' + LastName, YEAR(SalesOrderHeader.OrderDate)) grupa
PIVOT(MAX(grupa.Sprzedaz) FOR Rok IN ([2011],[2012],[2013],[2014])) pivotName;

--Query 4
SELECT
	YEAR(SalesOrderHeader.OrderDate) Rok, MONTH(SalesOrderHeader.OrderDate) 
	Miesiac, DAY(SalesOrderHeader.OrderDate) Dzien, 
	CAST(SUM(SalesOrderHeader.TotalDue) AS DECIMAL(15,2)) AS 'Suma', 
	COUNT(DISTINCT SalesOrderDetail.ProductID) AS 'Liczba roznych produktow'
FROM Sales.SalesOrderHeader SalesOrderHeader 
JOIN Sales.SalesOrderDetail SalesOrderDetail ON SalesOrderDetail.SalesOrderID = SalesOrderHeader.SalesOrderID
GROUP BY YEAR(SalesOrderHeader.OrderDate), MONTH(SalesOrderHeader.OrderDate), DAY(SalesOrderHeader.OrderDate)
ORDER BY 1,2,3;

--Query 5
SELECT
	CASE MONTH(OrderDate)
		WHEN 1 THEN 'Styczen'
		WHEN 2 THEN 'Luty'
		WHEN 3 THEN 'Marzec'
		WHEN 4 THEN 'Kwiecien'
		WHEN 5 THEN 'Maj'
		WHEN 6 THEN 'Czerwiec'
		WHEN 7 THEN 'Lipiec'
		WHEN 8 THEN 'Sierpien'
		WHEN 9 THEN 'Wrzesien'
		WHEN 10 THEN 'Pazdziernik'
		WHEN 11 THEN 'Listopad'
		WHEN 12 THEN 'Grudzien'
	END Miesiac,
	CASE DATEPART(WEEKDAY, OrderDate)
		WHEN 1 THEN 'Poniedzialek'
		WHEN 2 THEN 'Wtorek'
		WHEN 3 THEN 'Sroda'
		WHEN 4 THEN 'Czwartek'
		WHEN 5 THEN 'Piatek'
		WHEN 6 THEN 'Sobota'
		WHEN 7 THEN 'Niedziela'
	END Dzien,
	CAST(SUM(TotalDue) AS DECIMAL(15,2)) 'Suma kwot', 
	COUNT(DISTINCT ProductID) 'Liczba roznych produktow'
FROM Sales.SalesOrderHeader 
JOIN Sales.SalesOrderDetail ON SalesOrderHeader.SalesOrderID = SalesOrderDetail.SalesOrderID
GROUP BY MONTH(OrderDate), DATEPART(WEEKDAY, OrderDate)
ORDER BY MONTH(OrderDate), DATEPART(WEEKDAY, OrderDate)

--Query 6
CREATE TABLE KartyLojalnosciowe(
	Imie VARCHAR(255),
	Nazwisko VARCHAR(255),
	"Liczba transakcji" int,
	"Laczna kwota transakcji" float,
	"Kolor karty" VARCHAR(255));

INSERT INTO KartyLojalnosciowe(Imie, Nazwisko, "Liczba transakcji", "Laczna kwota transakcji","Kolor karty")
SELECT Person.FirstName Imie, Person.LastName Nazwisko, liczba, kwota,
CASE
	WHEN [min_w_roku] >= 2 and [years] = 4 THEN 'Platynowa'
	WHEN [numberAVG] >= 2 then 'Zlota'
	WHEN liczba >= 5 THEN 'Srebrna'
	ELSE 'Brak'
END Karta
FROM 
(SELECT CustomerID, SUM([powavg]) numberAVG, COUNT(CustomerID) years, MIN([powavg]) min_w_roku
FROM 
	(SELECT
	CustomerID, Rok, SUM([powyzej]) powavg
	FROM 
		(SELECT CustomerID, YEAR(OrderDate) AS Rok, IIF(AVG(SubTotal) OVER() * 1.5 < SubTotal, 1, 0) powyzej
		FROM Sales.SalesOrderHeader) a 
		GROUP BY CustomerID, Rok) powyej_sredniej 
	GROUP BY CustomerID) b
JOIN 
(SELECT CustomerID, COUNT(SalesOrderID) liczba, CAST(SUM(SubTotal) AS DECIMAL(15,2)) kwota
FROM Sales.SalesOrderHeader
GROUP BY CustomerID) Customer ON Customer.CustomerID = b.CustomerID
JOIN Sales.Customer SalCus ON SalCus.CustomerID = Customer.CustomerID
JOIN Person.Person Person ON SalCus.PersonID = Person.BusinessEntityID
WHERE ([min_w_roku] >= 2 AND [years] = 4) OR [numberAVG] >= 2 OR liczba >= 5;

SELECT * FROM KartyLojalnosciowe;

SELECT "Kolor karty", COUNT(*) Liczba_kart
FROM KartyLojalnosciowe
GROUP BY "Kolor karty"
