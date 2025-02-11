SELECT CountryRegionCode, st.Name, p.Name, LineTotal, 
SUM(LineTotal) OVER ( 
		PARTITION BY CountryRegionCode, st.Name ORDER BY LineTotal 
		ROWS UNBOUNDED PRECEDING) 'Narastajaco'
FROM Sales.SalesTerritory st
JOIN Sales.SalesOrderHeader soh ON soh.TerritoryID = st.TerritoryID
JOIN Sales.SalesOrderDetail sod ON sod.SalesOrderID = soh.SalesOrderID
JOIN Production.Product p ON p.ProductID = sod.ProductID
GROUP BY CountryRegionCode, st.Name, p.Name, LineTotal
ORDER BY 1, 2;
