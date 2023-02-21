import random

def playTour(number_of_throws):
    try:
        sum = 0
        for i in range(0, number_of_throws):
            sum += random.randint(1, 6)
        return sum

    except TypeError:
        print("You need to enter an integer number!")


tour1 = playTour("a")
tour2 = playTour(4)

print("First tour: {0:2d} \nSecond tour: {1:2d}".format(tour1, tour2))