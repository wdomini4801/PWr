# zadanie 3
import random
class Creature:
    position = 0

    def __init__(self, name="Burek", strength=1, speed=1, agility=1, streetRespect="medium"):
        self.name = name
        self.strength = strength
        self.speed = speed
        self.agility = agility
        self.streetRespect = streetRespect

    @property
    def name(self):
        return self._name

    @name.setter
    def name(self, name):
        if isinstance(name, str):
            if len(name) > 0:
                self._name = name
            else:
                raise ValueError("Name must have length > 0.")
        else:
            raise TypeError('Name must be a string')

    @name.deleter
    def name(self):
        del self._name

    @property
    def strength(self):
        return self._strength

    @strength.setter
    def strength(self, strength):
        if isinstance(strength, (int, float)):
            if strength > 0:
                self._strength = strength
            else:
                raise TypeError('The strength attribute must be positive.')
        else:
            raise ValueError('The strength attribute must be an int or float value.')

    @strength.deleter
    def strength(self):
        del self._strength

    @property
    def speed(self):
        return self._speed

    @speed.setter
    def speed(self, speed):
        if isinstance(speed, (int, float)):
            if speed > 0:
                self._speed = speed
            else:
                raise TypeError('The speed attribute must be positive.')
        else:
            raise ValueError('The speed attribute must be an int or float value.')

    @speed.deleter
    def speed(self):
        del self._speed

    @property
    def agility(self):
        return self._agility

    @agility.setter
    def agility(self, agility):
        if isinstance(agility, (int, float)):
            if agility > 0:
                self._agility = agility
            else:
                raise TypeError('The agility attribute must be positive.')
        else:
            raise ValueError('The agility attribute must be an int or float value.')

    @agility.deleter
    def agility(self):
        del self._agility

    @property
    def streetRespect(self):
        return self._streetRespect

    @streetRespect.setter
    def streetRespect(self, streetRespect):
        if isinstance(streetRespect, str):
            if len(streetRespect) > 0 and streetRespect.isalpha():
                self._streetRespect = streetRespect
            else:
                if len(streetRespect) == 0:
                    raise ValueError("streetRespect must have length > 0.")
                else:
                    raise ValueError("streetRespect can't contain numbers.")
        else:
            raise TypeError('streetRespect must be a string')

    @streetRespect.deleter
    def streetRespect(self):
        del self._streetRespect

    def fight(self, enemyStrength):
        if enemyStrength > self.strength:
            self.streetRespect = "low"
            if self.strength >= 5:
                self.strength -= 5
        elif enemyStrength == self.strength:
            self.streetRespect = "medium"
        else:
            print("The Creatures talked this through and are buddies now!")
            self.streetRespect = "high"
            self.strength += 5

    def move(self, newPosition):
        self.position = newPosition

    def runAway(self):
        self.position = 100
        self.streetRespect = "low"

    def printInfo(self):
        print(self.name, self.strength, self.streetRespect)


myPet = Creature("Burek", 10, 20, 4)
print(myPet.__dict__)
print()

myPet.move(20)
print(myPet.__dict__)
print()
print("Creature class position:", Creature.position)
print("Instance myPet position", myPet.position)
myPet.runAway()
print(myPet.__dict__)
print()
print("Creature class position:", Creature.position)
print("Instance myPet position", myPet.position)
myPet.fight(5)
myPet.fight(25)

myNewPet = Creature("Burek Junior", 5, 200, 100)
print(myNewPet.__dict__)
print()

print(myNewPet.__dict__)
print()

myNewPet.move(1000)
print(myNewPet.__dict__)
print()
print("Creature class position:", Creature.position)
print("Instance myNewPet position", myNewPet.position)
myNewPet.runAway()
print(myNewPet.__dict__)
print()
print("Creature class position:", Creature.position)
print("Instance myNewPet position", myNewPet.position)
myNewPet.fight(-5)


