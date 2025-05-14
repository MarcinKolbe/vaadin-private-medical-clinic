package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.domain.Doctor;
import com.rest.vaadin_private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.vaadin_private_medical_clinic.service.DoctorScheduleTemplateService;
import com.rest.vaadin_private_medical_clinic.service.DoctorService;
import com.rest.vaadin_private_medical_clinic.view.DoctorScheduleTemplateView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;

import java.time.DayOfWeek;

public class TemplateRegistrationForm extends FormLayout {

    private DoctorScheduleTemplateService templateService;
    private DoctorScheduleTemplateView templateView;
    private DoctorService doctorService;
    private Button registerButton = new Button("Register");
    private Button cancelButton = new Button("Cancel");
    private final ComboBox<Doctor> doctorComboBox = new ComboBox<>("Doctor");
    private ComboBox<DayOfWeek> dayOfWeek = new ComboBox<>("Day of week");
    private TimePicker startTime = new TimePicker("Start Time");
    private TimePicker endTime = new TimePicker("End Time");
    private Binder<DoctorScheduleTemplate> binder = new Binder<>(DoctorScheduleTemplate.class);

    public TemplateRegistrationForm(DoctorScheduleTemplateService templateService,
                                    DoctorService doctorService,
                                    DoctorScheduleTemplateView templateView) {
        this.templateService = templateService;
        this.doctorService = doctorService;
        this.templateView = templateView;
        doctorComboBox.setItems(doctorService.getAllDoctors());
        doctorComboBox.setItemLabelGenerator(doctor -> doctor.getFirstname() + " " + doctor.getLastname());
        HorizontalLayout buttons = new HorizontalLayout(registerButton, cancelButton);
        registerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setSpacing(true);
        add(doctorComboBox, dayOfWeek, startTime, endTime, buttons);
        dayOfWeek.setItems(DayOfWeek.values());
        binder.bind(dayOfWeek, DoctorScheduleTemplate::getDayOfWeek, DoctorScheduleTemplate::setDayOfWeek);
        binder.forField(startTime)
                .bind(DoctorScheduleTemplate::getStartTime, DoctorScheduleTemplate::setStartTime);
        binder.forField(endTime)
                .bind(DoctorScheduleTemplate::getEndTime, DoctorScheduleTemplate::setEndTime);
        registerButton.addClickListener(e -> register());
        cancelButton.addClickListener(e -> cancel());
    }

    public void register() {
        Doctor selectedDoctor = doctorComboBox.getValue();
        if (selectedDoctor == null) {
            Notification.show("Please select a doctor.");
            return;
        }

        DoctorScheduleTemplate newTemplate = new DoctorScheduleTemplate();
        try {
            binder.writeBean(newTemplate);
            newTemplate.setDoctorId(selectedDoctor.getId());
            templateService.createTemplate(newTemplate.getDoctorId(), newTemplate);
            Notification.show("Template registered");
            templateView.refresh();
            templateView.showGrid();
        } catch (Exception ex) {
            Notification.show("Registration failed: " + ex.getMessage());
        }
    }

    public void cancel() {
        templateView.showGrid();
    }
}
