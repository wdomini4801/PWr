import java.util.Locale;
import java.util.Scanner;

public class Nawiasy {

    public static boolean nawiasyZrownowazone(String wyrazenie) throws EmptyStackException, FullStackException
    {
        int l = wyrazenie.length();
        ArrayStack<Character> stos = new ArrayStack<>(l);
        for (int i=0; i<l; i++)
        {
            char c = wyrazenie.charAt(i);
            if (nawiasOtwierajacy(c))
            {
                stos.push(c);
            }
            if (nawiasZamykajacy(c))
            {
                if (!stos.isEmpty())
                {
                    char c1 = stos.top();
                    if (czyOdpowiada(c1, c))
                    {
                        stos.pop();
                    }
                    else
                        return false;
                }
                else
                {
                    return false;
                }
            }
        }
        return stos.isEmpty();
    }

    public static boolean palindrom(String napis) throws FullStackException, EmptyStackException {
        String napis2 = napis.toLowerCase(Locale.ROOT);
        int l = napis2.length();
        ArrayStack<Character> stos = new ArrayStack<>(l);
        for (int i=0; i<l; i++)
        {
            char c = napis2.charAt(i);
            stos.push(c);
        }
        ArrayStack <Character> odwrocony = stos.odwroconyStos();
        for (int i=0; i<l; i++)
        {
            char c1 = stos.pop();
            char c2 = odwrocony.pop();
            if (c1!=c2)
                return false;
        }
        return true;
    }

    public static boolean nawiasOtwierajacy(char ch)
    {
        return ch == '(' || ch == '[' || ch == '{';
    }

    public static boolean nawiasZamykajacy(char ch)
    {
        return ch == ')' || ch == ']' || ch == '}';
    }

    public static boolean czyOdpowiada(char ch1, char ch2)
    {
        return (ch1 == '(' && ch2 == ')') || (ch1 == '[' && ch2 == ']') || (ch1 == '{' && ch2 == '}');
    }

    public static void czyZrownowazone(String napis) throws FullStackException, EmptyStackException {
        if (nawiasyZrownowazone(napis))
        {
            System.out.println("Nawiasy zrównoważone");
        }
        else
        {
            System.out.println("Nawiasy niezrównoważone");
        }
    }

    public static void czyPalindrom(String napis) throws FullStackException, EmptyStackException {
        if (palindrom(napis))
        {
            System.out.println("Jest to palindrom");
        }
        else
        {
            System.out.println("Nie jest to palindrom");
        }
    }

    public static void main(String[] args) throws FullStackException, EmptyStackException {

        String napis = "(w*[x+y]/z-[p/{r-q}])";
        Scanner scan = new Scanner(System.in);
        //System.out.println("Podaj wyrażenie do sprawdzenia");
        //napis = scan.next();
        czyZrownowazone(napis);
        //czyPalindrom(napis);
        scan.close();
    }
}
