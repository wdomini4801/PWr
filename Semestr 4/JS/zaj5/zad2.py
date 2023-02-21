class Sportowiec:
    def __init__(self, name, surname):
        self.name = name
        self.surname = surname

    def greet(self):
        print('{0:15s} \t {1:15s}'.format(self.name, self.surname))


class Biegacz(Sportowiec):
    def __init__(self, name, surname, *args):
        super().__init__(name, surname)
        self.results = []
        for element in args:
            self.results.append(element)


class Kolarz(Sportowiec):
    def __init__(self, name, surname, **kwargs):
        super().__init__(name, surname)
        self.bikes = []
        for element in kwargs.keys():
            self.bikes.append(kwargs[element])


# s1 = Sportowiec('Wojciech', 'Wiśniewski')
# biegacz1 = Biegacz('Maria', 'Kowalska', 10.5, 20, 30.5)
# kolarz1 = Kolarz('Marcin', 'Nowak', rower1='BMX', rower2='Giant')
#
# s1.greet()
# biegacz1.greet()
# kolarz1.greet()
#
# print(biegacz1.results)
# print(kolarz1.bikes)

stri = "WELCOME"
print(stri.title())