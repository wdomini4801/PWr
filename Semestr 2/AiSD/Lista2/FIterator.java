package Lista2;

import java.util.Iterator;
import java.util.function.Predicate;

public class FIterator <T> implements Iterator <T>{
	
	private Iterator <T> iterator;
	private Predicate <T> predicate; 
	
	private T eNext = null;
	private boolean bHasNext = true;
	
	public FIterator (Iterator<T> iterator, Predicate<T> predicate)
	{
		this.iterator = iterator;
		this.predicate = predicate;
		findNextValid();
	}
	
	private void findNextValid ()
	{
		while (iterator.hasNext())
		{
			eNext = iterator.next();
			if (predicate.test(eNext))
			{
				return;
			}
		}
		bHasNext = false;
		eNext = null;
	}
	
	public boolean hasNext ()
	{
		return bHasNext;
	}
	
	public T next ()
	{
		T nextValue = eNext;
		findNextValid();
		return nextValue;
	}

}
