import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main (String [] args)
    {
        /*Graph graf = new Graph(5, 8);
        graf.addEdge(1, 2, 50);
        graf.addEdge(1, 3, 70);
        graf.addEdge(1, 5, 100);
        graf.addEdge(2, 3, 100);
        graf.addEdge(2, 4, 70);
        graf.addEdge(3, 4, 60);
        graf.addEdge(3, 5, 100);
        graf.addEdge(4, 5, 160);
        graf.KruskalAlgorithm();*/

        /*Scanner scan = new Scanner(System.in);
        System.out.println("Podaj nazwę pliku z danymi");
        String nazwa = scan.nextLine();*/

        try (Scanner scan = new Scanner(System.in))
        {
            System.out.println("Podaj dane do analizy");
            String line = scan.nextLine();
            String [] splitted = line.split("[ ]");
            int n, m, i, j, k;
            n = Integer.parseInt(splitted[0]);
            m = Integer.parseInt(splitted[1]);
            if (n >= 1 && n <= 20 && m >= 0 && m <= 190)
            {
                Graph graf = new Graph(n, m);

                for (int s = 0; s < m; s++)
                {
                    line = scan.nextLine();
                    splitted = line.split("[ ]");
                    i = Integer.parseInt(splitted[0]);
                    j = Integer.parseInt(splitted[1]);
                    k = Integer.parseInt(splitted[2]);

                    if (i >= 1 && j <= n && k >= 1 && k <= 500)
                        graf.addEdge(i, j, k);
                    else
                    {
                        System.out.println("Nieprawidłowy zakres danych w pliku!");
                        return;
                    }
                }

                graf.KruskalAlgorithm();
            }
            else
                System.out.println("Nieprawidłowy zakres danych w 1 linijce!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
