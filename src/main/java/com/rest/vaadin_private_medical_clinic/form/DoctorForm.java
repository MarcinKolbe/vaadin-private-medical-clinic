package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.domain.Doctor;
import com.rest.vaadin_private_medical_clinic.service.DoctorService;
import com.rest.vaadin_private_medical_clinic.view.DoctorView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class DoctorForm extends FormLayout {

    private DoctorService doctorService;
    private DoctorView doctorView;
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");
    private TextField firstname = new TextField("Firstname");
    private TextField lastname = new TextField("Lastname");
    private TextField specialization = new TextField("Specialization");
    private Binder<Doctor> binder = new Binder<>(Doctor.class);

    public DoctorForm(DoctorView doctorView, DoctorService doctorService) {
        this.doctorService = doctorService;
        HorizontalLayout buttons = new HorizontalLayout(updateButton, deleteButton);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setSpacing(true);
        add(firstname, lastname, specialization, buttons);
        binder.bindInstanceFields(this);
        this.doctorView = doctorView;
        updateButton.addClickListener(e -> update());
        deleteButton.addClickListener(e -> delete());
    }

    public void update() {
        Doctor doctor = binder.getBean();
        doctorService.updateDoctor(doctor);
        doctorView.refresh();
        setDoctor(null);
    }

    public void delete() {
        Doctor doctor = binder.getBean();
        doctorService.deleteDoctor(doctor.getId());
        doctorView.refresh();
        setDoctor(null);
    }

    public void setDoctor(Doctor doctor) {
        binder.setBean(doctor);

        if (doctor == null) {
            setVisible(false);
        } else {
            setVisible(true);
            lastname.focus();
        }
    }
}
