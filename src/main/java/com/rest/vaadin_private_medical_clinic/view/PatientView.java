package com.rest.vaadin_private_medical_clinic.view;

import com.rest.vaadin_private_medical_clinic.domain.Patient;
import com.rest.vaadin_private_medical_clinic.form.PatientForm;
import com.rest.vaadin_private_medical_clinic.service.PatientService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

@Route("patients")
public class PatientView extends VerticalLayout{

    private PatientService service;
    private Grid<Patient> grid = new Grid<>(Patient.class);
    private Button back = new Button("← Back");
    private TextField filter = new TextField();
    private Span header = new Span("Patients");
    private PatientForm form = new PatientForm(this);

    public PatientView(PatientService service) {
        form.setPatient(null);
        this.service = service;
        filter.setPlaceholder("Filter by lastname...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> update());
        grid.setColumns("id", "firstname", "lastname", "pesel", "phoneNumber", "birthDate");
        grid.setItems(service.getAllPatients());
        back.addClickListener (e -> getUI().ifPresent(ui -> ui.navigate("")));
        header.getStyle().set("text-align", "center");
        header.getStyle().set("font-size", "30px");
        header.getStyle().set("font-weight", "bold");
        header.setWidthFull();
        HorizontalLayout toolbar = new HorizontalLayout(back, filter);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(
                event -> form.setPatient(grid.asSingleSelect().getValue()));
        add(header, toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void update() {
        grid.setItems(service.getPatientsByLastname(filter.getValue()));
    }

    public void refresh() {
        grid.setItems(service.getAllPatients());
    }
}
