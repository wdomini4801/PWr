class Monster:

    label = 'MONSTER'

    def __init__(self, name, height, weight, strength):
        self.name = name
        self.height = height
        self.weight = weight
        self.strength = strength

    @property
    def name(self):
        return self._name

    @name.setter
    def name(self, name):
        if not isinstance(name, str):
            raise TypeError('Name must be of type string!')
        elif len(name) == 0:
            raise ValueError('Name must be at least 1 character long!')
        else:
            self._name = name

    @property
    def height(self):
        return self._height

    @height.setter
    def height(self, height):
        if not isinstance(height, int):
            raise ValueError('Height must be an integer number!')
        elif height <=0:
            raise ValueError('Height must be positive!')
        else:
            self._height = height

    @height.deleter
    def height(self):
        del self._height

    @property
    def weight(self):
        return self._weight

    @weight.setter
    def weight(self, weight):
        if not isinstance(weight, int):
            raise ValueError('Weight must be an integer number!')
        elif weight <= 0:
            raise ValueError('Weight must be positive!')
        else:
            self._weight = weight

    @property
    def strength(self):
        return self._strength

    @strength.setter
    def strength(self, strength):
        if not isinstance(strength, int):
            raise ValueError('Strength must be an integer number!')
        elif strength < 0 or strength > 100:
            raise ValueError('Strength must be between 0 and 100!')
        else:
            self._strength = strength

    def attacking(func):
        def wrapper(self, other_monster):
            print("Start attacking!")
            result = func(self, other_monster)
            if result == 0:
                print(f'Monster {self.name} attacked {other_monster.name}')
                print(f"Monster {self.name}'s strength: {self.strength}")
                print(f"Monster {other_monster.name}'s strength: {other_monster.strength}")
            else:
                print(f'Unable to attack {other_monster.name}!')
        return wrapper

    @attacking
    def attack(self, other_monster):
        if self.strength > other_monster.strength:
            other_monster.strength -= 1
            self.strength -= 1
            return 0
        else:
            return -1

    def isAlive(self):
        return self.strength > 0 and self.weight > 0

    def strengthen(self):
        if self.weight > 0:
            print(f'Monster {self.name} strengthens!')
            self.strength += 1
            self.weight -= 1
        else:
            print('Unable to strengthen!')


monster1 = Monster('Ola', 200, 100, 100)
monster2 = Monster('Kuba', 100, 50, 50)

monster1.attack(monster2)
print(monster1.strength)
monster2.strengthen()
print(monster2.strength)
print(monster2.weight)