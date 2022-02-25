package Lista6;

import java.text.DecimalFormat;

public class Magazyn {

    private ListQueue<Klient> kolejka;
    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private double s_kwot;

    public Magazyn()
    {
        this.kolejka = new ListQueue<>();
        this.s_kwot = 0;
    }

    public ListQueue<Klient> getKolejka() {
        return kolejka;
    }

    public double getS_kwot() {
        return s_kwot;
    }

    public void obsluga() throws EmptyQueueException
    {
        if (this.kolejka.isEmpty())
            System.out.println("Brak zamówień w magazynie!");
        else
        {
            double suma = 0;
            System.out.println("Obsługa magazynu");
            while (!this.kolejka.isEmpty())
            {
                Klient obslugiwany = this.kolejka.dequeue();
                double kwota = 0;
                while (!obslugiwany.getZamowienia().isEmpty())
                {
                    Zamowienie aktualne = obslugiwany.getZamowienia().dequeue();
                    kwota+=(double)(aktualne.getCena_jednostkowa()*aktualne.getL_sztuk());
                }
                System.out.println("Zlecenie zrealizowane: " + obslugiwany.getNazwa() + "\t" + "Kwota do zapłaty: " + df2.format(kwota) + " zł");
                this.s_kwot += kwota;
                suma += kwota;
            }
            System.out.println("Wszystkie zlecenia zrealizowane!" + "\t" + "Suma kwot za zamówienia: " + df2.format(suma) + " zł" + "\n");
        }
    }
}