class Game:
    __numberOfGames = 0

    def __init__(self, numberOfCreatures, winningStreetRespect, maxStrength, creatures, numberOfIterations, level=0):
        Game.__numberOfGames += 1
        print("Game has been created!")
        self.level = level
        self.numberOfCreatures = numberOfCreatures
        self.streetRespect = winningStreetRespect
        self.maxStrength = maxStrength
        self.creatures = creatures
        self.numberOfIterations = numberOfIterations

    @property
    def level(self):
        return self._level

    @level.setter
    def level(self, level):
        if isinstance(level, (int, float)):
            if level >= 0:
                self._level = level
            else:
                raise TypeError('The level attribute must be non-negative.')
        else:
            raise ValueError('The level attribute must be an int or float value.')

    @level.deleter
    def level(self):
        del self._level

    @property
    def numberOfCreatures(self):
        return self._numberOfCreatures

    @numberOfCreatures.setter
    def numberOfCreatures(self, numberOfCreatures):
        if isinstance(numberOfCreatures, (int, float)):
            if numberOfCreatures > 0:
                self._numberOfCreatures = numberOfCreatures
            else:
                raise TypeError('The numberOfCreatures attribute must be positive.')
        else:
            raise ValueError('The numberOfCreatures attribute must be an int or float value.')

    @numberOfCreatures.deleter
    def numberOfCreatures(self):
        del self._numberOfCreatures

    @property
    def streetRespect(self):
        return self._streetRespect

    @streetRespect.setter
    def streetRespect(self, streetRespect):
        if isinstance(streetRespect, str):
            if len(streetRespect) > 0 and streetRespect.isalpha():
                self._streetRespect = streetRespect
            else:
                if len(streetRespect) == 0:
                    raise ValueError("streetRespect must have length > 0.")
                else:
                    raise ValueError("streetRespect can't contain numbers.")
        else:
            raise TypeError('streetRespect must be a string')

    @streetRespect.deleter
    def streetRespect(self):
        del self._streetRespect

    @property
    def maxStrength(self):
        return self._maxStrength

    @maxStrength.setter
    def maxStrength(self, maxStrength):
        if isinstance(maxStrength, (int, float)):
            if maxStrength > 0:
                self._maxStrength = maxStrength
            else:
                raise TypeError('The maxStrength attribute must be positive.')
        else:
            raise ValueError('The maxStrength attribute must be an int or float value.')

    @maxStrength.deleter
    def maxStrength(self):
        del self._maxStrength

    @property
    def numberOfIterations(self):
        return self._numberOfIterations

    @numberOfIterations.setter
    def numberOfIterations(self, numberOfIterations):
        if isinstance(numberOfIterations, (int, float)):
            if numberOfIterations > 0:
                self._numberOfIterations = numberOfIterations
            else:
                raise TypeError('The numberOfIterations attribute must be positive.')
        else:
            raise ValueError('The numberOfIterations attribute must be an int or float value.')

    @numberOfIterations.deleter
    def numberOfIterations(self):
        del self._numberOfIterations

    @property
    def creatures(self):
        return self._creatures

    @creatures.setter
    def creatures(self, creatures):
        for creature in creatures:
            if not isinstance(creature, Creature):
                raise TypeError('Every creature must be of type Creature.')
        self._creatures = creatures

    @creatures.deleter
    def creatures(self):
        del self._creatures

    def numberOfGames(self):
        return Game.__numberOfGames

    def playGame(self):
        for creature in range(self.numberOfIterations):
            creature1 = random.randrange(0, numberOfCreatures)
            creature2 = random.randrange(0, numberOfCreatures)

            while creature1 == creature2:
                creature1 = random.randrange(0, numberOfCreatures)
                creature2 = random.randrange(0, numberOfCreatures)

            self.creatures[creature1].fight(self.creatures[creature2].strength())
            self.creatures[creature2].fight(self.creatures[creature1].strength())

            for creature in self.creatures:
                if creature.strength() > maxStrength / 2:
                    self.level += 1

        for creature in self.creatures:
            if creature.streetRespect() == self.streetRespect:
                creature.printInfo()


listOfCreatures = []
numberOfCreatures = 50
maxStrength = 50

for i in range(numberOfCreatures):
    listOfCreatures.append(Creature("Burek", random.randrange(1, maxStrength)))

print("number of games:", Game.numberOfGames())
game = Game(numberOfCreatures, "high", maxStrength, listOfCreatures, 100, 0)
print("number of games:", Game.numberOfGames())
game.playGame()