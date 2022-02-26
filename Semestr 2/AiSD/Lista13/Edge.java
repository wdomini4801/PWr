public class Edge implements Comparable<Edge>{

    private int vertex;
    private int dest;
    private int edge_length;

    public Edge(int vertex, int dest, int edge_length)
    {
        this.vertex = vertex;
        this.dest = dest;
        this.edge_length = edge_length;
    }

    public String toString()
    {
        return (this.vertex + " -> " + this.dest + ": " + this.edge_length);
    }

    public int getVertex() {
        return vertex;
    }

    public int getDest() {
        return dest;
    }

    public int getEdge_length() {
        return edge_length;
    }

    @Override
    public int compareTo(Edge o) {
        return this.edge_length-o.edge_length;
    }
}
