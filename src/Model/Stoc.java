package Model;

public class Stoc {
    private int idStoc;
    private int idFlorarie;
    private int idFloare;
    private String culoare;
    private int stoc;

    public Stoc() {

    }

    public Stoc(int idStoc, int idFlorarie, int idFloare, String culoare, int stoc) {
        this.idStoc = idStoc;
        this.idFlorarie = idFlorarie;
        this.idFloare = idFloare;
        this.culoare = culoare;
        this.stoc = stoc;
    }

    public int getStoc() {
        return stoc;
    }

    public String getCuloare() {
        return culoare;
    }

    public int getIdFloare() {
        return idFloare;
    }

    public int getIdFlorarie() {
        return idFlorarie;
    }

    public int getIdStoc() {
        return idStoc;
    }

    public void setIdFlorarie(int idFlorarie) {
        this.idFlorarie = idFlorarie;
    }

    public void setIdFloare(int idFloare) {
        this.idFloare = idFloare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public void setStoc(int stoc) {
        this.stoc = stoc;
    }

    public void setIdStoc(int idStoc) {
        this.idStoc = idStoc;
    }

    @Override
    public String toString() {
        return "Stoc{ " +
                "idStoc=" + idStoc +
                " idFlorarie = " + idFlorarie +
                ", idFloare = " + idFloare +
                ", stoc = " + stoc +
                ", culoare = " + culoare + "}";
    }
}
