package peaksoft.service;

import peaksoft.model.Appointment;

import java.util.List;

public interface AppointmentService {
    List<Appointment> getAll(Long hospitalId);

    void save(Long hospitalId, Appointment appointment);

    Appointment findById(Long appointmentId);

    void update(Long appointmentId, Appointment appointment);

    void delete(Long id, Long appointmentId);
}
