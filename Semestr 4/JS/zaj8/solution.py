from timeit import default_timer as timer

all_cases_set = set()
all_cases = []
by_date = {}
by_country = {}

# zadanie 1
try:
    with open('Covid.txt', 'r', encoding='utf=8') as file:
        file.readline()
        for line in file:
            split_line = line.split()

            country = split_line[6]
            year = int(split_line[3])
            month = int(split_line[2])
            day = int(split_line[1])
            deaths = int(split_line[5])
            cases = int(split_line[4])

            all_cases_set.add((country, year, month, day, deaths, cases))

except IOError:
    print("Could not read file")

all_cases = list(all_cases_set)

for case in all_cases:

    country = case[0]
    year = case[1]
    month = case[2]
    day = case[3]
    deaths = case[4]
    cases = case[5]

    if (year, month, day) in by_date:
        by_date[(year, month, day)].append((country, deaths, cases))
    else:
        by_date[(year, month, day)] = [(country, deaths, cases)]

    if country in by_country:
        by_country[country].append((year, month, day, deaths, cases))
    else:
        by_country[country] = [(year, month, day, deaths, cases)]


# print(all_cases)
# print(by_date[(2020, 11, 25)])
# print(by_country['Poland'])

# zadanie 2

def for_date_a(year, month, day):
    sum_deaths = 0
    sum_cases = 0

    for case in all_cases:
        if case[1] == year and case[2] == month and case[3] == day:
            sum_deaths += case[4]
            sum_cases += case[5]

    return sum_deaths, sum_cases


def for_date_d(year, month, day):
    sum_deaths = 0
    sum_cases = 0

    for case in by_date[(year, month, day)]:
        sum_deaths += case[1]
        sum_cases += case[2]

    return sum_deaths, sum_cases


def for_date_c(year, month, day):
    sum_deaths = 0
    sum_cases = 0

    for list in by_country.values():
        for case in list:
            if case[0] == year and case[1] == month and case[2] == day:
                sum_deaths += case[3]
                sum_cases += case[4]
                break

    return sum_deaths, sum_cases


# print(for_date_a(2020, 10, 30))
# print(for_date_d(2020, 10, 30))
# print(for_date_c(2020, 10, 30))


# zadanie 3

def for_country_a(country):
    sum_deaths = 0
    sum_cases = 0

    for case in all_cases:
        if case[0] == country:
            sum_deaths += case[4]
            sum_cases += case[5]

    return sum_deaths, sum_cases


def for_country_d(country):
    sum_deaths = 0
    sum_cases = 0

    for list in by_date.values():
        for case in list:
            if case[0] == country:
                sum_deaths += case[1]
                sum_cases += case[2]
                break

    return sum_deaths, sum_cases


def for_country_c(country):
    sum_deaths = 0
    sum_cases = 0

    for case in by_country[country]:
        sum_deaths += case[3]
        sum_cases += case[4]

    return sum_deaths, sum_cases


# print(for_country_a('Poland'))
# print(for_country_d('Poland'))
# print(for_country_c('Poland'))


# zadanie 4

def for_date_country_a(year, month, day, country):
    for case in all_cases:
        if case[0] == country and case[1] == year and case[2] == month and case[3] == day:
            return case[4], case[5]


def for_date_country_d(year, month, day, country):
    for case in by_date[(year, month, day)]:
        if case[0] == country:
            return case[1], case[2]


def for_date_country_c(year, month, day, country):
    for case in by_country[country]:
        if case[0] == year and case[1] == month and case[2] == day:
            return case[3], case[4]


# print(for_date_country_a(2020, 10, 31, 'Poland'))
# print(for_date_country_d(2020, 10, 31, 'Poland'))
# print(for_date_country_c(2020, 10, 31, 'Poland'))

# test zadanie 2
print("Zadanie 2 (sumaryczna liczba przypadków i zgonów w podanym dniu):")

start = timer()
print(for_date_a(2020, 3, 21))  # all_cases
end = timer()
print('all_cases: {} ms'.format(round((end - start)*1000, 2)))

start = timer()
print(for_date_d(2020, 3, 21))  # by_date
end = timer()
print('by_date: {} ms'.format(round((end - start)*1000, 2)))

start = timer()
print(for_date_c(2020, 3, 21))  # by_country
end = timer()
print('by_country: {} ms'.format(round((end - start)*1000, 2)))
print()


# test zadanie 3
print("Zadanie 3 (sumaryczna liczba przypadków i zgonów dla podanego kraju):")

start = timer()
print(for_country_a('Zimbabwe'))  # all_cases
end = timer()
print('all_cases: {} ms'.format(round((end - start)*1000, 2)))

start = timer()
print(for_country_d('Zimbabwe'))  # by_date
end = timer()
print('by_date: {} ms'.format(round((end - start)*1000, 2)))

start = timer()
print(for_country_c('Zimbabwe'))  # by_country
end = timer()
print('by_country: {} ms'.format(round((end - start)*1000, 2)))
print()


# test zadanie 4
print("Zadanie 4 (liczba przypadków i zgonów dla podanego kraju w podanym dniu):")

start = timer()
print(for_date_country_a(2020, 3, 21, 'Zimbabwe'))  # all_cases
end = timer()
print('all_cases: {} ms'.format(round((end - start)*1000, 2)))

start = timer()
print(for_date_country_d(2020, 3, 21, 'Zimbabwe'))  # by_date
end = timer()
print('by_date: {} ms'.format(round((end - start)*1000, 2)))

start = timer()
print(for_date_country_c(2020, 3, 21, 'Zimbabwe'))  # by_country
end = timer()
print('by_country: {} ms'.format(round((end - start)*1000, 2)))


