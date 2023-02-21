import random

countries = ['Uruguay', 'Russia', 'Saudi Arabia', 'Egypt', 'Spain', 'Portugal', 'Iran', 'Morocco', 'France',
             'Denmark', 'Peru', 'Australia', 'Croatia', 'Argentina', 'Nigeria', 'Iceland', 'Brazil',
             'Switzerland', 'Serbia', 'Costa Rica', 'Sweden', 'Mexico', 'Korea Republic', 'Germany',
             'Belgium', 'England', 'Tunisia', 'Panama', 'Colombia', 'Japan', 'Senegal', 'Poland']

random.shuffle(countries)
listDict = dict()

for i in range(0, 8):
    listDict[chr(ord("A") + i)] = [countries[random.randrange(0, len(countries))]]

print(listDict)

