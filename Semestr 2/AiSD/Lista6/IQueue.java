package Lista6;

class EmptyQueueException extends Exception {}
class FullQueueException extends Exception {}

public interface IQueue <T>{

    boolean isEmpty();
    boolean isFull();
    T dequeue() throws EmptyQueueException;
    void enqueue(T elem) throws FullQueueException;
    int size();
    T first() throws EmptyQueueException;

}
