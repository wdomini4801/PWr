package Lista6;

public class Klient {

    private String nazwa;
    private ListQueue<Zamowienie> zamowienia;

    public Klient(String nazwa)
    {
        this.nazwa = nazwa;
        this.zamowienia = new ListQueue<>();
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public ListQueue<Zamowienie> getZamowienia() {
        return zamowienia;
    }

}
