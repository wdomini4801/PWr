import random

kolory = ["trefl", "karo", "kier", "pik"]
figury = ["as", "król", "królowa", "joker", "10", "9"]

class Karta:
    def __init__(self, figura: str, kolor: str):
        if figura in figury and kolor in kolory:
            self.figura = figura
            self.kolor = kolor
        else:
            raise TypeError("Wrong value!")

talia = []
power = dict()

for figura in figury:
    for kolor in kolory:
        talia.append(Karta(figura, kolor))

for i in range(len(figury)):
    power[figury[i]] = 14-i

random.shuffle(talia)

gracz_1 = []
gracz_2 = []

for i in range(10):
    if i % 2 == 0 or i % 2 == 2:
        gracz_1.append(talia[i])
    else:
        gracz_2.append(talia[i])

print(gracz_1)
print(gracz_2)

while gracz_1 and gracz_2:
    card1 = gracz_1.pop(0)
    card2 = gracz_2.pop(0)

    if power[card1.figura] > power[card2.figura]:
        gracz_1.append(card1)
        gracz_1.append(card2)
    elif power[card1.figura] < power[card2.figura]:
        gracz_2.append(card1)
        gracz_2.append(card2)
    else:
        gracz_1.append(card1)
        gracz_2.append(card2)

if gracz_1:
    print("Wygrał gracz 1!")
else:
    print("Wygrał gracz 2!")
