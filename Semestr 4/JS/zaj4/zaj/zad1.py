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
        elif height <= 0:
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

    def attack(self, other_monster):
        if self.strength > other_monster.strength:
            print(f'Monster {self.name} attacks {other_monster.name}')
            other_monster.strength -= 1
            self.strength -= 1
        else:
            print(f'Unable to attack {other_monster.name}!')

    def isAlive(self):
        return self.strength > 0 and self.weight > 0

    def strengthen(self):
        if self.weight > 0:
            print(f'Monster {self.name} strengthens!')
            self.strength += 1
            self.weight -= 1
        else:
            print('Unable to strengthen!')


class Fighter(Monster):
    def __init__(self, name, height, weight, strength, fightpower, teamwork):
        super().__init__(name, height, weight, strength)
        self.fightpower = fightpower
        self.teamwork = teamwork

    @property
    def fightpower(self):
        return self._fightpower

    @fightpower.setter
    def fightpower(self, fightpower):
        if not isinstance(fightpower, int):
            raise TypeError('Fightpower must be an integer number!')
        elif fightpower < 0 or fightpower > 200:
            raise ValueError('Fightpower must be between 0 and 200!')
        else:
            self._fightpower = fightpower

    @property
    def teamwork(self):
        return self._teamwork

    @teamwork.setter
    def teamwork(self, teamwork):
        if not isinstance(teamwork, bool):
            raise TypeError('Teamwork must be of type bool!')
        else:
            self._teamwork = teamwork

    def fight(self):
        print(f'Fighter {self.name} starts fighting!')
        self.fightpower -= 10


class Magician(Monster):
    def __init__(self, name, height, weight, strength, superpower):
        super().__init__(name, height, weight, strength)
        self.superpower = superpower

    @property
    def superpower(self):
        return self._superpower

    @superpower.setter
    def superpower(self, superpower):
        if not isinstance(superpower, bool):
            raise TypeError('Superpower must be of type bool!')
        else:
            self._superpower = superpower


class Thief(Monster):
    def __init__(self, name, height, weight, strength, group):
        super().__init__(name, height, weight, strength)
        self.group = group

    @property
    def group(self):
        return self._group

    @group.setter
    def group(self, group):
        if not isinstance(group, str):
            raise TypeError('Group name must be of type string!')
        elif len(group) == 0:
            raise ValueError('Group name must have at least 1 character!')
        else:
            self._group = group


class Archer(Fighter):
    def __init__(self, name, height, weight, strength, fightpower, teamwork, arrow_size):
        super().__init__(name, height, weight, strength, fightpower, teamwork)
        self.arrow_size = arrow_size

    @property
    def arrow_size(self):
        return self._arrow_size

    @arrow_size.setter
    def arrow_size(self, arrow_size):
        if not isinstance(arrow_size, int):
            raise TypeError('Fightpower must be an integer number!')
        elif arrow_size < 0 or arrow_size > 50:
            raise ValueError('Fightpower must be between 0 and 50!')
        else:
            self._arrow_size = arrow_size

    def fight(self):
        super().fight()
        print('Starts shooting!')
        self.fightpower -= 5


class Shooter(Fighter):
    def __init__(self, name, height, weight, strength, fightpower, teamwork, precision):
        super().__init__(name, height, weight, strength, fightpower, teamwork)
        self.precision = precision

    @property
    def precision(self):
        return self._precision

    @precision.setter
    def precision(self, precision):
        if not isinstance(precision, int):
            raise TypeError('Fightpower must be an integer number!')
        elif precision < 0 or precision > 100:
            raise ValueError('Fightpower must be between 0 and 100!')
        else:
            self._precision = precision


print(issubclass(Fighter, Monster))
print(issubclass(Archer, Monster))
print(issubclass(Archer, Fighter))

monster1 = Monster("Ola", 180, 200, 97)
fighter1 = Fighter('Kuba', 170, 180, 44, 140, True)

print()

print(monster1.__class__.__name__)
print(fighter1.__class__.__name__)

print()

monster1.strengthen()
fighter1.strengthen()

print(monster1.strength)
print(fighter1.strength)

archer1 = Archer('Maciek', 200, 50, 49, 150, False, 16)

fighter1.fight()
archer1.fight()

print(archer1.name)
