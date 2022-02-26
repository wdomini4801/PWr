import java.util.ArrayList;
import java.util.Collections;

public class Graph {

    int vertices;
    int edges;
    ArrayList<Edge> list_edges;

    public Graph(int vertices, int edges)
    {
        this.vertices = vertices;
        this.edges = edges;
        this.list_edges = new ArrayList<>();
    }

    public void addEdge(int a, int b, int l)
    {
        if (this.list_edges.size() < this.edges)
            this.list_edges.add(new Edge(a, b, l));
        else
            System.out.println("Przekroczono liczbę krawędzi!");
    }

    static class set
    {
        int parent;
        int weight;

        public set (int parent, int weight)
        {
            this.parent = parent;
            this.weight = weight;
        }
    }

    private set [] makeSet(int vertices)
    {
        set [] tabl = new set[vertices];

        for (int i = 0; i < vertices; i++)
            tabl[i] = new set(i + 1, 0);

        return tabl;
    }

    private int findSet(set [] tabl, int i)
    {
        if (i != tabl[i-1].parent)
            tabl[i-1].parent = findSet(tabl, tabl[i-1].parent); // kompresja ścieżki
        return tabl[i-1].parent;
    }

    private void union(set [] tabl, int a, int b)
    {
        int a_repr = findSet(tabl, a);
        int b_repr = findSet(tabl, b);

        //łączenie według rangi
        if (tabl[a_repr-1].weight>tabl[b_repr-1].weight)
            tabl[b_repr-1].parent = a_repr;
        else if (tabl[a_repr-1].weight<tabl[b_repr-1].weight)
            tabl[a_repr-1].parent = b_repr;
        else
        {
            tabl[b_repr-1].parent = a_repr;
            tabl[a_repr-1].weight++;
        }
    }

    public void KruskalAlgorithm()
    {
        Edge [] wynik = new Edge[this.vertices];
        int e=0, i=0;
        Collections.sort(this.list_edges);
        set [] sets = makeSet(this.vertices);

        while (e < this.vertices-1)
        {
            Edge act = list_edges.get(i);
            i++;
            int a = findSet(sets, act.getVertex());
            int b = findSet(sets, act.getDest());
            if (a != b)
            {
                wynik[e] = act;
                e++;
                union(sets, a, b);
            }
        }

        int min_length = 0;

        for (int j = 0; j < e; j++)
        {
            //System.out.println(wynik[j].toString());
            min_length += wynik[j].getEdge_length();
        }
        System.out.println(min_length);
    }
}