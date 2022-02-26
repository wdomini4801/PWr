import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static boolean isGood (String a)
    {
        if (a.length()==1)
        {
            char a1 = a.charAt(0);
            return Character.isLetter(a1) || Character.isDigit(a1);
        }
        return false;
    }

    public static void main (String [] args) throws EmptyQueueException {
        RBT drzewo = new RBT();
        /*drzewo.Insert("parasol", 1);
        drzewo.Insert("noś", 1);
        drzewo.Insert("i", 1);
        drzewo.Insert("przy", 1);
        drzewo.Insert("pogodzie", 1);*/
        String plik = "przyklad";
        int cur_line=1;
        try (BufferedReader br = new BufferedReader(new FileReader("src/" + plik + ".txt")))
        {
            String line;
            while((line = br.readLine())!=null)
            {
                String [] splitted = line.split("[.,?! -  ]");
                for (String s : splitted)
                {
                    Node a = drzewo.find(s.toLowerCase(Locale.ROOT));
                    if ((s.length() == 1 && isGood(s) && a==null) || (s.length()>1 && a==null))
                        drzewo.Insert(s.toLowerCase(Locale.ROOT), cur_line);
                    else if (a!=null)
                        a.getLines().add(cur_line);
                }
                cur_line++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //drzewo.wyswietlWyrazy();
        //drzewo.przechodzenieWszerz();
        drzewo.delete("i");
        //drzewo.delete("dzisiaj");
        //drzewo.wyswietlWyrazy();
        drzewo.przechodzenieWszerz();
        //System.out.println(drzewo.root.getColor());
        //System.out.println(drzewo.root.getLeft().getColor());
        //System.out.println(drzewo.root.getRight().getColor());
        //System.out.println(drzewo.root.getRight().getLeft().getColor());
    }
}
