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
import static jakarta.persistence.FetchType.*;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@Setter
@Getter
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "department_id_gen")
    @SequenceGenerator(name = "department_id_gen", sequenceName = "department_id_seq",allocationSize = 1)
    private Long id;
    @NotEmpty(message = "Department name should not be empty!")
    @Size(min = 2, max = 20, message = "Department name should be between 2 and 20 characters!")
    @Column( unique = true)
    private String name;
    @ManyToOne(cascade = {PERSIST,REFRESH,MERGE,DETACH})
    private Hospital hospital;
    @ManyToMany(cascade = {PERSIST,REFRESH,MERGE,DETACH},fetch = EAGER, mappedBy = "departments")
    private List<Doctor>doctors;

    public void addDoctor(Doctor doctor){
        if (doctors == null){
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }
}
