CREATE TABLE Dominiak.DIM_CUSTOMER (
    CustomerID INT PRIMARY KEY,
    FirstName VARCHAR(50),
    LastName VARCHAR(50),
    Title VARCHAR(8),
    City VARCHAR(30),
    TerritoryName VARCHAR(50),
    CountryRegionCode VARCHAR(3),
    [Group] VARCHAR(50)
);

CREATE TABLE Dominiak.DIM_PRODUCT (
    ProductID INT PRIMARY KEY,
    Name VARCHAR(50) NOT NULL,
    ListPrice MONEY NOT NULL,
    Color VARCHAR(15),
    SubCategoryName VARCHAR(50),
    CategoryName VARCHAR(50),
    Weight DECIMAL(8,2),
    Size VARCHAR(5),
    IsPurchased BIT NOT NULL
);

CREATE TABLE Dominiak.DIM_SALESPERSON (
    SalesPersonID INT PRIMARY KEY,
    FirstName VARCHAR(50) NOT NULL,
    LastName VARCHAR(50) NOT NULL,
    Title VARCHAR(8),
    Gender CHAR(1),
    CountryRegionCode VARCHAR(3),
    [Group] VARCHAR(50)
);

CREATE TABLE Dominiak.FACT_SALES (
    FactID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT FOREIGN KEY REFERENCES Dominiak.DIM_PRODUCT(ProductID),
    CustomerID INT FOREIGN KEY REFERENCES Dominiak.DIM_CUSTOMER(CustomerID),
    SalesPersonID INT FOREIGN KEY REFERENCES Dominiak.DIM_SALESPERSON(SalesPersonID),
    OrderDate INT NOT NULL,
    ShipDate INT,
    OrderQty INT NOT NULL,
    UnitPrice MONEY NOT NULL,
    UnitPriceDiscount MONEY NOT NULL,
    LineTotal AS (ISNULL(([UnitPrice]*((1.0)-[UnitPriceDiscount]))*[OrderQty],(0.0)))
);


--Task 3
WITH data AS(
    SELECT
        c.CustomerID, p.FirstName, p.LastName, p.Title, a.City, st.Name, st.CountryRegionCode, st.[Group]
    FROM
        Sales.Customer c LEFT JOIN
        Person.Person p ON c.PersonID = p.BusinessEntityID LEFT JOIN
        Person.BusinessEntityAddress bea ON p.BusinessEntityID = bea.BusinessEntityID LEFT JOIN
        Person.Address a ON bea.AddressID = a.AddressID LEFT JOIN
        Person.StateProvince sp ON a.StateProvinceID = sp.StateProvinceID LEFT JOIN
        Sales.SalesTerritory st ON sp.TerritoryID = st.TerritoryID LEFT JOIN 
        Person.AddressType at ON bea.AddressTypeID = at.AddressTypeID
        WHERE at.Name = 'Home' Or at.Name IS NULL    
)
INSERT INTO Dominiak.DIM_CUSTOMER
SELECT * FROM data;

WITH data AS(
    SELECT
        p.ProductID, p.Name AS PName, p.ListPrice, p.Color, ps.Name AS SubCategoryName, pc.Name AS CategoryName, p.Weight, p.Size, CASE WHEN COUNT(sod.SalesOrderId) = 0 THEN 0 ELSE 1 END AS IsPurchased
    FROM
        Production.Product p LEFT JOIN
        Production.ProductSubcategory ps ON p.ProductSubcategoryID = ps.ProductSubcategoryID LEFT JOIN
        Production.ProductCategory pc ON ps.ProductCategoryID = pc.ProductCategoryID LEFT JOIN
        Sales.SalesOrderDetail sod ON p.ProductID = sod.ProductID
    GROUP BY
        p.ProductID, p.Name, p.ListPrice, p.Color, ps.Name, pc.Name, p.Weight, p.Size
)
INSERT INTO Dominiak.DIM_PRODUCT
SELECT * FROM data;

WITH data AS (
    SELECT
        sp.BusinessEntityID, p.FirstName, p.LastName, p.Title, e.Gender, st.CountryRegionCode, st.[Group]
    FROM
        Person.Person p JOIN
        HumanResources.Employee e ON  p.BusinessEntityID = e.BusinessEntityID JOIN
        Sales.SalesPerson sp ON p.BusinessEntityID = sp.BusinessEntityID LEFT JOIN
        Sales.SalesTerritory st ON sp.TerritoryID = st.TerritoryID
)
INSERT INTO Dominiak.DIM_SALESPERSON
SELECT * FROM data;

WITH data AS (
    SELECT
        sod.ProductId,
        soh.CustomerID,
        soh.SalesPersonID,
        DATEPART(YYYY, soh.OrderDate) * 10000 + DATEPART(M, soh.OrderDate) * 100 + DATEPART(D, soh.OrderDate) OrderDate,
        DATEPART(YYYY, soh.ShipDate) * 10000 + DATEPART(M, soh.ShipDate) * 100 + DATEPART(D, soh.ShipDate) ShipDate,
        sod.OrderQty,
        sod.UnitPrice,
        sod.UnitPriceDiscount
    FROM
        Sales.SalesOrderHeader soh JOIN Sales.SalesOrderDetail sod ON soh.SalesOrderID = sod.SalesOrderID
)
INSERT INTO Dominiak.FACT_SALES
SELECT * FROM data;

SELECT * FROM Dominiak.FACT_SALES;


--Task 4
INSERT INTO Dominiak.FACT_SALES (ProductId, CustomerID, SalesPersonID, OrderDate, OrderQty, UnitPrice, UnitPriceDiscount) 
values (4,142432, 1, 20010501, 2, 7, 6);

SELECT * FROM Dominiak.FACT_SALES WHERE CustomerID = 142432;
