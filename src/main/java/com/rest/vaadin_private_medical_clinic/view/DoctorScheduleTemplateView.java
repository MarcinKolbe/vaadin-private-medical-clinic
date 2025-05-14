package com.rest.vaadin_private_medical_clinic.view;

import com.rest.vaadin_private_medical_clinic.domain.Doctor;
import com.rest.vaadin_private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.vaadin_private_medical_clinic.form.DoctorScheduleTemplateForm;
import com.rest.vaadin_private_medical_clinic.form.PatientForm;
import com.rest.vaadin_private_medical_clinic.form.PatientRegistrationForm;
import com.rest.vaadin_private_medical_clinic.form.TemplateRegistrationForm;
import com.rest.vaadin_private_medical_clinic.service.DoctorScheduleTemplateService;
import com.rest.vaadin_private_medical_clinic.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("templates")
public class DoctorScheduleTemplateView extends VerticalLayout {

    private DoctorScheduleTemplateService service;
    private DoctorService doctorService;
    private Grid<DoctorScheduleTemplate> grid = new Grid<>(DoctorScheduleTemplate.class);
    private Button back = new Button("← Back");
    private Span header = new Span("Doctor Schedule Template");
    private final ComboBox<Doctor> doctorComboBox = new ComboBox<>();
    private DoctorScheduleTemplateForm templateForm;
    private Button registerTemplate = new Button("Register Template");
    private TemplateRegistrationForm registrationForm;

    public DoctorScheduleTemplateView(DoctorScheduleTemplateService service, DoctorService doctorService) {
        this.service = service;
        this.templateForm = new DoctorScheduleTemplateForm(this, this.service);
        this.doctorService = doctorService;
        this.registrationForm = new TemplateRegistrationForm(this.service, this.doctorService, this);
        templateForm.setTemplate(null);
        registrationForm.setVisible(false);
        grid.setColumns("id", "doctorId", "dayOfWeek", "startTime", "endTime");
        grid.setItems(service.getALLTemplates());
        back.addClickListener (e -> getUI().ifPresent(ui -> ui.navigate("")));
        registerTemplate.addClickListener (e -> showRegistrationForm());
        header.getStyle().set("text-align", "center");
        header.getStyle().set("font-size", "30px");
        header.getStyle().set("font-weight", "bold");
        header.setWidthFull();
        doctorComboBox.setClearButtonVisible(true);
        doctorComboBox.setPlaceholder("Choose doctor...");
        doctorComboBox.setItems(doctorService.getAllDoctors());
        doctorComboBox.setItemLabelGenerator(doctor -> doctor.getFirstname() + " " + doctor.getLastname());
        doctorComboBox.addValueChangeListener(event -> {
            Doctor selectedDoctor = event.getValue();
            if (selectedDoctor != null) {
                List<DoctorScheduleTemplate> templates = service.getTemplatesByDoctorId(selectedDoctor.getId());
                grid.setItems(templates);
            } else {
                refresh();
            }
        });
        HorizontalLayout toolbar = new HorizontalLayout(back, doctorComboBox, registerTemplate);
        HorizontalLayout mainContent = new HorizontalLayout(grid, templateForm, registrationForm);
        mainContent.setSizeFull();
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(
                event -> templateForm.setTemplate(grid.asSingleSelect().getValue()));
        add(header, toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void refresh() {
        grid.setItems(service.getALLTemplates());
    }

    public void showGrid() {
        registrationForm.setVisible(false);
        templateForm.setVisible(false);
        grid.setVisible(true);
        refresh();
    }

    public void showRegistrationForm() {
        templateForm.setVisible(false);
        grid.setVisible(false);
        registrationForm.setVisible(true);
    }
}
