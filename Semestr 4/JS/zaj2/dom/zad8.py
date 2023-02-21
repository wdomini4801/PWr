import os

def readFile(filename):
    try:
        with open(filename, 'r') as file:
            print(file.read())
    except IOError:
        print("Could not read file")


def writeToFile(filename, list):
    try:
        with open(filename, 'w') as file:
            for element in list:
                file.write(element + "\n")
    except IOError as e:
        print("Could not read file")

finish = False
filename = input("Podaj nazwę pliku: ")

while not finish:
    try:
        line = input("Podaj adres strony: ")
        with open(filename, 'a') as file:
            file.write(line + "\n")
        choice = input("Do you want to continue? (0 - no): ")
        if choice == '0':
            finish = True
    except IOError:
        print("Could not read file")


readFile("test.txt")

elements = ["Bread", "Butter", "Milk", "Cheese"]

writeToFile("test3.txt", elements)

readFile("test3.txt")
