import copy
import os
from datetime import datetime
from tkinter import *
from tkinter import messagebox, filedialog
from tkinter.colorchooser import askcolor

from CovidDatabase import CovidDatabase
from configparser import ConfigParser
import tkinter.ttk as ttk


class TkinterApp(Tk):

    database = None

    def __init__(self):
        super().__init__()
        self.config = ConfigParser()
        self.config.read('config.ini')
        file = self.config.get('settings', 'database_path')
        self.db = CovidDatabase(file)
        TkinterApp.database = copy.deepcopy(self.db.database)
        self.menubar = Menu(self)
        self.create_menu()
        self.case_view = BooleanVar(self, FALSE)
        self.death_view = BooleanVar(self, FALSE)
        self.sum_chose = BooleanVar(self, FALSE)
        self.sort_chose = StringVar()
        self.label_main = Label(self, text="Choose options", font=('Arial', 25))
        self.toolbar = Frame(self, width=1000, height=60)
        self.create_toolbar()
        self.cbutton_cases = Checkbutton(self, text='cases', variable=self.case_view, onvalue=TRUE, offvalue=FALSE)
        self.cbutton_deaths = Checkbutton(self, text='deaths', variable=self.death_view, onvalue=TRUE, offvalue=FALSE)
        self.label_location = Label(self, text='Location')
        self.location_entry = Entry(self, width=30)
        self.label_date = Label(self, text="Date (dd.mm.yyyy or 'month year')")
        self.date_entry = Entry(self, width=30)
        self.label_order = Label(self, text="Order")
        self.combobox_order = ttk.Combobox(self, state='readonly', textvariable=self.sort_chose,
                                values=('none', 'date', 'date desc', 'cases/deaths',
                                        'cases/deaths desc'))
        self.cbutton_sum = Checkbutton(self, text='sum', variable=self.sum_chose, onvalue=TRUE, offvalue=FALSE)
        self.button_main = Button(self, text='Print choice', command=self.execute)
        self.textbox = Text(self, height=25)
        self.scrollbar = Scrollbar(command=self.textbox.yview)
        self.statusbar = Label(self, text="Linia statusu...")
        self.geometry(self.config.get('settings', 'geometry'))
        self.title("Course of the COVID-19 pandemic")
        # self.mainloop()

    @staticmethod
    def current_time():
        return datetime.now().strftime("%d/%m/%Y %H:%M:%S")

    def create_menu(self):
        self.configure(menu=self.menubar)
        file_menu = Menu(self.menubar, tearoff=False)
        for label, command, shortcut_text, shortcut in (
                ("Open...", self.open_file, "Ctrl+O", "<Control-o>"),
                ("Save...", self.save_file, "Ctrl+S", "<Control-s>"),
                ("Quit", self.exit, "Ctrl+Q", "<Control-q>")):
            file_menu.add_command(label=label, underline=0, accelerator=shortcut_text,
                                 command=command)
            self.bind(shortcut, command)
        self.menubar.add_cascade(label="File", menu=file_menu, underline=0)
        preferences_menu = Menu(self.menubar, tearoff=False)
        for label, command, shortcut_text, shortcut in (
                ("Set default preferences...", self.set_default_preferences, "Ctrl+D", "<Control-d>"),
                ("Set last settings...", self.set_last_settings, "Ctrl+L", "<Control-l>"),
                ("Set background color...", self.set_background_color, "Ctrl+B", "<Control-b>"),
                ("Set font color...", self.set_font_color, "Ctrl+P", "<Control-p>")):
            preferences_menu.add_command(label=label, underline=0, accelerator=shortcut_text,
                                  command=command)
            self.bind(shortcut, command)
        self.menubar.add_cascade(label="Preferences", menu=preferences_menu, underline=0)

    def cases_or_deaths(self):
        if self.case_view.get() and not self.death_view.get():
            return 'cases'
        elif self.death_view.get() and not self.case_view.get():
            return 'deaths'
        else:
            return 'all'

    def open_file(self, event=None):
        file = filedialog.askopenfilename()
        TkinterApp.database = CovidDatabase(file)
        self.db = copy.deepcopy(TkinterApp.database)
        self.set_statusbar('File: {0}\t{1}'.format(file, self.current_time()))

    def save_file(self, event=None):
        files = [('Text Document', '*.txt')]
        file = filedialog.asksaveasfile(filetypes=files, defaultextension=files, mode='a')
        if file is not None:
            f = open(file.name, "a", encoding='utf=8')
            f.write(self.current_time() + '\n')
            f.write('{0} {1} {2} {3}\n'.format(self.cases_or_deaths(), self.location_text, self.date_text, self.order_text))
            if self.sum_chose.get():
                f.write('sum\n')
            f.write(self.textbox.get("1.0", "end-1c"))
            self.set_statusbar('Saved in: {0}\t{1}'.format(file.name, self.current_time()))

    def exit(self, event=None):
        answer = messagebox.askyesno(title='Confirmation', message='Save file before quitting?')
        if answer:
            self.save_file()
        self.destroy()

    def set_default_preferences(self, event=None):
        answer = messagebox.askyesno(title='Confirmation', message='Are you sure you want to restore default settings?')
        if answer:
            TkinterApp.database = CovidDatabase(self.config.get('settings', 'database_path'))
            self['background'] = self.config.get('settings', 'background')
            self.textbox['fg'] = self.config.get('settings', 'font_color')
            self.geometry(self.config.get('settings', 'geometry'))
            self.set_statusbar('Default preferences restored\t{0}'.format(self.current_time()))

    def set_last_settings(self, event=None):
        answer = messagebox.askyesno(title='Confirmation', message='Are you sure you want to restore previous criteria?')
        if answer:
            last_case_view = self.config.get('last_search', 'case_view')
            last_death_view = self.config.get('last_search', 'death_view')
            last_location = self.config.get('last_search', 'location')
            last_date = self.config.get('last_search', 'date')
            last_order = self.config.get('last_search', 'order')
            last_sum = self.config.get('last_search', 'sum')

            if last_case_view == 'True' and last_death_view == 'False':
                self.cbutton_cases.select()
                self.cbutton_deaths.deselect()
            elif last_death_view == 'True' and last_case_view == 'False':
                self.cbutton_deaths.select()
                self.cbutton_cases.deselect()
            else:
                self.cbutton_cases.select()
                self.cbutton_deaths.select()

            self.location_entry.delete(0, END)
            self.location_entry.insert(0, last_location)

            self.date_entry.delete(0, END)
            self.date_entry.insert(0, last_date)

            if last_order == '':
                self.combobox_order.current(0)
            else:
                index = self.combobox_order['values'].index(last_order)
                self.combobox_order.current(index)

            if last_sum == 'True':
                self.cbutton_sum.select()
            else:
                self.cbutton_sum.deselect()

            self.set_statusbar('Previous criteria restored\t{0}'.format(self.current_time()))

    def set_background_color(self, event=None):
        color = askcolor(title='Choose color')
        if color is not None:
            self['background'] = color[1]
            self.set_statusbar('Background color set to {0}\t{1}'.format(self['background'], self.current_time()))

    def set_font_color(self, event=None):
        color = askcolor(title='Choose color')
        if color is not None:
            self.textbox['fg'] = color[1]
            self.set_statusbar('Font color set to {0}\t{1}'.format(self.textbox['fg'], self.current_time()))

    def clear_all(self, event=None):
        answer = messagebox.askyesno(title='Confirmation', message='Are you sure you want to clear everything?')
        if answer:
            self.cbutton_cases.deselect()
            self.cbutton_deaths.deselect()

            self.location_entry.delete(0, END)
            self.date_entry.delete(0, END)
            self.combobox_order.current(0)
            self.cbutton_sum.deselect()

            self.textbox['state'] = 'normal'
            self.textbox.delete(1.0, END)
            self.textbox['state'] = 'disabled'
            self.set_statusbar('Cleared\t{0}'.format(self.current_time()))

    def create_toolbar(self):
        self.toolbar_images = []
        for image, command in (
                ("images/editedit.gif", self.execute),
                ("images/filesave.gif", self.save_file),
                ("images/editdelete.gif", self.clear_all)):
            image = os.path.join(os.path.dirname(__file__), image)
            try:
                image = PhotoImage(file=image)
                self.toolbar_images.append(image)
                button = Button(self.toolbar, image=image, command=command)
                button.grid(row=0, column=len(self.toolbar_images) - 1)
            except TclError as err:
                print(err)

    def set_statusbar(self, txt):
        self.statusbar['text'] = txt

    def clear_statusbar(self):
        self.statusbar['text'] = ''

    def prepare_components(self):
        self.bind("<Return>", self.execute)
        self.bind("<Control-Delete>", self.clear_all)
        self.toolbar.place(x=0, y=0)
        self.label_main.place(x=400, y=40)
        self.cbutton_cases.place(x=250, y=130)
        self.cbutton_deaths.place(x=320, y=130)
        self.label_location.place(x=598, y=110)
        self.location_entry.place(x=600, y=130)
        self.label_date.place(x=248, y=180)
        self.date_entry.place(x=250, y=200)
        self.combobox_order.current(0)
        self.label_order.place(x=598, y=180)
        self.combobox_order.place(x=600, y=200)
        self.cbutton_sum.place(x=248, y=240)
        self.button_main.place(x=480, y=240)
        self.textbox['state'] = 'disabled'
        self.textbox.place(x=198, y=290)
        self.scrollbar.place(x=840, y=290)
        self.textbox['yscroll'] = self.scrollbar.set
        self.statusbar.place(x=0, y=700)
        self.statusbar.after(5000, self.clear_statusbar)

    def evaluate_location(self):
        self.location_text = self.location_entry.get().title()
        if len(self.location_text) != 0:
            if self.location_text in CovidDatabase.continents:
                self.db.get_by_continent(self.location_text)
            else:
                self.db.get_by_country(self.location_text)

    def evaluate_date(self):
        self.date_text = self.date_entry.get()
        if len(self.date_text) != 0:
            if '-' in self.date_text:
                date_list = self.date_text.split('-')
                date1 = date_list[0].split('.')
                date2 = date_list[1].split('.')

                try:
                    self.db.get_by_date_range(int(date1[0]), int(date1[1]), int(date1[2]), int(date2[0]), int(date2[1]),
                                         int(date2[2]))
                except ValueError or TypeError:
                    return -1

            elif '.' in self.date_text:
                date_list = self.date_text.split('.')
                try:
                    self.db.get_by_date(int(date_list[0]), int(date_list[1]), int(date_list[2]))
                except ValueError or TypeError:
                    return -1
            else:
                date_list = self.date_text.split()
                if date_list[0].title() in CovidDatabase.months:
                    try:
                        self.db.get_by_month(CovidDatabase.months[date_list[0].title()], int(date_list[1]))
                    except ValueError or TypeError:
                        return -1
                else:
                    return -1

    def evaluate_order(self):
        self.order_text = self.combobox_order['values'][self.combobox_order.current()]
        match self.order_text:
            case 'date':
                self.db.sort_database(date=True)
            case 'date desc':
                self.db.sort_database(date=True, desc=True)
            case 'cases/deaths':
                self.db.sort_database(deaths=self.death_view.get(), cases=self.case_view.get())
            case 'cases/deaths desc':
                self.db.sort_database(deaths=self.death_view.get(), cases=self.case_view.get(), desc=True)
        if self.order_text == 'none':
            self.order_text = ''

    def return_database(self):
        result = ''
        if self.case_view.get() and not self.death_view.get():
            for element in self.db.database:
                result += (element.case_view() + '\n')
        elif self.death_view.get() and not self.case_view.get():
            for element in self.db.database:
                result += (element.death_view() + '\n')
        else:
            for element in self.db.database:
                result += (element.__str__() + '\n')
        return result + '\n'

    def display_database(self):
        result = self.return_database()
        if len(result) != 0:
            self.textbox.insert(END, result)
            if self.sum_chose.get():
                sum_result = self.db.get_sum(case_view=self.case_view.get(), death_view=self.death_view.get())
                if sum_result[1] != 0:
                    self.textbox.insert(END, 'Sum: {0} cases, {1} deaths\n'.format(sum_result[0], sum_result[1]))
                else:
                    self.textbox.insert(END, 'Sum: {0}\n'.format(sum_result[0]))
        else:
            self.textbox.insert(END, 'No data!')

    def write_settings(self):
        self.config.set('last_search', 'case_view', str(self.case_view.get()))
        self.config.set('last_search',  'death_view', str(self.death_view.get()))
        self.config.set('last_search',  'location', self.location_text)
        self.config.set('last_search', 'date', self.date_text)
        self.config.set('last_search', 'order', self.order_text)
        self.config.set('last_search', 'sum', str(self.sum_chose.get()))

        with open('config.ini', 'w') as f:
            self.config.write(f)

    def execute(self, event=None):
        if not self.case_view.get() and not self.death_view.get():
            messagebox.showinfo('Error', 'You have to choose data to display!')
            self.set_statusbar('Not corrected input\t{0}'.format(self.current_time()))
        else:
            self.evaluate_location()
            date = self.evaluate_date()
            self.evaluate_order()
            if date != -1:
                self.textbox['state'] = 'normal'
                self.display_database()
                self.textbox['state'] = 'disabled'
                self.write_settings()
                self.db.database = TkinterApp.database
                self.set_statusbar('Completed\t{0}'.format(self.current_time()))
            else:
                messagebox.showinfo('Error', 'Wrong date input!')
                self.set_statusbar('Not correct input\t{0}'.format(self.current_time()))

    def run(self):
        self.prepare_components()
        self.mainloop()


app1 = TkinterApp()
app1.run()
