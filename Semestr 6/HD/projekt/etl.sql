CREATE TABLE Dominiak.DIM_TIME(
	TimeID INT PRIMARY KEY,
	Year INT,
	Month INT,
	Day INT,
	Hour INT,
	Month_name VARCHAR(20),
);

CREATE TABLE Dominiak.DIM_LOCATION(
	LocationID INT IDENTITY(1,1) PRIMARY KEY,
	State VARCHAR(30),
	NearestStation VARCHAR(30)
);

CREATE TABLE Dominiak.DIM_VEHICLE(
	VehicleID INT IDENTITY(1,1) PRIMARY KEY,
	Type VARCHAR(10),
	Class VARCHAR(1)
);

CREATE TABLE Dominiak.DIM_CONDITION(
	ConditionID INT IDENTITY(1,1) PRIMARY KEY,
	Temperature FLOAT,
	WeatherConditions VARCHAR(10),
	Visibility VARCHAR(10)
);

CREATE TABLE Dominiak.DIM_RAILROAD(
	RailroadID INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(100)
);

CREATE TABLE Dominiak.DIM_HIGHWAYUSER(
	UserID INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(30),
	Age INT,
	Action VARCHAR(50),
	Position VARCHAR(50),
);

CREATE TABLE Dominiak.DIM_CROSSING(
	CrossingID INT IDENTITY(1,1) PRIMARY KEY,
	WarningLocation VARCHAR(50),
	CrossingIlluminated VARCHAR (10)
);

CREATE TABLE Dominiak.MonthHelper(
	MonthID INT IDENTITY(1,1) PRIMARY KEY,
	Name VARCHAR(20) NOT NULL
);

INSERT INTO Dominiak.MonthHelper VALUES
('styczeń'),('luty'),('marzec'),('kwiecień'),('maj'),('czerwiec'),('lipiec'),
('sierpień'),('wrzesień'),('październik'),('listopad'),('grudzień');

WITH data AS (
	SELECT DISTINCT
	COALESCE(DATEPART(YYYY, a.Date) * 1000000, 0) + a.Month * 10000 + a.Day * 100 + DATEPART(HOUR, PARSE(Time AS TIME)) AS TimeID,
	COALESCE(DATEPART(YYYY, a.Date), 0) AS Year,
	a.Month AS Month,
	a.Day As Day,
	DATEPART(HOUR, PARSE(Time AS TIME)) AS Hour,
	mh.Name As Month_name
	FROM accidents a 
	JOIN Dominiak.MonthHelper mh ON a.Month = mh.MonthID 
	WHERE a.Time NOT LIKE '%None'
)
INSERT INTO Dominiak.DIM_TIME
SELECT * FROM data;

WITH data AS (
	SELECT DISTINCT
	COALESCE(DATEPART(YYYY, a.Date) * 1000000, 0) + a.Month * 10000 + a.Day * 100 + 24 AS TimeID,
	COALESCE(DATEPART(YYYY, a.Date), 0) AS Year,
	a.Month AS Month,
	a.Day As Day,
	24 AS Hour,
	mh.Name As Month_name
	FROM accidents a 
	JOIN Dominiak.MonthHelper mh ON a.Month = mh.MonthID 
	WHERE a.Time LIKE '%None'
)
INSERT INTO Dominiak.DIM_TIME
SELECT * FROM data;

SELECT * FROM Dominiak.DIM_TIME
WHERE TimeID = 20;

WITH data AS (
	SELECT DISTINCT [Track Type], [Track Class]
	FROM accidents
)
INSERT INTO Dominiak.DIM_VEHICLE(Type, Class)
SELECT * FROM data;

WITH data AS (
	SELECT DISTINCT Temperature, [Weather Condition], Visibility
	FROM accidents
)
INSERT INTO Dominiak.DIM_CONDITION(Temperature, WeatherConditions, Visibility)
SELECT * FROM data;

