package com.rest.vaadin_private_medical_clinic;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private Button userButton = new Button("User List");
    private Button doctorButton = new Button("Doctor List");
    private Button patientsButton = new Button("Patient list");
    private Span welcome = new Span("Welcome to the private medical clinic");

    public MainView() {
        welcome.getStyle().set("text-align", "center");
        welcome.getStyle().set("font-size", "30px");
        welcome.getStyle().set("font-weight", "bold");
        patientsButton.addClickListener(e ->
                getUI().ifPresent(ui -> ui.navigate("patients")));
        HorizontalLayout header = new HorizontalLayout(welcome);
        header.setWidthFull();
        header.setJustifyContentMode(JustifyContentMode.CENTER);
        HorizontalLayout toolbar = new HorizontalLayout(userButton, doctorButton, patientsButton);
        toolbar.setWidthFull();
        toolbar.setJustifyContentMode(JustifyContentMode.CENTER);

        add(header, toolbar);

    }
}
