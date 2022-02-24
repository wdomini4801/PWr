package Lista1;

import java.io.Serializable;

public class PracownikEtatowy extends Pracownik implements Serializable{
	
	private static final long serialVersionUID = -2609234830788538597L;
	//Sk³adowe
	private double etat;
	private double stawka;
	
	//Konstruktor domyœlny
	public PracownikEtatowy ()
	{
		super();
		this.etat = 0;
		this.stawka = 3000;
	}
	
	//Konstruktor inicjuj¹cy
	public PracownikEtatowy (String nazwisko, String imie, long pesel, String stanowisko, int staz, double etat, double stawka)
	{
		super(nazwisko, imie, pesel, stanowisko, staz);
		this.etat = etat;
		this.stawka = stawka;
	}
	
	//Getery
	public double getEtat ()
	{
		return etat;
	}
	
	public double getStawka ()
	{
		return stawka;
	}
	
	//Setery
	public void setEtat (double etat)
	{
		this.etat = etat;
	}
	
	public void setStawka (double stawka)
	{
		this.stawka = stawka;
	}
	
	//Metoda "pensja"
	public double pensja ()
	{
		return this.getStawka() * this.getEtat();
	}
	
	//Metoda "toString"
	public String toString ()
	{
		return (super.toString() + "\t" + String.format("%3.2f", getEtat()) + "\t" + String.format("%6.2f", getStawka()));
	}
	

}
