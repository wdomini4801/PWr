package Lista1;

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;

public class Main {
	
	public static Pracownik[] pracownicy = new Pracownik[10];
	public static int i=0;
	
	public static void menu ()
	{
		System.out.println("Wybierz czynnoœæ");
		System.out.println("1 - dodanie pracownika etatowego");
		System.out.println("2 - dodanie pracownika godzinowego");
		System.out.println("3 - wyœwietlenie listy pracowników");
		System.out.println("4 - zapis do pliku");
		System.out.println("5 - odczyt danych z pliku");
		System.out.println("6 - wyjœcie");
	}
	
	public static void dzialanie ()
	{
		Scanner scan = new Scanner (System.in);
		int n;
		menu();
		n = scan.nextInt();
		while (n!=6)
		{
			try
			{
				if (n==1)
				{
					System.out.println();
					System.out.println("Podaj nazwisko");
					String nazwisko = scan.next();
					System.out.println("Podaj imiê");
					String imie = scan.next();
					System.out.println("Podaj PESEL");
					long pesel = scan.nextLong();
					System.out.println("Podaj stanowisko");
					String stanowisko = scan.next();
					System.out.println("Podaj sta¿");
					int staz = scan.nextInt();
					System.out.println("Podaj etat");
					double etat = scan.nextDouble();
					System.out.println("Podaj podstawow¹ pensjê");
					double stawka = scan.nextDouble();
					pracownicy[i] = new PracownikEtatowy(nazwisko, imie, pesel, stanowisko, staz, etat, stawka);
					System.out.println("Dodano!" + "\n");	
					i++;
				}
				if (n==2)
				{
					System.out.println();
					System.out.println("Podaj nazwisko");
					String nazwisko = scan.next();
					System.out.println("Podaj imiê");
					String imie = scan.next();
					System.out.println("Podaj PESEL");
					long pesel = scan.nextLong();
					System.out.println("Podaj stanowisko");
					String stanowisko = scan.next();
					System.out.println("Podaj sta¿");
					int staz = scan.nextInt();
					System.out.println("Podaj liczbê przepracowanych godzin");
					int liczba_godz = scan.nextInt();
					System.out.println("Podaj podstawow¹ stawkê");
					double stawka = scan.nextDouble();
					pracownicy[i] = new PracownikEtatowy(nazwisko, imie, pesel, stanowisko, staz, liczba_godz, stawka);
					System.out.println("Dodano!" + "\n");	
					i++;
				}
				if (n==3)
				{
					System.out.println();
					naglowek();
					wyswietlListe();
					System.out.println();
				}
				if (n==4)
				{
					System.out.println();
					System.out.println("Podaj nazwê pliku");
					String nazwa = scan.next();
					zapis(nazwa);
					System.out.println();
				}
				if (n==5)
				{
					System.out.println();
					System.out.println("Podaj nazwê pliku");
					String nazwa = scan.next();
					odczyt(nazwa);
					System.out.println();
				}
				menu();
				n = scan.nextInt();
			}
			catch (InputMismatchException e)
			{
				e.printStackTrace();
			}
			
		}
		scan.close();
	}
	
	public static void zapis (String plik)
	{
		XIterator <Pracownik> it = new XIterator <Pracownik> (pracownicy, i);
		try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("src/Serializacja/pliki/" + plik + ".ser")))
		{
			os.writeInt(i);
			while (it.hasNext())
			{
				os.writeObject(it.next());
			}
			System.out.println("Zapisano!");
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.out.println("B³¹d podczas zapisu do pliku!");
		}
	}
	
	public static void odczyt (String plik)
	{
		try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(new File ("src/Serializacja/pliki/" + plik + ".ser"))))
		{
			System.out.println();
			int liczba = is.readInt();
			Pracownik pr;
			System.out.println("Liczba pracowników - " + liczba + "\n");
			naglowek();
			for (int i=0; i<liczba; i++)
			{
				Object obj = is.readObject();
				pr = (Pracownik)obj;
				pr.wyswietl();
			}
		}
		catch (IOException | ClassNotFoundException e) 
		{
			 e.printStackTrace();
			 System.out.println("B³¹d podczas odczytu z pliku!");
		}
	}
	
	public static void naglowek ()
	{
		System.out.println("-------------------------------------------------------------------------------------------------");
		System.out.print("|");
		System.out.printf("%-20s", "Nazwisko");
		System.out.print("|");
		System.out.printf("%-15s", "Imiê");
		System.out.print("|");
		System.out.printf("%-11s", "PESEL");
		System.out.print("\t" + "|");
		System.out.printf("%-15s", "Stanowisko");
		System.out.print("|");
		System.out.printf("%-7s", "Sta¿");
		System.out.print("|");
		System.out.printf("%-8s", "Pensja");
		System.out.print("\t" + "|");
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------------------");
	}
	
	public static void wyswietlListe ()
	{
		XIterator <Pracownik> it = new XIterator <Pracownik> (pracownicy, i);
		
		while (it.hasNext())
			it.next().wyswietl();
	}
	
	public static void main(String[] args) {
		
		dzialanie();
	}
	

}
