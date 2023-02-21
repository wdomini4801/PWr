all_cases_set = set()
amount = input('Podaj liczbę: ')

indexes = [6, 3, 2, 1, 5, 4]


def choose_elements(list, number):
    tuple_list = []
    if number <= 6:
        for i in range(number):
            if list[indexes[i]].isnumeric():
                tuple_list.append(int(list[indexes[i]]))
            else:
                tuple_list.append(list[indexes[i]])
    return tuple(tuple_list)


try:
    with open('Covid.txt', 'r', encoding='utf=8') as file:
        file.readline()
        for line in file:
            split_line = line.split()
            all_cases_set.add(choose_elements(split_line, int(amount)))

except IOError:
    print("Could not read file")

all_cases = list(all_cases_set)

print(all_cases)
