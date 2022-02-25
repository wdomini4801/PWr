package Lista4;

import java.util.Iterator;
import java.util.Random;

public class ListaKart {

    public static OneWayLinkedListWithHead<Karta> karty;
    public static boolean[] zawartosc;

    public ListaKart()
    {
        karty = new OneWayLinkedListWithHead<>();
        zawartosc = new boolean[52];
    }

    public void wyzerujTablice()
    {
        for (int i=0; i<52; i++)
            zawartosc[i] = false;
    }

    public int znajdzIndeks(int w, int k)
    {
        Iterator<Karta> iter = karty.iterator();
        int i=0;
        while (iter.hasNext())
        {
            Karta aktualna = iter.next();

            if (!aktualna.getZnacznik())
                return i;
            else
            {
                if (aktualna.getWartosc()<w)
                    return i;
                else
                {
                    if (aktualna.getWartosc()==w)
                    {
                        if (aktualna.getKolor()<k)
                            return i;
                        else
                            i++;
                    }
                    else
                        i++;
                }
            }
        }
        return i;
    }



    public void utworzListe()
    {
        Random generator = new Random();
        int w = 14, k, n=0;
        wyzerujTablice();
        karty.clear();

        while (w!=0)
        {
            w = generator.nextInt((14) + 1);
            k = generator.nextInt((3) + 1);

            if (w!=0)
            {
                if (w==14)
                {
                    karty.add(new Karta(w, k, false));
                    n++;
                }
                else
                {
                    if (!zawartosc[(w - 1) + 13 * k])
                    {
                        if (n==0)
                        {
                            karty.add(new Karta(w, k, true));
                        }
                        else
                        {
                            karty.add(znajdzIndeks(w, k), new Karta(w, k, true));
                        }
                        zawartosc[(w-1)+13*k]=true;
                        n++;
                    }
                }
            }
        }

    }

    public void wyswietlListe()
    {
        for (Karta karta : karty)
            System.out.println(karta);
    }

    public void wyswietlLiczbeElementow()
    {
        int z=0, o=0;
        for (Karta k : karty)
        {
            if (k.getZnacznik())
                o++;
            else
                z++;
        }
        System.out.println("Liczba elementów listy: " + (o+z));
        System.out.println("Kart zakrytych: " + z);
        System.out.println("Kart odkrytych: " + o);
    }

    public void wyswietlKartyOWartosci(int wartosc)
    {
        for (Karta k : karty)
        {
            if (k.getWartosc()==wartosc && k.getZnacznik())
                System.out.println(k);
        }
    }

    public void wyswietlKartyOKolorze(int kolor)
    {
        for (Karta k : karty)
        {
            if (k.getKolor()==kolor && k.getZnacznik())
                System.out.println(k);
        }
    }

    public void usunZakryte()
    {
        for (Karta k : karty)
        {
            if (!k.getZnacznik())
                karty.remove(k);
        }
        wyswietlListe();
    }
}
