package com.rest.vaadin_private_medical_clinic.service;

import com.rest.vaadin_private_medical_clinic.domain.Patient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class PatientService {
    private final RestTemplate restTemplate;

    public PatientService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Patient> getAllPatients() {
        ResponseEntity<List<Patient>> response = restTemplate.exchange(
                "http://localhost:8080/v1/patients", // zakładamy, że backend działa lokalnie
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public List<Patient> getPatientsByLastname(String lastname) {
        List<Patient> patients = getAllPatients();
        String lowerCaseLastname = lastname.toLowerCase();
        return patients.stream()
                .filter(patient -> patient.getLastname().toLowerCase().contains(lowerCaseLastname))
                .toList();
    }

    public void updatePatient(Patient patient) {}

    public void deletePatient(long patientId) {}

}
