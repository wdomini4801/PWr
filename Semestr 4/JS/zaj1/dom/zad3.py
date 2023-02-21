import sys

emails = []
domains = []
finish = False

try:
    while not finish:
        email = input('Enter your email: ')
        emails.append(email)

        domains.append(email[email.find('@') + 1:len(email)])

        cond = input('Do you want to continue? (0 - no): ')

        if cond == '0':
            finish = True

except ValueError as e:
    print("Wrong value type")

domainSet = set(domains)

if domainSet:
    print()
    print('{0:10s} \t {1:6s}'.format('Domain', 'Amount'))

    for domain in domainSet:
        print('{0:10s} \t {1:6d}'.format(domain, domains.count(domain)))

else:
    print("No domains!")
