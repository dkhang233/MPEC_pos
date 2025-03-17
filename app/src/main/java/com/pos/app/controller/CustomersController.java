package com.pos.app.controller;

import com.pos.app.model.Customer;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class CustomersController {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Number> idCol;
    @FXML
    private TableColumn<Customer, String> firstNameCol, lastNameCol, emailCol, phoneCol, updateCol;

    @FXML
    private TextField firstNameField, lastNameField, emailField, phoneField;

    @FXML
    private Button addCustomerBtn, deleteCustomerBtn, updateCustomerBtn;

    private final ObservableList<Customer> customersList = FXCollections.observableArrayList();

    private int nextId = 3; // ID tự tăng

    @FXML
    public void initialize() {
        System.out.println("Controller initialized!");
        setupCustomersTable();
        loadCustomers();
    }

    private void setupCustomersTable() {
        idCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()));
        firstNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));
        lastNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));
        emailCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNumber()));

        // Thêm nút "Update" vào từng hàng
        updateCol.setCellFactory(col -> new TableCell<>() {
            final Button updateButton = new Button("Edit");

            {
                updateButton.setOnAction(event -> {
                    Customer customer = getTableView().getItems().get(getIndex());
                    firstNameField.setText(customer.getFirstName());
                    lastNameField.setText(customer.getLastName());
                    emailField.setText(customer.getEmail());
                    phoneField.setText(customer.getPhoneNumber());
                    updateCustomerBtn.setUserData(customer); // Lưu khách hàng cần cập nhật
                });
            }

            @Override
            protected void updateItem(String cell, boolean empty) {
                super.updateItem(cell, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(updateButton);
                }
            }
        });

        customersTable.setItems(customersList);
        customersTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void loadCustomers() {
        customersList.addAll(
                new Customer(1, "Nguyen", "Huy", "huy@gmail.com", "0123456789"),
                new Customer(2, "Danh", "Khang", "khang@gmail.com", "0987654321")
        );
    }

    @FXML
    private void addCustomer() {
        if (validateInput()) {
            Customer newCustomer = new Customer(
                    nextId++,
                    firstNameField.getText(),
                    lastNameField.getText(),
                    emailField.getText(),
                    phoneField.getText()
            );
            customersList.add(newCustomer);
            clearFields();
        }
    }

    @FXML
    private void deleteCustomer() {
        List<Customer> selectedCustomers = List.copyOf(customersTable.getSelectionModel().getSelectedItems());
        customersList.removeAll(selectedCustomers);
    }

    @FXML
    private void updateCustomer() {
        Customer customer = (Customer) updateCustomerBtn.getUserData();
        if (customer != null && validateInput()) {
            customer.setFirstName(firstNameField.getText());
            customer.setLastName(lastNameField.getText());
            customer.setEmail(emailField.getText());
            customer.setPhoneNumber(phoneField.getText());
            customersTable.refresh();
            clearFields();
        }
    }

    private boolean validateInput() {
        return !firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()
                && !emailField.getText().isEmpty() && !phoneField.getText().isEmpty();
    }

    private void clearFields() {
        firstNameField.clear();
        lastNameField.clear();
        emailField.clear();
        phoneField.clear();
        updateCustomerBtn.setUserData(null);
    }
}
