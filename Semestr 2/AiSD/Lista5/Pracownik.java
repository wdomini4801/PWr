package Lista5;

import java.io.Serializable;

public class Pracownik implements Serializable {

    private long PESEL;
    private String nazwisko;
    private String imie;
    private String data_ur;
    private String stanowisko;
    private double pensja;
    private int staz;
    private double premia;

    public Pracownik (long PESEL, String nazwisko, String imie, String data_ur, String stanowisko, double pensja, int staz)
    {
        this.PESEL=PESEL;
        this.nazwisko=nazwisko;
        this.imie=imie;
        this.data_ur=data_ur;
        this.stanowisko=stanowisko;
        this.pensja=pensja;
        this.staz=staz;
        obliczPremie();
    }

    private void obliczPremie()
    {
        if (this.staz>=20)
        {
            this.premia = this.pensja*0.2;
        }
        else
        {
            if (this.staz<20 && this.staz>=10)
            {
                this.premia = this.pensja*0.1;
            }
            else
            {
                this.premia = 0;
            }
        }
    }

    public long getPESEL() {
        return PESEL;
    }

    public void setPESEL(long PESEL) {
        this.PESEL = PESEL;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getData_ur() {
        return data_ur;
    }

    public void setData_ur(String data_ur) {
        this.data_ur = data_ur;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    public double getPensja() {
        return pensja;
    }

    public void setPensja(double pensja)
    {
        this.pensja = pensja;
        obliczPremie();
    }

    public int getStaz() {
        return staz;
    }

    public void setStaz(int staz)
    {
        this.staz = staz;
        obliczPremie();
    }

    public double getPremia() {
        return premia;
    }

    public void setPremia(double premia) {
        this.premia = premia;
    }

    public void wyswietl ()
    {
        System.out.print("|");
        System.out.printf("%-11d", getPESEL());
        System.out.print("\t" + "|");
        System.out.printf("%-36s", getNazwisko() + " " + getImie());
        System.out.print("|");
        System.out.printf("%-10s", getData_ur());
        System.out.print("\t" + "|");
        System.out.printf("%-15s", getStanowisko());
        System.out.print("|");
        System.out.printf("%6.2f", getPensja());
        System.out.print("\t" + "|");
        System.out.printf("%2d", getStaz());
        System.out.print("\t"  + "\t" + "|");
        System.out.printf("%6.2f", getPremia());
        System.out.print("\t"  + "\t" + "|");
        System.out.println();
    }

    public String toString ()
    {
        return (String.format("%-11d", getPESEL()) + "\t" + String.format("%-20s", getNazwisko()) + String.format("%-15s", getImie())
        + String.format("%-10s", getData_ur()) + String.format("%-15s", getStanowisko()) + String.format("%6.2f", getPensja())
            + String.format("%2d", getStaz()) + String.format("%6.2f", getPremia()));
    }


}
