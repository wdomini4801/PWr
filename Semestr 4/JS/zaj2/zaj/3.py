def CreateFunction(kind):
    source = '''

def f(set1, set2):
    return set1.{}(set2)
''' .format(kind)
    exec(source, globals())


    return f  # Wykonywanie dynamicznego kodu polecenie exec()

a = {1, 4, 5, 7}
b = {2, 5}

f_add = CreateFunction('union')
print(f_add(a, b))

f_dif = CreateFunction('difference')
print(f_dif(a, b))