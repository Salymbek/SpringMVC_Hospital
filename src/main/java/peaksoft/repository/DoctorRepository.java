package peaksoft.repository;

import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository {
    List<Doctor> getAll(Long id);

    void save(Long id, Doctor doctor);

    Optional<Doctor> findById(Long doctorId);

    void update(Long doctorId, Doctor doctor);

    void delete(Long doctorId, Hospital hospital);

    List<Department> getDepartments(Long doctorId);

    void assignToDepartment(Doctor doctor);

    void deleteDepartment(Doctor doctor);

    List<Appointment> getAppointments(Long doctorId);
}
