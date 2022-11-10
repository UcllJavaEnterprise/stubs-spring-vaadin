package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Student")
@NamedQueries({
        @NamedQuery(name = "Student.getAll", query = "SELECT e FROM StudentEntity e ORDER BY e.id"),
        @NamedQuery(name = "Student.countAll", query = "SELECT count(e) FROM StudentEntity e")
})
public class StudentEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sseq")
    @SequenceGenerator(name = "sseq", sequenceName = "student_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 128)
    private String naam;

    @Column(length = 128)
    private String voornaam;

    @Column
    private Date geboortedatum;

    // 1 "Student" is ingeschreven voor 0, 1 of meerdere "Inschrijving"
    // De mappedBy refereert naar het private field 'student' in de Java class InschrijvingEntity.java
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private List<InschrijvingEntity> inschrijvingen;

    /* ***** Constructors ***** */
    public StudentEntity() {
        // Default constructor
    }

    // Constructor with all MANDATORY fields
    public StudentEntity(long id, String naam, String voornaam, Date geboortedatum) {
        this.id = id;
        this.naam = naam;
        this.voornaam = voornaam;
        this.geboortedatum = geboortedatum;
    }

    /* ***** Getters en Setters ***** */

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

    public List<InschrijvingEntity> getInschrijvingen() {
        return inschrijvingen;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentEntity that = (StudentEntity) o;
        return id == that.id &&
                Objects.equals(naam, that.naam) &&
                Objects.equals(voornaam, that.voornaam) &&
                Objects.equals(geboortedatum, that.geboortedatum) &&
                Objects.equals(inschrijvingen, that.inschrijvingen);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, naam, voornaam, geboortedatum, inschrijvingen);
    }

    @Override
    public String toString() {
        return "StudentEntity{" +
                "id=" + id +
                ", naam='" + naam + '\'' +
                ", voornaam='" + voornaam + '\'' +
                ", geboortedatum=" + getGeboortedatumstr() +
                ", inschrijvingen=" + inschrijvingen +
                '}';
    }
}