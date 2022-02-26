class EmptyStackException extends Exception {

    @Override
    public void printStackTrace() {
        System.out.println("Nieprawidłowe wyrażenie!");
    }
}

class FullStackException extends Exception {}

public interface IStack<T> {

    boolean isEmpty ();
    boolean isFull ();
    T pop() throws EmptyStackException;
    void push(T elem) throws FullStackException;
    int size();
    T top() throws EmptyStackException;
}
