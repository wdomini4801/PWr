package Lista5;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static TwoWayCycledListWithSentinel<Pracownik> baza;
    public static ListStack<Pracownik> parking = new ListStack<>();
    public static ListStack<Pracownik> doPrzestawienia = new ListStack<>();
    public static ListStack<Pracownik> przestawione = new ListStack<>();

    public static ListStack<Pracownik> odwroc (ListStack<Pracownik> stos) throws EmptyStackException
    {
        ListStack<Pracownik> odwrocony = new ListStack<>();
        while (!stos.isEmpty())
        {
            odwrocony.push(stos.pop());
        }
        return odwrocony;
    }

    public static void utworzBaze ()
    {
        baza = new TwoWayCycledListWithSentinel<>();
    }

    public static void naglowek ()
    {
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        System.out.print("|");
        System.out.printf("%-11s", "PESEL");
        System.out.print("\t" + "|");
        System.out.printf("%-36s", "Nazwisko i imię");
        System.out.print("|");
        System.out.printf("%-11s", "Data urodzenia");
        System.out.print("|");
        System.out.printf("%-15s", "Stanowisko");
        System.out.print("|");
        System.out.printf("%-8s", "Pensja");
        System.out.print("\t" + "|");
        System.out.printf("%-7s", "Staż");
        System.out.print("|");
        System.out.printf("%-8s", "Premia");
        System.out.print("\t" + "|");
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
    }

    public static void wyswietlBaze ()
    {
        if (baza.size()!=0)
        {
            naglowek();
            for (Pracownik p : baza)
            {
                p.wyswietl();
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------");

        }
        else
        {
            System.out.println("Baza pusta!");
        }
    }

    public static int znajdzIndeks (long PESEL)
    {
        Iterator<Pracownik> iter = baza.iterator();
        int i=0;
        while (iter.hasNext())
        {
            Pracownik aktualny = iter.next();
            if (aktualny.getPESEL()>=PESEL)
            {
                return i;
            }
            else
            {
                i++;
            }
        }
        return i;
    }

    public static Pracownik znajdzPESEL (long PESEL)
    {
        Pracownik znaleziony = null;
        for (Pracownik p : baza)
        {
            if (p.getPESEL()==PESEL)
            {
                znaleziony = p;
            }
        }
        return znaleziony;
    }

    public static void wyswietlPracownika (long PESEL)
    {
        Pracownik znaleziony = znajdzPESEL(PESEL);
        if (znaleziony == null)
        {
            System.out.println("Brak pracownika o podanym PESELu!");
        }
        else
        {
            naglowek();
            znaleziony.wyswietl();
        }
    }

    public static void usunPracownika (long PESEL)
    {
        Pracownik znaleziony = znajdzPESEL(PESEL);
        if (znaleziony == null)
        {
            System.out.println("Brak pracownika o podanym PESELu!" + "\n");
        }
        else
        {
            baza.remove(znaleziony);
            System.out.println("Pracownik usunięty!" + "\n");
        }
    }

    public static double sredniaPensja ()
    {
        int ilosc = baza.size();
        double s=0;
        for (Pracownik p : baza)
        {
            s=s+(p.getPensja() + p.getPremia());
        }
        return s/ilosc;
    }

    public static int zarobkiPonizejSredniej()
    {
        int n=0;
        double sr = sredniaPensja();
        for (Pracownik p : baza)
        {
            if ((p.getPensja() + p.getPremia())<sr)
            {
                n++;
            }
        }
        return n;
    }

    public static void zapisDoPliku(String plik)
    {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/Serializacja/pliki/" + plik + ".ser")))
        {
            os.writeObject(baza);
            System.out.println("Bazę zapisano pomyślnie!" + "\n");
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.out.println("Błąd podczas zapisu do pliku!");
        }
    }

    public static void odczytZPliku (String plik)
    {
        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream("src/Serializacja/pliki/" + plik + ".ser")))
        {
            Object obj = is.readObject();
            baza = (TwoWayCycledListWithSentinel<Pracownik>) obj;
            System.out.println("Baza została poprawnie odczytana!" + "\n");
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
            System.out.println("Błąd podczas odczytu z pliku!");
        }
    }

    public static void menu ()
    {
        System.out.println("Wybierz czynność");
        System.out.println("1 - utworzenie nowej bazy danych");
        System.out.println("2 - odczyt bazy z pliku");
        System.out.println("3 - wyświetlenie wszystkich rekordów");
        System.out.println("4 - wyświetlenie danych jednego pracownika");
        System.out.println("5 - dopisanie nowego pracownika");
        System.out.println("6 - usunięcie pracownika z bazy");
        System.out.println("7 - aktualizacja danych pracownika");
        System.out.println("8 - obliczenie średniej pensji w firmie");
        System.out.println("9 - obliczenie ilości pracowników zarabiających poniżej średniej");
        System.out.println("10 - zapis bazy do pliku");
        System.out.println("11 - symulacja parkingu");
        System.out.println("12 - wyjście");
    }

    public static void menu_parking ()
    {
        System.out.println("Wybierz czynność");
        System.out.println("1 - wjazd samochodu");
        System.out.println("2 - przestawienie samochodu");
        System.out.println("3 - wyjazd samochodu");
        System.out.println("4 - wyjście");
    }

    public static void dzialanie ()
    {
        Scanner scan = new Scanner (System.in);
        int n;
        String nazwa;
        long pesel;
        menu();
        n = scan.nextInt();
        while (n!=12)
        {
            try
            {
                if (n==1)
                {
                    utworzBaze();
                    System.out.println("Nowa baza danych została utworzona!" + "\n");
                }
                if (n==2)
                {
                    System.out.println("Podaj nazwę pliku");
                    nazwa = scan.next();
                    odczytZPliku(nazwa);
                }
                if (n==3)
                {
                    wyswietlBaze();
                    System.out.println();
                }
                if (n==4)
                {
                    System.out.println("Podaj PESEL pracownika");
                    pesel = scan.nextLong();
                    wyswietlPracownika(pesel);
                    System.out.println();
                }
                if (n==5)
                {
                    System.out.println("Podaj PESEL");
                    long p = scan.nextLong();
                    if (znajdzPESEL(p)==null)
                    {
                        System.out.println("Podaj nazwisko");
                        String nazwisko = scan.next();
                        System.out.println("Podaj imię");
                        String imie = scan.next();
                        System.out.println("Podaj datę urodzenia (w formacie dd.mm.rr)");
                        String data = scan.next();
                        System.out.println("Podaj stanowisko");
                        String stanowisko = scan.next();
                        System.out.println("Podaj pensję");
                        double pensja = scan.nextDouble();
                        System.out.println("Podaj staż");
                        int staz = scan.nextInt();
                        baza.add(znajdzIndeks(p), new Pracownik(p, nazwisko, imie, data, stanowisko, pensja, staz));
                        System.out.println("Pracownik dodany pomyślnie!" + "\n");
                    }
                    else
                    {
                        System.out.println("Pracownik o podanym PESELu instnieje już w bazie!");
                    }
                }
                if (n==6)
                {
                    System.out.println("Podaj PESEL pracownika, którego chcesz usunąć");
                    pesel = scan.nextLong();
                    usunPracownika(pesel);
                }
                if (n==7)
                {
                    System.out.println("Podaj PESEL pracownika, któremu chcesz zaktualizować dane");
                    pesel = scan.nextLong();
                    Pracownik znaleziony = znajdzPESEL(pesel);
                    if (znaleziony != null)
                    {
                        System.out.println("Co chcesz zaktualizować?");
                        System.out.println("1 - PESEL, 2 - nazwisko, 3 - imię, 4 - datę urodzenia, 5 - stanowisko, 6 - pensję, 7 - staż");
                        int n1 = scan.nextInt();
                        if (n1==1)
                        {
                            System.out.println("Podaj nowy PESEL (aktualny: " + znaleziony.getPESEL() + ")");
                            long pesel1 = scan.nextLong();
                            znaleziony.setPESEL(pesel1);
                            System.out.println("PESEL zaktualizowany pomyślnie!");
                        }
                        if (n1==2)
                        {
                            System.out.println("Podaj nowe nazwisko (aktualne: " + znaleziony.getNazwisko() + ")");
                            String nazwisko = scan.next();
                            znaleziony.setNazwisko(nazwisko);
                            System.out.println("Nazwisko zaktualizowane pomyślnie!");
                        }
                        if (n1==3)
                        {
                            System.out.println("Podaj nowe imię (aktualne: " + znaleziony.getImie() + ")");
                            String imie = scan.next();
                            znaleziony.setImie(imie);
                            System.out.println("Imię zaktualizowane pomyślnie!");
                        }
                        if (n1==4)
                        {
                            System.out.println("Podaj nową datę urodzenia (aktualna: " + znaleziony.getData_ur() + ")");
                            String data = scan.next();
                            znaleziony.setData_ur(data);
                            System.out.println("Data urodzenia zaktualizowana pomyślnie!");
                        }
                        if (n1==5)
                        {
                            System.out.println("Podaj nowe stanowisko (aktualne: " + znaleziony.getStanowisko() + ")");
                            String stanowisko = scan.next();
                            znaleziony.setStanowisko(stanowisko);
                            System.out.println("Stanowisko zaktualizowane pomyślnie!");
                        }
                        if (n1==6)
                        {
                            System.out.println("Podaj nową pensję (aktualna: " + znaleziony.getPensja() + ")");
                            double pensja = scan.nextDouble();
                            znaleziony.setPensja(pensja);
                            System.out.println("Pensja zaktualizowana pomyślnie!");
                        }
                        if (n1==7)
                        {
                            System.out.println("Podaj nowy staż (aktualny: " + znaleziony.getStaz() + ")");
                            int staz = scan.nextInt();
                            znaleziony.setStaz(staz);
                            System.out.println("Staż zaktualizowany pomyślnie!");
                        }
                    }
                    else
                    {
                        System.out.println("Brak pracownika o podanym PESELu");
                    }
                }
                if (n==8)
                {
                    System.out.println("Średnia pensja w firmie wynosi: " + sredniaPensja() + " zł" + "\n");
                }
                if (n==9)
                {
                    System.out.println("Liczba pracowników zarabiających poniżej średniej: " + zarobkiPonizejSredniej() + "\n");
                }
                if (n==10)
                {
                    System.out.println("Podaj nazwę pliku");
                    nazwa = scan.next();
                    zapisDoPliku(nazwa);
                }
                if (n==11)
                {
                    menu_parking();
                    int n2 = scan.nextInt();
                    while (n2!=4)
                    {
                        if (n2==1)
                        {
                            System.out.println("Podaj PESEL");
                            long l = scan.nextLong();
                            Pracownik pr = znajdzPESEL(l);
                            if (pr!=null)
                            {
                                parking.push(pr);
                                System.out.println("Samochód pracownika o nr PESEL: " + pr.getPESEL() + " (" + pr.getNazwisko() + " " + pr.getImie() + ")"
                                +  " wjechał na parking!");
                            }
                            else
                            {
                                System.out.println("Brak pracownika w bazie!");
                            }
                            System.out.println();
                        }
                        if (n2==2)
                        {
                            System.out.println("Podaj PESEL");
                            long l = scan.nextLong();
                            Pracownik pr = znajdzPESEL(l);
                            if (pr!=null)
                            {
                                doPrzestawienia = odwroc(doPrzestawienia);
                                if (pr.equals(doPrzestawienia.top()))
                                {
                                    przestawione.push(doPrzestawienia.pop());
                                    System.out.println("Pracownik o nr PESEL " + pr.getPESEL() + " (" + pr.getNazwisko() + " " + pr.getImie() + ") przestawił samochód!");
                                }
                                else
                                {
                                    System.out.println("Proszę poczekać, aż przestawione zostaną samochody za pojazdem!");
                                }
                            }
                            System.out.println();
                        }
                        if (n2==3)
                        {
                            System.out.println("Podaj PESEL");
                            long l = scan.nextLong();
                            Pracownik pr = znajdzPESEL(l);
                            if (pr!=null)
                            {
                                if (pr.equals(parking.top()))
                                {
                                    System.out.println("Pracownik o nr PESEL " + pr.getPESEL() + " (" + pr.getNazwisko() + " " + pr.getImie() + ") może wyjechać!");
                                    parking.pop();
                                    ListStack<Pracownik> Przestawione = odwroc(przestawione);
                                    if (!Przestawione.isEmpty())
                                    {
                                        while(!Przestawione.isEmpty())
                                        {
                                            System.out.println("Pracownik o nr PESEL: " + Przestawione.top().getPESEL() + " (" + Przestawione.top().getNazwisko()
                                             + " " + Przestawione.top().getImie() + ")" + " może ponownie wjechać na parking");
                                            Przestawione.pop();
                                        }
                                    }
                                }
                                else
                                {
                                    while (!pr.equals(parking.top()))
                                    {
                                        System.out.println("Pracownik o nr PESEL " + parking.top().getPESEL() + " (" + parking.top().getNazwisko() + " " + parking.top().getImie() + ") proszony o przestawienie samochodu!");
                                        doPrzestawienia.push(parking.pop());
                                    }
                                }
                            }
                            System.out.println();
                        }
                        menu_parking();
                        n2 = scan.nextInt();
                    }

                }
                menu();
                n = scan.nextInt();
            }
            catch (InputMismatchException | EmptyStackException e)
            {
                e.printStackTrace();
            }

        }
        scan.close();
    }



    public static void main (String[] args) throws FullStackException, EmptyStackException
    {
        dzialanie();
    }
}
