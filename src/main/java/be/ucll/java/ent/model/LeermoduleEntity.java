package be.ucll.java.ent.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Leermodule")
@NamedQueries({
        @NamedQuery(name = "Leermodule.getAll", query = "SELECT e FROM LeermoduleEntity e ORDER BY e.id"),
        @NamedQuery(name = "Leermodule.countAll", query = "SELECT count(e) FROM LeermoduleEntity e")
})
public class LeermoduleEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lmseq")
    @SequenceGenerator(name = "lmseq", sequenceName = "leermodule_sequence", allocationSize = 1)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

    @Column(length = 8, nullable = false)
    private String code;

    @Column(length = 256)
    private String beschrijving;

    @Column(length = 16)
    private String schooljaar;

    // 1 "Leermodule" is gekoppeld aan 0, 1 of meerdere "Inschrijving"
    // De mappedBy refereert naar het private field 'leermodule' in de Java class InschrijvingEntity.java
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "leermodule", cascade = CascadeType.ALL)
    private List<InschrijvingEntity> inschrijvingen;

    /* ***** Constructors ***** */
    public LeermoduleEntity() {
        // Default constructor
    }

    // Constructor with all MANDATORY fields
    public LeermoduleEntity(long id, String code, String beschrijving, String schooljaar) {
        this.id = id;
        this.code = code;
        this.beschrijving = beschrijving;
        this.schooljaar = schooljaar;
    }

    /* ***** Getters en Setters ***** */

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
        LeermoduleEntity that = (LeermoduleEntity) o;
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
