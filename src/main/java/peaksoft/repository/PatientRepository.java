package peaksoft.repository;

import peaksoft.model.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientRepository {
    List<Patient> getAll(Long hospitalId);

    void save(Long hospitalId, Patient patient);

    Optional<Patient> getById(Long patientId);

    void update(Long patientId, Patient patient);

    void delete(Long patientId);
}
