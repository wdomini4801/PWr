package Lista1;

import java.io.Serializable;

public abstract class Pracownik implements Serializable {
	
	private static final long serialVersionUID = -803479036665775865L;
	
	//Sk³adowe
	private String nazwisko;
	private String imie;
	private long pesel;
	private String stanowisko;
	private int staz;
	
	//Konstruktor domyœlny
	public Pracownik ()
	{
		this.nazwisko = "";
		this.imie = "";
		this.pesel = 00000000000L;
		this.stanowisko = "";
		this.staz = 0;
	}
	
	//Konstruktor inicjuj¹cy
	public Pracownik (String nazwisko, String imie, long pesel, String stanowisko, int staz)
	{
		this.nazwisko = nazwisko;
		this.imie = imie;
		this.pesel = pesel;
		this.stanowisko = stanowisko;
		this.staz = staz;
	}
	
	//Getery
	public String getNazwisko ()
	{
		return nazwisko;
	}
	
	public String getImie ()
	{
		return imie;
	}

	public long getPesel ()
	{
		return pesel;
	}
	
	public String getStanowisko ()
	{
		return stanowisko;
	}
	
	public int getStaz ()
	{
		return staz;
	}
	
	//Setery
	public void setNazwisko (String nazwisko) 
	{
		this.nazwisko = nazwisko;
	}
	
	public void setImie (String imie)
	{
		this.imie = imie;
	}
	
	public void setPesel (long pesel)
	{
		this.pesel = pesel;
	}
	
	public void setStanowisko (String stanowisko)
	{
		this.stanowisko = stanowisko;
	}
	
	public void setStaz (int staz)
	{
		this.staz = staz;
	}
	
	//Metoda "wyœwietl"
	public void wyswietl ()
	{
		System.out.print("|");
		System.out.printf("%-20s", getNazwisko());
		System.out.print("|");
		System.out.printf("%-15s", getImie());
		System.out.print("|");
		System.out.printf("%-11d", getPesel());
		System.out.print("\t" + "|");
		System.out.printf("%-15s", getStanowisko());
		System.out.print("|");
		System.out.printf("%2d", getStaz());
		System.out.print("\t" + "|");
		System.out.printf("%6.2f", pensja());
		System.out.print("\t" + "|");
		System.out.println();
	}
	
	//Metoda "toString"
	public String toString ()
	{
		return  (String.format("%-20s", getNazwisko()) + String.format("%-15s", getImie()) + 
				String.format("%-11d", getPesel()) + "\t" + String.format("%-15s", getStanowisko()) + 
				"\t" + String.format("%2d", getStaz()) + "\t" + String.format("%6.2f", pensja()));
	}
	
	//Abstrakcyjna metoda "pensja"
	public abstract double pensja ();
}

