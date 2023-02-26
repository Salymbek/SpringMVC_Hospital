package peaksoft.service;

import peaksoft.model.Hospital;

import java.util.List;

public interface HospitalService {
    List<Hospital> getAllHospitals();

    void save(Hospital hospital);

    void delete(Long id);

    Hospital getById(Long id);

    void update(Long id, Hospital hospital);

    List<Hospital> getAllHospitals(String keyWord);
}
