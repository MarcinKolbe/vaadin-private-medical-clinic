package com.rest.vaadin_private_medical_clinic.view;

import com.rest.vaadin_private_medical_clinic.domain.Doctor;
import com.rest.vaadin_private_medical_clinic.form.DoctorForm;
import com.rest.vaadin_private_medical_clinic.form.DoctorRegistrationForm;
import com.rest.vaadin_private_medical_clinic.service.DoctorService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("doctors")
public class DoctorView extends VerticalLayout {

    private DoctorService service;
    private Grid<Doctor> grid = new Grid<>(Doctor.class);
    private Button back = new Button("← Back");
    private TextField filter = new TextField();
    private Span header = new Span("Doctors");
    private DoctorForm form;
    private Button registerDoctor = new Button("Register Doctor");
    private DoctorRegistrationForm registrationForm;

    public DoctorView(DoctorService service) {
        this.service = service;
        this.form = new DoctorForm(this, this.service);
        this.registrationForm = new DoctorRegistrationForm(this, this.service);
        form.setDoctor(null);
        registrationForm.setVisible(false);
        filter.setPlaceholder("Filter by lastname...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> update());
        grid.setColumns("id", "firstname", "lastname", "specialization", "rating");
        grid.setItems(service.getAllDoctors());
        back.addClickListener (e -> getUI().ifPresent(ui -> ui.navigate("")));
        registerDoctor.addClickListener(e -> showRegistrationForm());
        header.getStyle().set("text-align", "center");
        header.getStyle().set("font-size", "30px");
        header.getStyle().set("font-weight", "bold");
        header.setWidthFull();
        HorizontalLayout toolbar = new HorizontalLayout(back, filter, registerDoctor);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form, registrationForm);
        mainContent.setSizeFull();
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(
                event -> form.setDoctor(grid.asSingleSelect().getValue()));
        add(header, toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void update() {
        grid.setItems(service.getDoctorsByLastname(filter.getValue()));
    }

    public void refresh() {
        grid.setItems(service.getAllDoctors());
    }

    public void showRegistrationForm() {
        form.setVisible(false);
        grid.setVisible(false);
        registrationForm.setVisible(true);
    }

    public void showGrid() {
        registrationForm.setVisible(false);
        form.setVisible(false);
        grid.setVisible(true);
        refresh();
    }
}
