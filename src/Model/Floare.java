package Model;

public class Floare {
    private int idFloare;
    private String denumire;
    private String imagine;

    public Floare() {}

    public Floare(int idFloare, String denumire, String imagine) {
        this.idFloare = idFloare;
        this.denumire = denumire;
        this.imagine = imagine;
    }

    public int getIdFloare() {
        return idFloare;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getImagine() {
        return imagine;
    }

    public void setIdFloare(int idFloare) {
        this.idFloare = idFloare;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public void setImagine(String imagine) {
        this.imagine = imagine;
    }

    @Override
    public String toString() {
        return "Floare{ " +
                " idFloare = " + idFloare +
                ", denumire = " + denumire +
                ", imagine = " + imagine + "}";
    }
}
