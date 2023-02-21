import datetime

from Covid_data import CovidData


class CovidDatabase:

    continents = set()
    months = {'January': 1, 'February': 2, 'March': 3, 'April': 4, 'May': 5, 'June': 6, 'July': 7, 'August': 8,
              'September': 9, 'October': 10, 'November': 11, 'December': 12}

    @staticmethod
    def read_file(file):
        all_cases_set = set()
        try:
            with open(file, 'r', encoding='utf=8') as file:
                file.readline()
                for line in file:
                    spl = line.split()

                    if len(spl) >= 10:
                        date_repr = spl[0]
                        day = spl[1]
                        month = spl[2]
                        year = spl[3]
                        country = spl[6]
                        continent = spl[10]
                        cases = spl[4]
                        deaths = spl[5]

                        all_cases_set.add((date_repr, day, month, year, country, continent, cases, deaths))
                        CovidDatabase.continents.add(continent)

            return all_cases_set

        except IOError:
            print("Could not read data from file")

    def __init__(self, file):
        self.database = []

        data_set = CovidDatabase.read_file(file)

        for element in data_set:
            self.database.append(CovidData(element[0], element[1], element[2], element[3], element[4], element[5],
                                           element[6], element[7]))

    def get_by_date(self, day, month, year, change=True):
        new_database = []
        temp_date = datetime.datetime(year, month, day)
        for element in self.database:
            if temp_date == element.date:
                new_database.append(element)
        if change:
            self.database = new_database
        else:
            return new_database

    def get_by_month(self, month, year, change=True):
        new_database = []
        for element in self.database:
            if element.month == month and element.year == year:
                new_database.append(element)
        if change:
            self.database = new_database
        else:
            return new_database

    def get_by_date_range(self, day1, month1, year1, day2, month2, year2, change=True):
        new_database = []
        date1 = datetime.datetime(year1, month1, day1)
        date2 = datetime.datetime(year2, month2, day2)

        if date1 <= date2:
            for element in self.database:
                if date1 <= element.date <= date2:
                    new_database.append(element)
            if change:
                self.database = new_database
            else:
                return new_database
        else:
            raise ValueError('Incorrect date order!')

    def get_by_country(self, country, change=True):
        new_database = []
        for element in self.database:
            if element.country == country:
                new_database.append(element)
        if change:
            self.database = new_database
        else:
            return new_database

    def get_by_continent(self, continent, change=True):
        new_database = []
        for element in self.database:
            if element.continent == continent:
                new_database.append(element)
        if change:
            self.database = new_database
        else:
            return new_database

    def sort_database(self, date=False, deaths=True, cases=True, desc=False):
        if date:
            self.database.sort(key=lambda x: x.date, reverse=desc)
        elif cases and not deaths:
            self.database.sort(key=lambda x: x.cases, reverse=desc)
        elif deaths and not cases:
            self.database.sort(key=lambda x: x.deaths, reverse=desc)
        else:
            self.database.sort(key=lambda x: (x.cases, x.deaths), reverse=desc)

    def get_sum(self, case_view=True, death_view=True):
        sum = 0
        sum2 = 0
        if case_view and not death_view:
            for element in self.database:
                sum += element.cases
        elif death_view and not case_view:
            for element in self.database:
                sum += element.deaths
        else:
            for element in self.database:
                sum += element.cases
                sum2 += element.deaths
        return sum, sum2
