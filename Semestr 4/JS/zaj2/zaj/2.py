def swapCaseVowels(string):
    vowels = ['a', 'e', 'i', 'o', 'u', 'y']
    newString = ""

    for i in range(len(string)):
        if string[i] in vowels:
            newString += string[i].swapcase()
        else:
            newString += string[i]

    return newString

swapVowels = swapCaseVowels

print(swapVowels("Hello"))
