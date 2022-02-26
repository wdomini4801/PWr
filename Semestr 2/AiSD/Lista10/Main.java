import java.io.*;
import java.util.*;

public class Main {

    public static ArrayList<Node> wystapienia(String s)
    {
        int [] wyst = new int[s.length()];
        char [] string = s.toCharArray();
        boolean [] pom = new boolean[string.length];

        ArrayList<Node> result = new ArrayList<>();

        for(int i = 0; i < s.length(); i++)
        {
            wyst[i] = 1;
            for(int j = i+1; j <s.length(); j++)
            {
                if(string[i] == string[j])
                {
                    wyst[i]++;
                    pom[j]=true;
                }
            }
        }

        for (int i=0; i<wyst.length; i++)
        {
            if (!pom[i])
            {
                result.add(new Node(Character.toString(string[i]), wyst[i]));
            }
        }

        return result;
    }

    public static BST drzewoHuffmana(String s) throws InvalidArgumentException
    {
        PriorityQueue<Node> kolejka = new PriorityQueue<>(new ComparatorFrequency());
        kolejka.addAll(wystapienia(s));
        while (kolejka.size()>1)
        {
            Node n1 = kolejka.poll();
            Node n2 = kolejka.poll();
            Node n = new Node(n1.getFrequency() + n2.getFrequency());
            n.setLeft(n1);
            n.setRight(n2);
            kolejka.add(n);
        }
        if (kolejka.peek()!=null)
        {
            return new BST(kolejka.poll());
        }
        else
            throw new InvalidArgumentException();
    }

    public static String odczytZPliku(String plik)
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader("src/" + plik + ".txt")))
        {
            String line;
            while((line = br.readLine())!=null)
            {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void zapisDoPliku(String plik, String napis)
    {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src/" + plik + ".txt")))
        {
            bw.write(napis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InvalidTreeException, InvalidArgumentException {

        Scanner scan = new Scanner(System.in);
        System.out.println("Podaj nazwę pliku z tekstem do zaszyfrowania");
        String nazwa = scan.nextLine();
        System.out.println("Podaj nazwę pliku, w którym ma zostać zapisany zaszyfrowany tekst");
        String nazwa2 = scan.nextLine();

        String tekst = odczytZPliku(nazwa);
        BST drz = drzewoHuffmana(tekst);
        drz.wyswietlKody();
        zapisDoPliku(nazwa2, drz.szyfr(tekst));
        System.out.println("Tekst odszyfrowany: " + drz.deszyfr(odczytZPliku(nazwa2)));
    }
}
