package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Department;
import peaksoft.model.Doctor;
import peaksoft.model.Hospital;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.DepartmentRepository;
import peaksoft.repository.DoctorRepository;
import peaksoft.service.DoctorService;

import java.util.List;

@Service
@Transactional
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;
    private final AppointmentRepository appointmentRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository, DepartmentRepository departmentRepository, AppointmentRepository appointmentRepository) {
        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<Doctor> getAll(Long id) {
        return doctorRepository.getAll(id);
    }

    @Override
    public void save(Long id, Doctor doctor) {
        doctorRepository.save(id, doctor);

    }

    @Override
    public Doctor findById(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(
                        ()-> new NotFoundException("Doctor by id " + doctorId + " not found"));
    }

    @Override
    public void update(Long doctorId, Doctor doctor) {
        doctorRepository.update(doctorId, doctor);
    }

    @Override
    public void delete(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        ()-> new NotFoundException("Doctor by id " + doctorId + " not found"));
        Hospital hospital = doctor.getHospital();
        List<Appointment> appointments = doctor.getAppointments();
        appointments.forEach(a-> a.getPatient().setAppointments(null));

        hospital.getAppointments().removeAll(appointments);
        for (int i = 0; i < appointments.size(); i++) {
            appointmentRepository.delete(appointments.get(i).getId());
        }
        doctorRepository.delete(doctorId, hospital);
    }

    @Override
    public List<Department> getDepartments(Long doctorId) {
        return doctorRepository.getDepartments(doctorId);
    }

    @Override
    public void assignToDepartment(Long doctorId, Doctor doctor) {
        Department department = departmentRepository
                .findById(doctor.getDepartmentId())
                .orElseThrow(()-> new NotFoundException("Department not found"));
        Doctor oldDoctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        ()-> new NotFoundException("Doctor by id " + doctorId + " not found"));
        department.addDoctor(oldDoctor);
        oldDoctor.addDepartment(department);

        doctorRepository.assignToDepartment(oldDoctor);
    }




    @Override
    public void deleteDepartment(Long doctorId, Long departmentId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        ()-> new NotFoundException("Doctor by id " + doctorId + " not found"));
        Department department = departmentRepository.findById(departmentId)
                .orElseThrow(()-> new NotFoundException("Department not found"));
        doctor.getDepartments().remove(department);
        department.getDoctors().remove(doctor);
        doctorRepository.deleteDepartment(doctor);
    }

    @Override
    public List<Appointment> getAppointments(Long doctorId) {
        return doctorRepository.getAppointments(doctorId);
    }

    @Override
    public List<Department> getCanBeAssignDepartments(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(
                        ()-> new NotFoundException("Doctor by id " + doctorId + " not found"));
        List<Department> allDep = departmentRepository.getAll(doctor.getHospital().getId());
        if (!doctor.getDepartments().isEmpty()) {
            allDep.removeAll(doctor.getDepartments());
        }
        return allDep;
    }

}
