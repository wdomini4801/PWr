import java.util.Random;

public class Zadanie {

    int udzial;
    int czas_wykonania;

    public Zadanie()
    {
        this.udzial = 0;
        this.czas_wykonania = 0;
    }

    public Zadanie(int N)
    {
        Random generator = new Random();
        int los = generator.nextInt(N-1) + 1;
        int los2 = generator.nextInt(80) + 300;
        this.udzial = los;
        this.czas_wykonania = los2;
    }

    public Zadanie(Zadanie z)
    {
        int ud = z.udzial;
        int cz = z.czas_wykonania;
        this.udzial = ud;
        this.czas_wykonania = cz;
    }

    public String toString()
    {
        return("Udział: " + udzial + "%" + "\t" + "Czas wykonania: " + czas_wykonania + " ms");
    }

    public void doTask()
    {
        this.czas_wykonania = this.czas_wykonania-1;
    }

    public boolean isDone()
    {
        return this.czas_wykonania<=0;
    }

    public void increase(int a)
    {
        this.czas_wykonania = this.czas_wykonania*(this.udzial/a);
        this.udzial = a;
    }
}
