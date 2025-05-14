package com.rest.vaadin_private_medical_clinic.service;

import com.rest.vaadin_private_medical_clinic.domain.Patient;
import com.rest.vaadin_private_medical_clinic.domain.PatientRegistration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
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
                "http://localhost:8080/v1/patients",
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

    public void updatePatient(Patient patient) {
        String url = "http://localhost:8080/v1/patients";
        HttpEntity<Patient> requestUpdate = new HttpEntity<>(patient);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas aktualizacji pacjenta: " + e.getMessage(), e);
        }
    }

    public void deletePatient(Long id) {
        String url = "http://localhost:8080/v1/users/" + id;
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas usuwania pacjenta: " + e.getMessage(), e);
        }
    }

    public void registerPatient(PatientRegistration patientRegistration) {
        String url = "http://localhost:8080/v1/patients";
        HttpEntity<PatientRegistration> request = new HttpEntity<>(patientRegistration);
        try {
            restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas dodawania nowego pacjenta: " + e.getMessage(), e);
        }
    }

}
