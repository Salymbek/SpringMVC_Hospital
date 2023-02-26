package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "appointments")
@NoArgsConstructor
@Setter @Getter
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "appointment_id_gen")
    @SequenceGenerator(name = "appointment_id_gen", sequenceName = "appointment_id_seq", allocationSize = 1)

    private Long id;
    @NotNull(message = "date must not be empty!!!")
    @Future(message = "date must be future time!!!")
    private LocalDate date;
    @ManyToOne(cascade = {PERSIST,DETACH,REFRESH,MERGE}, fetch = EAGER)
    private Patient patient;
    @ManyToOne(cascade = {PERSIST,DETACH,REFRESH,MERGE}, fetch = EAGER)
    private Doctor doctor;
    @ManyToOne(cascade = {PERSIST,DETACH,REFRESH,MERGE}, fetch = EAGER)
    private Department department;

    @Transient
    @NotNull(message = "select patient!")
    private Long patientId;

    @Transient
    @NotNull(message = "select doctor!")
    private Long doctorId;

    @Transient
    @NotNull(message = "select department!")
    private Long departmentId;



}
