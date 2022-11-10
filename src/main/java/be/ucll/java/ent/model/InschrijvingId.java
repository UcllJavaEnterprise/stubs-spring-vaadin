package be.ucll.java.ent.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class InschrijvingId implements java.io.Serializable {

    @Column
    private long student_id;

    @Column
    private long leermodule_id;

    /* ***** Constructors ***** */
    public InschrijvingId() {
        // Default constructor
    }

    public InschrijvingId(long student_id, long leermodule_id) {
        this.student_id = student_id;
        this.leermodule_id = leermodule_id;
    }

    /* ***** Getters en Setters ***** */

    public long getStudent_id() {
        return student_id;
    }

    public void setStudent_id(long student_id) {
        this.student_id = student_id;
    }

    public long getLeermodule_id() {
        return leermodule_id;
    }

    public void setLeermodule_id(long leermodule_id) {
        this.leermodule_id = leermodule_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InschrijvingId that = (InschrijvingId) o;
        return student_id == that.student_id &&
                leermodule_id == that.leermodule_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(student_id, leermodule_id);
    }
}

