package Lista2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class KIterator <T> implements Iterator <T>{
	
	private ArrayList<T> lista;
	private int poz = -1;
	
	public KIterator (ArrayList<T> list)
	{
		lista = list;
	}
	
	public boolean hasNext()
	{
		if (poz < lista.size()-1)
			return true;
		else
			return false;
	}
	
	public T next () throws NoSuchElementException
	{
		if (hasNext())
		{
			poz++;
			return lista.get(poz);
		}
		else
			throw new NoSuchElementException();
	}

}
