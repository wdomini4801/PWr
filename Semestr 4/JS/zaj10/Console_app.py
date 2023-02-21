import datetime

from Console import Console
from Covid_database import CovidDatabase
import copy


class ConsoleApp:

    def run(self):
        chosen_data_file = input('Choose a file to read: ')
        chosen_output_file = input('Choose a log file: ')
        f = open(chosen_output_file, "a", encoding='utf=8')
        db = CovidDatabase(chosen_data_file)
        cur_db = copy.deepcopy(db)
        finish = False
        case_view = True
        death_view = True
        sum = False

        def print_database(case_view=True, death_view=True):
            if case_view and not death_view:
                for element in cur_db.database:
                    print(element.case_view())
                    f.write(element.case_view())
                    f.write('\n')
            elif death_view and not case_view:
                for element in cur_db.database:
                    print(element.death_view())
                    f.write(element.death_view())
                    f.write('\n')
            else:
                for element in cur_db.database:
                    print(element)
                    f.write(element)
                    f.write('\n')
            f.write('\n')

        while not finish:
            exception = False
            sum = False
            query = Console.get_string(message='\nquery', maximum_length=100)
            spl = []
            if not query.lower().startswith('exit'):
                spl = query.split()
            else:
                finish = True
            while spl:
                word = spl[0].lower()
                match word:
                    case 'show':
                        cur_db = copy.deepcopy(db)
                        case_view = True
                        death_view = True
                        spl = spl[1:]
                    case 'all':
                        case_view = True
                        death_view = True
                        spl = spl[1:]
                    case 'cases':
                        case_view = True
                        death_view = False
                        spl = spl[1:]
                    case 'deaths':
                        case_view = False
                        death_view = True
                        spl = spl[1:]
                    case 'in':
                        spl = spl[1:]
                        if spl[0].isnumeric():
                            try:
                                cur_db.get_by_date(int(spl[0]), int(spl[1]), int(spl[2]))
                                spl = spl[3:]
                            except IndexError or ValueError:
                                #print('Invalid query!')
                                exception = True
                        elif spl[0].title() in CovidDatabase.continents:
                            cur_db.get_by_continent(spl[0].title())
                            spl = spl[1:]
                        elif spl[0].title() in CovidDatabase.months:
                            try:
                                cur_db.get_by_month(CovidDatabase.months[spl[0].title()], int(spl[1]))
                                spl = spl[2:]
                            except IndexError or ValueError:
                                #print('Invalid query!')
                                exception = True
                        else:
                            cur_db.get_by_country(spl[0].title())
                            spl = spl[1:]
                    case 'from':
                        spl = spl[1:]
                        try:
                            first_date = spl[0:3]
                            second_date = spl[4:7]
                            if spl[3].lower() == 'to':
                                cur_db.get_by_date_range(int(first_date[0]), int(first_date[1]), int(first_date[2]),
                                                         int(second_date[0]), int(second_date[1]), int(second_date[2]))
                                spl = spl[7:]
                            else:
                                #print('Invalid query!')
                                exception = True
                        except IndexError or ValueError:
                            #print('Invalid query!')
                            exception = True
                    case 'sort':
                        spl = spl[1:]
                        if len(spl) == 0:
                            cur_db.sort_database(cases=case_view, deaths=death_view)
                        else:
                            if spl[0].lower() == 'date':
                                spl = spl[1:]
                                if len(spl) == 0:
                                    cur_db.sort_database(date=True)
                                elif spl[0].lower() == 'desc':
                                    cur_db.sort_database(date=True, desc=True)
                                    spl = spl[1:]
                                else:
                                    #print('Invalid query!')
                                    exception = True
                            elif spl[0].lower() == 'desc':
                                cur_db.sort_database(cases=case_view, deaths=death_view, desc=True)
                                spl = spl[1:]
                            else:
                                #print('Invalid query!')
                                exception = True

                    case 'sum':
                        sum = True
                        spl = spl[1:]
                    case _:
                        #print('Invalid query!')
                        exception = True

                if not exception and not spl and not finish:
                    f.write(datetime.datetime.now().strftime("%d/%m/%Y %H:%M:%S") + '\n')
                    f.write(query + '\n')
                    if len(cur_db.database) != 0:
                        print_database(case_view, death_view)
                        if sum:
                            sum_result = cur_db.get_sum(case_view=case_view, death_view=death_view)
                            if sum_result[1] != 0:
                                print('Sum: {0} cases, {1} deaths'.format(sum_result[0], sum_result[1]))
                                f.write('Sum: {0} cases, {1} deaths\n'.format(sum_result[0], sum_result[1]))
                            else:
                                print('Sum: {0}'.format(sum_result[0]))
                                f.write('Sum: {0}\n'.format(sum_result[0]))
                    else:
                        print('No data!')
                        f.write('No data!\n\n')
                elif exception:
                    f.write(datetime.datetime.now().strftime("%d/%m/%Y %H:%M:%S") + '\n')
                    f.write(query + '\n')
                    print('Invalid query!')
                    f.write('Invalid query!\n\n')
                    spl = []


c1 = ConsoleApp()
c1.run()
