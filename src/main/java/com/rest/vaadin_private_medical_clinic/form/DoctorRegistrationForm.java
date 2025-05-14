package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.domain.DoctorRegistration;
import com.rest.vaadin_private_medical_clinic.service.DoctorService;
import com.rest.vaadin_private_medical_clinic.view.DoctorView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class DoctorRegistrationForm extends FormLayout {

    private DoctorService doctorService;
    private DoctorView doctorView;
    private Button registerButton = new Button("Register");
    private Button cancelButton = new Button("Cancel");
    private TextField username = new TextField("Username");
    private PasswordField password = new PasswordField("Password");
    private TextField mail = new TextField("Email");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField specialization = new TextField("Specialization");
    private Binder<DoctorRegistration> binder = new Binder<>(DoctorRegistration.class);

    public DoctorRegistrationForm(DoctorView doctorView, DoctorService doctorService) {
        this.doctorService = doctorService;
        this.doctorView = doctorView;
        HorizontalLayout buttons = new HorizontalLayout(registerButton, cancelButton);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setSpacing(true);
        add(username, password, mail, firstname, lastname, specialization, buttons);
        binder.bindInstanceFields(this);
        registerButton.addClickListener(e -> register());
        cancelButton.addClickListener(e -> cancel());

    }

    public void register() {
        DoctorRegistration newDoctor = new DoctorRegistration();
        binder.writeBeanIfValid(newDoctor);
        try {
            doctorService.registerDoctor(newDoctor);
            Notification.show("Doctor registered");
            doctorView.showGrid();
        } catch (Exception ex) {
            Notification.show("Registration failed: " + ex.getMessage());
        }
    }

    public void cancel() {
        doctorView.showGrid();
    }
}
