package Lista2;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Predicate;

public class ListaKart {
	
	private static ArrayList<Karta> karty;
	
	public ListaKart()
	{
		karty = new ArrayList<Karta>();
	}
	
	public void utworzListe()
	{
		karty.clear();
		Random generator = new Random ();
		int w = 14, k, i;
		
		while (w != 0)
		{
			w = generator.nextInt((13 - 0) + 1);
			k = generator.nextInt((3 - 0) + 1);
			
			if (w!=0)
			{
				for (i=0; i<karty.size(); i++)
				{
					if (w > karty.get(i).getWartosc())
					{
						karty.add(i, new Karta(w,k));
						break;
					}
					else if (w == karty.get(i).getWartosc())
						if (k > karty.get(i).getKolor())
						{
							karty.add(i, new Karta(w,k));
							break;
						}
				}
				if (i == karty.size())
				{
					karty.add(new Karta(w,k));
				}
			}
		}
	}
	
	public void wyswietlListe()
	{
		for (Karta k : karty)
		{
			System.out.println(k.toString());
		}
	}
	
	public void wyswietlLiczbeElementow()
	{
		System.out.println("Liczba elementów listy: " + karty.size() + "\n");
	}
	
	public void wyswietlWarunek(Predicate <Karta> warunek)
	{
		KIterator <Karta> iterator = new KIterator<Karta>(karty);
		FIterator <Karta> filtr = new FIterator <Karta> (iterator, warunek);
		while (filtr.hasNext())
		{
			System.out.println(filtr.next().toString());
		}
	}
	
	public void wyswietlKartyOWartosci(int wartosc)
	{
		wyswietlWarunek((Karta k) -> k.getWartosc() == wartosc);
	}
	
	public void wyswietlKartyOKolorze(int kolor)
	{
		wyswietlWarunek((Karta k) -> k.getKolor() == kolor);
	}
	
	public void usunPowtorzenia()
	{
		karty.add(new Karta());
		karty.add(new Karta());
		karty.add(new Karta());
		
		for (int i=0; i<karty.size()-1; i++)
		{
			if (karty.get(i).equals(karty.get(i+1)))
			{
				karty.remove(i);
				i--;
			}
		}
		wyswietlListe();
	}

}
