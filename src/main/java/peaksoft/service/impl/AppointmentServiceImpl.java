package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import peaksoft.exceptions.NotFoundException;
import peaksoft.model.Appointment;
import peaksoft.model.Hospital;
import peaksoft.repository.*;
import peaksoft.service.AppointmentService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalRepository hospitalRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final DepartmentRepository departmentRepository;

    public AppointmentServiceImpl(AppointmentRepository appointmentRepository, HospitalRepository hospitalRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, DepartmentRepository departmentRepository) {
        this.appointmentRepository = appointmentRepository;
        this.hospitalRepository = hospitalRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Appointment> getAll(Long hospitalId) {
        List<Appointment> appointments = new ArrayList<>();
        int size = appointmentRepository.getAll(hospitalId).size();
        for (int i = 0; i < size; i++) {
            appointments.add(appointmentRepository.getAll(hospitalId).get(size - 1 - i));
        }
        return appointments;
    }

    @Override
    public void save(Long hospitalId, Appointment appointment) {
        Hospital hospital = hospitalRepository.getById(hospitalId)
                .orElseThrow(
                        ()-> new NotFoundException("Hospital by id " + hospitalId + " not found"));;
        appointment.setDoctor(doctorRepository
                .findById(appointment.getDoctorId()).orElseThrow(
                        ()-> new RuntimeException("Doctor not found")));
        appointment.setPatient(patientRepository
                .getById(appointment.getPatientId()).orElseThrow(
                        ()-> new NotFoundException("Patient not found")));
        appointment.setDepartment(departmentRepository
                .findById(appointment.getDepartmentId()).orElseThrow(
                        ()-> new NotFoundException("Department not found")
                ));
        hospital.addAppointment(appointment);
        appointment.getPatient().addAppointment(appointment);
        appointment.getDoctor().addAppointment(appointment);
        appointmentRepository.save(appointment);
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return appointmentRepository
                .findById(appointmentId).orElseThrow(
                        ()-> new NotFoundException("Appointment by id " + appointmentId +
                                " not found"));    }

    @Override
    public void update(Long appointmentId, Appointment appointment) {
        appointment.setDoctor(doctorRepository.findById(appointment.getDoctorId())
                .orElseThrow(()-> new NotFoundException("Doctor not found")));
        appointment.setPatient(patientRepository.getById(appointment.getPatientId())
                .orElseThrow(()-> new NotFoundException("Patient not found")));
        appointment.setDepartment(departmentRepository.findById(appointment.getDepartmentId())
                .orElseThrow(()-> new NotFoundException("Department not found")));
        appointmentRepository.update(appointmentId, appointment);
    }

    @Override
    public void delete(Long id, Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(
                        ()-> new NotFoundException("Appointment by id " + appointmentId +
                                " not found"));
        hospitalRepository.getById(id)
                .orElseThrow(
                        ()-> new NotFoundException("Hospital by id " + id + " not found"))
                .getAppointments().remove(appointment);
        appointment.getDoctor().setAppointments(null);
        appointment.getPatient().setAppointments(null);
        appointmentRepository.delete(appointmentId);
    }
}
