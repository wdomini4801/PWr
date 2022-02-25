package Lista5;

public class ListStack <Pracownik> implements IStack<Pracownik>{

    OneWayLinkedListWithHead<Pracownik> _list;

    public ListStack()
    {
        _list = new OneWayLinkedListWithHead<Pracownik>();
    }

    @Override
    public boolean isEmpty()
    {
        return _list.isEmpty();

    }

    @Override
    public boolean isFull()
    {
        return false;
    }

    @Override
    public Pracownik pop() throws EmptyStackException
    {
        Pracownik value=_list.remove(0);
        if(value==null) throw new EmptyStackException();
        return value;
    }

    @Override
    public void push(Pracownik elem)
    {
        _list.add(0,elem);
    }

    @Override
    public int size()
    {
        return _list.size();
    }

    @Override
    public Pracownik top() throws EmptyStackException
    {
        Pracownik value=_list.get(0);
        if(value==null) throw new EmptyStackException();
        return value;
    }

}
