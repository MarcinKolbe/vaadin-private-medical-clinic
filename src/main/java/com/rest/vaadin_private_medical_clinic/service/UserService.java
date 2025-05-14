package com.rest.vaadin_private_medical_clinic.service;

import com.rest.vaadin_private_medical_clinic.domain.PasswordReset;
import com.rest.vaadin_private_medical_clinic.domain.User;
import com.rest.vaadin_private_medical_clinic.enums.UserRole;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserService {
    private final RestTemplate restTemplate;

    public UserService() {
        this.restTemplate = new RestTemplate();
    }

    public List<User> getAllUsers(UserRole userRole) {
        String url = "http://localhost:8080/v1/users";
        if (userRole != null) {
            url = "http://localhost:8080/v1/users?role=" + userRole.toString();
        }
        ResponseEntity<List<User>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

    public List<User> getUsersByUsername(String username) {
        List<User> users = getAllUsers(null);
        String lowerCaseLastname = username.toLowerCase();
        return users.stream()
                .filter(patient -> patient.getUsername().toLowerCase().contains(lowerCaseLastname))
                .toList();
    }

    public void updateUser(User user) {
        String url = "http://localhost:8080/v1/users";
        HttpEntity<User> requestUpdate = new HttpEntity<>(user);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, requestUpdate, Void.class);
        } catch (RestClientException e) {
            throw new RuntimeException("Błąd podczas aktualizacji użytkownika: " + e.getMessage(), e);
        }
    }

    public void resetPassword(Long userId, String newPassword) {
        String url = "http://localhost:8080/v1/users/" + userId + "/reset-password";
        PasswordReset resetRequest = new PasswordReset();
        resetRequest.setNewPassword(newPassword);
        HttpEntity<PasswordReset> request = new HttpEntity<>(resetRequest);
        restTemplate.exchange(url, HttpMethod.POST, request, Void.class);
    }

    public void blockUser(Long userId) {
        String url = "http://localhost:8080/v1/users/" + userId + "/block";
        restTemplate.put(url, null);
    }

    public void unblockUser(Long userId) {
        String url = "http://localhost:8080/v1/users/" + userId + "/unblock";
        restTemplate.put(url, null);
    }
}
