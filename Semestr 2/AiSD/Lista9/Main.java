import java.util.Scanner;

public class Main {

    public static int priorytet(char c)
    {
        if (c=='(')
            return 0;
        if (c=='+' || c=='-')
            return 1;
        if (c=='*' || c=='/')
            return 2;
        else
            return -1;
    }

    public static boolean czyOperator(char c)
    {
        return c == '+' || c == '-' || c == '*' || c == '/' || c == '%';
    }

    public static String utworzONP(String wyrazenie) throws FullStackException, EmptyStackException
    {
        ArrayStack<Character> stos = new ArrayStack<>(wyrazenie.length());
        StringBuilder sb = new StringBuilder();
        StringBuilder wyj = new StringBuilder();
        for (int i=0; i<wyrazenie.length(); i++)
        {
            char e = wyrazenie.charAt(i);
            if (e>='0' && e <= '9')
            {
                sb.append(e);
            }
            else
            {
                if (!sb.isEmpty())
                {
                    wyj.append(sb);
                    wyj.append(" ");
                    sb.delete(0, sb.length());
                }
                if (e=='(')
                    stos.push(e);
                if (e==')')
                {
                    while (stos.top()!='(')
                    {
                        wyj.append(stos.pop());
                        wyj.append(" ");
                    }
                    if (stos.top()=='(')
                        stos.pop();
                }
                if (czyOperator(e))
                {
                    while (!stos.isEmpty() && czyOperator(stos.top()) && priorytet(e)<=priorytet(stos.top()))
                    {
                        wyj.append(stos.pop());
                        wyj.append(" ");
                    }
                    stos.push(e);
                }
            }
        }
        if (!sb.isEmpty())
        {
            wyj.append(sb);
            wyj.append(" ");
        }
        while (!stos.isEmpty())
        {
            wyj.append(stos.pop());
        }
        return wyj.toString();
    }

    public static BST utworzDrzewo(String wyrazenie) throws FullStackException, EmptyStackException
    {
        ArrayStack<Node> stos = new ArrayStack<>(wyrazenie.length());
        String [] onp = wyrazenie.split("[ ]");

        for (String e : onp)
        {
            if (e.equals("+") || e.equals("-") || e.equals("*") || e.equals("/") || e.equals("%"))
            {
                Node n = new Node(e);
                n.setRight(stos.pop());
                n.setLeft(stos.pop());
                stos.push(n);
            }
            else {
                stos.push(new Node(e));
            }
        }
        return new BST(stos.pop());
    }

    public static void main (String [] args) throws FullStackException, EmptyStackException, EmptyQueueException
    {
        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj wyrażenie arytmetyczne (pamiętaj o nawiasach!)");
        String w = scan.nextLine();
        String w_onp = utworzONP(w);
        BST drz = utworzDrzewo(w_onp);
        drz.wyswietlInfiks();
        drz.wyswietlPostfiks();
        drz.liczbaLisci();
        drz.liczbaWezlow();
        drz.wysokosc();
        drz.przechodzenieWszerz();
    }
}
