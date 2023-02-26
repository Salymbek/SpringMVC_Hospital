package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "doctors")
@NoArgsConstructor
@Setter @Getter
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "doctor_id_gen")
    @SequenceGenerator(name = "doctor_id_gen",sequenceName = "doctor_id_seq",allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    @NotEmpty(message = "First name should not be empty!")
    @Size(min = 2, max = 20, message = "First name must contain between 2 and 20 characters!")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty!")
    @Size(min = 2, max = 33, message = "Last name must contain between 2 and 20 characters!")
    private String lastName;
    @NotEmpty(message = "Position should not be empty!")
    private String position;
    @NotEmpty(message = "Email should not be empty!")
    @Email(message = "Please provide a valid email address!")
    @Column(unique = true)
    private String email;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH}, fetch = EAGER)
    private Hospital hospital;
    @ManyToMany(cascade = {PERSIST,REFRESH,MERGE,DETACH}, fetch = EAGER)
    private List<Department>departments;
    public void addDepartment(Department department){
        if (departments == null){
            departments = new ArrayList<>();
        }
        departments.add(department);
    }
    @OneToMany(mappedBy = "doctor",cascade = ALL, fetch = EAGER)
    private List<Appointment>appointments;
    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }
    @Transient
    private Long departmentId;
}
