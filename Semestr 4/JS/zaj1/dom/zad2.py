autoMode = True
day = False
badWeather = ""
lightsOn = False

if not autoMode:
    print("Auto mode off, be careful!")
elif not day:
    lightsOn = True
    print("It's night! Turning on lights!")
elif badWeather != "":
    lightsOn = True
    print("Bad weather conditions! Turning on lights!")
else:
    lightsOn = False
    print("Lights are not necessary! Turning off lights!")

print("Lights on") if lightsOn else print("Lights off")
