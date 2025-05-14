package com.rest.vaadin_private_medical_clinic.form;

import com.rest.vaadin_private_medical_clinic.domain.User;
import com.rest.vaadin_private_medical_clinic.service.UserService;
import com.rest.vaadin_private_medical_clinic.view.UserView;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;

public class UserForm extends FormLayout {

    private UserService userService;
    private UserView userView;
    private Button updateButton = new Button("Update");
    private Button cancelButton = new Button("Cancel");
    private Button resetPasswordButton = new Button("Reset Password");
    private Button blockButton = new Button();
    private TextField username = new TextField("username");
    private TextField mail = new TextField("mail");
    private PasswordField passwordField = new   PasswordField("New Password");
    private PasswordField confirmPasswordField = new PasswordField("Confirm New Password");
    private Binder<User> binder = new Binder<>(User.class);

    public UserForm(UserView userView, UserService userService) {
        this.userService = userService;
        HorizontalLayout buttons = new HorizontalLayout(updateButton, blockButton, cancelButton);
        updateButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        blockButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        buttons.setSpacing(true);
        resetPasswordButton.addThemeVariants(ButtonVariant.LUMO_ERROR);
        VerticalLayout passwordSection = new VerticalLayout(passwordField, confirmPasswordField, resetPasswordButton);
        passwordSection.setSpacing(false);
        passwordSection.setPadding(false);

        add(username, mail, buttons, passwordSection);
        binder.bindInstanceFields(this);
        this.userView = userView;
        updateButton.addClickListener(e -> update());
        cancelButton.addClickListener(e -> cancel());
        resetPasswordButton.addClickListener(e -> resetPassword());
        blockButton.addClickListener(e -> toggleBlock());
    }

    public void update() {
        User user = binder.getBean();
        userService.updateUser(user);
        userView.refresh();
        setUser(null);
    }

    public void cancel() {
        userView.showGrid();
    }

    public void setUser(User user) {
        binder.setBean(user);

        if (user == null) {
            setVisible(false);
        } else {
            setVisible(true);
            username.focus();
            blockButton.setText(user.isBlocked() ? "Unblock User" : "Block User");
        }
    }

    private void resetPassword() {
        String newPassword = passwordField.getValue();
        String confirmPassword = confirmPasswordField.getValue();

        if (newPassword.equals(confirmPassword)) {
            userService.resetPassword(binder.getBean().getId(), newPassword);
            passwordField.clear();
            confirmPasswordField.clear();
            userView.refresh();

            Notification success = Notification
                    .show("Password reset successfully", 3000, Notification.Position.MIDDLE);
            success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

        } else {
            Notification notification = Notification.show("Passwords do not match", 3000, Notification.Position.MIDDLE);
            notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void toggleBlock() {
        User user = binder.getBean();
        if (user == null) return;

        if (user.isBlocked()) {
            userService.unblockUser(user.getId());
            Notification.show("User unblocked", 3000, Notification.Position.MIDDLE);
        } else {
            userService.blockUser(user.getId());
            Notification.show("User blocked", 3000, Notification.Position.MIDDLE);
        }

        userView.refresh();
        setUser(null);
    }
}
