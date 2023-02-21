list = [12, 5, 8, 8, 23, 15, 7, 8, 9, 12, 34, 6, 9, 16, 8, 23, 12, 7, 5, 3]

list.sort()
print(list)

bus = []

busCapacity = 100
curCapacity = 0

for a in range(0, len(list)):
    if (curCapacity + list[a]) <= busCapacity:
        bus.append(list[a])
        curCapacity += list[a]
    else:
        break

print(bus)
