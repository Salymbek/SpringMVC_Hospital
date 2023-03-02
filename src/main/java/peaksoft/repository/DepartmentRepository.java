package peaksoft.repository;

import peaksoft.model.Department;
import peaksoft.model.Doctor;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    List<Department> getAll(Long id);

    void update(Long departmentId, Department department);

    void save(Long hospitalId, Department department);

    void delete(Long departmentId);

    Optional<Department> findById(Long departmentId);

    List<Doctor> getDoctors(Long departmentId);

    List<Department> findDepartmentByDoctor (Long hospitalId);

    List<Department> findAllByDoctor(Long doctorId);

}
