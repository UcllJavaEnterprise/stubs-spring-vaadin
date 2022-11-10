package be.ucll.java.ent.domain;

public class InschrijvingDTO {

    private String code;
    private String beschrijving;
    private boolean vrijgesteld;

    public InschrijvingDTO(String code, String beschrijving, boolean vrijgesteld) {
        this.code = code;
        this.beschrijving = beschrijving;
        this.vrijgesteld = vrijgesteld;
    }

    public String getCode() {
        return code;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public boolean isVrijgesteld() {
        return vrijgesteld;
    }

    @Override
    public String toString() {
        return code + " - " + beschrijving + (vrijgesteld ? " (VRIJSTELLING)": "") ;
    }
}
