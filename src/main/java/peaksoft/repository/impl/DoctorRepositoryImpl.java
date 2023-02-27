package peaksoft.repository.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.DoctorRepository;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class DoctorRepositoryImpl implements DoctorRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Doctor> getAll(Long id) {
        return entityManager.createQuery("from Doctor d join d.hospital h where h.id = :id", Doctor.class)
                .setParameter("id", id)
                .getResultList();
    }

    @Override
    public void save(Long id, Doctor doctor) {
        Hospital hospital = entityManager.createQuery("from Hospital where id = :id", Hospital.class)
                .setParameter("id", id)
                .getSingleResult();
        hospital.addDoctor(doctor);
        doctor.setHospital(hospital);
        entityManager.merge(doctor);
    }

    @Override
    public Optional<Doctor> findById(Long doctorId) {
        Doctor doctor = entityManager.find(Doctor.class, doctorId);
        return Optional.ofNullable(doctor);
    }

    @Override
    public void update(Long doctorId, Doctor doctor) {
        entityManager.createQuery("update Doctor set firstName = :f, lastName = :l," +
                        "position  = :pos, email = :e where id = :id")
                .setParameter("f", doctor.getFirstName())
                .setParameter("l", doctor.getLastName())
                .setParameter("pos", doctor.getPosition())
                .setParameter("e", doctor.getEmail())
                .setParameter("id", doctorId)
                .executeUpdate();
    }

    @Override
    public void delete(Long doctorId, Hospital hospital) {
        entityManager.createQuery("delete from Doctor where id = :id ")
                .setParameter("id", doctorId)
                .executeUpdate();
    }

    @Override
    public List<Department> getDepartments(Long doctorId) {
        return entityManager
                .createQuery("from Department d join d.doctors doc where doc.id = :id",
                        Department.class)
                .setParameter("id", doctorId)
                .getResultList();
    }

    @Override
    public void assignToDepartment(Doctor doctor) {
        entityManager.merge(doctor);
    }

    @Override
    public void deleteDepartment(Doctor doctor) {
        entityManager.merge(doctor);
    }

    @Override
    public List<Appointment> getAppointments(Long doctorId) {
        return entityManager
                .createQuery("from Appointment a join a.doctor d where d.id = :id",
                        Appointment.class)
                .setParameter("id", doctorId)
                .getResultList();
    }
}
