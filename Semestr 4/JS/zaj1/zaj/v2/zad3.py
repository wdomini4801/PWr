percent = [2.606255012, 1.222935044, 1.283079391, 3.628708901, 6.856455493, 4.911788292,
           2.886928629, 0.781876504, 0.962309543, 2.265437049, 6.816359262, 3.688853248,
           3.468323978, 5.633520449, 4.530874098, 1.984763432, 0.922213312, 3.327987169,
           4.190056135, 5.493183641, 1.864474739, 10.60545309, 2.425821973, 2.726543705,
           8.740978348, 6.174819567]

countries = ['Ukraine', 'Spain', 'Slovenia', 'Lithuania', 'Austria', 'Estonia',
             'Norway', 'Portugal', 'United Kingdom', 'Serbia', 'Germany', 'Albania',
             'France', 'Czech Republic', 'Denmark', 'Australia', 'Finland', 'Bulgaria',
             'Moldova', 'Sweden', 'Hungary', 'Israel', 'Netherlands', 'Ireland',
             'Cyprus', 'Italy']

maxIndex = percent.index(max(percent))
minIndex = percent.index(min(percent))

print('Max value is {0:11f} for {1:25s}'.format(percent[maxIndex], countries[maxIndex]))
print('Min value is {0:11f} for {1:25s}'.format(percent[minIndex], countries[minIndex]))

sumAll = 0.0
sumABCDE = 0.0

for i in range(0, len(percent)):
    sumAll += percent[i]
    if countries[i].startswith("A") or countries[i].startswith("B") or countries[i].startswith("C") or \
            countries[i].startswith("D") or countries[i].startswith("E"):
        sumABCDE += percent[i]

print(sumAll)
print(sumABCDE)
