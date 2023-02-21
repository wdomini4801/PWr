class Employee:

    counter = 0

    def __init__(self, name, surname, address, mail, money_per_month):
        self.name = name
        self.surname = surname
        self.address = address
        self.mail = mail
        self.money_per_month = money_per_month
        Employee.counter += 1

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
    def surname(self):
        return self._surname

    @surname.setter
    def surname(self, surname):
        if not isinstance(surname, str):
            raise TypeError('Surname must be of type string!')
        elif len(surname) == 0:
            raise ValueError('Surname must be at least 1 character long!')
        else:
            self._surname = surname

    @property
    def address(self):
        return self._address

    @address.setter
    def address(self, address):
        if not isinstance(address, str):
            raise TypeError('Name must be of type string!')
        else:
            self._address = address

    @address.deleter
    def address(self):
        del self._address

    @property
    def mail(self):
        return self._mail

    @mail.setter
    def mail(self, mail):
        if not isinstance(mail, str):
            raise TypeError('Mail must be of type string!')
        elif '@' not in mail:
            raise ValueError('Mail must contain "@" character!')
        else:
            self._mail = mail

    @mail.deleter
    def mail(self):
        del self._mail

    @property
    def money_per_month(self):
        return self._money_per_month

    @money_per_month.setter
    def money_per_month(self, money_per_month):
        if not isinstance(money_per_month, (int, float)):
            raise TypeError('Money must be of type int or float!')
        elif money_per_month < 0:
            raise ValueError('Money must be greater than or equal 0!')
        else:
            self._money_per_month = money_per_month

    @money_per_month.deleter
    def money_per_month(self):
        del self._money_per_month

    def money_in_year(self):
        return 12 * self.money_per_month

    def show(self):
        print('{0:15s} \t {1:20s} \t {2:20s}'.format(self.name, self.surname, self.mail))


employee1 = Employee('Wojciech', 'Dominiak', 'abc', 'wdomini@gmail.com', 1200)
employee2 = Employee('Aleksandra', 'Nowak', 'def', 'ola@wp.pl', 1500)
print(Employee.counter)
employee1.show()
employee2.show()
print(employee1.money_in_year())
