names = []
surnames = []
passwords = []

continuation = True

while continuation:
    name = input('Enter name: ')
    surname = input('Enter surname: ')

    passwordCorrect = False

    while not passwordCorrect:
        password = input('Enter password: ')

        if len(password) < 10:
            print('Password must be at least 10 characters long!')
        elif not any(x.isupper() for x in password):
            print('Password must contain at least one capital letter!')
        elif not any(x.islower() for x in password):
            print('Password must contain at least one lowercase letter!')
        elif '#' not in password:
            print("Password must contain at least one ""#"" character!")
        else:
            print('Password correct!')
            passwordCorrect = True

            names.append(name)
            surnames.append(surname)
            passwords.append(password)

            choice = input('Do you want to continue? (0 - no): ')

            if choice == '0':
                continuation = False

print(names)
print(surnames)
print(passwords)