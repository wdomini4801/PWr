package Lista4;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {

    public static ListaKart lista = new ListaKart();

    public static void menu()
    {
        System.out.println("Wybierz czynność");
        System.out.println("1 - utworzenie listy");
        System.out.println("2 - wyświetlenie listy");
        System.out.println("3 - wyświetlenie liczby elementów listy");
        System.out.println("4 - wyświetlenie kart o podanej wartości");
        System.out.println("5 - wyświetlenie kart o podanym kolorze");
        System.out.println("6 - usunięcie zakrytych kart");
        System.out.println("7 - wyjście");
    }

    public static void dzialanie()
    {
        Scanner scan = new Scanner (System.in);
        int n, w;
        menu();
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
                    System.out.println();
                }
                if (n==4)
                {
                    System.out.println("Wybierz wartość (1 - as, 2-10 odpowiednio, 11 - walet, 12 - dama, 13 - król)");
                    w = scan.nextInt();
                    System.out.println("Karty o podanej wartości z listy to: ");
                    lista.wyswietlKartyOWartosci(w);
                    System.out.println();
                }
                if (n==5)
                {
                    System.out.println("Wybierz kolor (0- kier, 1 - karo, 2 - trefl, 3 - pik)");
                    w = scan.nextInt();
                    System.out.println("Karty o podanym kolorze z listy to: ");
                    lista.wyswietlKartyOKolorze(w);
                    System.out.println();
                }
                if (n==6)
                {
                    System.out.println("Lista kart po usunięciu zakrytych:");
                    lista.usunZakryte();
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

    public static void main(String[] args)
    {
        dzialanie();
    }
}
