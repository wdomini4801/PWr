package Lista6;

public class ListQueue <T> implements IQueue<T> {

    OneWayLinkedListWithHeadTail<T> queue;

    public ListQueue ()
    {
        queue = new OneWayLinkedListWithHeadTail<T>();
    }

    public boolean isEmpty ()
    {
        return queue.isEmpty();
    }

    public boolean isFull ()
    {
        return false;
    }

    public T dequeue () throws EmptyQueueException
    {
        if (queue.isEmpty())
        {
            throw new EmptyQueueException();
        }
        else
            return queue.remove(0);
    }

    public void enqueue (T elem)
    {
        queue.add(elem);
    }

    public int size ()
    {
        return queue.size();
    }

    public T first ()
    {
        return queue.get(0);
    }

}
