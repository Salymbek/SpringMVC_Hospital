package peaksoft.repository;

import peaksoft.model.Hospital;

import java.util.List;
import java.util.Optional;

public interface HospitalRepository {
    List<Hospital> getAllHospitals();

    void save(Hospital hospital);

    void delete(Long id);

    Optional<Hospital> getById(Long id);

    void update(Long id, Hospital hospital);

    List<Hospital> search(String keyWord);
}