WITH data AS (
	SELECT DISTINCT [Railroad Name]
	FROM accidents
)
INSERT INTO Dominiak.DIM_RAILROAD(Name)
SELECT * FROM data;

WITH data AS (
	SELECT DISTINCT [Highway User], [User Age], [Highway User Action], [Highway User Position]
	FROM accidents
)
INSERT INTO Dominiak.DIM_HIGHWAYUSER(Name, Age, Action, Position)
SELECT * FROM data;

WITH data AS (
	SELECT DISTINCT [Crossing Warning Location], [Crossing Illuminated]
	FROM accidents
)
INSERT INTO Dominiak.DIM_CROSSING(WarningLocation, CrossingIlluminated)
SELECT * FROM data;

CREATE TABLE Dominiak.FACT_ACCIDENT (
	FactID INT IDENTITY(1,1) PRIMARY KEY,
	TimeID INT,
	LocationID INT,
	VehicleID INT,
	HighwayUserID INT,
	RailroadID INT,
	ConditionID INT,
	CrossingID INT,
	UsersKilled INT,
	UsersInjured INT,
	PassengersKilled INT,
	PassengersInjured INT,
	NumberOfVehicles INT,
	TrainSpeed FLOAT,
	VehicleSpeed FLOAT
);

WITH data AS (
	SELECT 
		COALESCE(DATEPART(YYYY, a.Date) * 1000000, 0) + a.Month * 10000 + a.Day * 100 + DATEPART(HOUR, PARSE(Time AS TIME)) AS TimeID,
		dl.LocationID,
		dv.VehicleID,
		dh.UserID,
		dr.RailroadID,
		dc.ConditionID,
		dc1.CrossingID,
		[Crossing Users Killed For Reporting Railroad],
		[Crossing Users Injured For Reporting Railroad],
		[Passengers Killed For Reporting Railroad],
		[Passengers Injured For Reporting Railroad],
		[Number of Cars],
		[Train Speed],
		[Estimated Vehicle Speed]
	FROM accidents a
	LEFT JOIN Dominiak.DIM_LOCATION dl ON dl.State = a.[State Name] AND dl.NearestStation = a.[Nearest Station]
	LEFT JOIN Dominiak.DIM_VEHICLE dv ON dv.Type = a.[Track Type] AND dv.Class = a.[Track Class]
	LEFT JOIN Dominiak.DIM_CONDITION dc ON dc.Temperature = a.Temperature AND dc.WeatherConditions = a.[Weather Condition] AND dc.Visibility = a.Visibility
	LEFT JOIN Dominiak.DIM_RAILROAD dr ON dr.Name = a.[Railroad Name]
	LEFT JOIN Dominiak.DIM_HIGHWAYUSER dh ON dh.Name = a.[Highway User] AND dh.Age = a.[User Age] AND dh.Action = a.[Highway User Action] AND dh.Position = a.[Highway User Position]
	LEFT JOIN Dominiak.DIM_CROSSING dc1 ON dc1.WarningLocation = a.[Crossing Warning Location] AND dc1.CrossingIlluminated = a.[Crossing Illuminated]
	WHERE a.Time NOT LIKE '%None'
)
INSERT INTO Dominiak.FACT_ACCIDENT
SELECT * FROM data;

