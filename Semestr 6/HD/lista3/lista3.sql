--Query 1.1

--with ROLLUP
SELECT COALESCE(CONCAT(FirstName, ' ', LastName),'') Klient, COALESCE(CAST(DATEPART(YEAR, OrderDate) AS VARCHAR),'') Rok, SUM(TotalDue) Kwota 
FROM Sales.SalesOrderHeader soh 
JOIN Sales.Customer c ON soh.CustomerID = c.CustomerID
JOIN Person.Person p ON c.PersonID = p.BusinessEntityID
GROUP BY ROLLUP(CONCAT(FirstName, ' ', LastName), DATEPART(YEAR, OrderDate))
UNION SELECT '', COALESCE(CAST(YEAR(OrderDate) AS VARCHAR), '') Rok, SUM(TotalDue) Kwota
FROM Sales.SalesOrderHeader soh 
JOIN Sales.Customer c ON soh.CustomerID = c.CustomerID
JOIN Person.Person p ON c.PersonID = p.BusinessEntityID
GROUP BY YEAR(OrderDate)
ORDER BY 1;

--with CUBE
SELECT COALESCE(CONCAT(FirstName, ' ', LastName),'') Klient, COALESCE(CAST(DATEPART(YEAR, OrderDate) AS VARCHAR),'') Rok, SUM(TotalDue) Kwota 
FROM Sales.SalesOrderHeader soh 
JOIN Sales.Customer c ON soh.CustomerID = c.CustomerID
JOIN Person.Person p ON c.PersonID = p.BusinessEntityID
GROUP BY CUBE(CONCAT(FirstName, ' ', LastName), DATEPART(YEAR, OrderDate))
ORDER BY 1;

--with GROUPING SETS
SELECT COALESCE(CONCAT(FirstName, ' ', LastName),'') Klient, COALESCE(CAST(DATEPART(YEAR, OrderDate) AS VARCHAR),'') Rok, SUM(TotalDue) Kwota 
FROM Sales.SalesOrderHeader soh 
JOIN Sales.Customer c ON soh.CustomerID = c.CustomerID
JOIN Person.Person p ON c.PersonID = p.BusinessEntityID
GROUP BY GROUPING SETS((YEAR(OrderDate), CONCAT(FirstName, ' ', LastName)), DATEPART(YEAR, OrderDate), CONCAT(FirstName, ' ', LastName), ())
ORDER BY 1;


--Query 1.2
SELECT
	COALESCE(pc.Name, '') Kategoria,
	COALESCE(p.Name, '') Produkt,
	COALESCE((CAST(YEAR(soh.OrderDate) AS VARCHAR)), '') Rok,
	ROUND(SUM(sod.OrderQty * soffer.DiscountPct * sod.UnitPrice), 2) Kwota
FROM Production.ProductCategory pc
JOIN Production.ProductSubcategory ps ON pc.ProductCategoryID = ps.ProductCategoryID
JOIN Production.Product p ON ps.ProductSubcategoryID = p.ProductSubcategoryID
JOIN Sales.SalesOrderDetail sod ON p.ProductID = sod.ProductID 
JOIN Sales.SpecialOffer soffer ON sod.SpecialOfferID = soffer.SpecialOfferID
JOIN Sales.SalesOrderHeader soh ON sod.SalesOrderID = soh.SalesOrderID
GROUP BY ROLLUP(pc.Name, p.Name, YEAR(soh.OrderDate));


--Query 2.1
SELECT *
FROM
(
	SELECT YEAR(OrderDate) AS rok, pc.Name AS Kategoria, 
	ROUND(SUM(TotalDue) OVER (PARTITION BY YEAR(OrderDate)) * 100 / SUM(TotalDue) OVER (), 2) AS 'Procentowy udzial'
	FROM Production.ProductCategory pc 
	JOIN Production.ProductSubcategory ps ON pc.ProductCategoryID = ps.ProductCategoryID
	JOIN Production.Product p ON ps.ProductSubcategoryID = p.ProductSubcategoryID 
	JOIN Sales.SalesOrderDetail sod ON p.ProductID = sod.ProductID
	JOIN Sales.SalesOrderHeader soh ON sod.SalesOrderID = soh.SalesOrderID
	WHERE pc.Name = 'Components'
	GROUP BY YEAR(OrderDate), pc.Name, TotalDue
	) AS src
PIVOT 
(
	MAX([Procentowy udzial])
	FOR rok IN ([2011], [2012], [2013], [2014])
) AS pivoted;

