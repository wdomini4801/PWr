import java.util.ArrayList;

public class ArrayQueue <T> implements IQueue<T>{

    ArrayList<T> queue;

    public ArrayQueue ()
    {
        queue = new ArrayList<>();
    }

    @Override
    public boolean isEmpty()
    {
        return queue.isEmpty();
    }

    @Override
    public boolean isFull()
    {
        return false;
    }

    @Override
    public T dequeue() throws EmptyQueueException
    {
        if (queue.isEmpty())
            throw new EmptyQueueException();
        else
        {
            return queue.remove(0);
        }
    }

    @Override
    public void enqueue(T elem)
    {
        queue.add(elem);
    }

    @Override
    public int size()
    {
        return queue.size();
    }

    @Override
    public T first()
    {
        return queue.get(0);
    }
}
