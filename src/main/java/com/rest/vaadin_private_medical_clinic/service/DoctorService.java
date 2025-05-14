package com.rest.vaadin_private_medical_clinic.service;

import com.rest.vaadin_private_medical_clinic.domain.Doctor;
import com.rest.vaadin_private_medical_clinic.domain.DoctorRegistration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DoctorService {
    private final   RestTemplate restTemplate;

    public DoctorService() {
        this.restTemplate = new RestTemplate();
    }

    public List<Doctor> getAllDoctors() {
        ResponseEntity<List<Doctor>> response = restTemplate.exchange(
                "http://localhost:8080/v1/doctors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public List<Doctor> getDoctorsByLastname(String lastname) {
        List<Doctor> doctors = getAllDoctors();
        String lowerCaseLastname = lastname.toLowerCase();
        return doctors.stream()
                .filter(doctor -> doctor.getLastname().toLowerCase().contains(lowerCaseLastname))
                .toList();
    }

    public void updateDoctor(Doctor doctor) {
        String url = "http://localhost:8080/v1/doctors";
        HttpEntity<Doctor> requestUpdate = new HttpEntity<>(doctor);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas aktualizacji doktora: " + e.getMessage(), e);
        }
    }

    public void deleteDoctor(Long id) {
        String url = "http://localhost:8080/v1/users/" + id;
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas usuwania doktora: " + e.getMessage(), e);
        }
    }

    public void registerDoctor(DoctorRegistration doctorRegistration) {
        String url = "http://localhost:8080/v1/doctors";
        HttpEntity<DoctorRegistration> request = new HttpEntity<>(doctorRegistration);
        try {
            restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas dodawania nowego doktora: " + e.getMessage(), e);
        }
    }
}
