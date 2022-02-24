package Lista1;

import java.io.Serializable;

public class PracownikGodzinowy extends Pracownik implements Serializable{

	private static final long serialVersionUID = -3276701144890074515L;
	
	//Sk³adowe
	private int liczba_godz;
	private double stawka;
	
	//Konstruktor domyœlny
	public PracownikGodzinowy ()
	{
		super();
		this.liczba_godz = 0;
		this.stawka = 18.5;
	}
	
	//Konstruktor inicjuj¹cy
	public PracownikGodzinowy (String nazwisko, String imie, long pesel, String stanowisko, int staz, int liczba_godz, double stawka)
	{
		super(nazwisko, imie, pesel, stanowisko, staz);
		this.liczba_godz = liczba_godz;
		this.stawka = stawka;
	}
	
	//Getery
	public int getLiczba_godz ()
	{
		return liczba_godz;
	}
	
	public double getStawka ()
	{
		return stawka;
	}
	
	//Setery
	public void setLiczbagodz (int liczba_godz)
	{
		this.liczba_godz = liczba_godz;
	}
	
	public void setStawka (double stawka)
	{
		this.stawka = stawka;
	}
	
	//Metoda "pensja"
	public double pensja ()
	{
		return this.getStawka()*this.getLiczba_godz();
	}
	
	//Metoda "toString"
	public String toString ()
	{
		return (super.toString() + "\t" + String.format("%3d", getLiczba_godz()) + "\t" + String.format("%6.2f", getStawka()));
	}

}
