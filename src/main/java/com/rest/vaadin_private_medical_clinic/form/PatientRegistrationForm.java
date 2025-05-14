package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.domain.PatientRegistration;
import com.rest.vaadin_private_medical_clinic.service.PatientService;
import com.rest.vaadin_private_medical_clinic.view.PatientView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PatientRegistrationForm extends FormLayout {

    private final PatientService patientService;
    private final PatientView patientView;
    private Button registerButton = new Button("Register");
    private Button cancelButton = new Button("Cancel");
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private TextField mail = new TextField("Email");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField phone = new TextField("Phone");
    private TextField pesel = new TextField("PESEL");
    private DatePicker birthDate = new DatePicker("Birthdate");
    private Binder<PatientRegistration> binder = new Binder<>(PatientRegistration.class);

    public PatientRegistrationForm(PatientView patientView, PatientService patientService) {
        this.patientService = patientService;
        this.patientView = patientView;
        HorizontalLayout buttons = new HorizontalLayout(registerButton, cancelButton);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setSpacing(true);
        add(username, password, mail, firstname, lastname, phone, pesel, birthDate, buttons);
        binder.bindInstanceFields(this);
        registerButton.addClickListener(e -> register());
        cancelButton.addClickListener(e -> cancel());

    }

    public void register() {
        PatientRegistration newPatient = new PatientRegistration();
        binder.writeBeanIfValid(newPatient);
        try {
            patientService.registerPatient(newPatient);
            Notification.show("Patient registered");
            patientView.showGrid();
        } catch (Exception ex) {
            Notification.show("Registration failed: " + ex.getMessage());
        }
    }

    public void cancel() {
        patientView.showGrid();
    }

}
