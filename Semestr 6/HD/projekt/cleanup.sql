UPDATE accidents
SET [State Name] = COALESCE([State Name], 'Unknown')

UPDATE accidents
SET [Nearest Station] = COALESCE([Nearest Station], 'Unknown')

UPDATE accidents
SET [Track Type] = COALESCE([Track Type], 'Unknown')

UPDATE accidents
SET [Track Class] = COALESCE([Track Class], 'Unknown')

UPDATE accidents
SET [Weather Condition] = COALESCE([Weather Condition], 'Unknown')

UPDATE accidents
SET Visibility = COALESCE(Visibility, 'Unknown')

UPDATE accidents
SET [Highway User] = COALESCE([Highway User], 'Unknown')

UPDATE accidents
SET [User Age] = COALESCE([User Age], 0)

UPDATE accidents
SET [Highway User Action] = COALESCE([Highway User Action], 'Unknown')

UPDATE accidents
SET [Highway User Position] = COALESCE([Highway User Position], 'Unknown')

UPDATE accidents
SET [Crossing Warning Location] = COALESCE([Crossing Warning Location], 'Unknown')

UPDATE accidents
SET [Crossing Illuminated] = COALESCE([Crossing Illuminated], 'Unknown')

UPDATE accidents
SET Temperature = COALESCE(Temperature, -500)
