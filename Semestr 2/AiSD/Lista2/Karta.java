package Lista2;

public class Karta {
	
	private int wartosc;
	private int kolor;
	
	public Karta ()
	{
		this.wartosc = 1;
		this.kolor = 0;
	}
	
	public Karta (int wartosc, int kolor)
	{
		this.wartosc = wartosc;
		this.kolor = kolor;
	}
	
	public int getWartosc ()
	{
		return wartosc;
	}

	public int getKolor ()
	{
		return kolor;
	}
	
	public void setWartosc (int wartosc)
	{
		this.wartosc = wartosc;
	}
	
	public void setKolor (int kolor)
	{
		this.kolor = kolor;
	}
	
	public String toString ()
	{
		String w="", k="";
		int wartosc = this.getWartosc();
		int kolor = this.getKolor();
		
		switch (kolor)
		{
		case 0:
		{
			k = "kier";
			break;
		}
		case 1:
		{
			k = "karo";
			break;
		}
		case 2:
		{
			k = "trefl";
			break;
		}
		case 3:
		{
			k = "pik";
			break;
		}
		}
		
		switch (wartosc)
		{
		case 1:
		{
			w = "as";
			break;
		}
		case 2: case 3: case 4: case 5: case 6: case 7: case 8: case 9: case 10:
		{
			w = String.valueOf(getWartosc());
			break;
		}
		case 11:
		{
			w = "walet";
			break;
		}
		case 12:
		{
			w = "dama";
			break;
		}
		case 13:
		{
			w = "król";
			break;
		}
		}
		return (w + " " + k);
	}
	
	public boolean equals (Object obj)
	{
		return (this.getWartosc()==((Karta) obj).getWartosc() && this.getKolor()==((Karta) obj).getKolor());
	}
}
