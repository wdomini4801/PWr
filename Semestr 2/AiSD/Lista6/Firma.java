package Lista6;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Firma {

    public ArrayList<Magazyn> magazyny;
    private static final DecimalFormat df2 = new DecimalFormat("#.##");

    public Firma()
    {
        this.magazyny = new ArrayList<>();
    }

    public ArrayList<Magazyn> getMagazyny() {
        return magazyny;
    }

    public void przychodyFirmy()
    {
        double przychody = 0;

        for (int i=0; i<getMagazyny().size(); i++)
            przychody+=getMagazyny().get(i).getS_kwot();

        System.out.println("Przychody firmy: " + df2.format(przychody) + " zł");
    }
}
