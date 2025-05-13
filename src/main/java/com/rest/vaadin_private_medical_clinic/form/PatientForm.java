package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.MainView;
import com.rest.vaadin_private_medical_clinic.domain.Patient;
import com.rest.vaadin_private_medical_clinic.service.PatientService;
import com.rest.vaadin_private_medical_clinic.view.PatientView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class PatientForm extends FormLayout {

    private PatientService patientService;
    private PatientView patientView;
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField phoneNumber = new TextField("Phone");
    private TextField pesel = new TextField("PESEL");
    private DatePicker birthDate = new DatePicker("Birthdate");
    private Binder<Patient> binder = new Binder<>(Patient.class);

    public PatientForm(PatientView patientView) {
        HorizontalLayout buttons = new HorizontalLayout(updateButton, deleteButton);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setSpacing(true);
        add(firstname, lastname, pesel, phoneNumber, birthDate, buttons);
        binder.bindInstanceFields(this);
        this.patientView = patientView;
        updateButton.addClickListener(e -> update());
        deleteButton.addClickListener(e -> delete());
    }

    public void update() {
        Patient patient = binder.getBean();
        patientService.updatePatient(patient);
        patientView.refresh();
        setPatient(null);
    }

    public void delete() {
        Patient patient = binder.getBean();
        patientService.deletePatient(patient.getId());
        patientView.refresh();
        setPatient(null);
    }

    public void setPatient(Patient patient) {
        binder.setBean(patient);

        if (patient == null) {
            setVisible(false);
        } else {
            setVisible(true);
            lastname.focus();
        }
    }
}
