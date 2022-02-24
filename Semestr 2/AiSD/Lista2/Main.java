package Lista2;

import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {
	
	public static void Menu ()
	{
		System.out.println("Wybierz czynnoœæ");
		System.out.println("1 - utworzenie listy");
		System.out.println("2 - wyœwietlenie listy");
		System.out.println("3 - wyœwietlenie liczby elementów listy");
		System.out.println("4 - wyœwietlenie kart o podanej wartoœci");
		System.out.println("5 - wyœwietlenie kart o podanym kolorze");
		System.out.println("6 - usuniêcie powtarzaj¹cych siê kart");
		System.out.println("7 - wyjœcie");
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
					System.out.println("Wybierz wartoœæ (1 - as, 2-10 odpowiednio, 11 - walet, 12 - dama, 13 - król)");
					w = scan.nextInt();
					System.out.println("Karty o podanej wartoœci z listy to: ");
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
					System.out.println("Lista kart bez powtórzeñ:");
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
