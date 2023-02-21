products = [("Bread", 2, 23, 15), ("Butter", 1, 23, 6), ("Juice", 4, 23, 12), ("Sugar", 10, 8, 15),
            ("Cheese", 2, 23, 9), ("Milk", 3, 23, 7), ("Tomato", 4, 23, 12)]

# pierwszy rodzaj formatowania

headerFormat1 = "%-8s \t%8s \t%3s \t%5s"
productFormat1 = "%-8s \t%8d \t%3d \t%5d"

print(headerFormat1 % ("Product", "Amount", "Vat", "Price"))

for product in products:
    print(productFormat1 % product)

print()


# drugi rodzaj formatowania

headerFormat2 = "{0:8s} \t{1:>8s} \t{2:3s} \t{3:5s}"
productFormat2 = "{0:8s} \t{1:8d} \t{2:3d} \t{3:5d}"

print(headerFormat2.format("Product", "Amount", "Vat", "Price"))

for product in products:
    print(productFormat2.format(product[0], product[1], product[2], product[3]))
