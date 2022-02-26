public class BST {

    Node root;

    public BST (Node root)
    {
        this.root = root;
    }

    private boolean czyOperator(String e)
    {
        return e.equals("+") || e.equals("-") || e.equals("*") || e.equals("/") || e.equals("%");
    }

    private double operation(double a, double b, String s)
    {
        if (s.equals("+"))
            return a + b;
        if (s.equals("-"))
            return a - b;
        if (s.equals("*"))
            return a * b;
        if (s.equals("/"))
        {
            if (b == 0)
            {
                throw new IllegalArgumentException();
            }
            else
                return a/b;
        }
        if (s.equals("%"))
            if (b == 0)
                throw new IllegalArgumentException();
            else
                return a%b;

        return 0;
    }

    private void postorder(Node elem)
    {
        if (elem.getLeft() != null)
        {
            postorder(elem.getLeft());
        }
        if (elem.getRight() != null)
        {
            postorder(elem.getRight());
        }
        System.out.print(" " + elem.getValue());
    }

    public void wyswietlPostfiks()
    {
        System.out.print("Wyrażenie w postaci postfiksowej (ONP): ");
        postorder(root);
        System.out.print(" = "+  wynik());
        System.out.println();
    }

    private void inorder(Node elem)
    {
        if (elem.getLeft() != null)
        {
            System.out.print("(");
            inorder(elem.getLeft());
        }

        if (!czyOperator(elem.getValue()))
            System.out.print("(" + elem.getValue() + ")");
        else
            System.out.print(elem.getValue());

        if (elem.getRight() != null)
        {
            inorder(elem.getRight());
            System.out.print(")");
        }
    }

    public void wyswietlInfiks()
    {
        System.out.print("Wyrażenie w postaci infiksowej (z nawiasami): ");
        inorder(root);
        System.out.print(" = " +  wynik());
        System.out.println();
    }

    private double evaluate(Node elem)
    {
        if (elem==null)
            return 0;
        if (elem.getLeft()==null && elem.getRight()==null)
            return Integer.parseInt(elem.getValue());

        double a = evaluate(elem.getLeft());
        double b = evaluate(elem.getRight());

        return operation(a, b, elem.getValue());
    }

    private double wynik()
    {
        return evaluate(root);
    }

    public void wartoscWyrazenia()
    {
        System.out.println("Wartość wyrażenia zapisanego w drzewie wynosi: " + wynik());
    }

    private int leaves(Node elem)
    {
        int count = 0;
        if (elem!=null)
        {
            if (elem.getLeft() == null && elem.getRight() == null)
                count++;
            if (elem.getLeft() != null)
                count += leaves(elem.getLeft());
            if (elem.getRight() != null)
                count += leaves(elem.getRight());
        }
        return count;
    }

    public void liczbaLisci()
    {
        System.out.println("Liczba liści: " + leaves(root));
    }

    private int nodes(Node elem)
    {
        int count = 0;
        if (elem == null)
            return 0;

        count++;

        if (elem.getLeft() != null)
            count+=nodes(elem.getLeft());
        if (elem.getRight() != null)
            count+=nodes(elem.getRight());

        return count;
    }

    public void liczbaWezlow()
    {
        System.out.println("Liczba węzłów: " + nodes(root));
    }

    private int height(Node elem)
    {
        int count =- 1;
        if (elem != null)
        {
            count = Math.max(height(elem.getLeft()), height(elem.getRight()));
            count++;
        }
        return count;
    }

    public void wysokosc()
    {
        System.out.println("Wysokość: " + height(root));
    }

    private void levelorder(Node elem) throws EmptyQueueException
    {
        if (elem != null)
        {
            ArrayQueue<Node> kolejka = new ArrayQueue<>();
            kolejka.enqueue(elem);
            while (!kolejka.isEmpty())
            {
                Node n = kolejka.dequeue();
                System.out.print(n.getValue() + " ");

                if (n.getLeft() != null)
                    kolejka.enqueue(n.getLeft());
                if (n.getRight() != null)
                    kolejka.enqueue(n.getRight());
            }
        }
    }

    public void przechodzenieWszerz() throws EmptyQueueException
    {
        System.out.print("Drzewo wszerz: ");
        levelorder(root);
        System.out.println();
    }
}
