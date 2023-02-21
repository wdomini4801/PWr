from datetime import datetime
from tkinter import *
import tkinter.ttk as ttk
from tkinter import messagebox
from Covid_database import CovidDatabase
import copy


class TkinterApp:

    exception = False

    def run(self, file):
        f = open(file, "a", encoding='utf=8')
        db = CovidDatabase('Covid.txt')
        orig_database = copy.deepcopy(db.database)

        root = Tk()

        root.title("Przebieg pandemii Covid-19")
        root.geometry('700x400')

        case_view = BooleanVar(root, FALSE)
        death_view = BooleanVar(root, FALSE)
        sum = BooleanVar(root, FALSE)

        sort_chose = StringVar()

        cbutton_cases = Checkbutton(root, text='cases', variable=case_view, onvalue=TRUE, offvalue=FALSE)
        cbutton_deaths = Checkbutton(root, text='deaths', variable=death_view, onvalue=TRUE, offvalue=FALSE)

        cbutton_cases.place(x=100, y=50)
        cbutton_deaths.place(x=170, y=50)

        label1 = Label(root, text='Location')
        location = Entry(root, width=30)

        label1.place(x=398, y=30)
        location.place(x=400, y=50)

        label2 = Label(root, text="Date (to enter range type '-')")
        date = Entry(root, width=30)

        label2.place(x=98, y=120)
        date.place(x=100, y=140)

        label3 = Label(root, text="Order")
        combobox = ttk.Combobox(root, state='readonly', textvariable=sort_chose,
                                values=('none', 'date', 'date desc', 'cases/deaths',
                                        'cases/deaths desc'))
        combobox.current(0)

        label3.place(x=398, y=120)
        combobox.place(x=400, y=140)

        cbutton_sum = Checkbutton(root, text='sum', variable=sum, onvalue=TRUE, offvalue=FALSE)
        cbutton_sum.place(x=98, y=180)

        def get_location():
            location_text = location.get().title()
            if len(location_text) != 0:
                if location_text in CovidDatabase.continents:
                    db.get_by_continent(location_text)
                else:
                    db.get_by_country(location_text)
            return location_text

        def get_date():
            date_text = date.get()
            if len(date_text) != 0:
                if '-' in date_text:
                    date_list = date_text.split('-')
                    date1 = date_list[0].split('.')
                    date2 = date_list[1].split('.')

                    try:
                        db.get_by_date_range(int(date1[0]), int(date1[1]), int(date1[2]), int(date2[0]), int(date2[1]),
                                             int(date2[2]))
                    except ValueError or TypeError:
                        messagebox.showinfo('Error', 'Wrong date input!')
                        f.write('Wrong date input!\n\n')
                        TkinterApp.exception = True

                elif '.' in date_text:
                    date_list = date_text.split('.')
                    try:
                        db.get_by_date(int(date_list[0]), int(date_list[1]), int(date_list[2]))
                    except ValueError or TypeError:
                        messagebox.showinfo('Error', 'Wrong date input!')
                        f.write('Wrong date input!\n\n')
                        TkinterApp.exception = True
                else:
                    date_list = date_text.split()
                    if date_list[0].title() in CovidDatabase.months:
                        try:
                            db.get_by_month(CovidDatabase.months[date_list[0].title()], int(date_list[1]))
                        except ValueError or TypeError:
                            messagebox.showinfo('Error', 'Wrong date input!')
                            f.write('Wrong date input!\n\n')
                            TkinterApp.exception = True
                    else:
                        messagebox.showinfo('Error', 'Wrong date input!')
                        f.write('Wrong date input!\n\n')
                        TkinterApp.exception = True
            return date_text

        def get_order():
            order_text = combobox['values'][combobox.current()]
            match order_text:
                case 'date':
                    db.sort_database(date=True)
                case 'date desc':
                    db.sort_database(date=True, desc=True)
                case 'cases/deaths':
                    db.sort_database(deaths=death_view.get(), cases=case_view.get())
                case 'cases/deaths desc':
                    db.sort_database(deaths=death_view.get(), cases=case_view.get(), desc=True)
            if order_text == 'none':
                return ''
            else:
                return order_text

        def write_cases_deaths():
            if case_view.get() and not death_view.get():
                f.write('cases')
            elif death_view.get() and not case_view.get():
                f.write('deaths')
            else:
                f.write('all')

        def print_database(case_view=True, death_view=True):
            if case_view and not death_view:
                for element in db.database:
                    print(element.case_view())
                    f.write(element.case_view())
                    f.write('\n')
            elif death_view and not case_view:
                for element in db.database:
                    print(element.death_view())
                    f.write(element.death_view())
                    f.write('\n')
            else:
                for element in db.database:
                    print(element)
                    f.write(element.__str__())
                    f.write('\n')
            f.write('\n')

        def execute():
            f.write(datetime.now().strftime("%d/%m/%Y %H:%M:%S") + '\n')
            if not case_view.get() and not death_view.get():
                messagebox.showinfo('Error', 'You have to choose data to display!')
                f.write('You have to choose data to display!\n\n')
            else:
                location_text = get_location()
                date_text = get_date()
                order_text = get_order()
                write_cases_deaths()
                f.write(' {0} {1} {2}\n'.format(location_text, date_text, order_text))
                if len(db.database) != 0 and not TkinterApp.exception:
                    print_database(case_view=case_view.get(), death_view=death_view.get())
                    if sum.get():
                        sum_result = db.get_sum(case_view=case_view.get(), death_view=death_view.get())
                        if sum_result[1] != 0:
                            print('Sum: {0} cases, {1} deaths'.format(sum_result[0], sum_result[1]))
                            f.write('Sum: {0} cases, {1} deaths\n'.format(sum_result[0], sum_result[1]))
                        else:
                            print('Sum: {0}'.format(sum_result[0]))
                            f.write('Sum: {0}\n'.format(sum_result[0]))
                else:
                    print('No data!')
                    f.write('No data!\n')
                    TkinterApp.exception = False
                db.database = orig_database
                print()

        main_button = Button(root, text='Print choice', command=execute)
        main_button.place(x=300, y=250)

        root.mainloop()


t1 = TkinterApp()
t1.run('log2.txt')