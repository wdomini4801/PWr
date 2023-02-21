name = input('Enter your name: ')
files = []
extensionSet = set()
extensionDict = dict()

print('Hello {0:10s}'.format(name))
finish = False

while not finish:
    file = input('Enter file: ')
    extension = file[file.find('.')+1:len(file)]
    files.append(file)
    extensionSet.add(extension)
    choice = input('Do you want to continue (0 - no): ')

    if choice == '0':
        finish = True

files.sort()

for element in extensionSet:
    extensionList = []
    for element2 in files:
        if element in element2:
            extensionList.append(element)
    extensionDict[element] = len(extensionList)

print('{0:9s} \t {1:5s}'.format("Extension", "Count"))

for element in extensionDict.keys():
    print('{0:9s} \t {1:5d}'.format(element, extensionDict[element]))
