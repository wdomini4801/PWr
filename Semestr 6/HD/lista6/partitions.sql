CREATE TABLE Dominiak.FACT_SALES_2011 (
    FactID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT FOREIGN KEY REFERENCES Dominiak.DIM_PRODUCT(ProductID),
    CustomerID INT FOREIGN KEY REFERENCES Dominiak.DIM_CUSTOMER(CustomerID),
    SalesPersonID INT FOREIGN KEY REFERENCES Dominiak.DIM_SALESPERSON(SalesPersonID),
    OrderDate INT NOT NULL,
    ShipDate INT,
    OrderQty INT NOT NULL,
    UnitPrice MONEY NOT NULL,
    UnitPriceDiscount MONEY NOT NULL,
    LineTotal MONEY NOT NULL,
);

CREATE TABLE Dominiak.FACT_SALES_2012 (
    FactID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT FOREIGN KEY REFERENCES Dominiak.DIM_PRODUCT(ProductID),
    CustomerID INT FOREIGN KEY REFERENCES Dominiak.DIM_CUSTOMER(CustomerID),
    SalesPersonID INT FOREIGN KEY REFERENCES Dominiak.DIM_SALESPERSON(SalesPersonID),
    OrderDate INT NOT NULL,
    ShipDate INT,
    OrderQty INT NOT NULL,
    UnitPrice MONEY NOT NULL,
    UnitPriceDiscount MONEY NOT NULL,
    LineTotal MONEY NOT NULL,
);

CREATE TABLE Dominiak.FACT_SALES_2013 (
    FactID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT FOREIGN KEY REFERENCES Dominiak.DIM_PRODUCT(ProductID),
    CustomerID INT FOREIGN KEY REFERENCES Dominiak.DIM_CUSTOMER(CustomerID),
    SalesPersonID INT FOREIGN KEY REFERENCES Dominiak.DIM_SALESPERSON(SalesPersonID),
    OrderDate INT NOT NULL,
    ShipDate INT,
    OrderQty INT NOT NULL,
    UnitPrice MONEY NOT NULL,
    UnitPriceDiscount MONEY NOT NULL,
    LineTotal MONEY NOT NULL,
);

CREATE TABLE Dominiak.FACT_SALES_2014 (
    FactID INT IDENTITY(1,1) PRIMARY KEY,
    ProductID INT FOREIGN KEY REFERENCES Dominiak.DIM_PRODUCT(ProductID),
    CustomerID INT FOREIGN KEY REFERENCES Dominiak.DIM_CUSTOMER(CustomerID),
    SalesPersonID INT FOREIGN KEY REFERENCES Dominiak.DIM_SALESPERSON(SalesPersonID),
    OrderDate INT NOT NULL,
    ShipDate INT,
    OrderQty INT NOT NULL,
    UnitPrice MONEY NOT NULL,
    UnitPriceDiscount MONEY NOT NULL,
    LineTotal MONEY NOT NULL,
);

WITH data AS (
    SELECT 
		Dominiak.FACT_SALES.ProductID, 
		Dominiak.FACT_SALES.CustomerID, 
		Dominiak.FACT_SALES.SalesPersonID, 
		Dominiak.FACT_SALES.OrderDate, 
		Dominiak.FACT_SALES.ShipDate,
		Dominiak.FACT_SALES.OrderQty,
		Dominiak.FACT_SALES.UnitPrice,
		Dominiak.FACT_SALES.UnitPriceDiscount,
		Dominiak.FACT_SALES.LineTotal
    FROM Dominiak.FACT_SALES
	WHERE OrderDate >= 20110101 AND OrderDate < 20120101
)
INSERT INTO Dominiak.FACT_SALES_2011
SELECT * FROM data;

WITH data AS (
    SELECT 
		Dominiak.FACT_SALES.ProductID, 
		Dominiak.FACT_SALES.CustomerID, 
		Dominiak.FACT_SALES.SalesPersonID, 
		Dominiak.FACT_SALES.OrderDate, 
		Dominiak.FACT_SALES.ShipDate,
		Dominiak.FACT_SALES.OrderQty,
		Dominiak.FACT_SALES.UnitPrice,
		Dominiak.FACT_SALES.UnitPriceDiscount,
		Dominiak.FACT_SALES.LineTotal
    FROM Dominiak.FACT_SALES
	WHERE OrderDate >= 20120101 AND OrderDate < 20130101
)
INSERT INTO Dominiak.FACT_SALES_2012
SELECT * FROM data;

WITH data AS (
    SELECT 
		Dominiak.FACT_SALES.ProductID, 
		Dominiak.FACT_SALES.CustomerID, 
		Dominiak.FACT_SALES.SalesPersonID, 
		Dominiak.FACT_SALES.OrderDate, 
		Dominiak.FACT_SALES.ShipDate,
		Dominiak.FACT_SALES.OrderQty,
		Dominiak.FACT_SALES.UnitPrice,
		Dominiak.FACT_SALES.UnitPriceDiscount,
		Dominiak.FACT_SALES.LineTotal
    FROM Dominiak.FACT_SALES
	WHERE OrderDate >= 20130101 AND OrderDate < 20140101
)
INSERT INTO Dominiak.FACT_SALES_2013
SELECT * FROM data;

WITH data AS (
    SELECT 
		Dominiak.FACT_SALES.ProductID, 
		Dominiak.FACT_SALES.CustomerID, 
		Dominiak.FACT_SALES.SalesPersonID, 
		Dominiak.FACT_SALES.OrderDate, 
		Dominiak.FACT_SALES.ShipDate,
		Dominiak.FACT_SALES.OrderQty,
		Dominiak.FACT_SALES.UnitPrice,
		Dominiak.FACT_SALES.UnitPriceDiscount,
		Dominiak.FACT_SALES.LineTotal
    FROM Dominiak.FACT_SALES
	WHERE OrderDate >= 20140101 AND OrderDate < 20150101
)
INSERT INTO Dominiak.FACT_SALES_2014
SELECT * FROM data;
