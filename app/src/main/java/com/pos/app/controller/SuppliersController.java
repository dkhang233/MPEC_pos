package com.pos.app.controller;

import com.pos.app.model.Customer;
import com.pos.app.model.Supplier;
import com.pos.app.store.ItemStore;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class SuppliersController {
    @FXML
    private TableView<Supplier> customersTable;

    @FXML
    private TableColumn<Supplier, Number> idCol;
    @FXML
    private TableColumn<Supplier, String> companyNameCol, phoneNumberCol, addressCol, updateCol;

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
        companyNameCol.setCellValueFactory(new PropertyValueFactory<>("companyName"));
        phoneNumberCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

        // Thêm cột update với nút edit
        updateCol.setCellFactory(col -> {
            final Button editButton = new Button("Cập nhật");
            TableCell<Supplier, String> cell = new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                        editButton.setOnAction(event -> {
                            Supplier supplier = getTableView().getItems().get(getIndex());
                            showEditDialog(supplier);
                        });
                    }
                }
            };
            return cell;
        });

        customersTable.setItems(ItemStore.suppliers.get());
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
        Supplier selected = customersTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            ItemStore.suppliers.get().remove(selected);
            customersTable.getSelectionModel().clearSelection(); // Xóa lựa chọn sau khi xóa
        } else {
            showAlert("Không có lựa chọn", "Hãy chọn một khách hàng để xóa");
        }
    }

    @FXML
    private void updateCustomer() {
        // Xử lý trong showEditDialog
    }

    // Hiển thị dialog để thêm khách hàng
    private void showAddDialog() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Thêm nhà cung cấp mới");
        dialog.setHeaderText("Nhập thông tin nhà cung cấp");

        ButtonType okButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField();
        TextField lastName = new TextField();
        TextField email = new TextField();

        grid.add(new Label("Tên công ty:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Số điện thoại:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Địa chỉ:"), 0, 2);
        grid.add(email, 1, 2);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton && validateInput(firstName.getText(), lastName.getText(), email.getText())) {
                Supplier newCustomer = new Supplier();
                newCustomer.setId(nextId++);
                newCustomer.setCompanyName(firstName.getText());
                newCustomer.setPhoneNumber(lastName.getText());
                newCustomer.setAddress(email.getText());
                ItemStore.suppliers.add(newCustomer); // Thêm khách hàng mới vào danh sách
                return new Pair<>(firstName.getText(), lastName.getText());
            }
            return null;
        });

        dialog.showAndWait();
    }

    // Hiển thị dialog để chỉnh khách hàng
    private void showEditDialog(Supplier supplier) {
        if (supplier == null) {
            showAlert("Không có lựa chọn", "Hãy chọn một khách hàng để sửa !");
            return;
        }

        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Cập nhật thông tin khách hàng");
        dialog.setHeaderText("Sửa thông tin khách hàng");

        ButtonType okButton = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField firstName = new TextField(supplier.getCompanyName());
        TextField lastName = new TextField(supplier.getPhoneNumber());
        TextField email = new TextField(supplier.getAddress());
//        TextField phone = new TextField(customer.getPhoneNumber());

        grid.add(new Label("Họ:"), 0, 0);
        grid.add(firstName, 1, 0);
        grid.add(new Label("Tên:"), 0, 1);
        grid.add(lastName, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(email, 1, 2);
        grid.add(new Label("Số điện thoại:"), 0, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == okButton && validateInput(firstName.getText(), lastName.getText(), email.getText())) {
                supplier.setCompanyName(firstName.getText());
                supplier.setPhoneNumber(lastName.getText());
                supplier.setAddress(email.getText());
                customersTable.refresh();
                return new Pair<>(firstName.getText(), lastName.getText());
            }
            return null;
        });

        dialog.showAndWait();
    }

    private boolean validateInput(String firstName, String lastName, String email) {
        return !firstName.isEmpty() && !lastName.isEmpty() && !email.isEmpty();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
