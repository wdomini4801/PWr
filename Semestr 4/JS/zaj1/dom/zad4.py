list = [19, 3, 15, 43, 98, 16, 9, 23, 4]

for element in list:
    factors = []
    for a in range(1, element + 1):
        if element % a == 0:
            factors.append(a)
    if len(factors) == 2:
        print(element, "\t prime number")
    else:
        print(element, "\t", factors)

print()

listSortedAscending = list.copy()
listSortedAscending.sort()
listSortedDescending = listSortedAscending.copy()
listSortedDescending.reverse()

print(list)
print(listSortedAscending)
print(listSortedDescending)

print()

list_3 = list.copy()
list_3[0:3] = sorted(list_3[0:3])
print(list_3)
