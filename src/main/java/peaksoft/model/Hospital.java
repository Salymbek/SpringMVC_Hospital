package peaksoft.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;

@Entity
@Table(name = "hospitals")
@Setter
@Getter
@NoArgsConstructor
public class Hospital {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "hospital_id_gen")
    @SequenceGenerator(name = "hospital_id_gen", sequenceName = "hospital_id_seq",allocationSize = 1)
    private Long id;
    @Size(min = 2, max = 20, message = "Name must contain between 2 and 20 characters")
    @NotEmpty(message = "Name should not be empty!")
    private String name;
    @NotEmpty(message = "Address should not be should!")
    private String address;
    private String image;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Doctor> doctors;

    public void addDoctor(Doctor doctor){
        if (doctors == null){
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Patient> patients;

    public void addPatient(Patient patient){
        if (patients == null){
            patients = new ArrayList<>();
        }
        patients.add(patient);
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
    private List<Department> departments;

    public void addDepartment(Department department){
        if (departments == null){
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointments;

    public void addAppointment(Appointment appointment){
        if (appointments == null){
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }

}
