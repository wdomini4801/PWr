package Lista6;

public class Main {

    public static void main (String [] args) throws EmptyQueueException
    {
        Firma fir = new Firma();
        Magazyn mag1 = new Magazyn();
        Magazyn mag2 = new Magazyn();
        Magazyn mag3 = new Magazyn();
        fir.getMagazyny().add(mag1);
        fir.getMagazyny().add(mag2);
        fir.getMagazyny().add(mag3);
        Klient k1 = new Klient("k1");
        Klient k2 = new Klient("k2");
        Klient k3 = new Klient("k3");
        Klient k4 = new Klient("k4");
        mag1.getKolejka().enqueue(k1);
        mag1.getKolejka().enqueue(k2);
        mag2.getKolejka().enqueue(k3);
        mag3.getKolejka().enqueue(k4);
        k1.getZamowienia().enqueue(new Zamowienie("mleko", 3, 3));
        k1.getZamowienia().enqueue(new Zamowienie("masło", 5, 5.53));
        k2.getZamowienia().enqueue(new Zamowienie("towar1", 5, 4.50));
        k2.getZamowienia().enqueue(new Zamowienie("towar2", 6, 6.60));
        k3.getZamowienia().enqueue(new Zamowienie("towar11", 7, 10.87));
        k3.getZamowienia().enqueue(new Zamowienie("twr", 10, 5.02));
        k4.getZamowienia().enqueue(new Zamowienie("twr1", 8, 0.88));
        k4.getZamowienia().enqueue(new Zamowienie("towar22", 11, 11.11));
        mag1.obsluga();
        mag2.obsluga();
        mag3.obsluga();
        fir.przychodyFirmy();
    }
}
