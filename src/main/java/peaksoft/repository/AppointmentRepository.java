package peaksoft.repository;

import peaksoft.model.Appointment;

import java.util.List;
import java.util.Optional;

public interface AppointmentRepository {
    List<Appointment> getAll(Long hospitalId);

    void save(Appointment appointment);

    Optional<Appointment> findById(Long appointmentId);

    void update(Long appointmentId, Appointment appointment);

    void delete(Long appointmentId);

    void deleteByDoctor(List<Appointment> appointments);

    void merge(Appointment appointment);
}
