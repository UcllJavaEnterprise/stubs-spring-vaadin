package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

// Data Transfer Object for Student information between presentation and business logic layer
public class StudentDTO implements Serializable {

    private long id;
    private String naam;
    private String voornaam;
    private Date geboortedatum;

    // Constructors
    public StudentDTO() {
        // Default constructor
    }

    public StudentDTO(long id) {
        this.id = id;
    }

    public StudentDTO(long id, String naam) {
        this.id = id;
        this.naam = naam;
    }

    public StudentDTO(long id, String naam, String voornaam, Date geboortedatum) {
        this.id = id;
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboortedatum = geboortedatum;
    }

    // Getters and Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public Date getGeboortedatum() {
        return geboortedatum;
    }

    public void setGeboortedatum(Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    public String getGeboortedatumstr() {
        if (geboortedatum != null) {
            return new SimpleDateFormat("dd/MM/yyyy").format(geboortedatum);
        }
        return "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDTO that = (StudentDTO) o;
        return id == that.id &&
                Objects.equals(naam.trim().toLowerCase(), that.naam.trim().toLowerCase()) &&
                Objects.equals(voornaam.trim().toLowerCase(), that.voornaam.trim().toLowerCase()) &&
                Objects.equals(geboortedatum, that.geboortedatum);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam, voornaam, geboortedatum);
    }

    @Override
    public String toString() {
        return "StudentDTO{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", geboortedatum=" + getGeboortedatumstr() +
                '}';
    }
}