package Lista4;

public class Karta {

    private int wartosc;
    private int kolor;
    private boolean znacznik;

    public Karta(int wartosc, int kolor, boolean znacznik)
    {
        this.wartosc = wartosc;
        this.kolor = kolor;
        this.znacznik = znacznik;
    }

    public int getWartosc() {
        return wartosc;
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }

    public int getKolor() {
        return kolor;
    }

    public void setKolor(int kolor) {
        this.kolor = kolor;
    }

    public boolean getZnacznik() {
        return znacznik;
    }

    public void setZnacznik(boolean znacznik) {
        this.znacznik = znacznik;
    }

    public String toString()
    {
        if (!getZnacznik())
            return "()";
        else
        {
            String w = "", k = "";
            int wartosc = this.getWartosc();
            int kolor = this.getKolor();

            switch (kolor) {
                case 0 -> k = "kier";
                case 1 -> k = "karo";
                case 2 -> k = "trefl";
                case 3 -> k = "pik";
            }

            switch (wartosc) {
                case 1 -> w = "as";
                case 2, 3, 4, 5, 6, 7, 8, 9, 10 -> w = String.valueOf(getWartosc());
                case 11 -> w = "walet";
                case 12 -> w = "dama";
                case 13 -> w = "król";
            }

            return (w + " " + k);
        }
    }
}
