x0public class Repulok {
    private String Tipus;
    private Double Hossz;
    private Integer Suly;
    private Integer Ferohelyek;
    private Integer Uzemanyagtank;

    public String getTipus() {
        return Tipus;
    }

    public Double getHossz() {
        return Hossz;
    }

    public Integer getSuly() {
        return Suly;
    }

    public Integer getFerohelyek() {
        return Ferohelyek;
    }

    public Integer getUzemanyagtank() {
        return Uzemanyagtank;
    }

    public Repulok(String sor){
        String tomb[] = sor.split(";");
        Tipus = tomb[0];
        Hossz = Double.parseDouble(tomb[1]);
        Suly = Integer.parseInt(tomb[2]);
        Ferohelyek = Integer.parseInt(tomb[3]);
        Uzemanyagtank = Integer.parseInt(tomb[4]);

    }

    @Override
    public String toString() {
        return  Tipus + " / " + Ferohelyek + "hely";
    }
}
