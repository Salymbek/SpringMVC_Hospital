package peaksoft.service;

import peaksoft.model.Department;
import peaksoft.model.Doctor;

import java.util.List;

public interface DepartmentService {
    List<Department> getAll(Long id);

    void update(Long departmentId, Department department);

    void save(Long hospitalId, Department department);

    void delete(Long departmentId);

    Department findById(Long departmentId);

    List<Doctor> getDoctors(Long id, Long departmentId);


}
