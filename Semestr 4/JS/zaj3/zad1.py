class Student:

    __name = ""
    __surname = ""
    _index_number = "000000"

    def __init__(self, name, surname, index_number):
        if isinstance(name, str) and isinstance(surname, str) and isinstance(index_number, str):
            self.__name = name
            self.__surname = surname
            self._index_number = index_number
        else:
            raise TypeError('Wrong type!')

    @property
    def name(self):
        return self.__name

    @name.setter
    def name(self, name):
        if not isinstance(name, str):
            raise TypeError('Name must be of type string!')
        elif len(name) == 0:
            raise ValueError('Name must be at least 1 character long!')
        else:
            self.__name = name

    @property
    def surname(self):
        return self.__surname

    @surname.setter
    def surname(self, surname):
        if not isinstance(surname, str):
            raise TypeError('Surname must be of type string!')
        elif len(surname) == 0:
            raise ValueError('Surname must be at least 1 character long!')
        else:
            self.__surname = surname

    @property
    def index_number(self):
        return self._index_number

    @index_number.setter
    def index_number(self, index_number):
        if not isinstance(index_number, str):
            raise TypeError('Index number must be of type string!')
        elif len(index_number) != 6:
            raise ValueError('Index number must have exactly 6 characters!')
        elif not index_number.isnumeric():
            raise ValueError('Index number must have numbers only!')
        else:
            self._index_number = index_number

    def print_object(self):
        print('{0:15s} \t {1:20s} \t {2:6s}'.format(self.__name, self.__surname, self._index_number))

    def print_attributes(self):
        for attr in self.__dict__.keys():
            print(attr)
        print()

    def capitalize_name(self):
        self.__name = self.__name.capitalize()

    def upper_surname(self):
        self.__surname = self.__surname.upper()

    def is_index_valid(self):
        return self._index_number.startswith('260')

    def set_index_number(self, new_index_number):
        if isinstance(new_index_number, str):
            self._index_number = new_index_number
        else:
            raise TypeError('Wrong type')

    def set_height(self, new_height):
        if isinstance(new_height, int):
            self.height = new_height
        else:
            raise TypeError('Wrong type')


students = [Student('Wojciech', 'Dominiak', '260402'), Student('Jan', 'Kowalski', '260494'),
            Student('Wiktoria', 'Nowak', '222222'), Student('Marta', 'Wiśniewska', '260405')]

for student in students:
    student.print_object()

setattr(students[0], "height", 177)
setattr(students[1], "height", 180)
setattr(students[2], "height", 150)
setattr(students[3], "height", 190)

students[0].capitalize_name()
students[0].upper_surname()
print(students[2].is_index_valid())
students[0].print_object()

students[0].index_number = "260444"
students[0].print_object()

students[0].height = 180
print(students[0].height)

print(Student._index_number)

