package Lista6;

public class Zamowienie {

    private String nazwa_towaru;
    private int l_sztuk;
    private double cena_jednostkowa;

    public Zamowienie (String nazwa_towaru, int l_sztuk, double cena_jednostkowa)
    {
        this.nazwa_towaru = nazwa_towaru;
        this.l_sztuk = l_sztuk;
        this.cena_jednostkowa = cena_jednostkowa;
    }

    public String getNazwa_towaru() {
        return nazwa_towaru;
    }

    public void setNazwa_towaru(String nazwa_towaru) {
        this.nazwa_towaru = nazwa_towaru;
    }

    public int getL_sztuk() {
        return l_sztuk;
    }

    public void setL_sztuk(int l_sztuk) {
        this.l_sztuk = l_sztuk;
    }

    public double getCena_jednostkowa() {
        return cena_jednostkowa;
    }

    public void setCena_jednostkowa(double cena_jednostkowa) {
        this.cena_jednostkowa = cena_jednostkowa;
    }
}
