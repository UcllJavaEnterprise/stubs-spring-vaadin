package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "Inschrijving")
public class InschrijvingEntity implements Serializable {

    @EmbeddedId
    private InschrijvingId id;

    @Column(name = "vrijgesteld")
    private boolean vrijgesteld;

    // Meerdere "Inschrijving" zijn mogelijk voor 1 "Student"
    // Foreign Key
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("student_id")
    private StudentEntity student;

    // Meerdere "Inschrijving" zijn mogelijk voor 1 "Leermodule".
    // Foreign Key
    @ManyToOne(fetch = FetchType.EAGER)
    @MapsId("leermodule_id")
    private LeermoduleEntity leermodule;

    /* ***** Constructors ***** */
    public InschrijvingEntity() {
        // Default constructor
    }

    public InschrijvingEntity(StudentEntity student, LeermoduleEntity leermodule, boolean vrijgesteld) {
        this.student = student;
        this.leermodule = leermodule;
        this.vrijgesteld = vrijgesteld;

        this.id = new InschrijvingId(student.getId(), leermodule.getId());
    }

    /* ***** Getters en Setters ***** */

    public StudentEntity getStudent() {
        return student;
    }

    public void setStudent(StudentEntity student) {
        this.student = student;
    }

    public LeermoduleEntity getLeermodule() {
        return leermodule;
    }

    public void setLeermodule(LeermoduleEntity leermodule) {
        this.leermodule = leermodule;
    }

    public boolean isVrijgesteld() {
        return vrijgesteld;
    }

    public void setVrijgesteld(boolean vrijgesteld) {
        this.vrijgesteld = vrijgesteld;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InschrijvingEntity that = (InschrijvingEntity) o;
        return vrijgesteld == that.vrijgesteld &&
                Objects.equals(student, that.student) &&
                Objects.equals(leermodule, that.leermodule);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, student, leermodule, vrijgesteld);
    }
}
