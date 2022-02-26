import java.util.Comparator;

public class ComparatorZadanie implements Comparator<Zadanie> {

    @Override
    public int compare(Zadanie o1, Zadanie o2)
    {
        if (o1.czas_wykonania != o2.czas_wykonania)
            return o1.czas_wykonania - o2.czas_wykonania;
        else
            return o1.udzial - o2.udzial;
    }
}
