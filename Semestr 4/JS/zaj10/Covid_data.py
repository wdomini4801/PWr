import datetime

class CovidData:

    def __init__(self, date_repr, day, month, year, country, continent, cases, deaths):
        try:
            self.__date_repr = date_repr
            self.__date = datetime.datetime(int(year), int(month), int(day))
            self.__country = country
            self.__continent = continent
            self.__cases = int(cases)
            self.__deaths = int(deaths)
        except TypeError:
            print('Invalid argument type!')

    @property
    def date_repr(self):
        return self.__date_repr

    @property
    def date(self):
        return self.__date

    @property
    def day(self):
        return self.date.day

    @property
    def month(self):
        return self.date.month

    @property
    def year(self):
        return self.date.year

    @property
    def country(self):
        return self.__country

    @property
    def continent(self):
        return self.__continent

    @property
    def cases(self):
        return self.__cases

    @property
    def deaths(self):
        return self.__deaths

    def __str__(self):
        return '{0:10s} \t {1:20s} \t {2:20s} \t {3:4d} \t {4:4d}'.format(self.date_repr, self.country,
                self.continent, self.cases, self.deaths)

    def case_view(self):
        return '{0:10s} \t {1:20s} \t {2:20s} \t {3:4d}'.format(self.date_repr, self.country, self.continent, self.cases)

    def death_view(self):
        return '{0:10s} \t {1:20s} \t {2:20s} \t {3:4d}'.format(self.date_repr, self.country, self.continent, self.deaths)
