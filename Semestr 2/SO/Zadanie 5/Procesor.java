import java.util.ArrayList;

public class Procesor {

    int obciazenie;
    ArrayList<Zadanie> procesy;

    public Procesor()
    {
        this.obciazenie=0;
        this.procesy = new ArrayList<>();
    }

    public Procesor(Procesor p)
    {
        int obc = p.obciazenie;
        ArrayList<Zadanie> proc = new ArrayList<>();
        this.obciazenie = obc;
        this.procesy = proc;
    }
}
