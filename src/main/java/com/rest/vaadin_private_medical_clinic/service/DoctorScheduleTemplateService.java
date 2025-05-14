package com.rest.vaadin_private_medical_clinic.service;

import com.rest.vaadin_private_medical_clinic.domain.DoctorScheduleTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DoctorScheduleTemplateService {
    private RestTemplate restTemplate;

    public DoctorScheduleTemplateService() {
        restTemplate = new RestTemplate();
    }

    public List<DoctorScheduleTemplate> getALLTemplates() {
        ResponseEntity<List<DoctorScheduleTemplate>> response = restTemplate.exchange(
                "http://localhost:8080/v1/schedule-template",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public List<DoctorScheduleTemplate> getTemplatesByDoctorId(long doctorId) {
        String url = "http://localhost:8080/v1/schedule-template/doctor/" + doctorId;
        ResponseEntity<List<DoctorScheduleTemplate>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public void updateTemplate(long templateId, DoctorScheduleTemplate template) {
        String url = "http://localhost:8080/v1/schedule-template/" + templateId;
        HttpEntity<DoctorScheduleTemplate> requestUpdate = new HttpEntity<>(template);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas aktualizacji: " + e.getMessage(), e);
        }
    }

    public void deleteTemplate(Long templateId) {
        String url = "http://localhost:8080/v1/schedule-template/" + templateId;
        try {
            restTemplate.exchange(url, HttpMethod.DELETE, null, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas usuwania: " + e.getMessage(), e);
        }
    }

    public void createTemplate(long doctorId, DoctorScheduleTemplate template) {
        String url = "http://localhost:8080/v1/schedule-template/doctor/" + doctorId;
        HttpEntity<DoctorScheduleTemplate> request = new HttpEntity<>(template);
        try {
            restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas dodawania nowego: " + e.getMessage(), e);
        }
    }
}
