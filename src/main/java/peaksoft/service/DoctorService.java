package peaksoft.service;

import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;

import java.util.List;

public interface DoctorService {
    List<Doctor> getAll(Long id);

    void save(Long id, Doctor doctor);

    Doctor findById(Long doctorId);

    void update(Long doctorId, Doctor doctor);

    void delete(Long doctorId);

    List<Department> getDepartments(Long doctorId);

    void assignToDepartment(Long doctorId,Doctor doctor);

    void deleteDepartment(Long doctorId, Long departmentId);

    List<Appointment> getAppointments(Long doctorId);
    List<Department> getCanBeAssignDepartments(Long doctorId);

}
