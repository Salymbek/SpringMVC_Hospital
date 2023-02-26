package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.model.Hospital;
import peaksoft.repository.HospitalRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class HospitalRepositoryImpl implements HospitalRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Hospital> getAllHospitals() {
        return entityManager.createQuery("from Hospital ", Hospital.class).getResultList();
    }

    @Override
    public void save(Hospital hospital) {
        entityManager.persist(hospital);
    }

    @Override
    public void delete(Long id) {
        entityManager.remove(entityManager
                .createQuery("from Hospital where id = :id", Hospital.class)
                .setParameter("id", id).getSingleResult()
        );
    }

    @Override
    public Optional<Hospital> getById(Long id) {
        Hospital hospital = entityManager.find(Hospital.class, id);
        return Optional.ofNullable(hospital);
    }

    @Override
    public void update(Long id, Hospital hospital) {
        entityManager.createQuery("update Hospital set name = :n, address = :a, " +
                        "image = :i where id = :id")
                .setParameter("n", hospital.getName())
                .setParameter("a", hospital.getAddress())
                .setParameter("i", hospital.getImage())
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public List<Hospital> search(String keyWord) {
        return entityManager.createQuery("from Hospital where name ilike (:keyWord)", Hospital.class)
                .setParameter("keyWord", "%"+keyWord+"%")
                .getResultList();
    }
}
