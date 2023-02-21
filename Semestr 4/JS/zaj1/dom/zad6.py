mailFormat = "%s.%s@pwr.edu.pl"
finish = False
names = []
surnames = []
namesAndSurnames = []
emails = []

print("Welcome to e-mail creator! \n")

try:
    while not finish:
        name = input('Enter your name: ')
        surname = input('Enter your surname: ')

        names.append(name.capitalize())
        surnames.append(surname.capitalize())
        namesAndSurnames.append((name.capitalize(), surname.capitalize()))

        email = mailFormat % (name.lower(), surname.lower())
        emails.append(email)

        cond = input('Do you want to continue? (0 - no): ')

        if cond == '0':
            finish = True

except ValueError as e:
    print('Wrong value type', e)

print()

for i in range(0, len(namesAndSurnames)):
    print('E-mail for \t {0:25s} \t {1:25s} \t is \t {2:50s}'.format(namesAndSurnames[i][0], namesAndSurnames[i][1],
                                                                     emails[i]))
