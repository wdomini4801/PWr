SELECT Years, AVG(Amount) AS 'Average amount', AVG(Average) as 'Average sum'
FROM 
	(SELECT SalesPersonID, 2023-YEAR(HireDate) AS 'Years', CAST(COUNT(*) AS FLOAT) AS 'Amount', CAST(AVG(TotalDue) AS FLOAT) AS 'Average'
	FROM Sales.SalesPerson
	JOIN HumanResources.Employee ON SalesPerson.BusinessEntityID = Employee.BusinessEntityID
	JOIN Sales.SalesOrderHeader ON SalesOrderHeader.SalesPersonID = SalesPerson.BusinessEntityID
	GROUP BY SalesPersonID, 2023-YEAR(HireDate)) AS sub
GROUP BY Years;
--GROUP BY 2023-YEAR(HireDate);
