package com.rest.vaadin_private_medical_clinic.view;

import com.rest.vaadin_private_medical_clinic.domain.User;
import com.rest.vaadin_private_medical_clinic.enums.UserRole;
import com.rest.vaadin_private_medical_clinic.form.UserForm;
import com.rest.vaadin_private_medical_clinic.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.List;

@Route("users")
public class UserView extends VerticalLayout {

    private UserService userService;
    private Grid<User> grid = new Grid<>(User.class);
    private Button back = new Button("← Back");
    private TextField filter = new TextField();
    private Span header = new Span("Users");
    private UserForm form;
    private ComboBox<UserRole> roleFilter = new ComboBox<>();

    public UserView(UserService userService) {
        this.userService = userService;
        this.form = new UserForm(this, this.userService);
        form.setUser(null);
        filter.setPlaceholder("Filter by username...");
        filter.setClearButtonVisible(true);
        filter.setValueChangeMode(ValueChangeMode.EAGER);
        filter.addValueChangeListener(event -> update());
        roleFilter.setItems(UserRole.values());
        roleFilter.setPlaceholder("Filter by role...");
        roleFilter.setClearButtonVisible(true);
        roleFilter.addValueChangeListener(event -> update());
        grid.setColumns("id", "username", "mail", "userRole", "blocked");
        grid.setItems(userService.getAllUsers(null));
        back.addClickListener (e -> getUI().ifPresent(ui -> ui.navigate("")));
        header.getStyle().set("text-align", "center");
        header.getStyle().set("font-size", "30px");
        header.getStyle().set("font-weight", "bold");
        header.setWidthFull();
        HorizontalLayout toolbar = new HorizontalLayout(back, filter, roleFilter);
        HorizontalLayout mainContent = new HorizontalLayout(grid, form);
        mainContent.setSizeFull();
        grid.setSizeFull();
        grid.asSingleSelect().addValueChangeListener(
                event -> form.setUser(grid.asSingleSelect().getValue()));
        add(header, toolbar, mainContent);
        setSizeFull();
        refresh();
    }

    public void update() {
        String usernameFilter = filter.getValue() != null ? filter.getValue().toLowerCase() : "";
        UserRole selectedRole = roleFilter.getValue();

        List<User> filteredUsers = userService.getAllUsers(selectedRole).stream()
                .filter(user -> user.getUsername() != null && user.getUsername().toLowerCase().contains(usernameFilter))
                .toList();

        grid.setItems(filteredUsers);
    }

    public void refresh() {
        grid.setItems(userService.getAllUsers(null));
    }

    public void showGrid() {
        form.setVisible(false);
        grid.setVisible(true);
        refresh();
    }
}
