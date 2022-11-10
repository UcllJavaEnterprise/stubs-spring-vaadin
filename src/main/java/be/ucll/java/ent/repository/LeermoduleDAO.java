package be.ucll.java.ent.repository;

import be.ucll.java.ent.model.LeermoduleEntity;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class LeermoduleDAO implements Dao<LeermoduleEntity> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(LeermoduleEntity leermodule) {
        em.persist(leermodule);
    }

    @Override
    public Optional<LeermoduleEntity> get(long leermoduleId) {
        return Optional.ofNullable(em.find(LeermoduleEntity.class, leermoduleId));
    }

    @Override
    public LeermoduleEntity read(long leermoduleId) {
        return em.find(LeermoduleEntity.class, leermoduleId);
    }

    public Optional<LeermoduleEntity> getOneByCode(String code) {
        try {
            LeermoduleEntity stud = null;
            try {
                Query q = em.createQuery("select e from LeermoduleEntity e where lower(e.code) like :p1");
                q.setParameter("p1", "%" + code.toLowerCase() + "%");
                stud = (LeermoduleEntity) q.getSingleResult();
            } catch (NoResultException e) {
                // ignore
            }
            return Optional.ofNullable(stud);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(LeermoduleEntity leermodule) {
        em.merge(leermodule);
    }

    @Override
    public void delete(long leermoduleId) {
        LeermoduleEntity ref = em.getReference(LeermoduleEntity.class, leermoduleId);
        if (ref != null) {
            em.remove(ref);
        } else {
            // Already gone
        }
    }

    public List<LeermoduleEntity> getLeermoduleByCode(String code) {
        try {
            List<LeermoduleEntity> lst = new ArrayList();
            try {
                // JPQL = Java Persistence Query Language
                String queryString = "select l from LeermoduleEntity l where lower(l.code) like '%" + code.toLowerCase().trim() + "%' ";
                Query query = em.createQuery(queryString);
                lst = query.getResultList();
            } catch (NoResultException e) {
                // ignore, is no problem
            }
            return lst;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<LeermoduleEntity> getAll() {
        return em.createNamedQuery("Leermodule.getAll").getResultList();
    }

    @Override
    public long countAll() {
        Object o = em.createNamedQuery("Leermodule.countAll").getSingleResult();
        return (Long) o;
    }
}
