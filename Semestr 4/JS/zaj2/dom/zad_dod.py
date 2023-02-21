i = 0


def some_function():
    global i
    print(i)
    i += 1


def other_function():
    print("End of while loop!")


def whileLoop(value, expression, end):
    for e in range(value):
        expression()
    end()


whileLoop(5, some_function, other_function)
