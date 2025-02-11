--Query 1
SELECT Count(*)
FROM Production.Product;

SELECT Count(*)
FROM Production.ProductCategory;

SELECT Count(*)
FROM Production.ProductSubcategory;


--Query 2
SELECT *
FROM Production.Product
WHERE Color IS NULL;


--Query 3
SELECT YEAR(OrderDate) AS 'Year', SUM(TotalDue) AS 'Total sum'
FROM Sales.SalesOrderHeader
GROUP BY YEAR(OrderDate)
ORDER BY YEAR(OrderDate);

--Query 4
SELECT COUNT(DISTINCT(PersonID))
FROM Sales.Customer
JOIN Person.Person ON Person.BusinessEntityID = Customer.PersonID;

SELECT SalesTerritory.Name AS 'Territory', COUNT(DISTINCT(PersonID)) AS 'Customers'
FROM Sales.Customer
JOIN Person.Person ON Person.BusinessEntityID = Customer.PersonID
LEFT JOIN Sales.SalesTerritory ON Customer.TerritoryID = SalesTerritory.TerritoryID
GROUP BY SalesTerritory.Name;

SELECT COUNT(*) 
FROM Sales.SalesPerson;

SELECT SalesTerritory.Name AS 'Territory', COUNT(*) AS 'SalesPerson' 
FROM Sales.SalesPerson
LEFT JOIN Sales.SalesTerritory ON SalesPerson.TerritoryID = SalesTerritory.TerritoryID
GROUP BY SalesTerritory.Name;

--Query 5
SELECT YEAR(OrderDate) AS 'Year', COUNT(*) AS 'Transactions'
FROM Sales.SalesOrderHeader
GROUP BY YEAR(OrderDate)
ORDER BY YEAR(OrderDate);

--Query 6
SELECT ProductCategory.Name AS 'Category', ProductSubcategory.Name AS 'Subcategory', Product.ProductID, Product.Name, ProductNumber
FROM Production.Product
LEFT JOIN Production.ProductSubcategory ON ProductSubcategory.ProductSubcategoryID = Product.ProductSubcategoryID
LEFT JOIN Production.ProductCategory ON ProductCategory.ProductCategoryID = ProductSubcategory.ProductCategoryID
LEFT JOIN Sales.SalesOrderDetail ON SalesOrderDetail.ProductID = Product.ProductID
WHERE SalesOrderDetail.SalesOrderDetailID IS NULL
GROUP BY ProductCategory.Name, ProductSubcategory.Name, Product.Name, ProductNumber, Product.ProductID;

--Query 7
SELECT ps.Name, MIN(s.UnitPriceDiscount) as "Minimum", MAX(s.UnitPriceDiscount) as "Maximum" 
FROM Sales.SalesOrderDetail s
JOIN Production.Product p ON p.ProductID = s.ProductID
RIGHT JOIN Production.ProductSubcategory ps ON ps.ProductSubcategoryID = p.ProductSubcategoryID
GROUP BY ps.Name;

--Query 8
SELECT Name, ListPrice
FROM Production.Product
WHERE ListPrice > (SELECT AVG(ListPrice) FROM Production.Product);

--Query 9
SELECT YEAR(OrderDate) AS OrderYear, MONTH(OrderDate) AS OrderMonth, pc.Name AS CategoryName, COUNT(sod.ProductID) / COUNT(DISTINCT soh.SalesOrderID) AS AverageProductsSold
FROM Sales.SalesOrderDetail AS sod
JOIN Sales.SalesOrderHeader AS soh ON sod.SalesOrderID = soh.SalesOrderID
JOIN Production.Product AS p ON sod.ProductID = p.ProductID
JOIN Production.ProductSubcategory AS psc ON p.ProductSubcategoryID = psc.ProductSubcategoryID
JOIN Production.ProductCategory AS pc ON psc.ProductCategoryID = pc.ProductCategoryID
GROUP BY YEAR(OrderDate), MONTH(OrderDate), pc.Name
ORDER BY YEAR(OrderDate), MONTH(OrderDate), pc.Name;

--Query 10
SELECT st.CountryRegionCode, AVG(CAST(DATEDIFF(day, OrderDate, ShipDate) AS FLOAT)) AS AverageDaysToShip
FROM Sales.SalesOrderHeader AS soh
JOIN Sales.SalesTerritory AS st ON soh.TerritoryID = st.TerritoryID
GROUP BY st.CountryRegionCode
ORDER BY st.CountryRegionCode;
