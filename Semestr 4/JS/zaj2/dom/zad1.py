import math

angle = 360

def degreestoradians(angle):
    return math.pi * angle / 180

print(degreestoradians(angle) == math.radians(angle))
print(math.radians(angle))

print(degreestoradians(90) == math.radians(90))
print(math.radians(90))

print(degreestoradians(45) == math.radians(45))
print(math.radians(45))


