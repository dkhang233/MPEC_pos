package com.pos.app.controller;

import com.pos.app.model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class CustomersController {

    @FXML
    private TableView<Customer> customersTable;

    @FXML
    private TableColumn<Customer, Number> idCol;
    @FXML
    private TableColumn<Customer, String> firstNameCol, lastNameCol, emailCol, phoneCol, updateCol;

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
        // Sử dụng PropertyValueFactory cho các cột
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        // Thêm cột update với nút edit
        updateCol.setCellFactory(col -> {
            final Button editButton = new Button("Edit");
            TableCell<Customer, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                        editButton.setOnAction(event -> {
                            Customer customer = getTableView().getItems().get(getIndex());
                            showEditDialog(customer);
                        });
                    }
                }
            };
            return cell;
        });

        customersTable.setItems(customersList);
        customersTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Chỉ cho phép chọn 1 hàng
    }

    private void loadCustomers() {
        customersList.addAll(
                new Customer(1, "Nguyen", "Huy", "huy@gmail.com", "0123456789"),
                new Customer(2, "Danh", "Khang", "khang@gmail.com", "0987654321")
        );
    }

    @FXML
    private void addCustomer() {
        showAddDialog();
    }

    @FXML
    private void deleteCustomer() {
        Customer selected = customersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            customersList.remove(selected);
            customersTable.getSelectionModel().clearSelection(); // Xóa lựa chọn sau khi xóa
        } else {
            showAlert("No Selection", "Please select a customer to delete.");
        }
    }

    @FXML
    private void updateCustomer() {
        // Xử lý trong showEditDialog
    }

    // Hiển thị dialog để thêm khách hàng
    private void showAddDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Add New Customer");
        dialog.setHeaderText("Enter customer details");

        ButtonType okButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField email = new TextField();
        TextField phone = new TextField();

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(email, 1, 2);
        grid.add(new Label("Phone Number:"), 0, 3);
        grid.add(phone, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton && validateInput(firstName.getText(), lastName.getText(), email.getText(), phone.getText())) {
                Customer newCustomer = new Customer(
                        nextId++,
                        firstName.getText(),
                        lastName.getText(),
                        email.getText(),
                        phone.getText()
                );
                customersList.add(newCustomer);
                return new Pair<>(firstName.getText(), lastName.getText());
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Hiển thị dialog để chỉnh khách hàng
    private void showEditDialog(Customer customer) {
        if (customer == null) {
            showAlert("No Selection", "Please select a customer to edit.");
            return;
        }

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Update Customer");
        dialog.setHeaderText("Edit customer details");

        ButtonType okButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField(customer.getFirstName());
        TextField lastName = new TextField(customer.getLastName());
        TextField email = new TextField(customer.getEmail());
        TextField phone = new TextField(customer.getPhoneNumber());

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(email, 1, 2);
        grid.add(new Label("Phone Number:"), 0, 3);
        grid.add(phone, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton && validateInput(firstName.getText(), lastName.getText(), email.getText(), phone.getText())) {
                customer.setFirstName(firstName.getText());
                customer.setLastName(lastName.getText());
                customer.setEmail(email.getText());
                customer.setPhoneNumber(phone.getText());
                customersTable.refresh();
                return new Pair<>(firstName.getText(), lastName.getText());
            }
            return null;
        });

        dialog.showAndWait();
    }

    private boolean validateInput(String firstName, String lastName, String email, String phone) {
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !phone.isEmpty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}