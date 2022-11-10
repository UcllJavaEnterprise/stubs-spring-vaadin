package be.ucll.java.ent.domain;

import java.io.Serializable;
import java.util.Objects;

public class LeermoduleDTO implements Serializable {

    private long id;
    private String code;
    private String beschrijving;
    private String schooljaar;

    // Constructors
    public LeermoduleDTO() {
        // Default constructor
    }

    public LeermoduleDTO(long id) {
        this.id = id;
    }

    public LeermoduleDTO(long id, String code, String beschrijving, String schooljaar) {
        this.id = id;
        this.code = code;
        this.beschrijving = beschrijving;
        this.schooljaar = schooljaar;
    }

    // Getters en Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public String getSchooljaar() {
        return schooljaar;
    }

    public void setSchooljaar(String schooljaar) {
        this.schooljaar = schooljaar;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LeermoduleDTO that = (LeermoduleDTO) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return code + " - " + beschrijving + " (" + schooljaar + ")";
    }
}
