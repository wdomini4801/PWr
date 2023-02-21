# zadanie 1

class ControlledText:

    @staticmethod
    def validate_text(text):
        if len(text) == 0:
            raise ValueError('Text must have at least one character!')
        elif not text.isprintable():
            raise ValueError('Text contains non-printable characters!')
        elif ' ' in text:
            raise ValueError('Text cannot contain any space!')
        else:
            return 0

    def __init__(self, text):
        if ControlledText.validate_text(text) == 0:
            self._text = text

    @property
    def text(self):
        return self._text

    @text.setter
    def text(self, text):
        if ControlledText.validate_text(text) == 0:
            self._text = text

    def __lt__(self, other):
        return self.text < other.text

    def __gt__(self, other):
        return self.text > other.text

    def __le__(self, other):
        return self.text <= other.text

    def __ge__(self, other):
        return self.text >= other.text

    def __eq__(self, other):
        return self.text == other.text

    def __str__(self):
        return self.text


# text1 = ControlledText('Hej!')
# text2 = ControlledText('Hej!')
# text3 = ControlledText('Abc')
# print(text1.text)
# print(text2.text)
# print(text1 <= text2)
# print(text1 < text3)


# zadanie 2

class First_name(ControlledText):

    all_content = []

    try:
        with open('PopularneImiona.txt', 'r', encoding='utf=8') as file:
            for line in file:
                line = line.replace('\n', '')
                if not line == '':
                    all_content.append(line)

    except IOError:
        print("Could not read file")

    possible_male_names = all_content[all_content.index('Mężczyźni')+1:len(all_content)]
    possible_female_names = all_content[all_content.index('Kobiety')+1:all_content.index('Mężczyźni')]

    @staticmethod
    def validate_name(name):
        name = name.title()
        if name in First_name.possible_male_names or name in First_name.possible_female_names:
            return name
        else:
            raise ValueError('This name is not allowed!')

    def __init__(self, text):
        super().__init__(text)
        self._text = First_name.validate_name(text)

    @property
    def name(self):
        return self._text

    @name.setter
    def name(self, name):
        self.text = name
        self._text = First_name.validate_name(name)

    def is_female(self):
        return First_name.female_name(self.name)

    def is_male(self):
        return not self.is_female()

    @staticmethod
    def female_name(name):
        if name in First_name.possible_male_names:
            return False
        elif name in First_name.possible_female_names:
            return True
        else:
            raise ValueError('This name is not allowed!')

    @staticmethod
    def male_name(name):
        return not First_name.female_name(name)


# name1 = First_name('Michał')
# name2 = First_name('Kaja')
# print(name1.name)
# name1.name = 'wOjciech'
# print(name1.name)
# print(name1.name)
# print(name1.text)
# print(name1.is_female())
# print(name2.is_female())
# print(First_name.female_name('Kaja'))
# print(First_name.male_name('Jowita'))


# zadanie 3

class Last_name(ControlledText):

    @staticmethod
    def validate_last_name(lname):
        if '-' in lname:
            spl = lname.split('-')
            before, after = spl[0], spl[1]
            if before.isalpha() and after.isalpha():
                valid_lastname = before.title() + '-' + after.title()
                return valid_lastname
            else:
                raise ValueError('Invalid last name!')
        elif lname.isalpha():
            return lname.title()
        else:
            raise ValueError('Invalid last name!')

    def __init__(self, text):
        super().__init__(text)
        self._text = Last_name.validate_last_name(text)

    @property
    def lname(self):
        return self._text

    @lname.setter
    def lname(self, lname):
        self.text = lname
        self._text = Last_name.validate_last_name(lname)


# lname1 = Last_name('nowak-sadowy')
# lname1.lname = 'KOWALSKI'
# print(lname1.lname)
# print(lname1.text)


# zadanie 4

class Ident_number(ControlledText):

    @staticmethod
    def is_number_valid(number):
        if not number.isdecimal():
            return False
        elif len(number) != 9:
            return False
        else:
            sum = 0
            for i in range(7):
                sum += int(number[i])
            control_number = int(number[7])*10 + int(number[8])
            if sum % 97 == control_number:
                return True
            else:
                return False

    def __init__(self, text):
        super().__init__(text)
        if Ident_number.is_number_valid(text):
            self._text = text
        else:
            raise ValueError('Number is incorrect!')

    @property
    def number(self):
        return self._text

    @number.setter
    def number(self, number):
        raise PermissionError('Change of this attribute is forbidden!')


# id1 = Ident_number('999999963')
# id1.number = '999999963'


# zadanie 5

class Person:

    def __init__(self, ident, first_name, last_name):
        if isinstance(ident, Ident_number) and isinstance(first_name, First_name) and isinstance(last_name, Last_name):
            self.ident = ident
            self.first_name = first_name
            self.last_name = last_name
        else:
            raise TypeError('Invalid type of arguments!')

    @classmethod
    def fromString(cls, content):
        if ' ' in content or ';' in content or '/' in content:
            personal_data = content.replace(';', ' ').replace('/', ' ').split()
            return cls(Ident_number(personal_data[0]), First_name(personal_data[1]), Last_name(personal_data[2]))
        else:
            raise ValueError('Incorrect string input!')

    def __str__(self):
        return '{0:9s} \t {1:15s} \t {2:15s}'.format(self.ident.number, self.first_name.name, self.last_name.lname)


# p1 = Person(Ident_number('999999963'), First_name('Michał'), Last_name('kowalski'))
# p2 = Person.fromString('111111107; Pola Pawlikowska-jasnorzeWSka')
#
# print(p1)
# print(p2)
#
# p1.first_name.name = 'JaKub'
# print(p1)


# zadanie 6
def read_and_create(filename):
    persons = []
    try:
        with open(filename, 'r', encoding='utf=8') as file:
            for line in file:
                persons.append(Person.fromString(line))
    except IOError:
        print("Could not read file")

    return persons


for person in read_and_create('plik2.txt'):
    print(person)
