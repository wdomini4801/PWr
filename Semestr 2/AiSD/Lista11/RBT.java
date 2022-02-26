import java.util.ArrayList;

public class RBT {

    Node root;
    Node TNULL;

    public RBT ()
    {
        this.root = null;
    }

    private Node search(Node elem, String value)
    {
        if (elem == null || value.equals(elem.getValue()))
            return elem;
        if (value.compareTo(elem.getValue()) < 0)
            return search(elem.getLeft(), value);
        return search(elem.getRight(), value);
    }

    public Node find(String value)
    {
        return search(root, value);
    }

    private void rotateLeft(Node elem)
    {
        Node prawy = elem.getRight(); // prawe dziecko elementu
        elem.setRight(prawy.getLeft());

        if (prawy.getLeft() != null)
        {
            prawy.getLeft().setParent(elem);
        }

        prawy.setParent(elem.getParent());

        if (elem.getParent() == null)
        {
            this.root = prawy;
        }
        else if (elem.getValue().equals(elem.getParent().getLeft().getValue()))
        {
            elem.getParent().setLeft(prawy);
        }
        else
        {
            elem.getParent().setRight(prawy);
        }
        prawy.setLeft(elem);
        elem.setParent(prawy);
    }

    private void rotateRight(Node elem)
    {
        Node lewy = elem.getLeft();
        elem.setLeft(lewy.getRight());

        if (lewy.getRight() != null)
        {
            lewy.getRight().setParent(elem);
        }

        lewy.setParent(elem.getParent());

        if (elem.getParent() == null)
        {
            this.root = lewy;
        }
        else if (elem.getValue().equals(elem.getParent().getRight().getValue()))
        {
            elem.getParent().setRight(lewy);
        }
        else
        {
            elem.getParent().setLeft(lewy);
        }
        lewy.setRight(elem);
        elem.setParent(lewy);
    }

    private void repair(Node elem)
    {
        Node uncle;
        while (elem.getParent().getColor().equals("R")) // dopóki rodzic czerwony - naprawa
        {
            if (elem.getParent().getParent().getRight() != null && elem.getParent().getValue().equals(elem.getParent().getParent().getRight().getValue()))
            {
                uncle = elem.getParent().getParent().getLeft();
                if (uncle != null && uncle.getColor().equals("R"))
                {
                    uncle.setColor("B");            //przypadek I - wujek czerwony - przekolorowanie
                    elem.getParent().setColor("B");
                    elem.getParent().getParent().setColor("R");
                    elem = elem.getParent().getParent();
                }
                else // wujek czarny
                {
                    if (elem.getParent().getLeft() != null && elem.getValue().equals(elem.getParent().getLeft().getValue()))
                    {
                        elem = elem.getParent(); //przypadek II - element jest lewym dzieckiem rodzica - rotacja w prawo i przejście do przypadku III
                        rotateRight(elem);
                    }
                    elem.getParent().setColor("B"); //przypadek III - element jest prawym dzieckiem rodzica - rotacja w lewo i przekolorowanie rodzica i dziadka
                    elem.getParent().getParent().setColor("R");
                    rotateLeft(elem.getParent().getParent());
                }
            }
            else
            {
                uncle = elem.getParent().getParent().getRight(); // lustrzane odbicie - rodzic elementu jest lewym dzieckiem dziadka
                if (uncle != null && uncle.getColor().equals("R"))
                {
                    uncle.setColor("B"); // przypadek I - wujek czerwony - przekolorowanie
                    elem.getParent().setColor("B");
                    elem.getParent().getParent().setColor("R");
                    elem = elem.getParent().getParent();
                }
                else
                {
                    if (elem.getParent().getRight() != null && elem.getValue().equals(elem.getParent().getRight().getValue()))
                    {
                        elem = elem.getParent(); // przypadek II - element jest prawym dzieckiem rodzica - rotacja w lewo i przejście do przypadku III
                        rotateLeft(elem);
                    }
                    elem.getParent().setColor("B"); // przypadek III - element jest lewym dzieckiem rodzica - przekolorowanie rodzica i dziadka oraz rotacja w prawo
                    elem.getParent().getParent().setColor("R");
                    rotateRight(elem.getParent().getParent());
                }
            }
            if (elem.getValue().equals(root.getValue()))
                break;
        }
        root.setColor("B"); //korzeń musi być czarny
    }

