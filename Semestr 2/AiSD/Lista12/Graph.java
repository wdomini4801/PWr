public class Graph {

    private OneWayLinkedListWithHead<Node> [] lists;

    public Graph(int N)
    {
        this.lists = new OneWayLinkedListWithHead[N];
        for (int i = 0; i < N; i++)
        {
            this.lists[i] = new OneWayLinkedListWithHead<>();
        }
    }

    public OneWayLinkedListWithHead<Node>[] getLists() {
        return lists;
    }

    public void addEdge(int a, int b, int l)
    {
        this.getLists()[a].add(new Node(b, l));
    }

    private String miasto(int a)
    {
        switch (a)
        {
            case 0:
            {
                return "Wrocław";
            }
            case 1:
            {
                return "Oława";
            }
            case 2:
            {
                return "Brzeg";
            }
            case 3:
            {
                return "Nysa";
            }
            case 4:
            {
                return "Opole";
            }
            default:
                return "";
        }
    }

    private int miasto_(String a)
    {
        switch (a)
        {
            case "Wrocław":
            {
                return 0;
            }
            case "Oława":
            {
                return 1;
            }
            case "Brzeg":
            {
                return 2;
            }
            case "Nysa":
            {
                return 3;
            }
            case "Opole":
            {
                return 4;
            }
            default:
                return -1;
        }
    }

    public void display()
    {
        System.out.println("Lista sąsiedztwa grafu (w nawiasie długości krawędzi):");

        for (int i = 0; i < this.getLists().length; i++)
        {
            System.out.print(miasto(i));

            if (this.getLists()[i].isEmpty())
                System.out.print(" ->");
            else
            {
                for (Node node : this.getLists()[i])
                    System.out.print(" -> " + node.toString());
            }
            System.out.println();
        }
    }

    private int minVertex(int [] distance, boolean [] visited)
    {
        int min = Integer.MAX_VALUE;
        int ind_min = -1;

        for (int i=0; i < distance.length; i++)
        {
            if (!visited[i] && distance[i] <= min)
            {
                min = distance[i];
                ind_min = i;
            }
        }
        return ind_min;
    }

    public void DijkstraShortestPath(String m)
    {
        int n = this.getLists().length;
        int [] distance = new int [n];
        boolean [] visited = new boolean [n];
        int a = miasto_(m);

        if (a != -1)
        {
            OneWayLinkedListWithHead<Node> list = this.getLists()[a];
            for (int i = 0; i < n; i++)
            {
                if (i == a)
                {
                    distance[i] = 0;
                    visited[i] = true;
                }
                else
                {
                    distance[i] = Integer.MAX_VALUE;
                    for (Node node : list)
                    {
                        if (node.getVertex() == i)
                        {
                            distance[i] = node.getEdge_length();
                            break;
                        }
                    }
                    visited[i] = false;
                }
            }

            for (int i = 0; i < n-1; i++)
            {
                int next = minVertex(distance, visited);
                visited[next] = true;
                OneWayLinkedListWithHead<Node> lista = this.getLists()[next];

                for (Node node : lista)
                {
                    if (distance[next] != Integer.MAX_VALUE && distance[next] + node.getEdge_length() < distance[node.getVertex()])
                        distance[node.getVertex()] = distance[next] + node.getEdge_length();
                }
            }
            System.out.println("Długości najkrótszych dróg: ");

            for (int i = 0; i < distance.length; i++)
            {
                if (distance[i] != Integer.MAX_VALUE)
                {
                    System.out.print(miasto(i) + " - ");
                    if (distance[i] == 0)
                        System.out.print(distance[i] + " (miasto wyjściowe)");
                    else
                        System.out.print(distance[i]);
                    System.out.println();
                }
            }
        }
        else
        {
            System.out.println("Nie znaleziono miasta o nazwie " + m + "!");
        }
    }

    private void dfs(int a, boolean [] visited)
    {
        visited[a] = true;
        System.out.print(miasto(a) + " ");
        for (Node node : this.getLists()[a])
        {
            if (!visited[node.getVertex()])
                dfs(node.getVertex(), visited);
        }
    }

    public void DFS(String m)
    {
        if (miasto_(m) != -1)
        {
            boolean [] visited = new boolean [this.getLists().length];
            System.out.println("Graf w głąb:");
            dfs(miasto_(m), visited);
        }
        else
            System.out.println("Nie znaleziono miasta o nazwie " + m + "!");
    }

}
