import random


def generate_set(amount):
    numbers = set()
    while len(numbers) != amount:
        numbers.add(random.randint(1, 49))
    return numbers


def generate_coupons(amount):
    coupons_list = []
    for i in range(0, amount):
        coupons_list.append(generate_set(6))
    return coupons_list


def count_hits(coupon, numbers):
   count = 0
   for number in coupon:
       if number in numbers:
           count += 1
   return count


winning_numbers = generate_set(6)
print("Winning numbers: ", winning_numbers)

coupons = generate_coupons(10)

for i in range(0, len(coupons)):
    print("Coupon \t {0:2d}: {1:1d} hits".format(i+1, count_hits(coupons[i], winning_numbers)))
