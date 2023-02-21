import os
import time

path = input("Podaj ścieżkę do katalogu: ")

if not os.path.isdir(path):
    print("Ścieżka nie jest katalogiem!")
else:
    file = input("Podaj nazwę pliku: ")
    fullpath = os.path.join(path, file)
    if not os.path.isfile(fullpath):
        print("Błąd!")
    else:
        modification_time = time.strftime('%Y-%m-%d  %H:%M:%S', time.localtime(os.path.getmtime(fullpath)))
        creation_time = time.strftime('%Y-%m-%d  %H:%M:%S', time.localtime(os.path.getctime(fullpath)))
        access_time = time.strftime('%Y-%m-%d  %H:%M:%S', time.localtime(os.path.getatime(fullpath)))
        size = os.path.getsize(fullpath)
        print("Data modyfikacji: \t {0:60s}".format(modification_time))
        print("Data utworzenia: \t {0:60s}".format(creation_time))
        print("Data ostatniego dostępu: \t {0:60s}".format(access_time))
        print("Rozmiar: \t {0:10d}".format(size))
