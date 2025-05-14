package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.vaadin_private_medical_clinic.service.DoctorScheduleTemplateService;
import com.rest.vaadin_private_medical_clinic.view.DoctorScheduleTemplateView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.binder.Binder;

import java.time.DayOfWeek;

public class DoctorScheduleTemplateForm extends FormLayout {

    private DoctorScheduleTemplateService templateService;
    private DoctorScheduleTemplateView templateView;
    private Button updateButton = new Button("Update");
    private Button deleteButton = new Button("Delete");
    private ComboBox<DayOfWeek> dayOfWeek = new ComboBox<>("Day of week");
    private TimePicker startTime = new TimePicker("Start Time");
    private TimePicker endTime = new TimePicker("End Time");
    private Binder<DoctorScheduleTemplate> binder = new Binder<>(DoctorScheduleTemplate.class);

    public DoctorScheduleTemplateForm(DoctorScheduleTemplateView templateView, DoctorScheduleTemplateService templateService) {
        this.templateService = templateService;
        HorizontalLayout buttons = new HorizontalLayout(updateButton, deleteButton);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        buttons.setSpacing(true);
        add(dayOfWeek, startTime, endTime, buttons);
        dayOfWeek.setItems(DayOfWeek.values());
        binder.bind(dayOfWeek, DoctorScheduleTemplate::getDayOfWeek, DoctorScheduleTemplate::setDayOfWeek);
        binder.forField(startTime)
                .bind(DoctorScheduleTemplate::getStartTime, DoctorScheduleTemplate::setStartTime);
        binder.forField(endTime)
                .bind(DoctorScheduleTemplate::getEndTime, DoctorScheduleTemplate::setEndTime);
        this.templateView = templateView;
        updateButton.addClickListener(e -> update());
        deleteButton.addClickListener(e -> delete());
    }

    public void update() {
        DoctorScheduleTemplate template = binder.getBean();
        templateService.updateTemplate(template.getId(), template);
        templateView.refresh();
        setTemplate(null);
    }

    public void delete() {
        DoctorScheduleTemplate template = binder.getBean();
        templateService.deleteTemplate(template.getId());
        templateView.refresh();
        setTemplate(null);
    }

    public void setTemplate(DoctorScheduleTemplate template) {
        binder.setBean(template);

        if (template == null) {
            setVisible(false);
        } else {
            setVisible(true);
            dayOfWeek.focus();
        }
    }
}
