public class InvalidArgumentException extends Exception{

    @Override
    public void printStackTrace() {
        System.out.println("Nieprawidłowy kod - w kodzie mogą się znajdować tylko znako 0 lub 1!");
    }
}
