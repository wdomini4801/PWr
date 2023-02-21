import datetime

today = datetime.datetime.today()

year_end = datetime.datetime.fromisoformat("2022-12-31 23:59:59")

to_the_end_of_year = year_end - today

days = to_the_end_of_year.days
hours = to_the_end_of_year.seconds // 3600
remainder = to_the_end_of_year.seconds % 3600
minutes = remainder // 60
seconds = remainder % 60

print("Do końca roku zostało ", days, " dni\n")

print("Do końca roku zostało {0} dni, {1} godzin, {2} minut, {3} sekund".format(days, hours, minutes, seconds))
