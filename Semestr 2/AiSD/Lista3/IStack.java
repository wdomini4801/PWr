class EmptyStackException extends Exception {}

class FullStackException extends Exception {}

public interface IStack <T> {

    boolean isEmpty();
    boolean isFull();
    T pop() throws EmptyStackException;
    void push(T elem) throws FullStackException;
    int size();
    T top() throws EmptyStackException;
}
