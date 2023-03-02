package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.DepartmentRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Department> getAll(Long id) {
        return entityManager
                .createQuery("from Department d join d.hospital h where h.id = :id", Department.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void update(Long departmentId, Department department) {
        entityManager.createQuery("update Department set name = :n where id = :id")
                .setParameter("n", department.getName())
                .setParameter("id", departmentId)
                .executeUpdate();
    }

    @Override
    public void save(Long hospitalId, Department department) {
        Hospital hospital = entityManager
                .createQuery("from Hospital where id = :id", Hospital.class)
                .setParameter("id", hospitalId)
                .getSingleResult();
        hospital.addDepartment(department);
        department.setHospital(hospital);
        entityManager.merge(department);
    }

    @Override
    public void delete(Long departmentId) {
        entityManager.createQuery("delete from Department where id = :id")
                .setParameter("id", departmentId)
                .executeUpdate();
    }

    @Override
    public Optional<Department> findById(Long departmentId) {
        Department department = entityManager.find(Department.class, departmentId);
        return Optional.ofNullable(department);
    }

    @Override
    public List<Doctor> getDoctors(Long departmentId) {
        return entityManager
                .createQuery("from Doctor d join d.departments dep where dep.id = :id",
                        Doctor.class).setParameter("id", departmentId).getResultList();
    }

    @Override
    public List<Department> findDepartmentByDoctor(Long hospitalId) {
        return entityManager
                .createQuery("select d from Department d\n" +
                        "            join d.doctors do on d.id = do.id\n" +
                        "            join d.hospital h on do.id = h.id\n" +
                        "            where do.id = ?1", Department.class)
                .getResultList();
    }



    @Override
    public List<Department> findAllByDoctor(Long doctorId) {
        return entityManager
                .createQuery("select d from Department d join d.doctors do\n" +
                        "            where do.id = ?1", Department.class)
                .getResultList();
    }
}
