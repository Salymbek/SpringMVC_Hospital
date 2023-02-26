package peaksoft.service;

import org.springframework.stereotype.Service;
import peaksoft.model.Appointment;
import peaksoft.model.Patient;

import java.util.List;

public interface PatientService {
    List<Patient> getAll(Long patientId);
    void save(Long hospitalId, Patient patient);

    Patient getById(Long patientId);

    void update(Long patientId, Patient patient);

    void delete(Long patientId);

    List<Appointment> getAppointments(Long id, Long patientId);
}