    public void Insert(String wyraz, int linia)
    {
        Node nowy = new Node(wyraz, linia);
        Node y = null;
        Node x = this.root;

        while (x!=null) // szukamy miejsca dla elementu - jak w BST
        {
            y = x; // rodzic
            if (nowy.getValue().compareTo(x.getValue()) < 0)
                x = x.getLeft();
            else
                x = x.getRight();
        }

        nowy.setParent(y);
        if (y == null)
            root = nowy;
        else if (nowy.getValue().compareTo(y.getValue()) < 0)
            y.setLeft(nowy);
        else if (nowy.getValue().compareTo(y.getValue()) > 0)
            y.setRight(nowy);

        if (nowy.getParent() == null)
        {
            nowy.setColor("B");
            return;
        }
        if (nowy.getParent().getParent() == null)
            return;

        repair(nowy);
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

    private void inorder(Node elem)
    {
        if (elem.getLeft() != null)
        {
            inorder(elem.getLeft());
        }

        elem.display();

        if (elem.getRight() != null)
        {
            inorder(elem.getRight());
        }
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
                n.display();
                if (n.getLeft() != null)
                    kolejka.enqueue(n.getLeft());
                if (n.getRight() != null)
                    kolejka.enqueue(n.getRight());
            }
        }
    }

    public void przechodzenieWszerz() throws EmptyQueueException
    {
        System.out.println("Drzewo wszerz: ");
        levelorder(root);
        System.out.println();
    }

    public void wyswietlWyrazy()
    {
        System.out.println("Skorowidz:");
        inorder(root);
    }

    private Node minimum(Node elem)
    {
        while (elem.getLeft() != null)
            elem = elem.getLeft();
        return elem;
    }

    private void repairDelete(Node elem)
    {
        Node w;
        while (elem != null && !elem.getValue().equals(root.getValue()) && elem.getColor().equals("B"))
        {
            if (elem.getValue().equals(elem.getParent().getLeft().getValue())) // element jest lewym dzieckiem rodzica
            {
                w = elem.getParent().getRight(); // brat - prawe dziecko rodzica
                if (w.getColor().equals("R")) // przypadek I - brat czerwony - przekolorowanie i rotacja w lewo
                {
                    w.setColor("B");
                    elem.getParent().setColor("R");
                    rotateLeft(elem.getParent());
                    w = elem.getParent().getRight(); // aktualizujemy brata - po rotacji spadł w dół, więc jest prawym dzieckiem
                }
                if (w.getLeft().getColor().equals("B") && w.getRight().getColor().equals("B")) // przypadek II - obydwoje dzieci brata są czarne
                {
                    w.setColor("R"); // przekolorowanie brata
                    elem = elem.getParent(); // przesuwamy się w górę o 1 poziom - do rodzica
                }
                else
                {
                    if (w.getRight().getColor().equals("B")) // przypadek III - prawe dziecko brata czarne, lewe - czerwone
                    {
                        w.getLeft().setColor("B"); // przekolorowanie
                        w.setColor("R");
                        rotateRight(w); // rotacja w prawo
                        w = elem.getRight().getParent(); // przygotowanie do przypadku IV - aktualizacja brata po rotacji
                    }
                    // przypadek IV - prawe dziecko brata czerwone, lewe - czarne
                    w.setColor(elem.getParent().getColor()); // przekolorowanie brata na kolor rodzica
                    elem.getParent().setColor("B");
                    w.getRight().setColor("B");
                    rotateLeft(elem.getParent()); // rotacja w lewo
                    elem = root; // przesuwamy analizę do korzenia
                }
            }
            else // lustrzane odbicie
            {
                w = elem.getParent().getLeft();
                if (elem.getValue().equals(elem.getParent().getRight().getValue()))
                {
                    w = elem.getParent().getLeft();
                }
                if (w.getColor().equals("R"))
                {
                    w.setColor("B");
                    elem.getParent().setColor("R");
                    rotateRight(elem.getParent());
                    w = elem.getParent().getLeft();
                }
                if (w.getRight().getColor().equals("B") && w.getLeft().getColor().equals("B"))
                {
                    w.setColor("R");
                    elem = elem.getParent();
                }
                else
                {
                    if (w.getLeft().getColor().equals("B"))
                    {
                        w.getRight().setColor("B");
                        w.setColor("R");
                        rotateLeft(w);
                        w = elem.getLeft().getParent();
                    }
                    w.setColor(elem.getParent().getColor());
                    elem.getParent().setColor("B");
                    w.getLeft().setColor("B");
                    rotateRight(elem.getParent());
                    elem = root;
                }
            }
        }
        if (elem != null)
            elem.setColor("B");
    }

    private Node successor(Node elem)
    {
        if (elem.getRight() != null)
        {
            return minimum(elem.getRight());
        }
        Node y = elem.getParent();
        while (y!=null && elem.getValue().equals(y.getRight().getValue()))
        {
            elem = y;
            y = y.getParent();
        }
        return y;
    }

    private void swap(Node x, Node y)
    {
        String v1 = x.getValue();
        String v2 = y.getValue();
        ArrayList<Integer> l1 = x.getLines();
        ArrayList<Integer> l2 = y.getLines();

        x.setValue(v2);
        y.setValue(v1);
        x.setLines(l2);
        y.setLines(l1);
    }

    public void delete(String value)
    {
        Node z = this.find(value);
        Node x;
        Node y;
        if (z == null)
        {
            System.out.println("Nie znaleziono wyrazu w tekście!");
            return;
        }
        else
        {
            if (z.getLeft() == null || z.getRight() == null)
            {
                y = z; // element jest liściem
            }
            else
            {
                y = successor(z); // element nie jest liściem - będzie zastąpiony przez następnik
            }
            if (y.getLeft()!=null)
            {
                x = y.getLeft(); // ustawiamy x na lewe dziecko elementu
            }
            else
            {
                x = y.getRight(); // ustawiamy x na prawe dziecko elementu
            }
            if (x!=null)
            {
                x.setParent(y.getParent()); // przestawiamy rodzica (na rodzica usuniętego elementu)
            }
            if (y.getParent() == null)
            {
                this.root = x; // jeżeli usuwamy korzeń - aktualizujemy go
            }
            else
            {
                if (y.getValue().equals(y.getParent().getLeft().getValue()))
                {
                    y.getParent().setLeft(x); // x musi też być lewym dzieckiem
                }
                else
                {
                    y.getParent().setRight(x); // x musi też być prawym dzieckiem
                }
            }
            if (!y.getValue().equals(z.getValue()))
            {
                swap(z, y); // zamiana usuniętego elementu z jego następnikiem (jeśli usunięty element nie był liściem)
            }
        }
        if (y.getColor().equals("B")) // jeżeli usunięto węzeł czarny - naprawiamy drzewo (od rodzica)
            repairDelete(x);
    }
}