WITH data AS (
	SELECT 
		COALESCE(DATEPART(YYYY, a.Date) * 1000000, 0) + a.Month * 10000 + a.Day * 100 + 24 AS TimeID,
		dl.LocationID,
		dv.VehicleID,
		dh.UserID,
		dr.RailroadID,
		dc.ConditionID,
		dc1.CrossingID,
		[Crossing Users Killed For Reporting Railroad],
		[Crossing Users Injured For Reporting Railroad],
		[Passengers Killed For Reporting Railroad],
		[Passengers Injured For Reporting Railroad],
		[Number of Cars],
		[Train Speed],
		[Estimated Vehicle Speed]
	FROM accidents a
	LEFT JOIN Dominiak.DIM_LOCATION dl ON dl.State = a.[State Name] AND dl.NearestStation = a.[Nearest Station]
	LEFT JOIN Dominiak.DIM_VEHICLE dv ON dv.Type = a.[Track Type] AND dv.Class = a.[Track Class]
	LEFT JOIN Dominiak.DIM_CONDITION dc ON dc.Temperature = a.Temperature AND dc.WeatherConditions = a.[Weather Condition] AND dc.Visibility = a.Visibility
	LEFT JOIN Dominiak.DIM_RAILROAD dr ON dr.Name = a.[Railroad Name]
	LEFT JOIN Dominiak.DIM_HIGHWAYUSER dh ON dh.Name = a.[Highway User] AND dh.Age = a.[User Age] AND dh.Action = a.[Highway User Action] AND dh.Position = a.[Highway User Position]
	LEFT JOIN Dominiak.DIM_CROSSING dc1 ON dc1.WarningLocation = a.[Crossing Warning Location] AND dc1.CrossingIlluminated = a.[Crossing Illuminated]
	WHERE a.Time LIKE '%None'
)
INSERT INTO Dominiak.FACT_ACCIDENT
SELECT * FROM data;

SELECT * FROM Dominiak.DIM_CONDITION dc;

UPDATE Dominiak.DIM_CONDITION
SET Temperature = ROUND((Temperature - 32) * (5.0/9.0), 2)

SELECT * FROM Dominiak.FACT_ACCIDENT fa

UPDATE Dominiak.FACT_ACCIDENT
SET TrainSpeed = ROUND(TrainSpeed * 1.609344, 2)

UPDATE Dominiak.FACT_ACCIDENT
SET VehicleSpeed = ROUND(VehicleSpeed * 1.609344, 2)

ALTER TABLE Dominiak.FACT_ACCIDENT
	ADD CONSTRAINT FK_TimeID FOREIGN KEY (TimeID) REFERENCES Dominiak.DIM_TIME(TimeID),
	CONSTRAINT FK_LocationID FOREIGN KEY (LocationID) REFERENCES Dominiak.DIM_LOCATION(LocationID),
	CONSTRAINT FK_VehicleID FOREIGN KEY (VehicleID) REFERENCES Dominiak.DIM_VEHICLE(VehicleID),
	CONSTRAINT FK_ConditionID FOREIGN KEY (ConditionID) REFERENCES Dominiak.DIM_CONDITION(ConditionID),
	CONSTRAINT FK_HighwayUserID FOREIGN KEY (HighwayUserID) REFERENCES Dominiak.DIM_HIGHWAYUSER(UserID),
	CONSTRAINT FK_RailroadID FOREIGN KEY (RailroadID) REFERENCES Dominiak.DIM_RAILROAD(RailroadID),
	CONSTRAINT FK_CrossingID FOREIGN KEY (CrossingID) REFERENCES Dominiak.DIM_CROSSING(CrossingID);

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'FACT_ACCIDENT')
   DROP TABLE Dominiak.FACT_ACCIDENT;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_TIME')
   DROP TABLE Dominiak.DIM_TIME;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_LOCATION')
   DROP TABLE Dominiak.DIM_LOCATION;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_VEHICLE')
   DROP TABLE Dominiak.DIM_VEHICLE;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_CONDITION')
   DROP TABLE Dominiak.DIM_CONDITION;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_RAILROAD')
   DROP TABLE Dominiak.DIM_RAILROAD;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_HIGHWAYUSER')
   DROP TABLE Dominiak.DIM_HIGHWAYUSER;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DIM_CROSSING')
   DROP TABLE Dominiak.DIM_CROSSING;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'DayHelper')
   DROP TABLE Dominiak.DayHelper;

IF EXISTS(SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'Dominiak' AND TABLE_NAME = 'MonthHelper')
   DROP TABLE Dominiak.MonthHelper;
