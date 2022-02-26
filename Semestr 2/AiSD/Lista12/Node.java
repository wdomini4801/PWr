public class Node {

    int vertex;
    int edge_length;

    public Node (int vertex, int edge_length)
    {
        this.vertex = vertex;
        this.edge_length = edge_length;
    }

    private String miasto (int a)
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

    public String toString ()
    {
        return (miasto(this.vertex) + " (" + this.edge_length + ")");
    }

    public int getVertex() {
        return vertex;
    }

    public int getEdge_length() {
        return edge_length;
    }

}
