public class ArrayStack <T> implements IStack<T>{

    private static final int poj_pocz = 50;
    T[] tablica;
    int topIndex;

    public ArrayStack(int rozmiar)
    {
        this.tablica = (T[]) (new Object[rozmiar]);
        this.topIndex = 0;
    }

    public ArrayStack(T[] tablica)
    {
        this.tablica = tablica;
        this.topIndex = tablica.length;
    }

    public ArrayStack()
    {
        this(poj_pocz);
    }

    public boolean isEmpty()
    {
        return topIndex==0;
    }

    public boolean isFull()
    {
        return topIndex==tablica.length;
    }

    public T pop() throws EmptyStackException
    {
        if (!isEmpty())
        {
            return tablica[--topIndex];
        }
        else
            throw new EmptyStackException();
    }

    public void push(T elem) throws FullStackException
    {
        if (!isFull())
        {
            tablica[topIndex++] = elem;
        }
        else
            throw new FullStackException();
    }

    public int size ()
    {
        return topIndex;
    }

    public T top () throws EmptyStackException
    {
        if (!isEmpty())
        {
            return tablica[topIndex-1];
        }
        else
            throw new EmptyStackException();
    }

    public ArrayStack<T> odwroconyStos ()
    {
       int s = this.size();
       int j=0;
       T [] tablica1 = (T[]) (new Object[s]);
       for (int i=s-1; i>=0; i--)
       {
           tablica1[j] = tablica[i];
           j++;
       }
       return new ArrayStack<>(tablica1);
    }
}
