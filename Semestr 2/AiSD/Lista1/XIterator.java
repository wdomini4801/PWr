package Lista1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class XIterator <T> implements Iterator <T> {
	
		private T[] tablica;
		private int poz = -1;
		private int il;
		
		public XIterator (T[] tabl)
		{
			tablica = tabl;
			il = tabl.length;
		}
		
		public XIterator (T[] tabl, int ilosc)
		{
			tablica = tabl;
			il = ilosc;
		}

		public boolean hasNext()
		{
			if (poz < il-1)
				return true;
			else
				return false;
		}
		
		public T next () throws NoSuchElementException
		{
			if (hasNext())
			{
				poz++;
				return tablica[poz];
			}
			else
				throw new NoSuchElementException();
		}
		
	}

