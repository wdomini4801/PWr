def average(*args):
    def avg(numbers):
        if len(numbers) != 0:
            sum = 0
            for number in numbers:
                sum += number
            return sum / len(numbers)
        else:
            return 0
    return avg(args)


print(average(-1, -2, -3))


def sum_of_values(**kwargs):
    def sum_values(dictionary):
        sum = 0
        for element in dictionary:
            sum += dictionary[element]
        return sum
    return sum_values(kwargs)


print(sum_of_values(box=1, can=3, bottle=1.5))

