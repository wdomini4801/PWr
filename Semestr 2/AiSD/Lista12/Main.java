public class Main {

    public static void main (String [] args)
    {
        Graph graf = new Graph(5);
        graf.addEdge(0, 1, 10);
        graf.addEdge(0, 3, 30);
        graf.addEdge(0, 4, 100);
        graf.addEdge(1, 2, 50);
        graf.addEdge(2, 4, 10);
        graf.addEdge(3, 2, 20);
        graf.addEdge(3, 4, 60);
        /*Graph graf = new Graph(6);
        graf.addEdge(0, 1, 2);
        graf.addEdge(0, 2, 4);
        graf.addEdge(1, 3, 7);
        graf.addEdge(1, 2, 1);
        graf.addEdge(2, 4, 3);
        graf.addEdge(3, 5, 1);
        graf.addEdge(4, 3, 2);
        graf.addEdge(4, 5, 5);*/
        graf.display();
        String miasto = "Wrocław";
        graf.DijkstraShortestPath(miasto);
        graf.DFS(miasto);
    }
}
