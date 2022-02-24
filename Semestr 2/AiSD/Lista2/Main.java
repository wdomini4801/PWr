package Lista2;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
	
	public static void Menu ()
	{
		System.out.println("Wybierz czynno��");
		System.out.println("1 - utworzenie listy");
		System.out.println("2 - wy�wietlenie listy");
		System.out.println("3 - wy�wietlenie liczby element�w listy");
		System.out.println("4 - wy�wietlenie kart o podanej warto�ci");
		System.out.println("5 - wy�wietlenie kart o podanym kolorze");
		System.out.println("6 - usuni�cie powtarzaj�cych si� kart");
		System.out.println("7 - wyj�cie");
	}
	
	public static void dzialanie ()
	{
		Scanner scan = new Scanner (System.in);
		int n, w;
		ListaKart lista = new ListaKart();
		Menu();
		n = scan.nextInt();
		while (n!=7)
		{
			try
			{
				if (n==1)
				{
					lista.utworzListe();
					System.out.println("Lista utworzona!" + "\n");
				}
				if (n==2)
				{
					System.out.println("Lista kart: ");
					lista.wyswietlListe();
					System.out.println();
				}
				if (n==3)
				{
					lista.wyswietlLiczbeElementow();
				}
				if (n==4)
				{
					System.out.println("Wybierz warto�� (1 - as, 2-10 odpowiednio, 11 - walet, 12 - dama, 13 - kr�l)");
					w = scan.nextInt();
					System.out.println("Karty o podanej warto�ci z listy to: ");
					lista.wyswietlKartyOWartosci(w);
					System.out.println();
				}
				if (n==5)
				{
					System.out.println("Wybierz kolor (0 - kier, 1 - karo, 2 - trefl, 3 - pik)");
					w = scan.nextInt();
					System.out.println("Karty o podanym kolorze z listy to: ");
					lista.wyswietlKartyOKolorze(w);
					System.out.println();
				}
				if (n==6)
				{
					System.out.println("Lista kart bez powt�rze�:");
					lista.usunPowtorzenia();
					System.out.println();
				}
				Menu();
				n = scan.nextInt();
			}
			catch (InputMismatchException e)
			{
				e.printStackTrace();
			}
			
		}
		scan.close();
	}
	
	public static void main(String[] args) {
		
			dzialanie();
	}

}
