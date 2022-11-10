package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.InschrijvingEntity;
import be.ucll.java.ent.model.InschrijvingId;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class InschrijvingDAO {

    @PersistenceContext
    private EntityManager em;

    public void create(InschrijvingEntity ie) {
        em.persist(ie);
    }

    public Optional<InschrijvingEntity> get(long student_id, long leermodule_id) {
        return Optional.ofNullable(em.find(InschrijvingEntity.class, new InschrijvingId(student_id, leermodule_id)));
    }

    public InschrijvingEntity read(long student_id, long leermodule_id) {
        return em.find(InschrijvingEntity.class, new InschrijvingId(student_id, leermodule_id));
    }

    public void update(InschrijvingEntity ie) {
        em.merge(ie);
    }

    public void delete(long student_id, long leermodule_id) {
        InschrijvingEntity ref = em.getReference(InschrijvingEntity.class, new InschrijvingId(student_id, leermodule_id));
        if (ref != null) {
            em.remove(ref);
        } else {
            // Already removed
        }
    }

    public List<InschrijvingEntity> getInschrijvingenVoorStudentId(long studentID) {
        try {
            List<InschrijvingEntity> inschrijvingen4student = new ArrayList<>();
            try {
                Query query = em.createQuery("select i from InschrijvingEntity i " +
                        "where i.id.student_id = " + studentID);
                inschrijvingen4student = query.getResultList();
            } catch (NoResultException e) {
                // ignore, no problem just returning empty list
            }
            return inschrijvingen4student;
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<String> getAllInschrijvingen() {
        try {
            List<String> inschrijvingenStr = new ArrayList<>();
            try {
                Query query = em.createQuery("select s.naam, s.voornaam, l.beschrijving, i.vrijgesteld " +
                        "from StudentEntity s, InschrijvingEntity i, LeermoduleEntity l " +
                        "where i.id.student_id = s.id " +
                        "and i.id.leermodule_id = l.id");
                List<Object[]> inschrijvingen = query.getResultList();
                if (inschrijvingen != null) {
                    for (Object[] o : inschrijvingen) {
                        if (o != null) {
                            String tmp = "";
                            for (Object value : o) {
                                if (value != null) {
                                    tmp += value.toString() + " ";
                                }
                            }
                            inschrijvingenStr.add(tmp.trim());
                        }
                    }
                }
            } catch (NoResultException e) {
                // ignore, no problem just returning empty list
            }
            return inschrijvingenStr;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