--Query 2.2
WITH ranking AS (
	SELECT RANK() OVER (ORDER BY COUNT(soh2.SalesOrderID) DESC, MIN(soh2.OrderDate)) AS ranking, soh2.CustomerID
	FROM Sales.SalesOrderHeader soh2
	GROUP BY soh2.CustomerID
)
SELECT
	COALESCE(CONCAT(p.FirstName, ' ', p.LastName),'') AS Klient,
	COALESCE(CAST(YEAR(soh.OrderDate) AS VARCHAR), '') AS Rok,
	SUM(COUNT(soh.SalesOrderID)) OVER (
		PARTITION BY COALESCE(CONCAT(p.FirstName, ' ', p.LastName), '')
		ORDER BY COALESCE(CONCAT(p.FirstName, ' ', p.LastName), '')
		ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
		) AS IloscZamowien
FROM Person.Person p 
JOIN Sales.Customer c ON p.BusinessEntityID = c.PersonID 
JOIN Sales.SalesOrderHeader soh ON c.CustomerID = soh.CustomerID
JOIN ranking ON soh.CustomerID = ranking.CustomerID
WHERE ranking.ranking <= 10
GROUP BY
	COALESCE(CONCAT(p.FirstName, ' ', p.LastName), ''),
	COALESCE(CAST(YEAR(soh.OrderDate) AS VARCHAR), ''),
	soh.CustomerID,
	ranking.ranking
ORDER BY ranking.ranking;

--Query 2.3
SELECT
	CONCAT(p.FirstName, ' ', p.LastName) 'Imie i nazwisko', 
	YEAR(soh.OrderDate) Rok,
	MONTH(soh.OrderDate) Miesiac,
	MIN(COUNT(soh.SalesOrderID)) OVER (
		PARTITION BY CONCAT(p.FirstName, ' ', p.LastName),
		YEAR(soh.OrderDate), MONTH(soh.OrderDate)) 'W miesiacu',
	SUM(COUNT(soh.SalesOrderID)) OVER ( 
		PARTITION BY CONCAT(p.FirstName, ' ', p.LastName),
		YEAR(soh.OrderDate)) 'W roku',
	SUM(COUNT(soh.SalesOrderID)) OVER ( 
		PARTITION BY CONCAT(p.FirstName, ' ', p.LastName),
		YEAR(soh.OrderDate) ORDER BY YEAR(soh.OrderDate) 
		ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW) 'W roku narastajaco',
	SUM(COUNT(soh.SalesOrderID)) OVER ( 
		PARTITION BY CONCAT(p.FirstName, ' ', p.LastName),
		YEAR(soh.OrderDate) ORDER BY YEAR(soh.OrderDate) ROWS BETWEEN 1 PRECEDING AND CURRENT ROW) 'Obecny i poprzedni miesiac'
FROM Person.Person p 
JOIN Sales.SalesOrderHeader soh ON p.BusinessEntityID = soh. SalesPersonID
GROUP BY CONCAT(p.FirstName, ' ', p.LastName), YEAR(soh.OrderDate), MONTH(soh.OrderDate);

--Query 2.4
SELECT DISTINCT pc.Name Kategoria, SUM(ROUND(MAX(sod.UnitPrice), 2)) OVER(PARTITION BY pc.Name) Suma
FROM Production.ProductCategory pc 
JOIN Production.ProductSubcategory ps ON pc.ProductCategoryID = ps.ProductCategoryID
JOIN Production.Product p ON ps.ProductSubcategoryID = p.ProductSubcategoryID 
JOIN Sales.SalesOrderDetail sod ON p.ProductID = sod.ProductID
GROUP BY pc.Name, ps.Name
ORDER BY 2 DESC;

--Query 2.5
SELECT
	person.FirstName Imie,
	person.LastName Nazwisko,
	SUM(sod.OrderQty) 'Suma produktów',
	DENSE_RANK() OVER (ORDER BY SUM(sod.OrderQty) DESC) 'DENSE RANK',
	RANK() OVER (ORDER BY SUM(sod.OrderQty) DESC) 'RANK'
FROM Person.Person person 
JOIN Sales.Customer customer ON person.BusinessEntityID = customer.PersonID
JOIN Sales.SalesOrderHeader soh ON customer.CustomerID = soh.CustomerID
JOIN Sales.SalesOrderDetail sod ON soh.SalesOrderID = sod.SalesOrderID
GROUP BY person.FirstName, person.LastName
ORDER BY 5,4,2,1;

--Query 2.6
SELECT 
	p.Name Nazwa,
	AVG(sod.OrderQty) 'Srednia liczba sztuk',
	CASE NTILE(3) OVER(ORDER BY AVG(sod.OrderQty) DESC, p.Name) 
		WHEN 1 THEN 'najlepiej'
		WHEN 2 THEN 'srednio'
		WHEN 3 THEN 'najslabiej'
	END ranking
FROM Production.Product p
JOIN Sales.SalesOrderDetail sod ON p.ProductID = sod.ProductID
GROUP BY p.Name
ORDER BY 2 DESC, 1;
