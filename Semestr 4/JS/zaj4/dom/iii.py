class Email:
    _domain = "@pwr.pl"

    def __init__(self, name, surname):
        self.name = name
        self.surname = surname

    @property
    def name(self):
        return self.name

    @name.setter
    def name(self, name):
        if len(name) > 0 and isinstance(name, str):
            if name.isalpha():
                self.name = name
            else:
                raise ValueError("Name can't contain numbers.")
        else:
            raise TypeError('Name must be a string')

    @name.deleter
    def name(self):
        del self._name

    @property
    def surname(self):
        return self._surname

    @surname.setter
    def surname(self, surname):
        if isinstance(surname, str):
            if len(surname) > 0 and surname.isalpha():
                self._surname = surname
            else:
                raise ValueError("Surname can't contain numbers.")
        else:
            raise TypeError('Surname must be a string')

    @surname.deleter
    def surname(self):
        del self._surname

    def create_email(self):
        return self._name.lower() + self._surname.lower() + Email._domain


email1 = Email("a", "v")