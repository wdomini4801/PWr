import time


def timer(func):
    def wrapper(*args):
        print('Starting execution')
        start = time.perf_counter()
        result = func(*args)
        end = time.perf_counter()
        print('Execution finished!')
        print('Result: {0:6d}'.format(result))
        difference = end - start
        print('Time: {0:8f} seconds'.format(difference))
    return wrapper


@timer
def sum_of_squares(*args):
    sum = 0
    for number in args:
        sum += number**2
    return sum


sum_of_squares(10000, 20000, 20000000, 27)
