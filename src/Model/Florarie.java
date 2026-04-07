package Model;

public class Florarie {
    private int idFlorarie;
    private String denumire;
    private String adresa;

    public Florarie() {}

    public Florarie(int idFlorarie, String denumire, String adresa) {
        this.idFlorarie = idFlorarie;
        this.denumire = denumire;
        this.adresa = adresa;
    }

    public int getIdFlorarie() {
        return idFlorarie;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setIdFlorarie(int idFlorarie) {
        this.idFlorarie = idFlorarie;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    @Override
    public String toString() {
        return "Florarie{ " +
                " idFlorarie = " + idFlorarie +
                ", denumire = " + denumire +
                ", adresa = " + adresa + "}";
    }
}
