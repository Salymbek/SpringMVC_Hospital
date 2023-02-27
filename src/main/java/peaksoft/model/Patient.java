package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.model.enums.Gender;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;


@Entity
@Table(name = "patients")
@NoArgsConstructor
@Getter @Setter

public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "patient_id_gen")
    @SequenceGenerator(name = "patient_id_gen",sequenceName = "patient_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    @NotEmpty(message = "First name should not be empty!")
    @Size(min = 2, max = 20, message = "First name must contain between 2 and 20 characters")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty!")
    @Size(min = 2, max = 20, message = "Last name must contain between 2 and 20 characters")
    private String lastName;
    @Column(name = "phone_number")
    @NotEmpty(message = "Phone number should not be empty!")
    @Pattern(regexp = "\\+996\\d{9}")
    private String phoneNumber;
    @NotNull(message = "Choice gender!")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Please provide a valid email address!")
    @Column(unique = true, name = "email")
    private String email;
    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST})
    private Hospital hospital;
    @OneToMany(mappedBy = "patient",
            cascade = ALL, fetch = FetchType.EAGER)
    private List<Appointment>appointments;
    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }

}
